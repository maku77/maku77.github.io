---
title: "Excel のセルの内容が変更されたときのイベントをハンドルする"
date: "2018-04-16"
---

Excel ワークシート内の任意のセル内容が変更された場合、Change イベントが発生します。

<kbd>Alt+F11</kbd> で VBA エディタを開き、下記のように入力すれば Change イベントをハンドルすることができます。

~~~ vb
Private Sub Worksheet_Change(ByVal target As Range)
    MsgBox target.Address & "の範囲のセルが変更されました。" & vbNewLine & _
            "セルごとの新しい値は次の通りです｡ "
    For Each r In target
        MsgBox r.Address & " = " & r.Value
    Next
End Sub
~~~

この例では、セルの内容が変更されたときにメッセージボックスを表示して、変更後のセルの内容を順番に表示します。


特定のセルが変更されたことを検出する
----

次のようにすれば、A1 セル（一番左上のセル）が変更された場合だけ処理を行うことができます。

~~~ vb
Private Sub Worksheet_Change(ByVal target As Range)
    If target.Address = "$A$1" Then
        MsgBox "$A$1 のセルが変更されました: " & target.Value
    End If
End Sub
~~~

変更されたセルの行番号は Range.Row プロパティ、列番号は Range.Column プロパティで参照することができるので、次のように書くこともできますね。

~~~ vb
Private Sub Worksheet_Change(ByVal target As Range)
    If target.Row = 1 And target.Column = 1 Then
        MsgBox "$A$1 のセルが変更されました: " & target.Value
    End If
End Sub
~~~

