---
title: "VBAメモ: VBA マクロの実行を強制終了する"
url: "p/mzjpnug/"
date: "2018-04-27"
tags: ["vba"]
aliases: /vba/other/abort-macro.html
---

次のようなマクロを実行してしまうと、無限ループで延々とメッセージボックスが表示されてしまいます。

```vb
Do
    MsgBox "こんにちは"
Loop
```

このような場合は、`Ctrl+Break` を押すことで、マクロの実行を強制的に停止することができます。
