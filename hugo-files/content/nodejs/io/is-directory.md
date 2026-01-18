---
title: "Node.jsメモ: 指定したパスがディレクトリかどうか調べる (Stats.isDirectory)"
url: "p/af2r6mq/"
date: "2015-05-09"
tags: ["nodejs"]
aliases: /nodejs/io/is-directory.html
---

Node.js の標準モジュール `fs` が提供している **`fs.stat(path, callback)`** 関数を使用すると、指定したファイルやディレクトリの情報を表す **`fs.Stats`** オブジェクトを取得することができます。

- 参考: [Node.js - `fs.stat` メソッド](https://nodejs.org/api/fs.html#fsstatpath-options-callback)
- 参考: [Node.js - `fs.Stats` クラス](https://nodejs.org/api/fs.html#fs_class_fs_stats)

{{< code lang="js" title="main.js" >}}
import fs from 'node:fs';

fs.stat('/dir/path', (err, stats) => {
  if (err) throw err;
  console.log(`stats: ${JSON.stringify(stats)}`);
});
{{< /code >}}

`fs.Stats` オブジェクトには、**`isDirectory()`** メソッドが用意されているので、これを利用して指定したパスがディレクトリかどうかを調べることができます。

```js
fs.stat('/dir/path', (err, stats) => {
  if (err) throw err;
  if (stats.isDirectory()) {
    console.log('This is a directory');
  }
});
```

`fs.stat` の同期バージョンである **`fs.statSync`** も用意されていますが、可能な限り上記の非同期バージョンを使うように実装すべきです。
`fs.statSync` は戻り値で `fs.Stats` オブジェクトを返します。

- 参考: [Node.js - `fs.statSync` メソッド](https://nodejs.org/api/fs.html#fsstatsyncpath-options)

{{< code lang="js" title="main.js" >}}
import fs from 'node:fs';

const stats = fs.statSync('/dir/path');
if (stats.isDirectory()) {
  console.log('This is a directory');
}
{{< /code >}}

