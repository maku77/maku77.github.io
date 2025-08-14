---
title: "Androidメモ: Androidの特殊ディレクトリの情報を取得する (android.os.Environment)"
url: "p/vjcad5m/"
date: "2010-06-15"
tags: ["android"]
aliases: [/android/special-dir-info.html]
---

Android の特殊ディレクトリの情報を取得するには、**`android.os.Environment`** クラスを使用します。
以下は、いくつかの主要なディレクトリのパスを取得する方法です。

```java
java.io.File dataDir = android.os.Environment.getDataDirectory();
java.io.File downloadDir = android.os.Environment.getDownloadCacheDirectory();
java.io.File storageDir = android.os.Environment.getExternalStorageDirectory();
java.io.File rootDir = android.os.Environment.getRootDirectory();

Log.d(TAG, dataDir.getAbsolutePath());      // "/data"
Log.d(TAG, downloadDir.getAbsolutePath());  // "/cache"
Log.d(TAG, storageDir.getAbsolutePath());   // "/sdcard"
Log.d(TAG, rootDir.getAbsolutePath());      // "/system"
```

