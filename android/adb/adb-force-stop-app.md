---
title: ADB で指定したアプリを強制終了する
date: "2016-06-06"
---

Android 端末に ADB 接続された状態で下記のコマンドを実行すると、任意のアプリケーションを強制終了することができます。

```
adb shell am force-stop com.example.myapp
```

ちなみに、インストールされているアプリのパッケージ名一覧は下記のように調べることができます（こちらは `am` じゃなくて `pm` であることに注意してください）。

```
adb shell pm list packages
```

