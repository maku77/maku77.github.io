---
title: Make 変数でビルド範囲を制御する
created: 2010-09-06
---

Android のワンショット make の仕組みでは、`Android.mk` に基づいてビルド処理が行われますが、下位ディレクトリにある `Android.mk` はデフォルトでは検索しないようになっています。
下位ディレクトリのモジュール（プロジェクト）までビルドするには、上位の `Android.mk` で `include` 処理を記述する必要がありますが、この `include` 処理を実行するかどうかを Make 変数で制御することにより、ビルド範囲の制御が可能です。

下記の例では、`CONFIG_HOGE` という Make 変数の値により、下位ディレクトリのモジュールのビルドを制御しています。

```makefile
ifeq ($(CONFIG_HOGE),true)
    include $(call all-subdir-makefiles)
endif
```

