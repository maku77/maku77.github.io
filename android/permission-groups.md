---
title: パーミッショングループの一覧を表示する
created: 2015-08-04
---

Android 端末内にどのようなパーミッショングループが存在しているかを調べるには、`adb shell` で接続した後に下記のコマンドを使用します。

```bash
shell@xxx:/ $ pm list permission-groups
permission group:android.permission-group.PHONE_CALLS
permission group:android.permission-group.WALLPAPER
permission group:android.permission-group.MESSAGES
permission group:android.permission-group.USER_DICTIONARY
permission group:android.permission-group.CALENDAR
permission group:android.permission-group.BLUETOOTH_NETWORK
permission group:android.permission-group.BOOKMARKS
permission group:android.permission-group.CAMERA
permission group:android.permission-group.DEVELOPMENT_TOOLS
permission group:android.permission-group.SCREENLOCK
permission group:android.permission-group.WRITE_USER_DICTIONARY
permission group:android.permission-group.AUDIO_SETTINGS
permission group:android.permission-group.HARDWARE_CONTROLS
permission group:android.permission-group.VOICEMAIL
permission group:android.permission-group.DISPLAY
permission group:android.permission-group.APP_INFO
permission group:android.permission-group.SOCIAL_INFO
permission group:android.permission-group.AFFECTS_BATTERY
permission group:android.permission-group.NETWORK
permission group:android.permission-group.ACCESSIBILITY_FEATURES
permission group:android.permission-group.STATUS_BAR
permission group:android.permission-group.SYNC_SETTINGS
permission group:android.permission-group.PERSONAL_INFO
permission group:android.permission-group.LOCATION
permission group:android.permission-group.SYSTEM_CLOCK
permission group:android.permission-group.STORAGE
permission group:android.permission-group.SYSTEM_TOOLS
permission group:android.permission-group.ACCOUNTS
permission group:android.permission-group.COST_MONEY
permission group:android.permission-group.DEVICE_ALARMS
permission group:android.permission-group.MICROPHONE
```

`adb shell` で接続前に実行したい場合は、下記のように `adb shell` に続けてコマンド入力しても OK です。
ホスト側の PC で出力を `sort` したい場合などはこちらの方が便利かもしれません。

```bat
C:\> adb shell pm list permission-groups | sort
permission group:android.permission-group.ACCESSIBILITY_FEATURES
permission group:android.permission-group.ACCOUNTS
permission group:android.permission-group.AFFECTS_BATTERY
permission group:android.permission-group.APP_INFO
permission group:android.permission-group.AUDIO_SETTINGS
permission group:android.permission-group.BLUETOOTH_NETWORK
...
```

それぞれのパーミッショングループが、どのようなパーミッションから構成されているかを調べるには以下のようにします。

```bash
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

