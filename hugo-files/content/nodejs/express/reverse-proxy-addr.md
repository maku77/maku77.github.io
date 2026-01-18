---
title: "Node.jsメモ: リバースプロキシ経由で Express サーバにアクセスしたときのクライアントのアドレスを取得する (x-forwarded-for)"
url: "p/hvoegru/"
date: "2014-03-09"
tags: ["nodejs"]
aliases: /nodejs/express/reverse-proxy-addr.html
---

クライアントからの Node サーバーへのアクセスがリバースプロキシ経由になる場合、**`req.ip`** で取得できる IP アドレスは、リバースプロキシのアドレスになってしまいます。
これを防ぐには、Express の `Application` オブジェクトを使って次のように設定します。

```js
app.set('trust proxy', true);
```

この機能を使用する場合、リバースプロキシが **`X-Forwarded-For`** (XFF) ヘッダーをサポートしている必要があります。
`X-Forwarded-For` ヘッダーは、プロキシやロードバランサを経由したときに「元のクライアントの IP アドレス」をサーバ側に伝えるための事実上の標準ヘッダーです。

