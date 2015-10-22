---
title: Windows で Ruby や Python のスクリプトを組み込みコマンドのように使用する
created: 2010-05-07
layout: windows
---

例えば、`hello.rb` を任意のディレクトリから `hello` と実行できるようにするには、PATH の通ったディレクトリに `hello.rb` と、以下のような `hello.cmd` を置いておけば OK です。

#### sample.cmd

```bat
@echo off
set script="%~dp0%sample.rb"
ruby %script% %*
```

Python の場合も同様です。

```bat
@echo off
set script="%~dp0%sample.py"
python %script% %*
```

