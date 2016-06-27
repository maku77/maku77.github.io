---
title: APK のパッケージ名から APK ファイルのパスを調べる
created: 2016-06-27
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

