---
title: "for/foreach によるループ処理"
date: "2008-03-16"
---

foreach の基本
----

`foreach`（あるいは `for` でも可）にリストを渡すと、リスト内の要素を 1 つずつ順番に処理できます。

~~~ perl
# 現在の要素は $_ で参照できる
foreach (@arr) {
    print "$_\n";
}

# 要素に名前を付けることもできる
foreach my $val (@arr) {
    print "$val\n";
}
~~~

式修飾子 (expression modifier) を使って次のように 1 文で書くこともできます。
`for` ループを式修飾子として記述する場合、各要素は必ず `$_` で参照することになります。

~~~ perl
print "$_\n" foreach @arr;
~~~


foreach と for
----

Perl では、C 言語風に `for` ループを記述することもできます。

~~~ perl
for ($i = 0; $i < 10; ++$i) {
    print "$i\n";
}
~~~

Perl の処理系は `foreach` と `for` を同じものとみなすので、`foreach` と書ける場所ではいつでも `for` と書くことができます（慣れてくるとタイプ数を減らすために全部 `for` と書くようになります）。
内部的には、`for` の後ろの括弧の中にセミコロンが 2 つある場合は、C 言語風の `for` ループとして処理され、それ以外の場合は `foreach` ループとして処理されます。

#### 以下は両方とも同じ foreach ループとして処理される

~~~ perl
print for 1..10;
print foreach 1..10;
~~~


last、next、redo によるループ制御
----

### break でループを抜ける

`foreach` (`for`) ループを途中で抜ける場合は、**`last`** 演算子を使います（C 言語の `break` のようなもの）。

~~~ perl
for (1..10) {
    print "$_\n";
    last if $_ == 5;  # 5 ならループを抜ける
}
~~~

### next で直ちに次の要素を処理する

`foreach` (`for`) ループで次の繰り返しへジャンプするには、**`next`** 演算子を使います（C 言語の `continue` のようなもの）。

~~~ perl
for (1..10) {
    next if $_ % 2 == 0;  # 偶数なら次の繰り返しへ
    print "$_\n";
}
~~~

### redo で現在の要素をもう一度処理する

`foreach` (`for`) ループで、現在の繰り返しをもう一度実行する（ブロックの先頭に戻る）には **`redo`** 演算子を使います（C 言語にはこのようなものはありません）。

~~~ perl
for (1..10) {
    print "$_\n";
    print "Redo? (Y/N): ";
    chomp (my $line = <STDIN>);
    redo if $line =~ /^Y$/i;
}
~~~

### 外側のループを制御する

ループ制御演算子 (`last`, `next`, `redo`) を一番内側のループ以外に作用させたい場合は、ラベル付きブロックを使い、ループ制御演算子にそのラベルを指定します。

~~~ perl
LINE: while (<>) {
    for (split) {
        last LINE if /__END__/;
        ...
    }
}
~~~

