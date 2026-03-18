---
title: "Perlメモ: ハッシュのキーや値のリストを取得する"
url: "p/as4ihjp/"
date: "2008-03-01"
tags: ["perl"]
aliases: ["/perl/hash/hash-key-value-list.html"]
---

```perl
my @k = keys %hash;    # キーのリスト
my @v = values %hash;  # 値のリスト
```

キーのリストはどのように並ぶかは分かりませんが、`values` 関数で得られる値のリストは、キーの並びと対応しています。

{{< code lang="perl" title="例: キーでループ処理してハッシュの要素を列挙" >}}
foreach $key (keys %hash) {
    print "$key => $hash{$key}\n";
}
{{< /code >}}
