---
title: "Windowsメモ: DIR コマンドでファイルやディレクトリを OR 検索する"
url: "p/gpuitde/"
date: "2008-11-30"
tags: ["windows"]
aliases: /windows/or-dir.html
---

`for` コマンドと組み合わせて `dir` コマンドを実行すると、複数のパターンでファイル検索を実行することができます。

以下の例では、C ドライブ以下にある aaa, bbb, ccc というファイルをすべて検索しています。

```bat
for %x in (aaa,bbb,ccc) do dir /s C:\*%x*
```

`for` コマンドによって、下記のコマンドが順番に実行されていることになります。

```bat
dir /s C:\*aaa*
dir /s C:\*bbb*
dir /s C:\*ccc*
```

{{% note %}}
これは、ドラマ『ブラッディ・マンデイ』の中で、主人公のハッカー、ファルコン（三浦春馬）が使っていたテクニックです。
{{% /note %}}
