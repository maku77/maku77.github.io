---
title: "request-promise モジュールを使用して HTTP 通信を行う"
date: "2018-11-27"
---

request パッケージと request-promise パッケージ
----

「[request モジュールを使用して HTTP 通信を行う](request-module.html)」の記事で説明しているように、`request` パッケージを使用すると、HTTP 通信による情報取得を簡単に行えます。
`request` パッケージの拡張版として、**`request-promise`** パッケージが提供されており、`request` パッケージの代わりに使用することで、ECMAScript 2015 のPromise、ECMAScript 2017 の async/await を使用したコーディングを行うことができます。

- [request-promise パッケージ](https://github.com/request/request-promise)

Promise や async/await の使い方に関しては下記の記事で説明していますが、簡単に言うと、従来のコールバックを使用したコーディングよりもわかりやすいコーディングが行えるようになります。

- [Promise オブジェクトで連続するコールバック処理を簡潔に記述する](/js/async/promise.html)
- [Promise な非同期処理を async/await でさらに読みやすくする](/js/async/async-await.html)


request-promise パッケージのインストール
----

`request-promise` パッケージは、`npm install` コマンドで簡単にインストールすることができます。
間接的に `request` パッケージに依存している (peed dependencies) ため、`request` パッケージも一緒にインストールしておく必要があります。

~~~
$ npm install request
$ npm install request-promise
~~~

ついでに `package.json` の `dependencies` エントリに追加したい場合は、次のように `--save` オプションを追加してインストールします。

~~~
$ npm install --save request
$ npm install --save request-promise
~~~

TypeScript で開発している方は、DefinitelyTyped プロジェクトで提供されている型情報 [@types/request-promise](https://www.npmjs.com/package/@types/request-promise) をインストールしましょう。

~~~
$ npm install --save-dev @types/request-promise
~~~


request-promise の基本的な使い方
----

`request-promise` による HTTP リクエストは、下記のように `Promise.then`、`Promise.catch` を使用してハンドルすることができます。

~~~ javascript
const rp = require('request-promise');

rp('https://www.yahoo.co.jp')
  .then(function (htmlString) {
    console.log(htmlString);
  })
  .catch(function (err) {
    console.error(err);
  });
~~~

さらに、ECMAScript 2017 の async/await を使用すると、同期的な記述を行うことができます。

~~~ javascript
const rp = require('request-promise');

async function getHtml(url) {
  try {
    let html = await rp(url);
    console.log(html)
  } catch (err) {
    console.error(err);
  }
}

getHtml('https://www.yahoo.co.jp');
~~~

### TypeScript の @types/request-promise モジュールを使う場合

TypeScript で型安全なコードを書きたいのであれば、次のような感じでインポートして使用します。

```ts
import * as rp from 'request-promise';

async function getUserName(userId: string): Promise<string | undefined> {
  const options = {
    url: "https://api.example.com/users/",
    method: 'GET',
    qs: { id: userId },
  };

  try {
    return await rp.get(options);
  } catch (err) {
    console.error(`ERROR: ${err.message}`);
    return undefined;
  }
}

const name = await getUserName('Maku');
if (name) {
  console.log(name);
}
```

