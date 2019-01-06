---
title: "Android Studio に割り当てるメモリを増やす"
date: "2016-03-22"
---

Android Studio に割り当てられる VM のメモリ容量は、デフォルトで最大 750MB (`-Xmx750m`) に設定されています。
物理メモリに余裕のある PC で作業している場合は、この最大サイズを大きくしておくとよいでしょう。

VM に関する設定は、`studio.exe.vmoptions` あるいは `studio64.exe.vmoptions` というファイルを作成して変更することができますが、これらのファイルの具体的な置き場所は、プラットフォームごと、Android Studio のバージョンごとに異なっています（下記参照）。

- [Configuring Android Studio: IDE & VM Options, JDK, etc - Android Tools Project Site](http://tools.android.com/tech-docs/configuration)

例えば、Windows で Android Studio Preview 2.0 (64bit) を使用している場合は、下記のようなファイルを作成します。

```
%USERPROFILE%\.AndroidStudioPreview2.0\studio64.exe.vmoptions
```

Android Studio が使用可能なメモリ容量を増やす（ここでは 4GB に設定する）には、このファイルに下記のように記述しておきます。

#### studio64.exe.vmoptions

```
-Xms1024m
-Xmx4096m
-Didea.platform.prefix=AndroidStudio
-Didea.paths.selector=AndroidStudioPreview2.0
```

`-Xms` の方は最少メモリ容量、`-Xmx` の方には最大メモリ容量を指定します。
最後の 2 行の AndroidStudio に関する設定（環境によっては `prefix` の方だけで OK かも）を行っておかないと、`studio64.exe` を起動しようとしたときに素の IntelliJ が起動しちゃったりします。

