---
title: "正規表現パターンにマッチした部分文字列を抜き出す"
date: "2008-03-12"
---


正規表現にマッチした文字列を抽出する
----

正規表現パターンの一部を括弧で囲んでグルーピングしておくと、括弧で囲まれた部分に一致したテキストを、マッチ変数 (`$1`, `$2`, `$3`, ...) を使って後から参照できます。

~~~ perl
$_ = "AAA BBB CCC DDD EEE";
if (/(B+) (\S+) (\S+)/) {
    print "<$1> <$2> <$3> <$4>\n";  # '<BBB> <CCC> <DDD> <>'
}
~~~

範囲外のマッチ変数（上記の場合は `$4`）を参照すると、`undef` が得られます。
マッチした部分全体を取得したい場合は、`$&` を参照します。

リストコンテキストでマッチングを行えば、マッチ変数の値をリストで取得することができます。

~~~ perl
for (`netstat`) {
    my ($local, $foreign) = /TCP\s+(\S+)\s+(\S+)/;
    next unless $local;  # マッチに失敗したら次の行へ
    print "$local -- $foreign\n";
}
~~~

マッチ演算子 `m//` をリストコンテキストで評価すると、正規表現メモリに捕獲されたサブストリングのリストを返します。
マッチしなかった場合は空リストを返します。
この性質を利用して、あるテキスト配列から、部分文字列を抽出することができます。

~~~ perl
my @stem = map { /(.*)\.cpp$/ } @files;  # ファイル名の stem 部分を抜き出す
~~~


マッチした部分の前後のテキストを抽出する
----

正規表現によるテキストマッチングを行うと、次のような特殊変数でマッチしたテキストや、その前後のテキストを取得できます。

- <code>$&</code> -- パターンにマッチした部分のテキスト
- <code>$`</code> -- マッチした部分より前のテキスト
- <code>$'</code> -- マッチした部分より後ろのテキスト

~~~ perl
$_ = 'You can do it.';
if (/can/) {
    print "$`<$&>$'\n";
}
~~~

#### 実行結果

~~~
You <can> do it.
~~~
