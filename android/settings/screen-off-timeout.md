---
title: "画面消灯 (SCREEN OFF) までの時間を取得・設定する"
date: "2010-11-01"
---

何ミリ秒の間、ユーザの無操作状態が続くと画面を OFF にするかを取得するには以下のようにします。

```java
// import android.provider.Settings;
// import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

int timeoutInMs = 0;
try {
    timeoutInMs = Settings.System.getInt(getContentResolver(), SCREEN_OFF_TIMEOUT);
} catch (SettingNotFoundException e) {
    e.printStackTrace();
}
```

例えば、1 時間に設定されている場合は、3600000（1時間＊60分＊60秒＊1000）という値が取得できます。

逆に、画面 OFF までのタイムアウト時間を設定することもできます。

```java
// 10 分でスクリーンオフ
Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, 10 * 60 * 1000);
```

