---
title: "C11 で定義された _Noreteurn の使い方"
date: "2014-03-26"
---

関数定義に `_Noreturn` を付加することで、その関数からは復帰することがない (return しない）ことを示すことができます。
`_Noreturn` の付いた関数内に return するパスが残っている場合は、コンパイラは警告を出力します。

~~~ cpp
_Noreturn void error_exit(std::string message) {
    std::cerr << message << std::endl;
    exit(1)
}

int main() {
    if (foo()) {
        error_exit("foo failed");
    }
    return 0;
}
~~~

