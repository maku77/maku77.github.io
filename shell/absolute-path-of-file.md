---
title: 指定したファイルの絶対パスを取得する
created: 2010-10-15
layout: shell
---

```bash
abs_path=$(cd $(dirname $1); pwd)/$(basename $1)
```

