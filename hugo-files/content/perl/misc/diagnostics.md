---
title: "Perlメモ: 一時的にエラーメッセージを詳しくする（diagnostics プラグマ）"
url: "p/2ck8jqg/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/misc/diagnostics.html"]
---

下記のように、`diagnostics` プラグマを有効にすると、プログラム実行時に `perldiag` マニュアルページをすべてロードし、Perl の診断メッセージを詳しく表示してくれるようになります。

```perl
use diagnostics;
```

エラーメッセージの意味が分からないときに、一時的に `use diagnostics;` することで原因を特定しやすくなります。
