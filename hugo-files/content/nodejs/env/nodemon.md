---
title: "Node.jsメモ: nodemon で Node.js サーバの再起動を自動化する"
url: "p/rvghobv/"
date: "2019-06-07"
tags: ["nodejs"]
aliases: /nodejs/env/nodemon.html
---

nodemon とは？
----

Express や Restify で Web サーバの実装をしていると、ソースコードを修正するたびに <kbd>Ctrl + C</kbd> でサーバを強制終了し、`node app.js` で再起動するという操作が必要になります。
`node` コマンドの代わりに [nodemon](https://nodemon.io/) コマンドを使用すると、**ソースコードの変更を監視し、自動的に `node` コマンドを再起動**してくれます。

```console
$ nodemon app.js
```

`node` の代わりに `nodemon` とタイプするだけで、快適な実装生活を送ることができます。
Node.js でサーバ系のコーディングを行うときは `nodemon` を使うようにしましょう！


nodemon のインストール
----

`nodemon` コマンドは、Node.js 付属の `npm` コマンドで簡単にインストールすることができます。

```console
$ npm install -g nodemon
```

システム全体で使用可能なコマンドとしてインストールしたいので、`-g` オプションを付けて実行してください。


nodemon を使ってみる
----

ここでは、Node.js の標準モジュールである `http` を使って簡単な Web サーバを作り、それを `nodemon` で起動してみます。
下記は、`Hello` という返答を返すだけの簡単な Web サーバプログラムです。

{{< code lang="js" title="app.js" >}}
var server = require('http').createServer();

server.on('request', function(req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.write('Hello\n');
  res.end();
});

server.listen(51200);
console.log('Server listening on port 51200');
{{< /code >}}

`node` コマンドの代わりに `nodemon` コマンドを使ってこのプログラムを実行します。

```console
$ nodemon app.js
[nodemon] 1.19.1
[nodemon] to restart at any time, enter `rs`
[nodemon] watching: *.*
[nodemon] starting `node app.js`
Server listening on port 51200
```

出力に `[nodemon]` というプレフィックスが付いている行は、`nodemon` によって出力されているメッセージです。
自分で実装したプログラムの出力はそのまま表示されています（`Server listening on port 51200` の部分）。

`nodemon` はデフォルトでは `*.js` ファイルや `*.json` ファイルの変更を監視しており、変更を検出すると自動的に `node` コマンドを再実行します。
例えば、上記の `app.js` の中の `Hello` という文字列を `Hogeeeee` に変更して保存すると、下記のように表示され、Web サーバが自動的に再起動します。

```console
[nodemon] restarting due to changes...
[nodemon] starting `node app.js`
Server listening on port 51200
```

監視するファイルをオプションで絞り込んだり、`package.json` にその設定を書いておくこともできます。
詳しくは、**`nodemon --help`** や、下記のドキュメントサイトを参照してください。

- [nodemon - Monitor for any changes in your node.js application and automatically restart the server](https://github.com/remy/nodemon#nodemon)

