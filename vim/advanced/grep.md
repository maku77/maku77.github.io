---
title: "Vim 内で grep を実行して見つかったファイルへジャンプする"
date: "2010-09-13"
---

内部 grep  (internal grep) と外部 grep (external grep)
----

Vim には自身に搭載されている検索機能を実行する**内部 grep** と、外部の grep 系コマンドを実行する**外部 grep** の機能が存在します。

- 内部 grep を起動するコマンド -- `:vimgrep`（省略形は `:vim`）
- 外部 grep を起動するコマンド -- `:grep`


内部 grep による検索 (:vimgrep)
----

内部 grep (`:vimgrep`) は、Vim に組み込まれた grep 機能であるため、どの OS 上でも同様なコマンドで検索を実行することができます。

#### 書式:

~~~
:vim /{pattern}/[g][j] {file} ...
~~~

- `j` オプション -- デフォルトでは検索にヒットした位置にカーソルが移動しますが、`j` オプションを付けて検索を実行すると、カーソル位置は移動しなくなります。


#### 例: 現在開いているファイルを grep

~~~
:vim /hoge/j %
~~~

#### 例: *.txt ファイルを grep

~~~
:vim /hoge/j *.txt
~~~

#### 例: カレントディレクトリ以下のファイルをすべて grep

~~~
:vim /hoge/j **/*.txt
~~~

オプション `ignorecase` がセットされている場合は、パターンの大文字・小文字を区別せずに検索します。
設定に関わらず、大文字・小文字を区別して検索したい場合は、パターンの中に `\C` を含めます。
逆に、大文字・小文字を区別せずに検索したい場合は、パターンの中に `\c` を含めます。

#### 例: 大文字・小文字を区別して include という文字列を探す

~~~
:vim /\Cinclude/j **/*.cpp
~~~

検索が終了したら、`copen` で Quickfix List のウィンドウを開いて、検索にヒットした行のリストを確認することができます。
Enter キーを入力することで、その場所にジャンプすることができます。

~~~
:copen
~~~

`vimgrep` の実行と同時に Quickfix List のウィンドウを開きたい場合は、以下のように `cw[indow]` コマンドを続けて実行します。

~~~
:vim /hoge/j *.cpp | cw
~~~

毎回 `cw` を入力するのが面倒な場合は、`.vimrc` に以下のように設定しておきます（Vim 7 以降）。

~~~
au QuickfixCmdPost make,grep,grepadd,vimgrep copen
~~~

この設定により、`vimgrep` や `grep` を実行した後に、自動的に `copen` されるようになります。


外部 grep による検索 (:grep)
----

`:vimgrep` コマンドの代わりに `:grep` コマンドを使用すると、`grepprg` に設定されている外部の grep 系コマンドを実行することができます。

~~~
:grep [arguments]
~~~

オプション `grepprg` のデフォルト値は、Windows の場合は `findstr /n`、Linux の場合は `grep -n $* /dev/null` に設定されています。
通常は、以下のような感じで実行できるはずです。

~~~
:grep hoge *.txt   "*.txt ファイルを grep
:grep hoge %       "編集中のファイルを grep
~~~

`vimgrep` と同様、`copen` で Quickfix List のウィンドウを開いて、マッチした行にジャンプできます。

~~~
:copen
~~~

ちなみに、`copen` の C はおそらく "Current List of Errors" の C です。
`copen` で開いたウィンドウの `buftype` は `quickfix` になり、全体１つだけ表示することができます。
続けて `:grep` を実行した場合、現在開いている Quickfix window に結果が表示されます。

