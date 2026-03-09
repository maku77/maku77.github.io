---
title: "sed/awkメモ: sed でパターンに一致する行を抽出する"
url: "/p/gximd9d/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/extract-lines.html]
---

{{< code title="入力ファイル (input.txt)" >}}
001 This
002 is
003 a
004 Hello
005 World
{{< /code >}}

下記のようにすると、上記のファイルから、`Hello` という文字列が存在する行だけ抜き出して表示することができます。

```console
$ sed -n -e '/Hello/p'
004 Hello
```

これだけでは、`grep` コマンドと同じですが、開始パターンと終了パターンを両方指定すれば、あるパターンが含まれる行から、あるパターンが含まれる行までを抜き出すことができます。

{{< code lang="console" title="例: is が含まれる行から、Hello が含まれる行までを出力する。" >}}
$ sed -n -e '/is/,/Hello/p'
001 This
002 is
003 a
004 Hello
{{< /code >}}

