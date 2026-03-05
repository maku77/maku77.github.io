---
title: "C++メモ: 文字列を逆順にする (reverse)"
url: "p/8p444dg/"
date: "2011-12-05"
tags: ["cpp"]
aliases: /cpp/string/reverse.html
---

`algorithm` ライブラリで提供されている `std::reverse()` 関数を使用すれば、`char` 配列や `string` 変数に格納された文字列を逆順にすることができます。

### char 配列を逆順にする

```cpp
// #include <algorithm>
char s[] = "ABCDE";
std::reverse(s, s + 5);    //=> "EDCBA"
```

### std::string を逆順にする

```cpp
// #include <algorithm>
// #include <string>
std::string s = "ABCDE";
std::reverse(s.begin(), s.end());    //=> "EDCBA"
```

