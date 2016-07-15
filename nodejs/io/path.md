---
title: 様々なディレクトリパスの取得やパスの結合方法
created: 2013-11-19
---

カレントディレクトリを取得する
----

```javascript
var basedir = process.cwd();  //=> 'C:\\Users\\maku'
```

実行中のスクリプトが格納されたディレクトリの絶対パスを取得する
----

```javascript
var dir = __dirname;
```

パスを結合する
----

ディレクトリ名を表すパスと、ディレクトリ名あるいはファイル名を結合するには、Node.js の標準モジュール `path` が提供している `path.join` を使用します。

#### 例: 実行中のスクリプトのディレクトリ名と hoge.txt というファイル名を結合したパスを取得

```javascript
var path = require('path');
var filepath = path.join(__dirname, 'hoge.txt');

console.log(filepath);
```

#### 実行結果

```
D:\y\sandbox\node> node sample
D:\y\sandbox\node\hoge.txt
```

