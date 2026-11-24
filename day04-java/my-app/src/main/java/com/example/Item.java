package com.example;

public class Item {
    private final String name;
    private int quantity;
    public Item(String name, int quantity) {
         if (quantity < 0) {
        // ここで例外を投げる（呼び出し元へエラーを知らせる）
        throw new IllegalArgumentException("追加数は0以上にしてください");
        }
        this.name = name;
        this.quantity = quantity;
    }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void add(int n) { 
        if (n < 0) {
            throw new IllegalArgumentException("追加数は0以上にしてください");
        }
        quantity += n; }
}   