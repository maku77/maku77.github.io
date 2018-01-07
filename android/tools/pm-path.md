---
title: APK のパッケージ名から APK ファイルのパスを調べる
date: "2016-06-27"
---

指定したパッケージの APK のファイルパスを調べる
----

`pm path ＜パッケージ名＞` とすることで、指定したパッケージ名の APK ファイルが、デバイス上のどのパスに置かれているかを調べることができます。

```
$ adb shell pm path com.example.myapp
package:/system/app/MyApp/MyApp.apk
```

ちなみに、インストールされている APK パッケージの一覧は下記のようにすれば調べられます。

```
$ adb shell pm list packages
package:com.google.android.apps.mediashell
package:com.google.android.katniss
package:com.android.providers.calendar
package:com.android.tv.settings
package:com.android.providers.media
...
```


インストールされているすべての APK のファイルパスとパッケージ名を調べる
----

下記のように、`pm list packages` コマンドに `-f` オプションを指定して実行すると、すべての APK に関するファイルパスとパッケージ名の対応情報を調べることができます。`sort` コマンドと組み合わせて使用すると見やすくなります。

```
$ adb shell pm list packages -f | sort
package:/system/app/Backdrop/Backdrop.apk=com.google.android.backdrop
package:/system/app/BasicDreams/BasicDreams.apk=com.android.dreams.basic
package:/system/app/Bluetooth/Bluetooth.apk=com.android.bluetooth
package:/system/app/KeyChain/KeyChain.apk=com.android.keychain
...
```

システムにプリインストールされているアプリケーションだけを列挙したい場合は、`-s` オプションも同時に指定します。

```
$ adb shell pm list packages -f -s
```

逆に、ユーザがインストールしたサードパーティ製のアプリだけを列挙したい場合は、`-3` オプションを指定します（`/data/app` 以下に格納されているのが分かります）。

```
$ adb shell pm list packages -f -3
package:/data/app/com.hulu.livingroomplus.jp-1/base.apk=com.hulu.livingroomplus.jp
package:/data/app/com.ted.android.tv-1/base.apk=com.ted.android.tv
package:/data/app/tv.pluto.android-1/base.apk=tv.pluto.android
```

