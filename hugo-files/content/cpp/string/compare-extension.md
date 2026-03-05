---
title: "C++メモ: 拡張子が `.txt` かどうか調べる"
url: "p/o8irjb9/"
date: "2005-08-22"
tags: ["cpp"]
aliases: /cpp/string/compare-extension.html
---

ファイル名を格納している文字列が `.txt` という拡張子を持っているかどうかを調べる実装例です。

大文字小文字を区別する場合
----

```cpp
#include <string.h>

bool isTxtFile(const char *fileName) {
    const char *ext = strrchr(fileName, '.');
    return strcmp(".txt", ext) == 0;
}
```

{{< code lang="cpp" title="使用例" >}}
bool b1 = isTxtFile("aaa.txt");  // true
bool b2 = isTxtFile("bbb.png");  // false
{{< /code >}}


大文字小文字を区別しない場合
----

`strcmp` の代わりに `stricmp` を使うことで、大文字と小文字を区別しない文字列比較が可能です。

```cpp
#include <string.h>

bool isTxtFileIgnoreCase(const char *fileName) {
    const char *ext = strrchr(fileName, '.');
    return stricmp(".txt", ext) == 0;
}
```

`stricmp` を使用できない環境では、以下のような関数で代用することができます。

```cpp
#include <ctype.h>

int my_stricmp(const char *s1, const char *s2) {
    while (toupper(*s1) == toupper(*s2)) {
        if (*s1 == '\0') {
            return 0;
        }
        ++s1;
        ++s2;
    }
    return toupper(*s1) - toupper(*s2);
}
```

