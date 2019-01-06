---
title: "メンバ関数の関数ポインタを扱う方法"
date: "2008-02-22"
---

グローバル関数を関数ポインタ経由で呼び出す場合は、関数がメモリ上のどの位置に存在するかが分かればよいので、関数ポインタさえ保持していれば呼び出し可能です。
これに比べ、メンバ変数の呼び出しの場合は、メモリ上のどこにオブジェクトが存在するかの情報も必要になるので、

- オブジェクトのアドレス（を格納したポインタ変数）
- メンバ関数のアドレス（を格納したポインタ変数）

の両方の情報が必要になります。
下記のサンプルでは、Sender クラスが Receiver クラスのインスタンスと、メンバ関数ポインタを保持し、Receiver クラスのメソッドを呼び出しています。

~~~ cpp
#include <iostream>

class Receiver {
public:
    void Function1() { std::cout << "1" << std::endl; }
    void Function2() { std::cout << "2" << std::endl; }
};

class Sender {
private:
    Receiver *m_pReceiver;
    void (Receiver::*m_pCallbackFunc)();

public:
    Sender(Receiver *pReceiver) : m_pReceiver(pReceiver) {}

    void SetCallbackFunction(void (Receiver::*pFunc)()) {
        m_pCallbackFunc = pFunc;
    }

    void Invoke() {
        (m_pReceiver->*m_pCallbackFunc)();
    }
};

int main() {
    Receiver *pReceiver = new Receiver();
    Sender *pSender = new Sender(pReceiver);

    // コールバック関数を Receiver::Function1() に設定
    pSender->SetCallbackFunction(Receiver::Function1);
    pSender->Invoke();

    // コールバック関数を Receiver::Function2() に設定
    pSender->SetCallbackFunction(Receiver::Function2);
    pSender->Invoke();

    delete pReceiver;
    delete pSender;
}
~~~

