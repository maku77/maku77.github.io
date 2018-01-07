---
title: Windows OS のバージョン情報をコマンドラインから調べる
date: "2015-04-13"
---


現在使用している Windows OS のバージョンを調べるには、`ver` コマンドを使用します。

```
C:\> ver

Microsoft Windows [Version 6.1.7601]
```

商品名としての OS 名を調べたい場合や、32-bit バージョンなのか 64-bit バージョンなのかなどを調べたい場合は、`wmic` コマンドが使用できます。

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

