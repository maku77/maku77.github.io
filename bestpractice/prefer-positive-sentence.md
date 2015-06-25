---
title: 肯定形で表現する
created: 2015-06-23
---

真偽値を表すのであれば、否定形よりも肯定形で統一しましょう。
例えば、disabled ではなく enabled を、hidden/invisible ではなく shown を使用します。

```java
boolean isInvisible();           // NG（isVisible とすればよい）
setHideFlag(boolean shouldHide)  // NG（show と言えばよい）
boolean isNotDisplayed;          // NG（反転させなくてよし）
setVisibility(boolean visible);  // OK（show() と hide() を用意するのも OK）
```

