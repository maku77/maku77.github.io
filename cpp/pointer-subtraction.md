---
title: "ポインタ同士の引き算"
date: "2004-05-01"
---

```cpp
pointer1 - pointer2
```

というポインタ同士の引き算は、内部で次のように計算されて整数の評価結果が得られます。

```
(pointer1 - pointer2) / sizeof(変数の型)
```

つまり、文字列の長さを調べる `strlen` は次のように実装できることになります。

```cpp
int strlen(const char *s) {
    const char *begin = s;
    while (*s) {
        s++;
    }
    return s - begin;
}
```

上記の `strlen` 関数に `const char*` の文字列を渡した場合は、`sizeof(char)` が 1 となるので、最後の減算では、ポインタが示すアドレスの差そのものを計算していることになります。

