---
title: "Node.jsメモ: Node.js でお手軽リバースプロキシを作る (http-proxy)"
url: "p/7i26pjh/"
date: "2012-12-07"
tags: ["nodejs"]
aliases: [/nodejs/net/reverse-proxy.html]
---

Node.js の http-proxy モジュールについて
----

Node.js の **node-http-proxy** モジュールを使うと、簡単にリバースプロキシを作ることができます。

* [nodejitsu/node-http-proxy: A full-featured http proxy for node.js](https://github.com/nodejitsu/node-http-proxy/)

Squid や Apache などを使用してプロキシサーバを立ち上げる方法もありますが、Node.js でプロキシサーバを作成する利点は以下のようなものがあります（本家 README.md より抜粋）。

* Forward proxy も Reverse proxy も簡単に作れます
* オーバヘッドも Latency も少ないです
* JSON ベースで簡単に設定できます
* HTTPS をサポートしています
* WebSockets をサポートしています


node-http-proxy によるリバースプロキシの作成
----

{{< code lang="console" title="http-proxy モジュールのインストール" >}}
$ npm install http-proxy
{{< /code >}}

{{< code lang="js" title="myproxy.js（リバースプロキシの実装）" >}}
var httpProxy = require('http-proxy');
httpProxy.createServer(80, 'localhost').listen(8080);
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ node myproxy.js
{{< /code >}}

これで、`localhost:8080` へのアクセスが、`localhost:80` へ転送されるようになります。
リバースプロキシが 2 行で書けた！
٩(๑❛ᴗ❛๑)۶ わーぃ


もうちょっと高度に振り分けてみる
----

さらに拡張して、サーバアクセス時のホスト名に応じて、別々のポートへ処理を振り分けるようにしてみます（バーチャルホスト）。

* `aaa.example.com` としてアクセスしてきたら `8000` ポートに振り分け
* `bbb.example.com` としてアクセスしてきたら `9000` ポートに振り分け

{{< code lang="js" title="myproxy.js（ホスト名に応じた振り分け）" >}}
var httpProxy = require('http-proxy');
var options = {
  hostnameOnly: true,
  router: {
    'aaa.example.com': '127.0.0.1:8000',
    'bbb.example.com': '127.0.0.1:9000'
  }
}
httpProxy.createServer(options).listen(80);
{{< /code >}}

んー、お手軽！

