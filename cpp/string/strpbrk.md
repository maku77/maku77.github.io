---
title: 文字列の中に指定した文字列の中のいずれかの文字が含まれているか調べる (strpbrk)
created: 2011-03-21
---

~~~ cpp
#include <string.h>
char *strpbrk(const char *s1, const char *s2);
~~~

`strpbrk` は、`s1` の中で `s2` で指定したいずれかの文字が見つかった場合、その位置を返します。
どの文字も見つからなかった場合は、`NULL` を返します。

例えば、以下のようにすれば、入力されたテキストの中に不正な文字が含まれていないかチェックすることができます。

~~~ cpp
const char *input = "abcd*efg";     // チェック対象のテキスト
const char *invalidChars = "~*/?";  // 含まれてはいけない不正な文字のリスト

if (strpbrk(input, invalidChars)) {
   std::cout << "including a invalid character" << std::endl;
}
~~~

