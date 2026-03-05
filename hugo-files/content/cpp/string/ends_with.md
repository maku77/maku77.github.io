---
title: "C++メモ: 文字列がある文字列で終わっていることを確認する (`ends_with`)"
url: "p/n2sy4jg/"
date: "2010-12-14"
tags: ["cpp"]
aliases: /cpp/string/ends_with.html
---

下記の `ends_with` 関数は、文字列 `str` の末尾に特定の文字列 `suffix` があるかどうかを調べます。

{{< code lang="cpp" title="C++ 版" >}}
bool ends_with(const std::string& str, const std::string& suffix) {
    size_t len1 = str.size();
    size_t len2 = suffix.size();
    return len1 >= len2 && str.compare(len1 - len2, len2, suffix) == 0;
}
{{< /code >}}

{{< code lang="cpp" title="C 版" >}}
#include <string.h>

bool ends_with(const char *str, const char *suffix) {
    size_t len1 = strlen(str);
    size_t len2 = strlen(suffix);
    // compare the suffix.
    return len1 >= len2 && strcmp(str + len1 - len2, suffix) == 0;
}
{{< /code >}}

{{< code lang="cpp" title="テスト" >}}
#include <iostream>

int main() {
    std::cout << ends_with("abcd", "") << std::endl;  // 1
    std::cout << ends_with("", "abcd") << std::endl;  // 0
    std::cout << ends_with("abcd", "ab") << std::endl;  // 0
    std::cout << ends_with("abcd", "cd") << std::endl;  // 1
    std::cout << ends_with("abcd", "abcd") << std::endl;  // 1
    std::cout << ends_with("abcd", "abcdef") << std::endl;  // 0
}
{{< /code >}}

