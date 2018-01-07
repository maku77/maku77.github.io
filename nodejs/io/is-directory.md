---
title: 指定したパスがディレクトリかどうか調べる
date: "2015-05-09"
---

Node.js の標準モジュール `fs` が提供している `fs.stat(path, callback)` を使用すると、指定したファイルやディレクトリの情報を表す `fs.Stats` オブジェクトを取得することができます。

- [Node.js - fs.stat メソッド](https://nodejs.org/api/fs.html#fs_fs_stat_path_callback)
- [Node.js - fs.Stats クラス](https://nodejs.org/api/fs.html#fs_class_fs_stats)

#### main.js

```javascript
var fs = require('fs');
fs.stat('/dir/path', function (err, stats) {
  if (err) throw err;
  console.log('stats: ' + JSON.stringify(stats));
});
```

`fs.Stats` オブジェクトには、`isDirectory()` メソッドが用意されているので、これを利用して指定したパスがディレクトリかどうかを調べることができます。

```javascript
fs.stat('/dir/path', function (err, stats) {
  if (err) throw err;
  if (stats.isDirectory()) {
    console.log('This is a directory');
  }
});
```

`fs.stat` の同期バージョンである `fs.statSync` も用意されていますが、可能な限り上記の非同期バージョンを使うように実装すべきです。
`fs.statSync` は戻り値で `fs.Stats` オブジェクトを返します。

- [Node.js - fs.statSync メソッド](https://nodejs.org/api/fs.html#fs_fs_statsync_path)

#### main.js

```javascript
var fs = require('fs');
var stats = fs.statSync('/dir/path');
if (stats.isDirectory()) {
  console.log('This is a directory');
}
```

