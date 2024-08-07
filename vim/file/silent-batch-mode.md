---
title: "サイレントバッチモードで Ex スクリプトをファイルに適用する"
date: "2018-09-18"
---

vim (gvim) コマンドを起動するときに、**`-es`** オプションを使用すると、サイレントバッチモードで起動し、任意の ex スクリプトを指定したファイルに適用することができます。

例えば、下記のスクリプトは `AAA` という文字を `XXX` に置換して保存するだけの簡単なスクリプトです。

#### replace.vim

~~~
:%s/AAA/XXX/g
:wq
~~~

このスクリプトを任意のテキストファイル (ここでは `input.txt`) に適用するには、下記のようにします。

~~~
$ gvim -es -S replace.vim input.txt
~~~

入力したファイルの内容が下記のような内容だとすると、

#### input.txt

~~~
AAA BBB AAA BBB
BBB AAA BBB AAA
AAA BBB AAA BBB
~~~

次のように変更されます。

~~~
XXX BBB XXX BBB
BBB XXX BBB XXX
XXX BBB XXX BBB
~~~

指定したファイル自体の内容が変更されることに注意してください（スクリプトの中で `wq` を実行しているため、上書き保存して終了までワンセットで実行される）。

`-eq` オプションの代わりに、**`-c`** オプションを使用すると、コマンドラインで直接 ex コマンドを指定して実行することができます。

~~~
$ gvim -c "%s/AAA/XXX/g" -c "wq" input.txt
~~~

