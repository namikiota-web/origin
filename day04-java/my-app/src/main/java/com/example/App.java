package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        List<Item> inventory = new ArrayList<>();

        // Scanner も try-with-resources で自動で閉じるようにすると安全です
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {  
                System.out.print("コマンドを入力 (add / list / search / quit): ");
                String command = scanner.nextLine().trim();

                switch (command) {
                    case "add" -> handleAdd(inventory, scanner);
                    case "list" -> handleList(inventory);
                    case "search" -> handleSearch(inventory, scanner);
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

        // ★ 呼び出し側で例外をキャッチするポイント
        try {
            int qty = Integer.parseInt(qtyInput); // 数字以外なら NumberFormatException

            Item existing = findByName(inventory, name);
            if (existing != null) {
                // 既存の Item に追加（負の数なら Item 側で IllegalArgumentException が発生）
                existing.add(qty);
            } else {
                // 新しい Item を作成（空の名前や負の数量なら IllegalArgumentException が発生）
                inventory.add(new Item(name, qty));
            }
            System.out.println("登録・追加しました。");

        } catch (NumberFormatException e) {
            System.out.println("エラー: 数量には整数を入力してください。");
        } catch (IllegalArgumentException e) {
            // Item クラス側で throw したメッセージを表示できる
            System.out.println("エラー: " + e.getMessage());
        }
    }

    private static void handleList(List<Item> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("在庫はありません。");
            return;
        }
        for (Item item : inventory) {
            System.out.println("- " + item.getName() + ": " + item.getQuantity());
        }
    }

    private static void handleSearch(List<Item> inventory, Scanner scanner) {
        System.out.print("検索キーワード: ");
        String keyword = scanner.nextLine().trim();

        boolean foundAny = false;
        for (Item item : inventory) {
            if (item.getName().contains(keyword)) {
                System.out.println("- " + item.getName() + ": " + item.getQuantity());
                foundAny = true;
            }
        }
        if (!foundAny) {
            System.out.println("該当するアイテムが見つかりません。");
        }
    }

    private static Item findByName(List<Item> inventory, String name) {
        for (Item item : inventory) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}