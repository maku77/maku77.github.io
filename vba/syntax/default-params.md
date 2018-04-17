---
title: "デフォルト引数や可変長引数を持つ関数を定義する"
date: "2018-04-17"
---

省略可能な引数を定義する (Optional)
----

VB/VBA には C++ のような関数のオーバーロードの仕組みはありませんが、パラメータの前に `Optional` キーワードを付けることにより、省略可能なパラメータを定義することができます。
`Optional` パラメータ以降のパラメータは、すべて `Optional` パラメータとして定義する必要があります。
`Optional` パラメータには、デフォルト値 (`= 値`) を設定しておくこともできます。

下記は、2つのオプショナルパラメータを持つプロシージャの定義例です。

~~~ vb
Public Sub Test(Optional opt1 As Integer = 1, Optional opt2 As Integer = 2)
    '...
End Sub
~~~

### 引数が指定されたか調べる（IsMissing を使用する方法）

オプショナルパラメータを持つプロシージャ（あるいは関数）が呼び出されたときに、実際にパラメータを指定されたかを調べるには `IsMissing` キーワードを使用します。

~~~ vb
Public Sub test(Optional opt As Variant)
    If IsMissing(opt) Then
        MsgBox "引数が指定されました"
    End If

    If Not IsMissing(opt) Then
        MsgBox "引数が指定されていません"
    End If
End Sub
~~~

### 引数が指定されたか調べる（デフォルト値を使用する方法）

デフォルト値が採用された場合に、パラメータが指定されなかったと判断する、という方法もあります。

~~~ vb
Public Sub test(Optional opt As Integer = -1)
    If opt = -1 Then
        MsgBox "引数が指定されていません"
    End If
End Sub
~~~

ただし、この方法は、デフォルト値と同じ値がパラメータとして渡された場合に区別できないことに注意してください。


可変長引数を定義する (ParamArray)
----

関数の呼び出し時に渡すパラメータの数があらかじめ決められない場合は、`ParamArray` キーワードを使用して可変長パラメータを定義することができます。
**`ParamArray` 指定されたパラメータは `Variant` 型の配列でなければいけない**という制約があることに注意してください。

下記の `IntSum` 関数は、任意の数の数値パラメータを受け取り、合計値を返します。

~~~ vb
Public Function IntSum(ParamArray nums() As Variant) As Integer
    Dim x As Variant, sum As Integer
    For Each x In nums
        If IsNumeric(x) Then
            sum = sum + x
        End If
    Next
    IntSum = sum
End Function
~~~

関数内部で `IsNumeric` を使用して、渡されたパラメータが数値として扱える場合だけ合計値に足し込んでいます。
`IsNumeric` は、`"123"` のような数値とみなせる文字列を渡された場合も、真となることに注意してください。

この関数には、次のように任意の数のパラメータを渡すことができます。

~~~ vb
MsgBox IntSum(1, 2, 3, 4, 5)  '=> 15
MsgBox IntSum(10, "20", 30, "40xxx", "xxx")  '=> 60
~~~

### パラメータが渡されたかどうかを調べるイディオム

可変長パラメータ (`ParamArray`) として定義されたパラメータが、関数の呼出し時に省略されたかどうかは、次のように `UBound` によるイディオムで調べることができます。
パラメータが省略された場合、`UBound` の値が -1 になることを利用しています。

~~~ vb
Public Function IntSum(ParamArray nums() As Variant) As Integer
    If UBound(nums) < 0 Then
        MsgBox "1つ以上引数を指定してください！"
        Exit Function
    End If

    '...
End Function
~~~

とはいえ、可変長パラメータを `For Each` 構文でループ処理するのであれば、上記のようなパラメータ数のチェックは必要ないことがほとんどです。

