package com.example;

public record Item(String name, int quantity, int price) {
    public Item {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("商品名は空にできません");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("数量は0以上にしてください");
        }
        if (price < 0) {
            throw new IllegalArgumentException("価格は0以上にしてください");
        }
    }

    public Item add(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("追加数は0以上にしてください");
        }
        return new Item(this.name, this.quantity + n, this.price);
    }
}