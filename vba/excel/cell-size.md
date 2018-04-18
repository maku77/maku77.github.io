---
title: "Excel のセルのサイズを変更する"
date: "2018-04-17"
---

Excel VBA マクロで、セルの幅や高さを変更するには、Range オブジェクトの `ColumnWidth` プロパティ、`RowHeight` プロパティを使用します。

~~~ vb
Public Sub Test()
    Range("A1:F1").ColumnWidth = 10  'セルの幅の変更
    Range("A1:A6").RowHeight = 30    'セルの高さの変更
End Sub
~~~

