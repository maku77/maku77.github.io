---
title: "Androidメモ: スリープモードに遷移する／スリープから抜ける"
url: "p/cfqazd7/"
date: "2010-11-01"
lastmod: "2016-07-07"
tags: ["android"]
changes:
  - 2016-07-07: ADB コマンドを使ってスリープモードに移行する方法を追加
aliases: [/android/go-to-sleep.html, /android/adb/go-to-sleep.html]
---

ADB コマンドを使ってスリープモードに移行する／スリープから抜ける
----

{{< code lang="console" title="ADB コマンドでスリープ状態に入る" >}}
$ adb shell "echo mem > /sys/power/state"
{{< /code >}}

{{< code lang="console" title="ADB コマンドでスリープ状態から抜ける" >}}
$ adb shell "echo on > /sys/power/state"
{{< /code >}}


コード内からスリープモードに移行する（ベンダー向け情報）
----

プログラム内からスリープモードに移行するには、`AndroidManifest.xml` に **`DEVICE_POWER`** のパーミッション使用宣言を追加しておきます。

{{< code lang="xml" title="AndroidManifest.xml" >}}
<manifest ...>
    <uses-permission android:name="android.permission.DEVICE_POWER" />
</manifest>
{{< /code >}}

適切にパーミッションが割り当てられると、コード内から以下のようにスリープへ移行することができます。

```java
PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
pm.goToSleep(SystemClock.uptimeMillis() + 1);
```

このコードを実行するには、システム権限アプリとして動作している必要があります。
通常アプリから実行すると、以下のような **SecurityException** が発生します。

```
E/AndroidRuntime( 7118): java.lang.RuntimeException:
    Unable to create service com.example.sample.MyService:
    java.lang.SecurityException:
    Neither user 10052 nor current process has android.permission.DEVICE_POWER.
```

