---
title: "例外のテストを記述する"
date: "2014-10-16"
---

例外が正しく発生しているかをテストするには、下記のように、通ってはいけない場所に `fail()` を記述します。

```java
public void testXxx() {
    try {
        // XxxException を発生するメソッド呼び出し
        obj.doSomething();

        fail("XxxException が発生すべき");
    } catch (XxxException e) {
        // 通るべきパスを示す目印（必須ではない）
        assertTrue(true);
    }
}
```

正しく例外が発生すれば、`fail()` を飛び越えて catch ブロックに入るので、テスト成功と判断されるという仕組みです。

