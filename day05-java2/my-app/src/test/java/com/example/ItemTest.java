package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ItemTest {

    // 正常系1: 生成時の値が正しく保持されること
    @Test
    void 商品名と数量と価格を指定して生成できること() {
        Item item = new Item("りんご", 10, 120);

        assertEquals("りんご", item.name());
        assertEquals(10, item.quantity());
        assertEquals(120, item.price());
    }

    // 正常系2: add で新しい Item が返り、元の Item は変わらないこと
    @Test
    void addすると数量が増えた新しいItemが返ること() {
        Item original = new Item("ノート", 5, 200);

        Item updated = original.add(3);

        // 新しい Item の値を確認
        assertEquals("ノート", updated.name());
        assertEquals(8, updated.quantity());
        assertEquals(200, updated.price());

        // record の不変性：元のインスタンスは変わっていないこと
        assertEquals(5, original.quantity());
    }

    @Test
    void 不正な値で生成すると例外になること() {
        IllegalArgumentException ex1 = assertThrows(
                IllegalArgumentException.class,
                () -> new Item("", 1, 100)
        );
        assertEquals("商品名は空にできません", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> new Item("ペン", -1, 100)
        );
        assertEquals("数量は0以上にしてください", ex2.getMessage());

        IllegalArgumentException ex3 = assertThrows(
                IllegalArgumentException.class,
                () -> new Item("ペン", 1, -10)
        );
        assertEquals("価格は0以上にしてください", ex3.getMessage());
    }
    
    // 異常系2: add に負の数を渡すと IllegalArgumentException になること
    @Test
    void addに負の数を渡すと例外になること() {
        Item item = new Item("消しゴム", 10, 80);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> item.add(-1)
        );
        assertEquals("追加数は0以上にしてください", ex.getMessage());
    }
}