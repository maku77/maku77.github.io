---
title: 値コピーできないメンバ変数を持つクラスでは、コピーコンストラクタと代入演算子をオーバーロードする
created: 2009-12-07
---

コピーコンストラクタや、代入演算子を定義していないクラスのオブジェクトを以下のように `=` を使って作成しようとすると、デフォルトのコピーコンストラクタ、代入演算子が暗黙的に使用されます。

~~~ cpp
Hoge a = b;  // デフォルトのコピーコンストラクタ
c = d;       // デフォルトの代入演算子
~~~

デフォルトの実装は、メンバフィールドを単純にコピーするだけなので、仮にポインタ変数を持っていると、同じアドレス値を 2 つのオブジェクトが持つことになってメモリの二重解放の原因になります。
また、代入された側のオブジェクトが持っていたポインタが指していたメモリを解放するオブジェクトがなくなり、メモリリークの原因にもなります。

~~~ cpp
class Book {
private:
    char *m_pTitle;  // このアドレスがコピーされてしまう
    ...
};
~~~

メンバー変数の単純なコピーが行われては困るクラスでは、コピーコンストラクタ、代入演算子を定義する必要があります。
ポインタではなく、**コピー演算子が定義されていないオブジェクトをメンバ変数として持っている場合も同様**です。

~~~ cpp
// コピーコンストラクタを実装
Book::Book(const Book& rhs) {
    // 新しいメモリを割り当てて内容をコピー
    int len = strlen(rhs.m_pTitle);
    m_pTitle = new char[len + 1];
    strncpy(m_pTitle, len, rhs.m_pTitle);
}

// 代入演算子を実装
Book& Book::operator=(const Book& rhs) {
    // 自分自身への代入を確認
    if (this == &rhs) {
        return *this;
    }

    // 古い値を削除
    delete [] m_pTitle;

    // 新しいメモリを割り当てて内容をコピー
    int len = strlen(rhs.m_pTitle);
    m_pTitle = new char[len + 1];
    strncpy(m_pTitle, len, rhs.m_pTitle);

    return *this;
}
~~~

あるいは、コピーコンストラクタ、代入演算子を `private` にして、代入によるオブジェクト生成を禁止する方法もあります。

~~~ cpp
class Book {
    ...
private:
    Book(const Book& rhs);  // 実装しない
    Book& operator=(const Book& rhs);  // 実装しない
};
~~~

以下のテストプログラムを実行すると、デフォルトの代入演算子を利用した場合に、各オブジェクトのコンストラクタ、デストラクタによってどのような処理が行われるかを調べることができます。

~~~ cpp
#include <iostream>
#include <string>

class Data {
private:
    std::string m_name;

public:
    Data(const std::string& name) : m_name(name) {
        std::cout << "Constructor " << m_name << std::endl;
    }
    virtual ~Data() {
        std::cout << "Destructor " << m_name << std::endl;
    }
};

int main() {
    Data a("A");
    Data b("B");
    a = b;  // "Destructor A" は永遠に呼ばれなくなる ⇒ メモリリークの可能性

    // a の破棄による "Destructor B"
    // b の破棄による "Destructor B" ⇒ 二重開放の可能性
}
~~~

#### 実行結果

~~~ cpp
Constructor A
Constructor B
Destructor B
Destructor B
~~~

