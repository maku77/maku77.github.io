---
title: 肯定形で表現する
created: 2015-06-23
layout: bestpractice
---

真偽値を表すのであれば、否定形よりも肯定形で統一しましょう。
例えば、disabled ではなく enabled を、hidden/invisible ではなく shown を使用します。

```java
boolean isInvisible();           // NG（isVisible とすればよい）
boolean isHidden();              // NG（同上）
setHideFlag(boolean shouldHide)  // NG（単純に show とすればよい）
boolean isNotDisplayed;          // NG（反転させなくてよい）
setVisibility(boolean visible);  // OK（show() と hide() を用意するのも OK）
show() / hide()                  // OK
```

