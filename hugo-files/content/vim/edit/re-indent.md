---
title: "Vim/NeoVim で選択した範囲を自動インデントする (=)"
url: "p/pxpgasg/"
date: "2009-12-07"
tags: ["vim"]
aliases: /vim/edit/re-indent.html
---

自動インデントの実行
----

ソースコードの一部分のインデントが崩れているような場合は、以下のように自動整形することができます。

1. 対象となるコードをビジュアルモードで行選択（__`Shift-V` + 行選択__）。
2. __`=`__ と入力


自動インデントを整形するときに使用するプログラムを指定する
----

`=` コマンドで自動インデントを行う場合は、デフォルトでは Vim 内部の整形プログラムが使用されますが、任意のインデント用プログラムを使用するように設定することもできます。

```vim
:set equalprg=/usr/local/bin/indent
```

参考
----

- [自動インデントモードを有効にする (`autoindent`, `smartindent`, `cindent`)](/p/oe94dkh/)
