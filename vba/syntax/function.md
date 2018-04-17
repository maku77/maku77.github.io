---
title: "関数やプロシージャを定義する"
date: "2018-04-17"
description: "VB/VBA では、戻り値を持つ「関数」と、戻り値を持たない「プロシージャ」を呼び分けており、定義方法や呼び出し方も異なっています。"
---

関数を定義する (Function)
----

戻り値を持つ関数を定義するには、`Function` キーワードを使用します。
下記は、足し算を行う関数の定義例です。
戻り値は、関数名に代入するという特殊な方法なので注意してください（C や Java のような return キーワードは使わない）。

~~~ vb
Function AddTest(a As Integer, b As Integer) As Integer
    AddTest = a + b  'VB の場合、戻り値は関数名に代入する
End Function
~~~

この関数を使用するには、下記のようにします。

~~~ vb
Dim result as Integer
result = AddTest(100, 200)
MsgBox result
~~~

関数を呼び出すときは、**戻り値を必ず変数に代入する**必要があります。
戻り値が必要ない場合は、次のように `Call` キーワードを使って呼び出すか、パラメータを括弧で囲まずに列挙します。

~~~ vb
Call AddTest(100, 200)
AddTest 100, 200
~~~

関数を途中で抜けるときは、`Exit Function` を使用します（`Return` ではありません）。


プロシージャを定義する (Sub)
----

戻り値を持たないプロシージャを定義するには、`Function` キーワードの代わりに `Sub` キーワードを使用します。

~~~ vb
Sub Greet(name As String)
    MsgBox "Hello, " & name
End Sub
~~~

プロシージャを呼び出すときは、パラメータを括弧で囲まずに指定します。

~~~ vb
Greet "まく"
~~~

プロシージャを途中で抜けるときは、`Exit Sub` を使用します（`Return` ではありません）。

