---
title: "findmakefile コマンドで Android.mk のあるディレクトリを調べる"
date: "2011-08-09"
---

`build/envsetup.sh` で定義されているシェル関数 `findmakefile` を使用すると、カレントディレクトリから上に `Android.mk` を探してそのパスを調べることができます。

```
$ findmakefile
/home/maku/gitwork/android/packages/apps/Browser/Android.mk
```

これを利用して、以下のように Vim で `Android.mk` を開くことができます。

```
$ vim `findmakefile`
```

