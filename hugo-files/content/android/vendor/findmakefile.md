---
title: "Androidベンダー向けメモ: findmakefile コマンドで Android.mk のあるディレクトリを調べる"
url: "p/63w3r2r/"
date: "2011-08-09"
tags: ["android"]
aliases: ["/android/vendor/findmakefile.html"]
---

`build/envsetup.sh` で定義されているシェル関数 **`findmakefile`** を使用すると、カレントディレクトリから上に `Android.mk` を探してそのパスを調べることができます。

```console
$ findmakefile
/home/maku/gitwork/android/packages/apps/Browser/Android.mk
```

これを利用して、以下のように Vim で `Android.mk` を開くことができます。

```console
$ vim `findmakefile`
```

