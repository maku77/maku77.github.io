---
title: "パーミッショングループの一覧を表示する (pm list permission-groups)"
url: "p/viry8fo/"
date: "2015-08-04"
tags: ["Android"]
aliases: /android/permission-groups.html
---

パーミッショングループの一覧
----

Android 端末内にどのようなパーミッショングループが存在しているかを調べるには、`adb shell` で接続した後に __`pm list permission-groups`__ コマンドを使用します。

```bash
shell@xxx:/ $ pm list permission-groups
permission group:android.permission-group.PHONE_CALLS
permission group:android.permission-group.WALLPAPER
permission group:android.permission-group.MESSAGES
permission group:android.permission-group.USER_DICTIONARY
permission group:android.permission-group.CALENDAR
permission group:android.permission-group.BLUETOOTH_NETWORK
...
```

`adb shell` で接続前に実行したい場合は、下記のように `adb shell` に続けてコマンド入力しても OK です。
ホスト側の PC で出力を `sort` したい場合などはこちらの方が便利かもしれません。

```console
$ adb shell pm list permission-groups | sort
permission group:android.permission-group.ACCESSIBILITY_FEATURES
permission group:android.permission-group.ACCOUNTS
permission group:android.permission-group.AFFECTS_BATTERY
permission group:android.permission-group.APP_INFO
permission group:android.permission-group.AUDIO_SETTINGS
permission group:android.permission-group.BLUETOOTH_NETWORK
...
```


パーミッショングループに属するパーミッションの一覧
----

それぞれのパーミッショングループが、どのようなパーミッションから構成されているかを調べるには、__`pm list permissions -g`__ コマンドを使用します。

```
shell@xxx:/ $ pm list permissions -g
All Permissions:

group:android.permission-group.PHONE_CALLS
  permission:android.permission.BIND_INCALL_SERVICE
  permission:android.permission.READ_PRECISE_PHONE_STATE
  permission:android.permission.BIND_CONNECTION_SERVICE
  ...

group:android.permission-group.WALLPAPER
  permission:android.permission.SET_WALLPAPER
  permission:android.permission.SET_WALLPAPER_HINTS
  ...
```

