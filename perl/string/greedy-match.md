---
title: "欲張りな量指定子と欲張りでない量指定子による文字列マッチング"
created: 2008-03-11
---

欲張りな量指定指定子 (+, *)
----

量指定子の `+` と `*` は、欲張り (greedy) なので、できるだけ多くの範囲にマッチしようとします。

#### 例: 欲張りな量指定子 (+)

~~~ perl
my $line = 'This is a bookable book.';
$line =~ s/(This.+book)/[$1]/;
print $line, "\n";
~~~

#### 実行結果

~~~
[This is a bookable book].
~~~


欲張りでない量指定子 (+?, *?)
----

`+` と `*` の代わりに、`+?` と `*?` を使うようにすると、できるだけ短い範囲でマッチしてくれます。

#### 例: 欲張りでない量指定子 (+?)

~~~ perl
my $line = 'This is a bookable book.';
$line =~ s/(This.+?book)/[$1]/;
print $line, "\n";
~~~

#### 実行結果

~~~
[This is a book]able book.
~~~

