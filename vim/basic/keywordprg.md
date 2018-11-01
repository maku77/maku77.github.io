---
title: "カーソル位置の単語に対して任意のコマンド（ヘルプなど）を実行する (keywordprg)"
date: "2007-11-21"
---

キーワードにカーソルを合わせた状態で、<kbd>SHIFT-K</kbd> と入力すると、そのキーワードをパラメータにして、**`keywordprg`** オプションに設定されたコマンドが実行されます。
`keywordprg` の初期値は以下のようになっています。

~~~ vim
set keywordprg=man    "Unix の場合
set keywordprg=:help  "Windows の場合
~~~

つまり、Unix の場合は、<kbd>SHIFT-K</kbd> と入力するだけで、カーソル位置にあるキーワードの man ページを参照することができます。
man ページのセクション番号を指定するには、<kbd>2</kbd><kbd>SHIFT-K</kbd> のように、先にセクション番号を入力します。

どの文字をキーワードとみなすかは、**`iskeyword`** オプションに文字コードで設定します。
`iskeyword` のデフォルト値は下記のような感じになっています。

~~~ vim
set iskeyword=@,48-57,_,128-167,224-235
~~~

