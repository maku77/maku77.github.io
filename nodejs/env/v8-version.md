---
title: Node.js がどのバージョンの V8 エンジンで動作しているか調べる
created: 2016-08-08
---

`node` コマンドを実行したときにどのバージョンの V8 エンジンで動作するかを調べるには下記のようにします。

```
$ node -p process.versions.v8
5.0.71.52
```
