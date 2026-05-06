---
title: "VBAメモ: 名前のついたセルを起点にして空のセルまでループ処理する"
url: "p/7b5votb/"
date: "2018-04-27"
tags: ["vba"]
aliases: /vba/advanced/offset-loop.html
---

Offset プロパティによるセルの参照
----

Range オブジェクトの Offset プロパティを使用すると、その Range オブジェクトが示すセルからのオフセット値を指定してセルを参照することができます。

{{< code lang="vb" title="例: A1 セルから下に１、右に３移動したセルを参照（= D2）" >}}
MsgBox Range("A1").Offset(1, 3)
{{< /code >}}

縦横どちらかのオフセットだけを指定する場合は、下記のように名前付きパラメータを使用するとわかりやすいです。

{{< code lang="vb" title="例: A1 セルの１つ下のセルを参照" >}}
MsgBox Range("A1").Offset(RowOffset := 1)
{{< /code >}}


名前付きセルを起点にしたループ処理
----

{{< image src="img-001.png" >}}

ここでは、上記のように、"StartCell" という名前のついた B2 セルから、下方向にデータのある限りループ処理を行う例を示します。
Range オブジェクトの Offset プロパティを利用して、1 つずつ参照するセルをずらしていくという方法を使用します。

{{< code lang="vb" title="StartCell という名前のセルから空のセルに到達するまで下方向へ１セルずつ処理" >}}
Sub Test()
    Dim startCell As Range
    Set startCell = Range("StartCell")
    Dim i As Long
    Dim r As Range

    Do
        Set r = startCell.Offset(RowOffset := i)
        If r = "" Then
            Exit Do
        End If
        Debug.Print r.Address & " = " & r
        i = i + 1
    Loop
End Sub
{{< /code >}}

{{< code title="実行結果（イミディエイトウィンドウ）" >}}
$B$2 = あああああ
$B$3 = いいいいい
$B$4 = うううううう
{{< /code >}}
