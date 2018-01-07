---
title: Express の動作環境を切り替える（NODE_ENV 変数）
date: "2014-03-09"
---

Express は、`NODE_ENV` 環境変数を見て、現在の実行環境を判断する機能を持っています。
`NODE_ENV` の値は、`app.get('env')` で取得可能です。

#### app.js

```javascript
var express = require('express');
var app = express();
console.log(app.get('env'));
```

`NODE_ENV` は `node` コマンド実行時に、シェルから設定することができます。

```
$ NODE_ENV=development node app
development

$ NODE_ENV=production node app
production
```

何も設定しなかった場合は、`NODE_ENV` の値はデフォルトで `development` になります。

```
$ node app
development
```

これを利用して、ミドルウェアの読み込みを、実行環境ごとに切り替えたりすることができます。
下記の例では、`development` 環境のときのみ、サーバ側のアクセスログをターミナルに表示するようにしています。

```javascript
var logger = require('morgan');

if ('development' == app.get('env')) {
  app.use(logger('dev'));
}
```

Express 3 までは、`app.configure()` メソッドを使用することでも実行環境ごとの処理を記述することができましたが、Express 4 で `app.configure()` は削除されています。
代わりに上記のように、`app.get('env')` か、`app.settings.env` か、`process.env.NODE_ENV` を使用して実行環境を判断する必要があります。

- 参考: http://expressjs.com/migrating-4.html#changes

独自の設定ファイルを用意しておいて、`app.get('env')` の値を利用して、環境ごとに設定を切り替えることも可能です。
例えば、以下のような設定ファイルを用意して、

#### config.json

```json
{
  "development": {
    "server": "http://localhost/",
    "user": "test_user",
    "password": "test_password"
  },
  "production": {
    "server": "http://example.com/",
    "user": "prod_user",
    "password": "prod_password"
  }
}
```

以下のようにロードして使うことができます。

#### app.js

```javascript
var express = require('express');
var app = express();

var config = require('./config.json')[app.get('env')];
console.log(config.server);
console.log(config.user);
console.log(config.password);
```

#### 実行例

```
$ node app
http://localhost/
test_user
test_password

$ NODE_ENV=production node app
http://example.com/
prod_user
prod_password
```

