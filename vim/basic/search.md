---
title: "ファイル内の文字列を検索する"
date: "2006-12-06"
---

検索の基本（vi コマンド）
----

文字列パターンを入力して、ファイル内の文字列を検索する場合は、次のコマンドの組み合わせで操作します。

~~~
/pattern [Enter] -- 順方向にパターンを検索
?pattern [Enter] -- 逆方向にパターンを検索
n                -- 前回と同じ方向に再検索
N                -- 前回と逆の方向に再検索
/ [Enter]        -- 順方向に再検索
? [Enter]        -- 逆方向に再検索
~~~


正規表現による検索
----

検索には正規表現が使用できます。

#### 例: void で始まる行を検索（^ は行の先頭を表します）

~~~
/^void
~~~

#### 例: 0 回以上の繰り返しに一致 (*)

~~~
/te*    ⇒ t, te, tee などに一致
~~~

#### 例: 1 回以上の繰り返しに一致 (\+)

~~~
/te\+    ⇒ te, tee, teee などに一致
~~~

#### 例: 0 回あるいは 1 回に一致 (\=)

~~~
/te\=    ⇒ t あるいは te に一致
~~~

#### 例: 任意のアルファベット (A-Za-z) に 1 文字だけ一致 (\a)

~~~
/\a\a\a    ⇒ 任意の 3 文字のアルファベットに一致
~~~

#### 例: 任意の数字に 1 文字だけ一致 (\d)

~~~
/\d\d\d    ⇒ 任意の 3 桁の数字に一致
~~~

#### 例: 指定したいずれかの文字に一致 ([...])

~~~
/x[+*/]y    ⇒ x+y, x*y, x/y
/x[\^\-]y    ⇒ x^y, x-y  ※ブラケット式の中の ^ や - はエスケープする必要がある
~~~

#### 例: 繰り返し回数の指定 (\{min,max})

~~~
/a\{3,5}    ⇒ aaa, aaaa, aaaaa に一致 （aaaaa があれば aaaaa に一致する）
/a\{-3,5}   ⇒ aaa, aaaa, aaaaa に一致 （aaaaa があっても aaa に一致する）
/a\{3,}     ⇒ 3 個以上の a に一致
/a\{,3}     ⇒ 3 個以下の a に一致
/a\{3}      ⇒ 3 個の a に一致
~~~

#### 例: グルーピング (\(...\))
~~~
/\(abc\)\+    ⇒ abc, abcabc, abcabcabc などに一致
/\(ab\+c\)\1    ⇒ abcabc, abbcabbc などに一致  ※\(\)で囲まれた部分に一致したテキストを \1 で参照
~~~

#### 例: OR (文字列1\|文字列2)

~~~
/foo\|bar    ⇒ foo か bar に一致
~~~

#### 例: print で始まる単語を検索（\< は単語の先頭を表します）

~~~
/\<print
~~~

#### 例: 単語 word を検索（\< は単語の先頭、\> は単語の末尾を表します）

~~~
/\<word\>
~~~

#### 例: The あるいは the を検索（:set ignorecase を実行しておけば、大文字小文字を区別せずに検索できます）

~~~
/[Tt]he
~~~

#### 例: 数字で始まる行を検索（ブラケット式の中の [:digit:] という文字クラスは数字を表します）

~~~
/^[[:digit:]]
~~~


検索履歴を利用して再検索
----

`/` あるいは `?` を入力した後で、上下カーソルキー、あるいは `Ctrl+P`、`Ctrl+N` で検索履歴を利用して再検索することができます。


識別子検索
----

~~~
[I  -- カーソル位置のキーワードを含む行をすべて表示する
[D  -- カーソル位置のマクロ定義を含む行をすべて表示する
~~~


行内に限定して検索する
----

~~~
fx  -- カレント行の次の文字 x へ移動
Fx  -- カレント行の前の文字 x へ移動
tx  -- カレント行の次の文字 x のひとつ前の文字へ移動
Tx  -- カレント行の前の文字 x のひとつ後ろの文字へ移動
;   -- 前回の行内の文字検索を繰り返し（同じ方向）
,   -- 前回の行内の文字検索を繰り返し（逆の方向）
~~~

#### 例: ピリオドまでのテキストを削除

~~~
dt.
~~~

#### 例: 閉じ括弧 `)` までのテキストを置換

~~~
ct)
~~~

#### 例: 空白スペースまでを削除

~~~
dt ← 最後はスペース. sdlflkj
~~~

