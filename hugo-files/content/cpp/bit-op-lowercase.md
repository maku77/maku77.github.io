---
title: "C++メモ: ビット演算で大文字と小文字を変換する小技"
url: "p/pczm6am/"
date: "2004-05-03"
tags: ["cpp"]
aliases: /cpp/bit-op-lowercase.html
---

ASCII コードの大文字を表す char 値に対して、下記のようなビット演算を行うと、小文字に変換することができます。

```cpp
ch | ('a' - 'A')
```

下記の例では、変数 `ch` に格納された大文字の `F` を `f` に変換しています。

```cpp
char ch = 'F';
ch |= 'a' - 'A';
```

