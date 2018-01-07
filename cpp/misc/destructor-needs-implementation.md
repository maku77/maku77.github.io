---
title: 仮想デストラクタには実装が必要
date: "2009-07-02"
---

~~~ cpp
class Base {
public:
    virtual ~Base() = 0;
    ...
};

inline Base::~Base() {}
~~~

C++ ではデストラクタを継承することができないので、デストラクタを純粋仮想関数にした場合でも、そのクラスで実装を持たなければいけません。
実装が存在しないと、コンパイル時か実行時に undefined symbol エラーになってしまいます。

純粋でない仮想関数にした場合も同様です。

~~~ cpp
class Base {
public:
    virtual ~Base() {}
};
~~~

