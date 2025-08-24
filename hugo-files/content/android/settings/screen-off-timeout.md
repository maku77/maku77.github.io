---
title: "Androidメモ: 画面消灯 (SCREEN OFF) までの時間を取得・設定する"
url: "p/fkn27wa/"
date: "2010-11-01"
tags: ["android"]
aliases: ["/android/settings/screen-off-timeout.html"]
---

ユーザの無操作状態が「何ミリ秒」続いたときに画面を自動的に OFF にするか、というタイムアウト値 (`SCREEN_OFF_TIMEOUT`) を取得するには以下のようにします。

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

次のようにすれば、画面 OFF までのタイムアウト時間を「設定」することができます。

```java
// 10 分でスクリーンオフ
Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, 10 * 60 * 1000);
```

