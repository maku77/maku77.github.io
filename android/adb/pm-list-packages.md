---
title: "ADB で Android 端末にインストールされているパッケージの一覧を取得する (pm list packages)"
permalink: "/p/uh84kfj/"
date: "2022-04-21"
tags: ["Android"]
---

確認バージョン: Android 12 (S)

pm list packages コマンド
----

__`adb shell pm list packages`__ コマンドを使用すると、Android デバイスにインストールされているパッケージ (APK) の一覧を取得できます。
パッケージ名は順不同で表示されるので、出力をパイプで `sort` につなぐと見やすくなります。

```console
$ adb shell pm list packages | sort
...
package:com.google.android.apps.maps
package:com.google.android.apps.messaging
package:com.google.android.apps.nexuslauncher
package:com.google.android.apps.photos
package:com.google.android.apps.restore
package:com.google.android.apps.wallpaper
...
```

__`-f`__ オプションを付けて実行すると、各 APK の __インストール先のパス__ を調べることができます。

```console
$ adb shell pm list packages -f | sort
...
package:/system/priv-app/UserDictionaryProvider/UserDictionaryProvider.apk=com.android.providers.userdictionary
package:/system/priv-app/VpnDialogs/VpnDialogs.apk=com.android.vpndialogs
package:/system_ext/priv-app/CarrierConfig/CarrierConfig.apk=com.android.carrierconfig
package:/system_ext/priv-app/EmergencyInfo/EmergencyInfo.apk=com.android.emergency
...
```

__`-3`__ オプションを付けて実行すると、__サードパーティ製のパッケージ__ の一覧を表示できます。
つまり、Google Play ストアからインストールしたアプリや、自分で作ってインストールしたアプリの一覧を確認できます。

```console
$ adb shell pm list packages -3
package:com.example.myapp
package:com.example.myapp2
```


（おまけ）pm help のヘルプ表示抜粋
----

`adb shell pm help` のヘルプ表示のうち、`pm list packages` コマンドに相当する部分の抜粋です。

```
list packages [-f] [-d] [-e] [-s] [-3] [-i] [-l] [-u] [-U] 
    [--show-versioncode] [--apex-only] [--uid UID] [--user USER_ID] [FILTER]
  Prints all packages; optionally only those whose name contains
  the text in FILTER.  Options are:
    -f: see their associated file
    -a: all known packages (but excluding APEXes)
    -d: filter to only show disabled packages
    -e: filter to only show enabled packages
    -s: filter to only show system packages
    -3: filter to only show third party packages
    -i: see the installer for the packages
    -l: ignored (used for compatibility with older releases)
    -U: also show the package UID
    -u: also include uninstalled packages
    --show-versioncode: also show the version code
    --apex-only: only show APEX packages
    --uid UID: filter to only show packages with the given UID
    --user USER_ID: only list packages belonging to the given user
```

