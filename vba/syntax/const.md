---
title: "定数を定義する (Const)"
date: "2018-04-17"
---

VB/VBA で定数を定義するには、`Const` キーワードを使用します。
下記の例では、グローバルな定数 `A` と、プロシージャ内の定数 `N` を定義しています。

~~~ vb
Private Const A As Integer = 100

Public Sub Hemumu()
    Const N As Integer = 200
    MsgBox A
    MsgBox N
End Sub
~~~~

