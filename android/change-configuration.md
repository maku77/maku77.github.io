---
title: 現在の Configuration を変更する
created: 2011-09-14
---

`ActivityManagerNative` は hide クラスなので、一般のアプリ開発者は以下のコードはビルドできません。

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

