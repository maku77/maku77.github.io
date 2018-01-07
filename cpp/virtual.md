---
title: C++ の virtual の効果まとめ
date: "2006-07-14"
---

virtual の基本
----

```cpp
Base *p = new Sub();
```

のように子クラスのインスタンスを親クラスのポインタ型で操作する場合、メンバ関数に `virtual` が付いていないと、

- 通常メソッドは親クラスのメソッドが呼び出される
- デストラクタは親クラスのデストラクタのみ呼び出される

といった動作になります。
つまり、ポリモーフィズムが利用できないし、子クラスのデストラクタが呼ばれずにメモリリークする可能性があります。

メンバ関数に virtual を付けると、

- 通常メソッドは子クラスのメソッドが呼び出される
- デストラクタは子クラス、親クラスの順で呼び出される

という動作をするようになります。
基本的には、次のようにコーディングすればよいことになります。

- 継承されるクラスのデストラクタには virtual を付ける。
- オーバーライドされるメンバ関数には virtual を付ける。


テスト（virtual を付けない場合）
----

#### main.cpp

```cpp
#include <iostream>
using namespace std;

class Base {
public:
    Base() { cout << "Base::Base()" << endl; }
    ~Base() { cout << "Base::~Base()" << endl; }
    void Hello() { cout << "Base::Hello()" << endl; }
};

class Sub : public Base {
public:
    Sub() { cout << "Sub::Sub()" << endl; }
    ~Sub() { cout << "Sub::~Sub()" << endl; }
    void Hello() { cout << "Sub::Hello()" << endl; }
};

int main() {
    Sub *p1 = new Sub();  // Base::Base(), Sub::Sub() の順で呼ばれる
    p1->Hello();          // Sub::Hello() が呼ばれる
    delete p1;            // Sub::~Sub(), Base::~Base() の順で呼ばれる

    cout << "-------------" << endl;

    Base *p2 = new Sub();  // Base::Base(), Sub::Sub() の順で呼ばれる
    p2->Hello();           // Base::Hello() が呼ばれる（危険）
    delete p2;             // Base::~Base() が呼ばれる（危険）
                           // Sub のデストラクタは呼ばれない！
}
```

#### 実行結果:

```
Base::Base()
Sub::Sub()
Sub::Hello()
Sub::~Sub()
Base::~Base()
-------------
Base::Base()
Sub::Sub()
Base::Hello()
Base::~Base()
```

テスト（virtual を付けた場合）
----

#### main.cpp

```cpp
#include <iostream>
using namespace std;

class Base {
public:
    Base() { cout << "Base::Base()" << endl; }
    virtual ~Base() { cout << "Base::~Base()" << endl; }
    virtual void Hello() { cout << "Base::Hello()" << endl; }
};

class Sub : public Base {
public:
    Sub() { cout << "Sub::Sub()" << endl; }
    virtual ~Sub() { cout << "Sub::~Sub()" << endl; }
    virtual void Hello() { cout << "Sub::Hello()" << endl; }
};

int main() {
    Sub *p1 = new Sub();  // Base::Base(), Sub::Sub() の順で呼ばれる
    p1->Hello();          // Sub::Hello() が呼ばれる
    delete p1;            // Sub::~Sub(), Base::~Base() の順で呼ばれる

    cout << "-------------" << endl;

    Base *p2 = new Sub();  // Base::Base(), Sub::Sub() の順で呼ばれる
    p2->Hello();           // Sub::Hello() が呼ばれる
    delete p2;             // Sub::~Sub(), Base::~Base() の順で呼ばれる
}
```

#### 実行結果:

```
Base::Base()
Sub::Sub()
Sub::Hello()
Sub::~Sub()
Base::~Base()
-------------
Base::Base()
Sub::Sub()
Sub::Hello()
Sub::~Sub()
Base::~Base()
```


コラム -- コンストラクタ、デストラクタ内で仮想関数は呼ぶな！
----

子クラスをインスタンス化しようとした場合、子クラスのコンストラクタは、親クラスのコンストラクタが正常に終了してから呼び出されます。

つまり、親クラスのコンストラクタ内での処理中は、子クラスのインスタンスがまだ生成されていないため、ここで仮想関数を呼び出そうとすると親クラスの関数が呼び出されてしまいます。
つまり、親クラスのコンストラクタ内で仮想関数を呼び出しても、仮想関数として振る舞わないのです。

仮に、親クラスのコンストラクタで純粋仮想関数を呼び出そうとすると、その関数を実装した子クラスのインスタンスがまだ存在しないわけですから、おそらく実行時に落っこちます。
例えば、`g++` で作成した実行ファイルを実行した場合は、`__pure_virtual` ほげほげといって落ちたりします。

以上のことは、親クラスのデストラクタで仮想関数を呼び出そうとした場合も同様にあてはまります。
結論として、コンストラクタ内、デストラクタ内での仮想関数の呼び出しは避けるべきです。
インスタンス化と同じタイミングで仮想関数を呼び出したい場合は、次のようにコンストラクタ以外の関数から仮想関数を呼び出す方法が一般的です（スマートではないのは分かっていても）。

```cpp
Base *p = new Sub();  // コンストラクタで仮想関数を呼び出すのはまずいので
p->Init();            // 別の関数の中で仮想関数を呼び出す
```

