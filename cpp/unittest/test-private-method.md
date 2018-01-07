---
title: CppUnit で private メソッドをテストできるようにする
date: "2009-03-26"
---

テスト対象となるクラスに、テストクラスを `friend class` 指定してやれば `private` メンバのテストが可能になります。

#### 例: IntValueTest クラスで IntValue クラスの private メンバメソッドをテストする

~~~ cpp
class IntValue {
friend class IntValueTest;  // IntValueTest クラスに private メンバへのアクセスを許可

public:
    IntValue(int val) : m_val(val) {}

private:
    int GetValue() const { return m_val; }
    int m_val;
};

class IntValueTest : public TestCase {
public:
    explicit IntValueTest(const char *name) : TestCase(name) {}

    void testGetValue() {
        IntValue a(100);
        TEST_ASSERT_EQUALS(100, a.GetValue());
    }
};
~~~

実際には、RELEASE ビルド時に `friend` 指定が無視されるように、以下のようなマクロを使うとよいです。

~~~ cpp
#ifdef DEBUG
  #define CPPUNIT_TEST_CLASS(x) friend class x;
#else
  #define CPPUNIT_TEST_CLASS(x)
#endif
~~~

#### マクロの使用例

~~~ cpp
class IntVal {
    CPPUNIT_TEST_CLASS(IntValTest)
public:
    ...
}
~~~

