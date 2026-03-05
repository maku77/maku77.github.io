---
title: "C++メモ: CppUnit で private メソッドをテストできるようにする"
url: "p/hj53vb3/"
date: "2009-03-26"
tags: ["cpp"]
aliases: /cpp/unittest/test-private-method.html
---

テスト対象となるクラスに、テストクラスを `friend class` 指定してやれば `private` メンバのテストが可能になります。

{{< code lang="cpp" title="例: IntValueTest クラスで IntValue クラスの private メンバメソッドをテストする" >}}
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
{{< /code >}}

実際には、RELEASE ビルド時に `friend` 指定が無視されるように、以下のようなマクロを使うとよいです。

```cpp
#ifdef DEBUG
  #define CPPUNIT_TEST_CLASS(x) friend class x;
#else
  #define CPPUNIT_TEST_CLASS(x)
#endif
```

{{< code lang="cpp" title="マクロの使用例" >}}
class IntVal {
    CPPUNIT_TEST_CLASS(IntValTest)
public:
    ...
}
{{< /code >}}

