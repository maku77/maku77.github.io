---
title: "Windows で Ruby や Python のスクリプトを組み込みコマンドのように使用する"
date: "2010-05-07"
lastmod: "2021-03-12"
---

例えば、`sample.rb` という Ruby スクリプトを、任意のディレクトリから `sample` と打つだけで実行できるようにするには、PATH の通ったディレクトリに `sample.rb` と、以下のような `sample.cmd` を置いておけば OK です。
もちろん、Ruby や Python の実行環境はあらかじめインストールしておく必要があります。

#### sample.cmd

```bat
@echo off
set script="%~dp0%sample.rb"
ruby %script% %*
```

#### ディレクトリ構成

```
C:\myapp\  （このディレクトリに PATH を通しておく）
    +-- sample.cmd  （sample というコマンドとして認識させるため）
    +-- sample.rb   （実際に呼び出す Ruby スクリプト）
```

Python の場合も同様です。

```bat
@echo off
set script="%~dp0%sample.py"
python %script% %*
```

