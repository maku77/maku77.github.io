---
title: "ファイルがオープンできないとき自動で終了する（autodie プラグマ）"
date: "2008-04-29"
---

`use autodie` プラグマを使用すると、`open` に失敗したときの `die` 処理を省略して記述できるようになります。

~~~ perl
use autodie qw(open close);  # open/close succeed or die
open(my $fh, '<', 'not-exist.txt');  # No need to check!
~~~

上記の例では `open` の後ろに `or die` 記述をしていませんが、ファイルが開けなかったときは自動的に `die` 処理が行われ、下記のような出力をしてプログラムが終了します。

~~~
Can't open 'not-exist.txt' for reading: 'No such file or directory' at sample.pl line 2
~~~

