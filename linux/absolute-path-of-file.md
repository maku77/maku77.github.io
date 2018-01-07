---
title: 指定したファイルの絶対パスを取得する
date: "2010-10-15"
---

```bash
abs_path=$(cd $(dirname $1); pwd)/$(basename $1)
```

