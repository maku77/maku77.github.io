---
title: "Perl のスカラ変数と配列に $, @ というプレフィックスを付ける理由"
date: "2004-04-11"
---

うんちく話。

Perl プログラミングでは、スカラ変数に `$` プレフィックスを付け（例:`$foo`）、配列変数に `@` プレフィックスを付ける（例:`@bar`）というルール（構文）になっています。
これらの記号を使用することに決めた理由は、scalar の頭文字が `$`、array の頭文字が `@` に似ているからとのことです（Larry 談）。

`@` の方は分かるんですが、`$` の方はこの記号を使いたかったから scalar というネーミングを採用したんじゃないかと思ってしまう(^^;

残念ながら連想配列 (hash, associative array) は `#` ではなく `%` ですね。
どうせならコメントに使う記号を `%` にして、ハッシュに `#` を使うようにすればよかったのに。
個人的にこれは Perl 言語仕様の最大の失敗だと思っています笑。

