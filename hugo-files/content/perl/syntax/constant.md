---
title: "Perlメモ: 定数を定義する（constant プラグマ）"
url: "p/2o7ed5k/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/syntax/constant.html"]
---

Perl では下記のような構文で定数を定義することがきます。

```perl
use constant 定数名 => 値
```

次の例では、`DEBUG` と `HOGE` という定数を定義しています。

```perl
use constant DEBUG => 1;
use constant HOGE => 10 * 10;
```

`use constant` はコンパイル時に処理されるので、ほかの `use` プラグマに先立って値を設定し、その値を参照することができます。

```perl
use constant LIB_DIR => '/home/joe/lib';
use lib LIB_DIR;  # 定数の値を使用（コンパイル時に処理される）
```

