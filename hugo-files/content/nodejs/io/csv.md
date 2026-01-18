---
title: "Node.jsメモ: CSV ファイルを読み込む (csv)"
url: "p/r7ckr36/"
date: "2015-03-22"
tags: ["nodejs"]
aliases: /nodejs/io/csv.html
---

Node.js で CSV ファイルを簡単に扱うための [`csv` モジュール](https://github.com/adaltas/node-csv)が公開されています。
このモジュールは `npm` コマンドで下記のようにインストールできます。

{{< code lang="console" title="csv モジュールのインストール" >}}
$ npm install csv
{{< /code >}}

下記の例では、`input.csv` という入力ファイルを読み込んで、各行ごとに配列データとして取得しています。
`Parser` オブジェクトを作成するときに、`trim` オプションを `true` に設定しているので、カンマの前後のスペースが自動的に削除されています。

{{< code lang="javascript" title="main.js" >}}
import fs from 'node:fs';
import path from 'node:path';
import csv from 'csv';

const FILE = path.join(__dirname, 'input.csv');

const parser = csv.parse({trim: true}, (err, data) => {
  console.log(data);
});
fs.createReadStream(FILE).pipe(parser);
{{< /code >}}

{{< code title="input.csv" >}}
aaa, bbb, ccc
100, 200, 300
400, 500, 600
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node main
[ [ 'aaa', 'bbb', 'ccc' ],
  [ '100', '200', '300' ],
  [ '400', '500', '600' ] ]
{{< /code >}}

さらに、`columns` オプションを `true` に設定することで、CSV ファイルの一行目をヘッダとして扱うことができます。
この場合、一行目に記述したカラム名をプロパティ名としたオブジェクトとして、各行のデータが取得されます。

{{< code lang="javascript" title="main.js（一部抜粋）" >}}
const parser = csv.parse({columns: true, trim: true}, (err, data) => {
  console.log(data);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node main
[ { aaa: '100', bbb: '200', ccc: '300' },
  { aaa: '400', bbb: '500', ccc: '600' } ]
{{< /code >}}

カラム名をプログラム内で指定するには、`columns` オプションにカラム名の配列を指定します。

{{< code lang="javascript" title="main.js（一部抜粋）" >}}
const parser = csv.parse({columns: ['c1', 'c2', 'c3'], trim: true}, (err, data) => {
  console.log(data);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node main
[ { c1: 'aaa', c2: 'bbb', c3: 'ccc' },
  { c1: '100', c2: '200', c3: '300' },
  { c1: '400', c2: '500', c3: '600' } ]
{{< /code >}}

