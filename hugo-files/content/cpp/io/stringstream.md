---
title: "C++メモ: ストリームの入力によって文字列を構築する (stringstream)"
url: "p/mzska7d/"
date: "2005-08-03"
tags: ["cpp"]
aliases: /cpp/io/stringstream.html
---

`std::stringstream` の使用例です。
下記の例では、`stringstream` に値を流し込み、結合した文字列を取得しています。

```cpp
#include <iostream>
#include <sstream>
#include <string>

int main () {
    std::stringstream ss;
    ss << "Value = " << 100;
    std::string s = ss.str();

    std::cout << s << std::endl;
    return 0;
}
```

