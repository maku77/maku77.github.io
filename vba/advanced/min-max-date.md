---
title: "指定した範囲のセルから最大／最小の日付を探す"
date: "2018-04-21"
---

下記のサンプルマクロは、A1:C3 の範囲のセルから、最大の日付、最小の日付を抽出します。

~~~ vb
Sub Test()
    Dim minDate As Date
    Dim maxDate As Date
    minDate = Application.WorksheetFunction.Min(Range("A1:C3"))
    maxDate = Application.WorksheetFunction.Max(Range("A1:C3"))
    MsgBox minDate & ", " & maxDate
End Sub
~~~

数値データが見つからない場合、Min 関数や Max 関数は 0 を返すので、次のようにエラー処理を記述することができます。

~~~ vb
Sub Test()
    Dim minDate As Date
    minDate = Application.WorksheetFunction.Min(Range("B2:C4"))
    If minDate = 0 Then
        MsgBox "日付情報が見つかりません。"
        End
    End If
    MsgBox minDate
End Sub
~~~

