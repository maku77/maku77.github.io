---
title: "Windowsメモ: Windows で Ruby や Python のスクリプトを組み込みコマンドのように使用する"
url: "p/256385g/"
date: "2010-05-07"
lastmod: "2021-03-12"
tags: ["windows"]
aliases: /windows/run-script-as-command.html
---

例えば、`sample.rb` という Ruby スクリプトを、任意のディレクトリから `sample` と打つだけで実行できるようにするには、PATH の通ったディレクトリに `sample.rb` と、以下のような `sample.cmd` を置いておけば OK です。
もちろん、Ruby や Python の実行環境はあらかじめインストールしておく必要があります。

{{< code lang="bat" title="sample.cmd" >}}
@echo off
set script="%~dp0%sample.rb"
ruby %script% %*
{{< /code >}}

{{< code title="ディレクトリ構成" >}}
C:\myapp\  （このディレクトリに PATH を通しておく）
    +-- sample.cmd  （sample というコマンドとして認識させるため）
    +-- sample.rb   （実際に呼び出す Ruby スクリプト）
{{< /code >}}

Python の場合も同様です。

```bat
@echo off
set script="%~dp0%sample.py"
python %script% %*
```
