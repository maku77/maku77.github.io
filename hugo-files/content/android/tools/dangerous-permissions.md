---
title: "protection レベルが dangerous なパーミッションの一覧を表示する (pm list permissions)"
url: "p/8x8qmy3/"
date: "2015-08-04"
tags: ["Android"]
aliases: /android/dangerous-permissions.html
---

`pm list permissions` コマンドに __`-d`__ オプションを付けて実行すると、__protectionLevel が dangerous__ に設定されたパーミッションのみを一覧表示してくれます。
Android M からは、dangerous なパーミッションはユーザが個別に On/Off することができるようになったため、どのようなパーミッションが存在しているのかを把握することがより重要になりました。

```
shell@xxx:/ $ pm list permissions -d
Dangerous Permissions:

permission:com.android.providers.tv.permission.READ_EPG_DATA
permission:com.android.providers.tv.permission.WRITE_EPG_DATA
```

`adb shell` で接続する前に実行する場合は、上記のコマンドの先頭に `adb shell` を付けて実行してください。

