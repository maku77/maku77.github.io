---
title: "文字列を置換する"
date: "2004-02-15"
---

文字列の置換 (s/AAA/BBB/)
----

置換演算子 (substitution operator) `s///` を使うと、正規表現にマッチしたテキストを置換することができます。

~~~ perl
$line =~ s/PATTERN/REPLACEMENT/[OPTION]
~~~

という構文で、文字列 `$line` に含まれる PATTERN というテキストを REPLACEMENT に置換し、`$line` に書き戻します。
対象となる文字列が `$_` である場合は、結合演算子 (binding operator) `=~` までの部分（`$line =~` の部分）は省略することができます。

### s/// のオプション

| オプション | 何の略称か | 意味 |
| ---- | ---- | ---- |
| e | eval | 置換用の文字列を実行時に評価する |
| g | global | マッチするものを全て置換し、置換数を返す |
| i | ignore |  大文字、小文字を無視 |
| m | multiple | 文字列を複数行として扱う (`\n` を行の区切りとみなす) |
| o | once | パターン部分のコンパイルを一度だけする |
| s | single | 文字列を単一行として扱う (`\n` を行の区切りとみなさない) |
| x | extended | 空白類の無視 |

#### 例: John を Bob に置換

~~~ perl
$_ = 'I am John.';
s/John/Bob/;    # $_ の値は 'I am Bob.' になる
print;
~~~

#### 例: 大なり記号と小なり記号をエスケープする

~~~ perl
while (<>) {
    s/</&lt;/g;
    s/>/&gt;/g;
    print;
}
~~~

`s///` 演算子による文字列置換の結果は、置換が行われた場合は真、行われなかった場合は偽になります。

~~~ perl
$is_replaced = s/from/to/;
$is_replaced = $line =~ s/from/to/;
~~~


デリミタはスラッシュ以外の記号を使用可能
----

置換演算子のデリミタには、スラッシュ以外の記号を使用することができます。

~~~
s!from!to!
s|from|to|
s#from#to#
s?from?to?
~~~

デリミタとして括弧を使用することもできますが、その場合は、正規表現パターン部分と置換文字列部分をそれぞれ一対の記号で囲む必要があります。

~~~
s{from}{to}
s<from><to>
s[from]<to>
s{from}#to#
s?from?(to)
~~~

正規表現パターンや、置換文字列部分に登場しないデリミタを使用することで、可読性を上げることができます。

~~~
s{http://}{ftp://};
~~~


修飾子 /g によるグローバル置換
----

置換演算子 `s///` 演算子は、デフォルトでは 1 箇所の文字列しか置換を行いません。
正規表現に一致するすべての文字列を置換する場合は、修飾子 `/g` を付ける必要があります。

~~~ perl
$_ = 'abc abc abc';
s/abc/XYZ/;    # $_ は 'XYZ abc abc' になる

$_ = 'abc abc abc';
s/abc/XYZ/g;    # $_ は 'XYZ XYZ XYZ' になる
~~~

