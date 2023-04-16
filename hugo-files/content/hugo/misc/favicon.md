---
title: "favicon.ico をサイトのルートに置く (static)"
date: "2019-09-10"
---

Hugo によって生成した Web サイトのルートディレクトリに **`favicon.ico`** を配置するには、`static` ディレクトリに `favicon.ico` ファイルをそのまま置いておけば OK です。

```
website/
 +-- content/
 +-- layouts/
 +-- static/
      +-- favicon.ico
```

`static` ディレクトリ以下に置いたファイル群は、その階層構造を保ったまま出力ディレクトリへコピーされます。

favicon の詳細については下記を参考にしてください。

- [Web サイトに favicon を設定する](/web/html/favicon.html)

