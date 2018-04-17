---
title: "Case 文による分岐処理"
date: "2018-04-17"
---

VB/VBA の Case 文は、C 言語や Java 言語の switch-case 処理のような条件分岐に使用します。

~~~ vb
Dim a As Integer
a = 2

Select Case a
    Case 0
        MsgBox "Zero!"
    Case 1
        MsgBox "One!"
    Case 2, 3, 4, 5
        MsgBox "Two, Three, Four, or Five!"
    Case Else
        MsgBox "What's this?"
End Select
~~~

VB/VBA の Case 文では次のような柔軟な条件指定を行うことができます。

~~~ vb
Select Case a
    Case Is < 10     'a < 10 のとき
        MsgBox "xxx"
    Case 10 To 20    '10 <= a <= 20 のとき
        MsgBox "yyy"
    Case 30, 40, 50  'a = 30 or 40 or 50 のとき
        MsgBox "zzz"
End Select
~~~

