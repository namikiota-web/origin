package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        List<Item> inventory = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("コマンドを入力 (add / list / search / total / top3 / find / quit): ");
                String command = scanner.nextLine().trim();

				switch (command) {
					case "add" -> handleAdd(inventory, scanner);
					case "list" -> handleList(inventory);
					case "search" -> handleSearch(inventory, scanner);
					case "total" -> handleTotal(inventory);
					case "top3" -> handleTop3(inventory);
					case "find" -> handleFind(inventory, scanner);
					case "quit" -> {
					    System.out.println("終了します。");
					    return;
					}
                    default -> System.out.println("不明なコマンドです: " + command);
                }
            }
        }
    }

    private static void handleAdd(List<Item> inventory, Scanner scanner) {
        System.out.print("商品名: ");
        String name = scanner.nextLine().trim();

        System.out.print("数量: ");
        String qtyInput = scanner.nextLine().trim();

		System.out.print("金額: ");
        String priceInput = scanner.nextLine().trim();
        
        try {
            int qty = Integer.parseInt(qtyInput);
            int price = Integer.parseInt(priceInput);

            int index = indexOfByName(inventory, name);
            if (index >= 0) {
                // 既存の Item から「新しい Item」を作り、リストの同じ位置にセットして置き換える
                Item existing = inventory.get(index);
                Item updated = existing.add(qty);
                inventory.set(index, updated);
            } else {
                // 新規登録はそのままリストに追加
                inventory.add(new Item(name, qty, price));
            }
            System.out.println("登録・追加しました。");

        } catch (NumberFormatException e) {
            System.out.println("エラー: 数量には整数を入力してください。");
        } catch (IllegalArgumentException e) {
            System.out.println("エラー: " + e.getMessage());
        }
    }

    private static void handleList(List<Item> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("在庫はありません。");
            return;
        }
        for (Item item : inventory) {
            // record では getName() / getQuantity() ではなく name() / quantity() を呼ぶ
            System.out.println("- " + item.name() + ": " + item.quantity());
        }
    }

    private static void handleSearch(List<Item> inventory, Scanner scanner) {
        System.out.print("検索キーワード: ");
        String keyword = scanner.nextLine().trim();

        boolean foundAny = false;
        for (Item item : inventory) {
            if (item.name().contains(keyword)) {
                System.out.println("- " + item.name() + ": " + item.quantity());
                foundAny = true;
            }
        }
        if (!foundAny) {
            System.out.println("該当するアイテムが見つかりません。");
        }
    }

	// Stream で合計金額を求める（単価 × 数量 の合計）
	private static long calculateTotalAmount(List<Item> inventory) {
	    return inventory.stream()
	            .mapToLong(item -> (long) item.price() * item.quantity())
	            .sum();
	}
	
	private static void handleTotal(List<Item> inventory) {
	    long total = calculateTotalAmount(inventory);
	    System.out.println("合計金額: " + total + "円");
	}
	
	// Stream で価格上位3件を求める（単価が高い順）
	private static List<Item> getTop3ByPrice(List<Item> inventory) {
	    return inventory.stream()
	            .sorted(Comparator.comparingInt(Item::price).reversed())
	            .limit(3)
	            .toList();
	}
	
	private static void handleTop3(List<Item> inventory) {
	    List<Item> top3 = getTop3ByPrice(inventory);
	    if (top3.isEmpty()) {
	        System.out.println("在庫がありません。");
	        return;
	    }
	    System.out.println("--- 価格上位3件 ---");
	    for (int i = 0; i < top3.size(); i++) {
	        Item item = top3.get(i);
	        System.out.println((i + 1) + ". " + item.name()
	                + " / 単価: " + item.price() + "円 / 数量: " + item.quantity());
	    }
	}
	
	// Optional で名前検索する（完全一致）	
	private static void handleFind(List<Item> inventory, Scanner scanner) {
	    System.out.print("検索する商品名: ");
	    String name = scanner.nextLine().trim();
	
	    findByNameOptional(inventory, name).ifPresentOrElse(
	            item -> System.out.println("見つかりました -> " + item.name()
	                    + " / 単価: " + item.price() + "円 / 数量: " + item.quantity()),
	            () -> System.out.println("「" + name + "」は見つかりませんでした。")
	    );
	}
    
	private static Optional<Item> findByNameOptional(List<Item> inventory, String name) {
	    return inventory.stream()
	            .filter(item -> item.name().equals(name))
	            .findFirst();
	}

    // 一致する商品名のリスト内インデックスを返す（なければ -1）
    private static int indexOfByName(List<Item> inventory, String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}