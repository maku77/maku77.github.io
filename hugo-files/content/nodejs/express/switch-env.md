---
title: "Node.jsメモ: Express の動作を環境ごとに切り替える（NODE_ENV 変数）"
url: "p/3eqz2eo/"
date: "2014-03-09"
tags: ["nodejs"]
aliases: /nodejs/express/switch-env.html
---

Express は、**`NODE_ENV`** 環境変数を見て、現在の実行環境を判断する機能を持っています。
現在設定されている `NODE_ENV` の値は、**`app.get('env')`** で参照可能です。

{{< code lang="javascript" title="app.js" >}}
var express = require('express');
var app = express();
console.log(app.get('env'));  // development
{{< /code >}}

`NODE_ENV` は `node` コマンド実行時に、シェルから設定することができます。

```console
$ NODE_ENV=development node app
development

$ NODE_ENV=production node app
production
```

何も設定しなかった場合は、`NODE_ENV` の値はデフォルトで `development` になります。

```console
$ node app
development
```

この値を利用して、実行環境ごとに異なる処理を実行することができます。
下記の例では、`development` 環境のときのみ、サーバ側のアクセスログをターミナルに表示するようにしています。

```javascript
var logger = require('morgan');

if ('development' == app.get('env')) {
  app.use(logger('dev'));
}
```

{{% note %}}
Express 3 までは、`app.configure()` メソッドを使用することでも実行環境ごとの処理を記述することができましたが、Express 4 で `app.configure()` は削除されています。
代わりに上記のように、`app.get('env')` か、`app.settings.env` か、`process.env.NODE_ENV` を使用して実行環境を判断する必要があります。
{{% /note %}}

環境ごとの振る舞いの違いを別のファイルにまとめておくのもよいでしょう。
例えば、以下のような設定ファイルを用意して、

{{< code lang="json" title="config.json" >}}
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
{{< /code >}}

以下のように読み込んで使うことができます。

{{< code lang="javascript" title="app.js" >}}
var express = require('express');
var app = express();

var config = require('./config.json')[app.get('env')];
console.log(config.server);
console.log(config.user);
console.log(config.password);
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ node app
http://localhost/
test_user
test_password

$ NODE_ENV=production node app
http://example.com/
prod_user
prod_password
{{< /code >}}

