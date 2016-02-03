---
title: ART/Dalvik VM のバージョンを調べる
created: 2010-06-01
---

Android デバイスの VM のバージョンは `dalvikvm` コマンドで調べることができます。

```
$ adb shell
# dalvikvm -showversion
DalvikVM version 1.1.0
```

ART を使用している場合は、下記のような感じで表示されます。

```
# dalvikvm -showversion
ART version 2.1.0
```

