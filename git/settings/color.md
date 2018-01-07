---
title: Git コマンドの出力をカラフルにする
date: "2010-07-17"
---

Git コマンドの出力をカラフルにする
----

~~~
$ git config --global color.ui "auto"
~~~

この設定をしておくと、ターミナルから Git コマンド（`git log` や `git diff`）を実行したときの出力に色がつくようになります。
特に理由がない限りこの設定をしておくとよいです。


色の設定をより細かく指定する
---

以下のような感じで、各パートの色や装飾の方法を設定することができます。

~~~
$ git config --global color.diff.meta "magenta reverse"
~~~

* 色: normal, black, red, green, yellow, blue, magenta, cyan, white
* 装飾: bold, dim, ul, blink, reverse

どのような項目があるかは、`git config` のヘルプサイトで確認できます。

