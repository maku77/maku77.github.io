---
title: インストールされた各 APK パッケージに割り当てられたユーザー ID を調べる
date: "2011-04-05"
---

`/data/system/packages.xml` ファイルに各 APK のユーザー ID が記述されています。

```xml
<package name="com.example.history" codePath="/system/app/History.apk" system="true" ts="1301918167000" version="7" userId="10006">
  <sigs count="1">
    <cert index="3" />
  </sigs>
</package>
```

