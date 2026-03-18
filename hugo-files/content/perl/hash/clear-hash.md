---
title: "Perlメモ: ハッシュを空にする（ハッシュの要素をすべて削除する）"
url: "p/9rv7aw4/"
date: "2008-05-16"
tags: ["perl"]
aliases: ["/perl/hash/clear-hash.html"]
---

ハッシュ `%hash` のエントリをすべてクリアしたいのであれば、単純に下記のように新しく空のハッシュを作成する感じで代入してやれば OK です。

```perl
%hash = ();  # すべてのキーがなくなる
```
