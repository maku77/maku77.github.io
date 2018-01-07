---
title: 複数のファイルを連結する
date: "2004-05-01"
---

a.txt と b.txt と c.txt を結合して、abc.txt というファイルを作成するには以下のようにします。

```bat
C:\> copy a.txt + b.txt + c.txt abc.txt
```

連結するファイルの指定には、ワイルドカードを使用することもできます。

```bat
C:\> copy *.sep abc.txt
```

バイナリファイルを連結する場合は、`/B` オプションを付けてください。

```bat
C:\> copy 1.bin + 2.bin + 3.bin app.bin /B
```

`copy` コマンドの詳しい説明は `copy /?` で確認できます。

