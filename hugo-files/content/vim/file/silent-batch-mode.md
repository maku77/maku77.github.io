---
title: "Vim のサイレントバッチモードで ex スクリプトをファイルに適用する (-es)"
url: "p/q7eoz2z/"
date: "2018-09-18"
tags: ["vim"]
aliases: /vim/file/silent-batch-mode.html
---

vim (gvim) コマンドを起動するときに、**`-es`** オプションを使用すると、サイレントバッチモードで起動し、任意の ex スクリプトを指定したファイルに適用することができます。

例えば、下記のスクリプトは `AAA` という文字を `XXX` に置換して保存するだけの簡単な ex スクリプトです。

{{< code lang="vim" title="replace.vim" >}}
:%s/AAA/XXX/g
:wq
{{< /code >}}

このスクリプトを任意のテキストファイル (ここでは `input.txt`) に適用するには、下記のように実行します。

```console
$ gvim -es -S replace.vim input.txt
```

例えば、入力したファイルの内容が下記のような内容だとすると、

{{< code title="input.txt" >}}
AAA BBB AAA BBB
BBB AAA BBB AAA
AAA BBB AAA BBB
{{< /code >}}

次のように変更されます。

{{< code title="input.txt（変更後）" >}}
XXX BBB XXX BBB
BBB XXX BBB XXX
XXX BBB XXX BBB
{{< /code >}}

指定したファイル自体の内容が変更されることに注意してください。
ex スクリプトの中で `wq` を実行しているため、上書き保存して終了するところまでがワンセットで実行されます。

`-eq` オプションの代わりに、**`-c`** オプションを使用すると、コマンドラインで直接 ex コマンドを指定して実行することができます。

```console
$ gvim -c "%s/AAA/XXX/g" -c "wq" input.txt
```

