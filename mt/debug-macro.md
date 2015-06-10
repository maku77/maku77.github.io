---
title: デバッグ用のマクロを定義する
created: 2015-06-10
---

MQL では、C/C++ と同様に `#define` を使用したマクロを定義することができます。
現在のファイル名を示す `__FILE__` や、行番号を示す `__LINE__` なども同様に使用することができます。
下記の例では、現在のファイル名と行番号、指定したメッセージを表示するマクロを定義しています。

#### HelloEA.mq4

```mql
#define DEBUG(text) Print(__FILE__, "(", __LINE__, "): ", text)

void OnTick() {
    DEBUG("Hello, Expert Advisor");
}
```

関数名を取得するための `__FUNCTION__` なども便利です。

```mql
Print(__FUNCTION__);  // OnTick()
Print(__FUNCSIG__);   // void OnTick()
```

