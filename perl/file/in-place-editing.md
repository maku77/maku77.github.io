---
title: "書き戻し編集の仕組みで読み込んでいるファイルの内容を直接変更する"
date: "2008-03-24"
---

テキストファイルの内容を置換した結果をファイルに出力する場合、一般的には標準出力に出力した内容をリダイレクトします。

~~~
$ ./replace.pl input.txt > output.txt
~~~

このとき、同じファイルに書き戻そうとすると、ファイルの内容はおそらく失われてしまいます。

~~~
$ ./replace.pl input.txt > input.txt  # 危険！
~~~

このようなことをしたい場合は、**書き戻し編集 (in-place editing)**の仕組みを利用すると、読み込み元のファイルを直接編集することができます。
実行方法は次のような感じになります。

~~~
$ ./replace.pl input.txt  # input.txt の内容を直接書き換える
~~~

in-place editing の機能を利用したい場合は、特殊変数 **`$^I`** と、ダイヤモンド演算子 **`<>`** を組み合わせて使用します。
以下の例では、ファイル内の `Date:` で始まる行を書き換えて、現在の日時を挿入しています。

#### replace.pl

~~~ perl
# Backup file extension for in-place editing.
$^I = '.bak';

my $date = localtime;
while (<>) {
    s/^Date:.*/Date: $date/;
    print;
}
~~~

特殊変数 `$^I` にバックアップファイルの拡張子名を設定すると、ダイヤモンド演算子 (`<>`) によって処理されるファイルは、`print` によって直接編集されるようになります。
元のファイルは指定した拡張子が付いたファイル名に置換されて残ります。

in-place editing の仕組みは、ワンライナーで `-i` オプションを付けることでも利用できます。

#### input.txt 内のテキストを置換する（Before → After）

~~~
$ perl -p -i.bak -e 's/Before/After/g' input.txt
~~~

※Windows で実行する場合は、`-e` オプションのコード部分をダブルクォートで囲む必要があります。

