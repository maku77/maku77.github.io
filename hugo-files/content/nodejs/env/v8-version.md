---
title: "Node.jsメモ: Node.js がどのバージョンの V8 エンジンで動作しているか調べる"
url: "p/cf7eoyo/"
date: "2016-08-08"
tags: ["nodejs"]
aliases: /nodejs/env/v8-version.html
---

`node` コマンドを実行したときにどのバージョンの V8 エンジンで動作するかを調べるには下記のようにします。

```console
$ node -p process.versions.v8
5.0.71.52
```

