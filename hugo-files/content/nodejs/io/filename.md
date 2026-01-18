---
title: "Node.jsメモ: 実行中の JavaScript ファイルのパスやディレクトリ名を取得する (__filename, __dirname)"
url: "p/d48fxh9/"
date: "2018-11-22"
tags: ["nodejs"]
aliases: /nodejs/io/filename.html
---

__filename と __dirname でモジュールのパスを取得する
----

Node.js のプログラム内から **`__filename`** や **`__dirname`** を参照すると、現在実行中のモジュール（jsファイル）の絶対パスや、そのファイルが含まれているディレクトリの絶対パスを取得することができます。

- [`__filename`](https://nodejs.org/api/modules.html#modules_filename): 現在実行中のモジュールの絶対パス
- [`__dirname`](https://nodejs.org/api/modules.html#modules_dirname): 現在実行中のモジュールの親ディレクトリの絶対パス

{{< code lang="js" title="サンプルコード (D:/sandbox/sample.js)" >}}
console.log(__filename);
console.log(__dirname);
{{< /code >}}

{{< code title="実行結果" >}}
D:\sandbox\node\sample.js
D:\sandbox\node
{{< /code >}}


カレントディレクトリを取得する process.cwd()
----

`__dirname` と似たようなものに、カレントディレクトリを調べる **`process.cwd()`** がありますが、こちらは現在のプロセスのカレントディレクトリを調べるものなので、JavaScript ファイルが置かれているパスとは関係がありません。
もちろん、JavaScript ファイルがあるディレクトリで `node` コマンドを実行すれば `__dirname` と同じ結果になりますが、`process.chdir()` などでカレントディレクトリを変更すると、`process.cwd()` の結果は変化します。

{{< code lang="js" title="sample.js" >}}
console.log(__dirname);
console.log(process.cwd());

process.chdir('..');  // 上のディレクトリへ移動

console.log(__dirname);
console.log(process.cwd());
{{< /code >}}

{{< code title="実行結果" >}}
D:\sandbox\node
D:\sandbox\node
D:\sandbox\node
D:\sandbox
{{< /code >}}

