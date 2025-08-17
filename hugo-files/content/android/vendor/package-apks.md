---
title: "Androidベンダー向けメモ: 製品にパッケージングする APK を指定する (PRODUCT_PACKAGES)"
url: "p/n8n3mwo/"
date: "2010-05-17"
tags: ["android"]
aliases: ["/android/vendor/package-apks.html"]
---

Android でプロダクトごとにインストールする APK パッケージは、`$(TARGET_PRODUCT).mk` の **`PRODUCT_PACKAGES`** 変数で指定します。

```makefile
PRODUCT_PACKAGES := \
    AlarmClock \
    AlarmProvider \
    Bluetooth \
    ...
```

上記は、`build/target/product/generic.mk` から抜粋しています。

