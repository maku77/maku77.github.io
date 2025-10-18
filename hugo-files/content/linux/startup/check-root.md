---
title: "Linuxメモ: シェルスクリプトを実行したユーザの名前を調べる"
url: "p/3eofc3v/"
date: "2009-12-16"
tags: ["linux"]
aliases: /linux/startup/check-root.html
---

下記の例では、シェルスクリプトを実行したユーザが root であるかどうかを調べています。

```bash
if [ "$USER" != "root" ]; then
  echo "Must be executed by root."
  exit -1
fi
```

