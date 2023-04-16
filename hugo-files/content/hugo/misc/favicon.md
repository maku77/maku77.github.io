---
title: "Hugo サイトの favicon.ico をサイトのルートに配置する (static)"
url: "p/vu7hr2b/"
date: "2019-09-10"
tags: ["Hugo"]
aliases: /hugo/misc/favicon.html
---

Hugo によって生成した Web サイトのルート階層に __`favicon.ico`__ を配置するには、`static` ディレクトリに `favicon.ico` ファイルをそのまま置いておけば OK です。

```
website/
 +-- content/
 +-- layouts/
 +-- static/
      +-- favicon.ico
```

`static` ディレクトリ以下に置いたファイル群は、その階層構造を保ったまま出力ディレクトリへコピーされます。

favicon の詳細については下記を参考にしてください。

- [Web サイトに favicon を設定する](/p/bdox8hr/)

