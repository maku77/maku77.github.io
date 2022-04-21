---
title: "protection レベルが dangerous なパーミッションの一覧を表示する (pm list permissions)"
permalink: /p/8x8qmy3/
redirect_from: /android/dangerous-permissions.html
date: "2015-08-04"
---

`pm list permissions` コマンドに **-d** オプションを付けて実行すると、**protectionLevel が dangerous** に設定されたパーミッションのみを一覧表示してくれます。
Android M からは、dangerous なパーミッションはユーザが個別に On/Off することができるようになったため、どのようなパーミッションが存在しているのかを把握することがより重要になりました。

```console
shell@xxx:/ $ pm list permissions -d
Dangerous Permissions:

permission:com.android.providers.tv.permission.READ_EPG_DATA
permission:com.android.providers.tv.permission.WRITE_EPG_DATA
```

`adb shell` で接続する前に実行する場合は、上記のコマンドの先頭に `adb shell` を付けて実行してください。

