---
title: "ADB で指定したアプリを強制終了する (am force-stop)"
url: "p/9yse3iz/"
date: "2016-06-06"
tags: ["Android"]
aliases: /android/adb/adb-force-stop-app.html
---

Android 端末に ADB 接続された状態で __`adb shell am force-stop`__ コマンドを実行すると、任意のアプリケーションを強制終了することができます。

```console
$ adb shell am force-stop com.example.myapp
```

`adb shell` 接続されている状態であれば、`adb shell` の部分は省略して実行します。

```
generic_x86:/ $ am force-stop com.example.myapp
```

ちなみに、インストールされているアプリのパッケージ名一覧は `adb shell pm list packages` で調べることができます（こちらは `am` じゃなくて __`pm`__ コマンドであることに注意してください）。

- 参考: [ADB で Android 端末にインストールされているパッケージの一覧を取得する (pm list packages)](/p/uh84kfj/)

