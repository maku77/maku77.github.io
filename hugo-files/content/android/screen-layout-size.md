---
title: "Androidメモ: 現在のスクリーンレイアウトサイズを取得する (Configuration)"
url: "p/7e9mdcp/"
date: "2011-09-14"
tags: ["android"]
aliases: [/android/screen-layout-size.html]
---

Android の現在のスクリーンサイズ (small, medium, large, xlarge) などの情報は **`Configuration`** オブジェクトに格納されています。
Activity の中から以下のように情報を取得できます。

```java
// import android.content.res.Configuration;

Configuration config = getResources().getConfiguration();
if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
    // LARGE の場合
}
```

