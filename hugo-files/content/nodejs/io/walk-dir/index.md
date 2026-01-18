---
title: "Node.jsメモ: ディレクトリ内のファイルを列挙する"
url: "p/ke25m82/"
date: "2015-05-09"
tags: ["nodejs"]
aliases: /nodejs/io/walk-dir.html
---

ディレクトリ内のファイルやディレクトリを列挙する（一階層のみ）
----

Node.js の標準モジュールである `fs` モジュールの **`readdir()`** を使用して、指定したディレクトリ内のファイルやディレクトリを列挙することができます。

- [`fs.readdir` function](https://nodejs.org/api/fs.html#fs_fs_readdir_path_options_callback)

見つかったファイルリストは、コールバック関数の第 2 引数として渡されます。
ドットで始まるファイルやディレクトリは検索対象になりますが、カレントディレクトリ (`.`) や、親ディレクトリ (`..`) は結果に入りません。

下記は、カレントディレクトリ内のファイルとディレクトリを列挙するサンプルです。

{{< code lang="js" title="sample.js" >}}
import fs from 'node:fs';

fs.readdir('.', (err, files) => {
  if (err) {
    console.error(err);
  } else {
    console.log(files);
  }
});
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ node sample
[ 'dummy.txt', 'dummy_dir', 'sample.js' ]
{{< /code >}}

指定したディレクトリが存在しない場合は、`ENOENT (-2)` エラーが発生します。

```
D:\y\sandbox\node> node sample
{ Error: ENOENT: no such file or directory, scandir 'D:\y\sandbox\node\aaaa'
    at Error (native)
  errno: -4058,
  code: 'ENOENT',
  syscall: 'scandir',
  path: 'D:\\y\\sandbox\\node\\aaaa' }
```


ディレクトリ内のファイルを再帰的に列挙する
----

下記の `dirutil` モジュールは、指定したディレクトリ内のファイルを**再帰的に**列挙する `walkDir` 関数を提供します。

- [dirutil.js](./dirutil.js)

例えば、ディレクトリ構成が下記のようになっている時、

```
dir1
  +— file1
  +— dir2
  | +— file2
  +— dir3
    +— file3
```

`walkDir('dir1', callback)` と呼び出した場合に、`callback` の第二引数に、`dir2/file2` のようなファイルパスを表す文字列が渡されて呼び出されます。
このコールバックはファイルが見つかるごとに呼び出されます（ファイルパスをリスト形式で受け取るバージョンは後述）。

{{< code lang="js" title="使用例" >}}
import { walkDir } from './dirutil.js';

walkDir('dir1', (err, path) => {
  console.log(path);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
file1
dir2/file2
dir3/file3
{{< /code >}}

上記の例では、ファイルが見つかる度にコールバックが呼び出されますが、この実装はちょっと使いにくいかもしれません。
再帰的に検索して見つかったファイルのパスを、配列データとしてまとめて受け取るには、下記のように実装します。

- [dirutil.js（見つかったファイルを配列で返すバージョン）](./dirutil2.js)

{{< code lang="js" title="使用例" >}}
import { walkDir } from './dirutil.js';

walkDir('dir1', (err, list) => {
  console.log(list);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
[ 'file1', 'dir2/file2', 'dir3/file3' ]
{{< /code >}}

