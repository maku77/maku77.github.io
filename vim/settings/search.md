---
title: "検索に関する設定"
date: "2009-01-29"
---

大文字・小文字の区別
----

~~~
:set noignorecase  "大文字・小文字を区別して検索 (default)
:set ignorecase    "大文字・小文字を区別しないで検索
~~~

`/pattern` によるファイル内の文字列検索では、デフォルトでは大文字・小文字を区別してパターンマッチングが行われますが、区別しないで検索するように設定しておくこともできます。

`ignorecase` に加えて `smartcase` オプションを指定すると、検索パターンに大文字を含むときだけ大文字・小文字を区別して検索できるようになります。オススメ。

~~~
:set ignorecase smartcase  " 検索パターンに大文字を含むときだけ大文字・小文字を区別して検索
~~~


ファイル末尾まで検索したら先頭から検索
----

~~~
:set wrapscan    "折り返し検索 ON (default)
:set nowrapscan  "折り返し検索 OFF
~~~

`n` を連打して検索パターンにヒットした箇所に次々とジャンプしていくとき、デフォルトでは最後にヒットした文字列まで到達すると、次はファイルの先頭に戻って検索されます。
`nowrapscan` を設定しておくと、ファイルの末尾でカーソルのジャンプが停止します（**下まで検索しましたが該当箇所はありません** と表示されます）。


検索結果のハイライト
----

~~~
:set hlsearch    "検索結果ハイライト ON
:set nohlsearch  "検索結果ハイライト OFF
~~~

ハイライト表示が ON になっているときに、ハイライトされた部分を一時的に通常表示に戻したい場合は、次のコマンドを実行します。

~~~
:nohlsearch
~~~


インクリメンタル・サーチを有効にする
----

~~~
:set incsearch    "インクリメンタル・サーチ ON
:set noincsearch  "インクリメンタル・サーチ OFF
~~~
