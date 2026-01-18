---
title: "Node.jsメモ: node-fetch モジュールを使用して HTTP 通信を行う"
url: "p/6hndyxs/"
date: "2020-08-17"
tags: ["nodejs"]
aliases: [/nodejs/net/node-fetch.html]
---

node-fetch とは
----

`node-fetch` モジュールは、Node.js アプリで Web ブラウザと同様の `fetch` 関数を扱えるようにするためのライブラリです。
これを使うと、Promise ベースの HTTP 通信を行うことができます。

- [node-fetch - npm](https://www.npmjs.com/package/node-fetch)

2020 年 2 月に HTTP 通信モジュールのデファクトスタンダードであった `request` モジュールやそれに依存する `request-promise` モジュールが非推奨（deprecated）になってしまったため、代わりの HTTP 通信モジュールが必要です。
代替モジュールとしては `axios` なども人気がありますが、`node-fetch` モジュールであれば、Web ブラウザの JavaScript から使える `fetch` 関数と同じ感覚で使えるため、API の使用方法を新しく学ぶ手間が省けます。


node-fetch のインストール
----

`node-fetch` は次のようにインストールできます。
コンパクトなモジュールなので、インストールは一瞬で終わります。

```
$ npm install node-fetch --save
```


node-fetch を使用する
----

次のサンプルコードは、`node-fetch` が提供する `fetch` 関数を使って、HTTP GET リクエストを送信する例です。
Promise に対応しているので、非同期処理を `.then` ～ `.catch` のチェーンで簡潔に記述することができます。

```js
const fetch = require('node-fetch');
const URL = 'https://example.com';

fetch(URL)
  .then(res => {
    if (!res.ok) {
      // 200 番台以外のレスポンスはエラーとして処理
      throw new Error(`${res.status} ${res.statusText}`);
    }
    return res.text();
  })
  // レスポンス本文のテキスト
  .then(text => console.log(text))
  // エラーはここでまとめて処理
  .catch(err => console.error(err));
```

`async` / `await` を使って、次のように同期的な処理として記述することもできます。

```js
const fetch = require('node-fetch');
const URL = 'https://example.com';

async function main() {
  try {
    const res = await fetch(URL);
    if (!res.ok) {
      throw new Error(`${res.status} ${res.statusText}`);
    }
    const text = await res.text();
    console.log(text);
  } catch (err) {
    console.error(err);
  }
}

main();
```

