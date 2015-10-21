---
title: Windows で Ruby や Python のスクリプトを組み込みコマンドのように使用する
created: 2010-05-07
---

例えば、`hello.rb` を任意のディレクトリから `hello` と実行できるようにするには、PATH の通ったディレクトリに `hello.rb` と、以下のような `hello.cmd` を置いておけば OK です。

#### sample.cmd

```bat
@set script="%~dp0%sample.rb"
ruby %script% %*
```

