---
title: "Perlメモ: 欲張りな量指定子と欲張りでない量指定子による文字列マッチング"
url: "p/ok9fz5i/"
date: "2008-03-11"
tags: ["perl"]
aliases: ["/perl/string/greedy-match.html"]
---

欲張りな量指定指定子 (+, *)
----

量指定子の `+` と `*` は、欲張り (greedy) なので、できるだけ多くの範囲にマッチしようとします。

{{< code lang="perl" title="例: 欲張りな量指定子 (+)" >}}
my $line = 'This is a bookable book.';
$line =~ s/(This.+book)/[$1]/;
print $line, "\n";
{{< /code >}}

{{< code title="実行結果" >}}
[This is a bookable book].
{{< /code >}}


欲張りでない量指定子 (+?, *?)
----

`+` と `*` の代わりに、`+?` と `*?` を使うようにすると、できるだけ短い範囲でマッチしてくれます。

{{< code lang="perl" title="例: 欲張りでない量指定子 (+?)" >}}
my $line = 'This is a bookable book.';
$line =~ s/(This.+?book)/[$1]/;
print $line, "\n";
{{< /code >}}

{{< code title="実行結果" >}}
[This is a book]able book.
{{< /code >}}
