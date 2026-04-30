---
title: "Windowsメモ: copy コマンドで複数のファイルを連結する"
url: "p/w7erbwg/"
date: "2004-05-01"
tags: ["windows"]
aliases: /windows/file/concat-file.html
---

a.txt と b.txt と c.txt を結合して、abc.txt というファイルを作成するには以下のようにします。

```
C:\> copy a.txt + b.txt + c.txt abc.txt
```

連結するファイルの指定には、ワイルドカードを使用することもできます。

```
C:\> copy *.sep abc.txt
```

バイナリファイルを連結する場合は、`/B` オプションを付けてください。

```
C:\> copy 1.bin + 2.bin + 3.bin app.bin /B
```

`copy` コマンドの詳しい説明は `copy /?` で確認できます。
