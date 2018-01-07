---
title: Bash / カレントディレクトリの絶対パスを取得する
date: "2010-08-26"
---

以下のどの方法でも、カレントディレクトリの絶対パスを取得できます。

```bash
current_dir=$(pwd)
current_dir="$PWD"
current_dir=`pwd`
```

