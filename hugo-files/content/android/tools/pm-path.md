---
title: "APK のパッケージ名から APK ファイルのパスを調べる (pm path, pm list packages)"
url: "p/ghtgxgw/"
date: "2016-06-27"
tags: ["Android"]
aliases: /android/tools/pm-path.html
---

指定したパッケージの APK のファイルパスを調べる
----

Android デバイスに `adb shell` で接続し、__`pm path <パッケージ名>`__ コマンドを実行すると、指定したパッケージ名の APK ファイルが、デバイス上のどのパスに置かれているかを調べることができます。

{{< code lang="console" title="com.example.myapp パッケージがどの APK ファイルか調べる" >}}
$ adb shell pm path com.example.myapp
package:/system/app/MyApp/MyApp.apk
{{< /code >}}


インストールされているすべての APK のファイルパスとパッケージ名を調べる
----

インストールされている APK パッケージの一覧は、__`pm list packages`__ コマンドで調べることができます。

{{< code lang="console" title="インストール済みパッケージの一覧を表示" >}}
$ adb shell pm list packages
package:com.google.android.apps.mediashell
package:com.google.android.katniss
package:com.android.providers.calendar
package:com.android.tv.settings
package:com.android.providers.media
...
{{< /code >}}

さらに、__`-f`__ オプションを指定して実行すると、すべての APK に関するファイルパスとパッケージ名の対応情報を調べることができます。
`sort` コマンドと組み合わせて使用すると見やすくなります。

{{< code lang="console" title="APK 名とパッケージ名の対応リスト" >}}
$ adb shell pm list packages -f | sort
package:/system/app/Backdrop/Backdrop.apk=com.google.android.backdrop
package:/system/app/BasicDreams/BasicDreams.apk=com.android.dreams.basic
package:/system/app/Bluetooth/Bluetooth.apk=com.android.bluetooth
package:/system/app/KeyChain/KeyChain.apk=com.android.keychain
...
{{< /code >}}

システムにプリインストールされているアプリケーションだけを列挙したい場合は、__`-s`__ オプションも同時に指定します。

{{< code lang="console" title="プリインアプリのみ表示" >}}
$ adb shell pm list packages -f -s
{{< /code >}}

逆に、ユーザがインストールしたサードパーティ製のアプリだけを列挙したい場合は、__`-3`__ オプションを指定します（`/data/app` 以下に格納されているのが分かります）。

{{< code lang="console" title="3rd パーティアプリのみ表示" >}}
$ adb shell pm list packages -f -3
package:/data/app/com.hulu.livingroomplus.jp-1/base.apk=com.hulu.livingroomplus.jp
package:/data/app/com.ted.android.tv-1/base.apk=com.ted.android.tv
package:/data/app/tv.pluto.android-1/base.apk=tv.pluto.android
{{< /code >}}

