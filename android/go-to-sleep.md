---
title: Sleep モードに遷移する
created: 2010-11-01
---

以下のコードを実行するには、システム権限アプリとして動かす必要があります。
通常アプリとして動作させると、以下のような *SecurityException* が出ます。

```
E/AndroidRuntime( 7118): java.lang.RuntimeException:
    Unable to create service com.example.sample.MyService:
    java.lang.SecurityException:
    Neither user 10052 nor current process has android.permission.DEVICE_POWER.
```

まず、`AndroidManifest.xml` に **DEVICE_POWER** のパーミッション使用宣言を追加しておきます。

#### AndroidManifest.xml

```xml
<manifest ...>
    <uses-permission android:name="android.permission.DEVICE_POWER" />
</manifest>
```

適切にパーミッションが割り当てられると、コードから以下のように実行することができます。

```java
PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
pm.goToSleep(SystemClock.uptimeMillis() + 1);
```

