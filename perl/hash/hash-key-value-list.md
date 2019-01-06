---
title: "ハッシュのキーや値のリストを取得する"
date: "2008-03-01"
---

~~~ perl
my @k = keys %hash;    # キーのリスト
my @v = values %hash;  # 値のリスト
~~~

キーのリストはどのように並ぶかは分かりませんが、`values` 関数で得られる値のリストは、キーの並びと対応しています。

#### 例: キーでループ処理してハッシュの要素を列挙

~~~ perl
foreach $key (keys %hash) {
    print "$key => $hash{$key}\n";
}
~~~

