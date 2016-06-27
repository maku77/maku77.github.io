---
title: APK のパッケージ名から APK ファイルのパスを調べる
created: 2016-06-27
---

`pm path ＜パッケージ名＞` とすることで、指定したパッケージ名の APK ファイルが、デバイス上のどのパスに置かれているかを調べることができます。

```
$ adb shell pm path com.example.myapp
package:/system/app/com.example.myapp.apk
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

