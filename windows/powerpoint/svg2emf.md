---
title: SVG ファイルを EMF ファイルに変換して PowerPoint に貼り付ける
date: "2016-08-25"
---

SVG ファイルは PowerPoint のスライドに直接貼り付けることができませんが、EMF ファイルに変換すると貼り付けられるようになります。
EMF ファイルもベクター形式の画像なので、スライドを拡大しても綺麗に表示されますし、PowerPoint のファイルサイズも通常小さくなります。

Inkscape で SVG を EMF に変換する
----

ここでは、ベクターグラフィックスを扱うフリーソフトの **Inkscape** を使用します。

- [Download Inkscape](https://inkscape.org/ja/download/windows/)

アーカイブを展開すると、`inkscape.exe` という実行ファイルが含まれていますので、下記のようにして SVG ファイルを EMF ファイルに変換することができます。

```
C:\inkscape> inkscape input.svg -M output.emf
```

あとは、できあがった EMF ファイルを PowerPoint のスライドにドラッグ＆ドロップで貼り付ければ OK です。


おまけ: svg2emf コマンドを作成する
----

下記のようなバッチファイルを作成して、PATH の通ったところに置いておけば、どのディレクトリからでも SVG ファイルを EMF ファイルに変換できるようになるので便利です。

#### svg2emf.cmd

```cmd
@echo off
set inkscape="C:\app\inkscape\inkscape.exe"
%inkscape% %1 -M %~n1.emf
```

下記のように実行すると、`sample.emf` ファイルがカレントディレクトリに作成されます。

#### 使い方

```
C:\> svg2emf sample.svg
```

`svg2emf.cmd` のアイコンに、SVG ファイルをドラッグ＆ドロップしても OK です。

