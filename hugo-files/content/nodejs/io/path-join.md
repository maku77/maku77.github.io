---
title: "Node.jsメモ: パスを結合する (path.join)"
url: "p/32q8w98/"
date: "2013-11-19"
tags: ["nodejs"]
aliases: /nodejs/io/path-join.html
---

ディレクトリ名を表すパスと、ディレクトリ名あるいはファイル名を結合するには、Node.js の標準モジュール `path` が提供している `path.join` メソッドを使用します。

{{< code lang="js" title="例: 実行中のスクリプトのディレクトリ名と hoge.txt というファイル名を結合したパスを取得" >}}
import path from 'node:path';

const filepath = path.join(__dirname, 'hoge.txt');

console.log(filepath);
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
D:\y\sandbox\node> node sample
D:\y\sandbox\node\hoge.txt
{{< /code >}}

