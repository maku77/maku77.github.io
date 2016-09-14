---
title: プロキシ経由で curl コマンドを実行する
created: 2012-12-07
---

プロキシ経由で `curl` コマンドを実行したい場合は、下記のように `--proxy` オプションを使用します。

```
$ curl --proxy http://proxy.example.com:8080/ http://target.example.com/
```

