---
title: "変数の型情報を文字列で取得する (typeid)"
date: "2007-10-30"
---

`typeid` 演算子を使うと、被演算子の型を表すオブジェクトである `type_info` のリファレンスを取得することができます。
`type_info#name()` メソッドを使用すると、型の名前を取得できます。

~~~ cpp
#include <iostream>
#include <typeinfo>

int main() {
    int i = 0;
    double d = 0;
    unsigned int ui = 0;
    int *pi = 0;
    std::string s;

    std::cout << typeid(i).name() << std::endl;       // ==> int
    std::cout << typeid(d).name() << std::endl;       // ==> double
    std::cout << typeid(i + d).name() << std::endl;   // ==> double
    std::cout << typeid(i + ui).name() << std::endl;  // ==> unsigned int
    std::cout << typeid(-ui).name() << std::endl;     // ==> unsigned int
    std::cout << typeid(pi).name() << std::endl;      // ==> int *
    std::cout << typeid(s).name() << std::endl;       // ==> std::basic_+string<...>
}
~~~

#### 実行結果

~~~
int
double
double
unsigned int
unsigned int
int *
std::basic_string<char,std::char_traits<char>,std::allocator<char> >
~~~

