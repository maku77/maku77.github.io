---
title: "ADB で Android 端末のバージョンや API レベルを調べる (getprop)"
permalink: /p/bg2g4bu/
date: "2022-04-21"
---

ADB 経由で __`adb shell getprop`__ コマンドを実行すると、Android 端末に設定されたシステムプロパティの一覧を取得することができます。
この中には、Android バージョンの情報も含まれています。

```console
$ adb shell getprop | grep "ro.product.build.version"
[ro.product.build.version.incremental]: [8015633]
[ro.product.build.version.release]: [12]
[ro.product.build.version.release_or_codename]: [12]
[ro.product.build.version.sdk]: [32]
```

この出力から次のようなことがわかります。

- `8015633` ... ビルドナンバー
- `12` ... Android のバージョン（この場合は Android S(12) を示す）
- `32` ... SDK バージョン

