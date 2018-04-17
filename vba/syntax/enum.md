---
title: "列挙型を定義する (Enum)"
date: "2018-04-17"
---

VB/VBA で列挙型を定義するには、Enum ステートメントを使用します。
Enum ステートメントで列挙した名前には 0 から順に整数値が割り当てられます。
次の例では、`MyEnum` という名前の列挙型を定義しています。

~~~ vb
Public Enum MyEnum
    zero   '0になる
    one    '1になる
    two    '2になる
    three  '3になる
End Enum
~~~

定義した `MyEnum` 型は次のように使用します。

~~~ vb
Public Sub MyEnumTest()
    Dim e As MyEnum
    e = MyEnum.one
    MsgBox e  '1と表示される
End Sub
~~~

Enum 型の各項目には、任意の値を代入することができます。
代入を省略した場合は、その前の項目の値に +1 した値が設定されます。

~~~ vb
Public Enum Fruits
    apple = 100
    banana = 200
    orange = 300
    grape
    kiwi
End Enum

Public Sub Test()
    MsgBox Fruits.apple   '100
    MsgBox Fruits.banana  '200
    MsgBox Fruits.orange  '300
    MsgBox Fruits.grape   '301
    MsgBox Fruits.kiwi    '302
End Sub
~~~

