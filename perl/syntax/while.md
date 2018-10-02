---
title: "while/until によるループ処理"
date: "2008-03-15"
---

while、until ループの基本
----

下記は Perl における `while` ループの簡単な使用例です。
`while` ループは、指定した条件を満たしている限り、ブロック内の処理を繰り返し実行します。

~~~ perl
my $val = 0;
while ($val < 100) {
    $val += 5;
    print "$val\n";
}
~~~

コードのブロックは、必ず `{` と `}` で囲む必要があります。
`while` の代わりに `until` を使うと、条件式の判定を逆にできます。

~~~ perl
until ($buf_size >= $data_size) {
    # $buf_size が $data_size 以上になるまで実行される
    $buf_size *= 2;
}
~~~

式修飾子 (expression modifier) を使って次のようにひとつの文で書くこともできます。

~~~ perl
print ($val += 5), "\n" while $val < 100;
$buf_size *= 2 until $buf_size >= $data_size;
~~~


last、next、redo によるループ制御
----

### break でループを抜ける

`while` (`until`) ループを途中で抜ける場合は、**`last`** 演算子を使います（C 言語の `break` のようなものです）。

~~~ perl
while ($val < 10) {
    print "$val\n";
    last if $val > 5;  # 5 を超えたらループ終了
    ++$val;
}
~~~

### next で直ちに次の要素を処理する

`while` (`until`) ループで現在の繰り返しを終了させ、次の繰り返しを行う（条件文のチェックへ戻る）には **`next`** 演算子を使います（C 言語の `continue` のようなものです）。

~~~ perl
while ($val < 10) {
    ++$val;
    next if $val % 2 == 0;  # 偶数なら次の繰り返しへ
    print "$val\n";
}
~~~

### redo で現在の要素をもう一度処理する

現在の繰り返しをもう一度実行する（ブロックの先頭に戻る）には **`redo`** 演算子を使います（C 言語には同様の機能はありません）。
このとき、条件文のチェックは行われません。

~~~ perl
while ($val < 10) {
    print "$val\n";
    print "Redo? (Y/N): ";
    chomp ($line = <STDIN>);
    redo if $line =~ /^Y$/i;
    ++$val;
}
~~~

