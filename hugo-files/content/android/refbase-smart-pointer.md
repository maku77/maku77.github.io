---
title: "Android Nativeメモ: C++ 実装用のスマートポインタ (RefBase, sp) を使用する"
url: "p/w48omg6/"
date: "2011-02-03"
tags: ["android"]
aliases: [/android/refbase-smart-pointer.html]
---

Android フレームワークの `android/frameworks/base/include/utils/RefBase.h` にスマートポインタクラス **`sp`** が定義されてます。
使い方は、

```cpp
sp<Hoge> hoge = new Hoge();
```

のような感じで、`hoge` がスコープを外れると自動的にデストラクタを呼んでくれます。
`sp` はリファレンスカウンタを持っているので、`sp` オブジェクト間で代入処理 (`operator=`) を行っても大丈夫です（代入した瞬間に `delete` されてしまうことはない）。

スマートポインタとして扱えるようにするクラスは、以下のように **`RefBase`** を継承して作成する必要があります。
これは、`RefBase` が参照カウンタなどを実装しているからです。

{{< code lang="cpp" title="Hoge.h" >}}
#include <utils/RefBase.h>

class Hoge : public virtual RefBase {
public:
    static sp<Hoge> self();
    ...
};
{{< /code >}}

{{< code lang="cpp" title="Hoge.cpp" >}}
Hoge::Hoge() : RefBase() {}
{{< /code >}}

`Android.mk` では `libutils.so` を使用する指定をしておく必要があります。

{{< code lang="makefile" title="Android.mk" >}}
LOCAL_SHARED_LIBRARIES := \
    libutils
{{< /code >}}

