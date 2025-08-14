---
title: "Androidベンダー向けメモ: Android の Locale を切り替える（Configuration 値の変更）"
url: "p/dzocr3s/"
date: "2011-05-19"
tags: ["android"]
aliases: [/android/change-configuration.html, /android/change-locale.html]
---

Android の Locale などの Configuration 値を切り替えるには、`IActivityManager` の `updateConfiguration()` メソッドを呼び出す必要があります。
ただし、`ActivityManagerNative` などは hide クラスとして定義されており、一般のアプリ開発者は以下のコードはビルドできません。

```java
IActivityManaager am = ActivityManagerNative.getDefault();
try {
    Configuration config = am.getConfiguration();
    config.XXXX = YYYY;
    am.updateConfiguration(config);
} catch (RemoteException e) {
    e.printStackTrace();
}
```

以下のコードは、Locale を変更する方法を示しています。

```java
IActivityManager am = ActivityManagerNative.getDefault();
Configuration config = am.getConfiguration();
config.locale = locale;
config.userSetLocale = true;
am.updateConfiguration(config);
```

