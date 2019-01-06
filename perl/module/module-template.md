---
title: "ディストリビューション用のテンプレートファイルを作成する (h2xs)"
date: "2008-07-06"
---

`h2xs` コマンドを使用すると、モジュールのテンプレートファイルを作成することができます。

```
D:\> h2xs -Xan Hoge
Defaulting to backwards compatibility with perl 5.10.0
If you intend this module to be compatible with earlier perl versions, please
specify a minimum perl version with the -b option.

Writing Hoge/lib/Hoge.pm
Writing Hoge/Makefile.PL
Writing Hoge/README
Writing Hoge/t/Hoge.t
Writing Hoge/Changes
Writing Hoge/MANIFEST
```

