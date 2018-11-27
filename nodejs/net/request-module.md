---
title: "request モジュールを使用して HTTP 通信を行う"
date: "2013-12-14"
---

request モジュールのインストール
----

オープンソースとして公開されている [request モジュール](https://github.com/mikeal/request)を使用すると、標準の `http` モジュールをそのまま使うより手軽に HTTP リクエストを発行することができます。
最新バージョンの `request` モジュールは下記のようにインストールできます。

```
$ npm install request
```

ついでに `package.json` の `dependencies` 情報を更新するには、下記のように `--save` オプションを付けてインストールします。

```
$ npm install --save request
```

- 参考: [package.json で依存パッケージを管理する](../npm/package-dependencies.html)


request モジュールの使い方の基本
----

下記は、`request` モジュールを使用して `www.yahoo.co.jp` に HTTP でアクセスするサンプルです。

#### sample.js

```javascript
var request = require('request');

request.get('http://www.yahoo.co.jp', function(err, res, body) {
  if (err) {
    console.log('Error: ' + err.message);
    return;
  }
  console.log(body);
});
```

WebAPI の呼び出しなどで、HTTP リクエスト時に JSON データを送る場合は、例えば以下のようにします。

```javascript
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


HTTP 通信のリクエストヘッダを設定する
----

`request` モジュールによる HTTP リクエストを送信する際に、任意の HTTP ヘッダをセットするには、`get` メソッドの第一引数で URL 文字列を指定する代わりに、URL とヘッダ情報を含んだオブジェクトを指定します。
下記の例では、`User-Agent` ヘッダを付加して GET リクエストを送信しています。

```javascript
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

すべての HTTP リクエストに共通の HTTP ヘッダ情報が必要な場合は、デフォルトのヘッダとして下記のように設定しておくこともできます。

```javascript
request = request.defaults({
  headers: { 'User-Agent': 'request' }
});

request.get('https://api.github.com/repos/request/request', callback);
```


HTTP 通信のレスポンスコードやレスポンスヘッダを取得する
----

`request` モジュールを使って HTTP アクセスしたときの、レスポンスコードや、レスポンスヘッダの情報を取得するには下記のようにします。
`get` メソッドのコールバックで `response` オブジェクトが渡されるので、この中の `statusCode` プロパティ、`headers` プロパティを参照します。

```javascript
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


参考
----

`request` パッケージを、Promise (ECMAScript 2015) 対応させた `request-promise` というパッケージも提供されています。
機能的には `request` パッケージと同等ですが、こちらを利用するとコードを簡潔に記述できるかもしれません。

- [request-promise モジュールを使用して HTTP 通信を行う](request-promise-module.html)

