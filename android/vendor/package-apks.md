---
title: "製品にパッケージングする APK を指定する"
date: "2010-05-17"
---

プロダクトごとにインストールする APK パッケージを制御するには、`$(TARGET_PRODUCT).mk` の `PRODUCT_PACKAGES` を使用します。

```makefile
PRODUCT_PACKAGES := \
    AlarmClock \
    AlarmProvider \
    Bluetooth \
    ...
```

上記は、`build/target/product/generic.mk` から抜粋しています。

