---
title: "CSV ファイルを読み込む"
date: "2015-03-22"
---

Node.js で CSV ファイルを簡単に扱うための `csv` モジュールが公開されています。
このモジュールは `npm` コマンドで下記のようにインストールできます。

```
$ npm install csv
```

このモジュールに関する Web サイトはこちらです。

- http://csv.adaltas.com/
- https://github.com/wdavidw/node-csv

下記の例では、`input.csv` という入力ファイルを読み込んで、各行ごとに配列データとして取得しています。
`Parser` オブジェクトを作成するときに、`trim` オプションを `true` に設定しているので、カンマの前後のスペースが自動的に削除されています。

#### main.js

```javascript
var csv = require('csv');
var fs = require('fs');
var path = require('path');
var FILE = path.join(__dirname, 'input.csv');

var parser = csv.parse({trim:true}, function(err, data) {
  console.log(data);
});
fs.createReadStream(FILE).pipe(parser);
```

#### input.csv

```
aaa, bbb, ccc
100, 200, 300
400, 500, 600
```

#### 実行結果

```
$ node main
[ [ 'aaa', 'bbb', 'ccc' ],
  [ '100', '200', '300' ],
  [ '400', '500', '600' ] ]
```

さらに、`columns` オプションを `true` に設定することで、CSV ファイルの一行目をヘッダとして扱うことができます。
この場合、一行目に記述したカラム名をプロパティ名としたオブジェクトとして、各行のデータが取得されます。

#### main.js（一部抜粋）

```javascript
var parser = csv.parse({columns:true, trim:true}, function(err, data) {
  console.log(data);
});
```

#### 実行結果

```
$ node main
[ { aaa: '100', bbb: '200', ccc: '300' },
  { aaa: '400', bbb: '500', ccc: '600' } ]
```

カラム名をプログラム内で指定するには、`columns` オプションにカラム名の配列を指定します。

#### main.js（一部抜粋）

```javascript
var parser = csv.parse({columns:['c1', 'c2', 'c3'], trim:true}, function(err, data) {
  console.log(data);
});
```

#### 実行結果

```
$ node main
[ { c1: 'aaa', c2: 'bbb', c3: 'ccc' },
  { c1: '100', c2: '200', c3: '300' },
  { c1: '400', c2: '500', c3: '600' } ]
```

