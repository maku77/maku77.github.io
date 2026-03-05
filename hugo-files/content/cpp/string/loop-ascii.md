---
title: "C++メモ: ASCII コードで次の文字を取得する、A〜Z までループで処理する"
url: "p/4rjdfzo/"
date: "2011-11-16"
tags: ["cpp"]
aliases: /cpp/string/loop-ascii.html
---

ASCII コードで表せる文字であれば、下記のような for ループで１文字ずつ処理することができます。

```cpp
for (char ch = 'A'; ch <= 'Z'; ++ch) {
    std::cout << ch;
}
```

Java も同じように記述することができます。

