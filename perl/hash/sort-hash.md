---
title: ハッシュをソートする
date: "2008-03-02"
---

ハッシュはあくまでキーと値のペアを保持しているものなので、ハッシュ自体をソートすることはできませんが、キーのリストをソートして、その順に値を取得することは可能です。

```perl
my %hash = ( A => 200, B => 300, C => 100 );
for my $key (sort keys %hash) {
    print "$key => $hash{$key}\n";
}
```

ハッシュの「値」でソートされたキーのリストを取得するには下記のようにします。

```perl
my %hash = ( A => 200, B => 300, C => 100 );
my @keys = sort { $hash{$a} <=> $hash{$b} } keys %hash;

print @keys;  # 'CAB'
```

