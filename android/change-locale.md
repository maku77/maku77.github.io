---
title: Android の Locale を切り替える
created: 2011-05-19
---

```java
IActivityManager am = ActivityManagerNative.getDefault();
Configuration config = am.getConfiguration();
config.locale = locale;
config.userSetLocale = true;
am.updateConfiguration(config);
```

`IActivityManager` は hide クラスなので、通常のアプリケーションからは呼び出せません。

