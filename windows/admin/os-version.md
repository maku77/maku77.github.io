---
title: Windows OS のバージョン情報をコマンドラインから調べる
created: 2015-04-13
---

現在使用している OS が Windows 7 なのか、64-bit バージョンなのかといった情報は、下記のように調べられます。

```
C:\> wmic os get caption
Caption
Microsoft Windows 7 Professional
```

```
C:\> wmic os get osarchitecture
OSArchitecture
64-bit
```

