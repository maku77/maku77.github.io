---
title: "Perlメモ: モジュールの検索パスを追加する（use lib プラグマ）"
url: "p/59ds7i5/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/module/use-lib.html"]
---

**`use lib`** プラグマを使用すると、`use` でモジュールを読み込むときの検索パスを追加することができます。

```perl
use lib '/home/maku/perlmodules';
```

プログラムのカレントディレクトリは固定されていないので、上記のパスは絶対パスで指定する必要があります。
