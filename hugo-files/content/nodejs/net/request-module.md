---
title: "Node.jsメモ: request モジュールを使用して HTTP 通信を行う"
url: "p/5y9mne4/"
date: "2013-12-14"
tags: ["nodejs"]
aliases: [/nodejs/net/request-module.html]
---

## request モジュールのインストール

オープンソースとして公開されている [request モジュール](https://github.com/mikeal/request)を使用すると、標準の `http` モジュールをそのまま使うより手軽に HTTP リクエストを発行することができます。
最新バージョンの `request` モジュールは以下のようにインストールできます。

```console
$ npm install request
```

`package.json` の `dependencies` 情報も同時に更新するには、`--save` オプションを付けてインストールします。

```console
$ npm install --save request
```

- 参考: [package.json で依存パッケージを管理する](/p/dfsuvkc/)


## request モジュールの使い方の基本

以下は、`request` モジュールを使用して `www.yahoo.co.jp` に HTTP でアクセスするサンプルです。

{{< code lang="js" title="sample.js" >}}
var request = require('request');

request.get('http://www.yahoo.co.jp', function(err, res, body) {
  if (err) {
    console.log('Error: ' + err.message);
    return;
  }
  console.log(body);
});
{{< /code >}}

Web API の呼び出しなどで、HTTP リクエスト時に JSON データを送る場合は、以下のようにします。

```js
var request = require('request');
var qs = require('querystring');

var query = { aaa: 100, bbb: 200 };
var url = 'http://example.com/api?' + qs.stringify(query);

request.get({ url: url, json: true }, function(err, res, body) {
  if (err) {
    console.log('Error: ' + err.message);
    return;
  }
  console.log(body);
});
```


## HTTP 通信のリクエストヘッダを設定する

`request` モジュールで HTTP リクエストを送信する際に、任意の HTTP ヘッダを設定するには、`get` メソッドの第一引数で URL 文字列を指定する代わりに、URL とヘッダ情報を含んだオブジェクトを指定します。
以下の例では、`User-Agent` ヘッダを付加して GET リクエストを送信しています。

```js
var request = require('request');

var options = {
  url: 'https://api.github.com/repos/request/request',
  headers: {
    'User-Agent': 'request'
  }
};

function callback(error, response, body) {
  if (!error && response.statusCode == 200) {
    var info = JSON.parse(body);
    console.log(info.stargazers_count + " Stars");
    console.log(info.forks_count + " Forks");
  }
}

request.get(options, callback);
```

すべての HTTP リクエストに共通の HTTP ヘッダ情報が必要な場合は、デフォルトのヘッダとして以下のように設定しておくこともできます。

```js
request = request.defaults({
  headers: { 'User-Agent': 'request' }
});

request.get('https://api.github.com/repos/request/request', callback);
```


## HTTP 通信のレスポンスコードやレスポンスヘッダを取得する

`request` モジュールを使って HTTP アクセスしたときのレスポンスコードやレスポンスヘッダの情報を取得するには、以下のようにします。
`get` メソッドのコールバックで渡される `response` オブジェクトの `statusCode` プロパティと `headers` プロパティを参照します。

```js
var request = require('request');

request.get('http://example.com/', function(err, res, body) {
  if (err) {
    console.log('Error: ' + err.message);
    return;
  }
  console.log('statusCode: ' + res.statusCode)
  console.log('headers:');
  for (var key in res.headers) {
    console.log('  ' + key + ': ' + res.headers[key]);
  }
});
```


## 参考

`request` パッケージを Promise (ECMAScript 2015) 対応させた `request-promise` というパッケージも提供されています。
機能的には `request` パッケージと同等ですが、こちらを利用するとコードをより簡潔に記述できます。

- [request-promise モジュールを使用して HTTP 通信を行う](/p/r8qw3qs/)

