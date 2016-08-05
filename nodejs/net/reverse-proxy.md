---
title: Node.js でお手軽リバースプロキシを作る
created: 2012-12-07
---

Node.js の http-proxy モジュールについて
----

**node-http-proxy** モジュールを使うと、簡単にリバースプロキシを作れます。

* [nodejitsu/node-http-proxy: A full-featured http proxy for node.js](https://github.com/nodejitsu/node-http-proxy/)

Squid や Apache などを使用してプロキシサーバを立ち上げる方法もありますが、Node.js でプロキシサーバを作成する利点は以下のようなものがあります（本家 README.md より抜粋）。

* Forward proxy も Reverse proxy も簡単に作れるよ！
* オーバヘッドも Latency も少ないよ！
* JSON ベースで簡単に設定できるよ！
* HTTPS サポート！
* WebSockets サポート！


node-http-proxy によるリバースプロキシの作成
----

#### http-proxy モジュールのインストール

```
$ npm install http-proxy
```

#### myproxy.js

```javascript
var httpProxy = require('http-proxy');
httpProxy.createServer(80, 'localhost').listen(8080);
```

#### 実行例

```
$ node myproxy.js
```

これで、`localhost:8080` へのアクセスが、`localhost:80` へ転送されるようになります。
リバースプロキシが２行で書けた（゜O゜）！


もうちょっと高度に振り分けてみる
----

さらに拡張して、サーバアクセス時のホスト名に応じて、別々のポートへ処理を振り分けるようにしてみます（バーチャルホスト）。

* `aaa.example.com` としてアクセスしてきたら `8000` ポートに振り分け
* `bbb.example.com` としてアクセスしてきたら `9000` ポートに振り分け

#### myproxy.js

```javascript
var httpProxy = require('http-proxy');
var options = {
  hostnameOnly: true,
  router: {
    'aaa.example.com': '127.0.0.1:8000',
    'bbb.example.com': '127.0.0.1:9000'
  }
}
httpProxy.createServer(options).listen(80);
```

んー、お手軽！

