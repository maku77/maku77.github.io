---
title: 自動インデントモードを有効にする
created: 2007-11-16
---

インデントモードの設定
----

下記のインデントモードを有効にしておくと、改行を入力したときに、適切なインデントを自動で行ってくれるようになります。

autoindent
: 一行前のインデントと同じだけインデント。挿入モードのまま、インデント量を変えるには `CTRL-T`、`CTRL-D` などのコマンドを使用すればよい。

cindent
: C/C++, Java などに特化したインデント。

smartindent
: `{` ごとにインデントレベルを上げ、`}` ごとにインデントレベルを下げる。ただし、C/C++ のプリプロセッサのために `#` のある行だけはインデントしないなどの特殊ルールが設定されている。このようなキーワードごとのインデント量は `cinwords` オプションで設定可能。

~~~
:set autoindent     " autoindent を ON
:set noautindent    " autoindent を OFF
:set cindent        " cindent を ON
:set noindent       " cindent を OFF
:set smartindent    " smartindent を ON
:set nosmartindent  " smartindent を OFF
~~~

C/C++ のソースファイルを開いたときに自動的に cindent モードを設定する
----

以下のように設定しておくと、C 言語や C++ 言語のファイルを開いたときに、`cindent` モードが自動的に有効になります。

#### ~/.vimrc

~~~
" ファイルタイプ検出を有効にする
filetype on

augroup vimrc
    " 以前の autocmd コマンドをクリア
    autocmd!

    " C/C++ 言語系のファイルタイプが設定されたら cindent モードを有効にする
    autocmd FileType c,cpp  set cindent
augroup END
~~~

参考
----
* [インデント（シフトコマンド）の設定 (shiftwidth, shiftround)](indent.html)
* [インデント用のスペースを入力する（シフトコマンド）](../edit/indent.html)
* [選択した範囲を自動インデントする](../edit/re-indent.html)

