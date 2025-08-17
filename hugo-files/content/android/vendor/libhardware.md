---
title: "Androidベンダー向けメモ: libhardware.so が hardware サポートライブラリをロードする仕組み"
url: "p/6qsndck/"
date: "2014-01-29"
tags: ["android"]
aliases: ["/android/vendor/libhardware.html"]
---

Android の `hardware/libhardware/hardware.c` で定義されている、`hw_get_modules()` の仕組みの説明です。

`hw_get_module()` が呼び出されると、以下のパスの `.so` ファイルを探してロードしようとします。

- `/vendor/lib/hw/<ライブラリ名>.<プロパティ値>.so`
- `/system/lib/hw/<ライブラリ名>.<プロパティ値>.so`

`<ライブラリ名>` は `hw_get_module()` で指定した名前です。
`<プロパティ値>` は以下のシステムプロパティ値が順番にセットされます（コメントの例は Nexus7 (2013) の場合）。

```python
ro.hardware         # 例: "flo"
ro.product.board    # 例: "flo"
ro.board.platform   # 例: "msm8960"
ro.arch             # 例: ""
```

例えば、Nexus7(2013) で、`hw_get_module("power", ...)` とすると、`/system/lib/hw/power.msm8960.so` がロードされます（内部で `dlopen()` される）。
どのシステムプロパティ値との組み合わせでもライブラリを見つけられない場合は、以下のデフォルトファイルがロードされます。

- `/system/lib/hw/<ライブラリ名>.default.so`

デフォルトファイルも見つからない場合は、`hw_get_module()` は単純に `-ENOENT` を返して終了します。

