---
title: "Androidベンダー向けメモ: ART/Dalvik VM のバージョンを調べる (dalvikvm -showversion)"
url: "p/5at33s8/"
date: "2010-06-01"
tags: ["android"]
aliases: [/android/dalvik-version.html]
---

Android デバイスの VM のバージョンは `dalvikvm` コマンドで調べることができます。

```console
$ adb shell
# dalvikvm -showversion
DalvikVM version 1.1.0
```

ART を使用している場合は、下記のような感じで表示されます。

```console
# dalvikvm -showversion
ART version 2.1.0
```

