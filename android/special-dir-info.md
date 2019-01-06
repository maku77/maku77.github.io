---
title: "特殊ディレクトリの情報を取得する"
date: "2010-06-15"
---

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

