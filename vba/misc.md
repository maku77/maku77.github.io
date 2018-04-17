---
title: "VBA マクロの雑多メモ"
date: "2018-04-17"
---


変数の型名を調べる (TypeName)
----

~~~ vb
MsgBox TypeName(Range("A1").Value)
~~~


日付リテラル
----

VBA マクロの中で、日付リテラルは `#` で囲んで表現します。
VB エディタの中で、

~~~ vb
Dim d As Date
d = #2018-04-01#
~~~

のように、日付を `#` で囲んで入力すると、VB エディタが自動的に正しいリテラル形式に変換してくれます。

~~~ vb
Dim d As Date
d = #4/1/2018#
~~~


Date オブジェクトから年月日、時分秒、曜日などの情報を取得する
----

Date オブジェクトから、年月日などの値を取り出すには、`Year` 関数などに Date オブジェクトを渡します。

~~~ vb
Sub Test()
    Dim d As Date
    d = #10/25/2018 11:30:55 PM#

    Debug.Print "Year:" & Year(d) & vbCrLf _
      & "Month:" & Month(d) & vbCrLf _
      & "Day:" & Day(d) & vbCrLf _
      & "Hour:" & Hour(d) & vbCrLf _
      & "Minute:" & Minute(d) & vbCrLf _
      & "Second:" & Second(d) & vbCrLf _
      & "Weekday:" & Weekday(d)
End Sub
~~~

#### 実行結果

~~~
Year:2018
Month:10
Day:25
Hour:23
Minute:30
Second:55
Weekday:5
~~~

ちなみに、曜日は下記のような定数値として定義されています。

| 定数名 | 数値 | 説明 |
| ---- | ---- | ---- |
| vbSunday | 1 | 日曜日 |
| vbMonday | 2 | 月曜日 |
| vbTuesday | 3 | 火曜日 |
| vbWednesday | 4 | 水曜日 |
| vbThursday | 5 | 木曜日 |
| vbFriday | 6 | 金曜日 |
| vbSaturday | 7 | 土曜日 |


日付の演算（Date オブジェクトの演算）
----

Date オブジェクトに対して、`+` 演算子や `-` 演算子を使って足し引きを行うと、日数ベースでの足し算、引き算になります。
次の例では、「2018年5月30日」の日付を格納した Date オブジェクトの値を 1 日ずつ進めています。

~~~ vb
Sub Test()
    Dim d As Date
    d = #5/30/2018#

    Debug.Print d  '2018-05-30
    d = d + 1
    Debug.Print d  '2018-05-31
    d = d + 1
    Debug.Print d  '2018-06-01
End Sub
~~~

この仕組みを利用すれば、指定した日付範囲をループ処理することができます。

次の例では、2018年1月1日から2018年1月5日までをループ処理しています。

~~~ vb
Sub Test()
    Const END_DATE As Date = #1/5/2018#
    Dim d As Date
    d = #1/1/2018#

    Do While d <= END_DATE
        Debug.Print d
        d = d + 1
    Loop
End Sub
~~~

#### 実行結果

~~~
2018-01-01
2018-01-02
2018-01-03
2018-01-04
2018-01-05
~~~


