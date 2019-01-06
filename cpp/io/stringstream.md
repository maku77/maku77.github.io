---
title: "ストリームの入力によって文字列を構築する (stringstream)"
date: "2005-08-03"
---

`std::stringstream` の使用例です。
下記の例では、標準入力から `stringstream` 経由で入力された文字列を取得しています。

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

