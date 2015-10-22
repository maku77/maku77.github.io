---
title: echo の出力を標準エラー (stderr) に出力する
created: 2015-05-21
layout: windows
---

`echo` コマンドの最後に **1>&2** とリダイレクトすることで、`echo` の結果を標準エラー出力に出力することができます。

```bat
C:\> echo Hello 1>&2
```

stdout (1) を stderr (2) に向けるという意味です。
