---
title: npm の HTTP プロキシ設定
date: "2012-11-26"
---

プロキシ経由でインターネットに接続しなければいけない環境（企業内ネットワークなど）では、`npm` コマンドのプロキシ設定を行う必要があります。
これをやっておかないと、`npm install` コマンドによるネットワーク経由でのモジュールのインストールができません。

#### プロキシを設定する

```
$ npm config set proxy http://proxy.example.com:8080/
```

#### プロキシ設定を削除する

```
$ npm config delete proxy  #設定を削除する場合
```

#### 現状のプロキシ設定を確認する

```
$ npm config get proxy
http://proxy.example.com:8080/
```

