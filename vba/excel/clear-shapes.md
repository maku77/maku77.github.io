---
title: "Excel のオートシェイプ（図形）をすべて削除する"
date: "2018-04-27"
---

アクティブなシート内のオートシェイプ（図形）をすべて削除するには下記のようなマクロを使用します。

~~~ vb
Sub DeleteAllShapes()
    Dim sp As Shape

    For Each sp In ActiveSheet.Shapes
        If sp.Type = msoAutoShape Then
            sp.Delete
        End If
    Next
End Sub
~~~

`ActiveSheet.Shapes` で返される Shape オブジェクト群には、フォームオブジェクト (`msoFormControl`) も含まれているようなので、`Type` プロパティを見てオートシェイプ (`msoAutoShape`) であるかを確認してから削除を実行しています。

