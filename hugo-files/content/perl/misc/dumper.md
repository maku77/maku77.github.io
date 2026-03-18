---
title: "Perlメモ: Data::Dumper で複雑なデータをダンプする"
url: "p/fqgzsf6/"
date: "2008-05-18"
tags: ["perl"]
aliases: ["/perl/misc/dumper.html"]
---

`Data::Dumper` モジュールを使うと、ハッシュなどの複雑なデータの内容をダンプできます。
`Dumper` にはリファレンスでデータを渡します。

```perl
use Data::Dumper;

my %hash = (
    aaa => [100, 101, 102],
    bbb => [200, 201, 202],
);
print Dumper(\%hash);
```

{{< code title="実行結果" >}}
$VAR1 = {
          'bbb' => [
                     200,
                     201,
                     202
                   ],
          'aaa' => [
                     100,
                     101,
                     102
                   ]
        };
{{< /code >}}

`Dumper` はデータの内容をファイルに保存できるテキスト形式にして（マーシャリングして）出力します。
これをファイルに保存すれば後からデータを復元することができます。
ある変数の値をファイルに保存する場合は、もっと効率的にバイト形式で保存／復元する `Storable` モジュールの `store`、`retrieve` を使用することもできます。
