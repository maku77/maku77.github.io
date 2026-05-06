---
title: "VBAメモ: Excel のオートシェイプ（図形）を操作する"
url: "p/5oepsfx/"
date: "2018-04-27"
tags: ["vba"]
aliases: /vba/excel/shape.html
---

オートシェイプ（図形）を追加する
----

### オートシェイプの種類、位置、サイズを指定して追加

下記の例では、矩形を座標 (100, 50) に、サイズ 200x50 で追加しています。
座標やサイズの単位は「ポイント」で指定します。

```vb
ActiveSheet.Shapes.AddShape msoShapeRectangle, 100, 50, 200, 50
```

オートシェイプの種類は下記に一覧があります。

- [MsoAutoShapeType 列挙](https://msdn.microsoft.com/ja-jp/VBA/Office-Shared-VBA/articles/msoautoshapetype-enumeration-office)


{{< code lang="vb" title="セルと同じサイズで矩形を追加する" >}}
' A1 セルに収まるサイズで矩形のオートシェイプを追加
' （見た目としては、セルの背景を塗りつぶしたような感じになる）
With ActiveSheet.Range("A1")
    ActiveSheet.Shapes.AddShape msoShapeRectangle, .Left, .Top, .Width, .Height
End With
{{< /code >}}

```vb
' A1:B2 の範囲のセルに収まるサイズでひし形のオートシェイプを追加
With ActiveSheet.Range("A1:B2")
    ActiveSheet.Shapes.AddShape msoShapeDiamond, .Left, .Top, .Width, .Height
End With
```

{{< code lang="vb" title="セルと同じ高さで横幅だけ指定して矩形を追加する" >}}
' B2 のセルに横幅 100 ポイントの矩形を左寄せで追加
With ActiveSheet.Range("B2")
    ActiveSheet.Shapes.AddShape msoShapeRectangle, .Left, .Top, 100, .Height
End With
{{< /code >}}


### 作成したオートシェイプ（`Shape` オブジェクト）の参照を取得する

`AddShape` 関数を Function として呼び出せば、作成された `Shape` オブジェクトの参照を戻り値として受け取ることができます。
`Shape` オブジェクトの参照を使って、オートシェイプの書式設定などを行うことができます。

```vb
Private Sub SetShapeStyle(ByRef sp As Shape)
    '枠線の設定
    sp.Line.Visible = msoFalse

    '塗りつぶしの設定
    With sp.Fill
        .Visible = msoTrue
        .ForeColor.RGB = RGB(255, 225, 139)
        .Transparency = 0
        .Solid
    End With
End Sub

Sub Main()
    Dim sp As Shape
    With ActiveSheet.Range("A1")
        Set sp = ActiveSheet.Shapes.AddShape(msoShapeRectangle, _
            .Left, .Top, .Width, .Height)
    End With
    SetShapeStyle sp
End Sub
```


枠線を設定する
----

{{< code lang="vb" title="枠あり／なしを切り替える" >}}
Selection.ShapeRange.Line.Visible = msoFalse
Selection.ShapeRange.Line.Visible = msoTrue
{{< /code >}}

{{< code lang="vb" title="枠線の色、透過度を指定する" >}}
Selection.ShapeRange.Line.ForeColor.RGB = RGB(255, 0, 0)
Selection.ShapeRange.Line.Transparency = 0
{{< /code >}}


図形の位置を変更する
----

{{< code lang="vb" title="絶対位置で指定（左上を 0, 0 として 100, 30 へ移動）" >}}
Selection.ShapeRange.Left = 100
Selection.ShapeRange.Top = 30
{{< /code >}}

{{< code lang="vb" title="移動量で指定（左へ50ポイント、下へ30ポイントだけ移動）" >}}
Selection.ShapeRange.IncrementLeft -50
Selection.ShapeRange.IncrementTop 30
{{< /code >}}


図形のサイズを変更する
----

{{< code lang="vb" title="ポイントでサイズ指定" >}}
Selection.ShapeRange.Width = 200
Selection.ShapeRange.Height = 50
{{< /code >}}

{{< code lang="vb" title="現在サイズからの倍率で指定（横方向、縦方向にそれぞれ1.5倍）" >}}
Selection.ShapeRange.ScaleWidth 1.5, msoFalse, msoScaleFromTopLeft
Selection.ShapeRange.ScaleHeight 1.5, msoFalse, msoScaleFromTopLeft
{{< /code >}}
