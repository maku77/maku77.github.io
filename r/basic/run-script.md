---
title: "R スクリプトファイルを実行する (source)"
date: "2014-12-11"
---

例えば、`commands.R` というテキストファイルに R のコマンドを記述してある場合、以下のようにして実行できます。

```r
> source("commands.R")
```

ファイル読み込み元となるカレントディレクトリは、`getwd()` で取得、`setwd()` で変更することができます。

