---
title: 単位を明確にする
created: 2015-06-25
---

時刻や距離、重さなど、その単位が重要な場合は、名前から単位が分かるように命名しましょう。
パラメータに関しては、ドキュメンテーションコメントで説明しておくこともできますが、コメントではなくてシンボル名から単位を読み取れるのが理想的です。

```java
long duration;          // NG
long durationInMillis;  // Good

public int convertTimeToPixel(long time);      // NG
public int convertMillisToPixel(long millis);  // Good
```

