---
title: "VBAメモ: 三項演算子 IIF を使用する"
url: "p/xrajes2/"
date: "2018-04-17"
tags: ["vba"]
aliases: /vba/syntax/iif.html
---

VBA の **`IIF`** 関数は C 言語や Java 言語の三項演算子 (`x ? a : b`) と同じように使用することができます。
次の例では、変数 `a` の値によって、表示するメッセージを切り替えています。

```vb
Dim a As Integer
a = 50

MsgBox IIF(a >= 10, "10以上だよ", "10より小さいよ")
```

次のように書いても同じ振る舞いをしますが、これくらいの分岐処理であれば、`IIF` 関数を使うことで一行ですっきり記述できます。

```vb
If a >= 10 Then
    MsgBox "10以上だよ"
Else
    MsgBox "10より小さいよ"
End If
```
