---
title: GDBus サーバから signal を発行する
date: "2012-06-25"
---

GDBus のサーバ実装側からシグナルを発行するには、`gdbus-codegen` で生成されたヘッダファイルに定義されている、`xxx_emit_xxx` という関数を呼び出します。

あるメソッドの実装の中からシグナルを発行するには、そのメソッドの `xxx_complete_xxx` 関数を呼び出す前に、`xxx_emit_xxx` 関数を呼び出す必要があります。

```cpp
// Implementation of "Add" method.
gboolean handleAdd(ComExampleMyAppCalc *object, GDBusMethodInvocation *invocation,
        gint val1, gint val2) {
    gint ret = val1 + val2;

    // ここでシグナル発行
    com_example_my_app_calc_emit_hoge_hoge(object, "data1", "data2");

    com_example_my_app_calc_complete_add(object, invocation, ret);
    return TRUE;
}
```

