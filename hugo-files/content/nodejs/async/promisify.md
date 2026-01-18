---
title: "Node.jsメモ: util.promisify でコールバックベースの関数を Promise 化する"
url: "p/62d6wuc/"
date: "2020-06-03"
tags: ["nodejs"]
aliases: /nodejs/async/promisify.html
---

util.promisify の概要
----

Node.js v8.0.0 で導入された [util.promisify 関数](https://nodejs.org/api/util.html#util_util_promisify_original) を使用すると、コールバックベースの関数を簡単に `Promise` クラスでラップすることができます。

次の例では、`fs.stat` 関数を `Promise` 化した `stat` 関数を作成しています。

```js
const util = require('util');
const fs = require('fs');

const stat = util.promisify(fs.stat);
stat('.').then((stats) => {
  // Do something with `stats`
}).catch((err) => {
  // Handle the error.
});
```

`util.promisify()` は、`Promise` 対応されていない 3rd パーティ製ライブラリを使用する際に威力を発揮します。

`Promise` クラス自体の詳しい使い方については下記の記事を参照してください。

- 参考: [Promise オブジェクトで連続するコールバック処理を簡潔に記述する ｜ まくまくJavaScriptノート](https://maku77.github.io/js/async/promise.html)


自力で Promise 対応した場合
----

ここでは、次のような、割り算の計算結果をコールバックで返す関数を題材にして説明します。

```js
function div(num1, num2, callback) {
  if (num2 == 0) {
    callback('ERROR: Zero division');
  } else {
    callback(null, num1 / num2);
  }
}
```

上記の例では、0 除算によるエラーが発生した場合、コールバック関数の第 1 パラメータでエラーメッセージを返すように実装しています。
このように、コールバック関数の第 1 パラメータをエラー情報として使うものを、[エラー・ファースト・コールバック (error-first callback)](https://jsprimer.net/basic/async/#error-first-callback) といい、非同期関数のコールバックは伝統的にこのような形式に従っていました。

上記の `div` 関数をそのまま使おうとすると、下記のようなコードになります。

```js
div(5, 2, (err, value) => {
  if (err) {
    console.error(err);
    return;
  }
  console.log(`Result: ${value}`);
});
```

`Promise` チェーン (`then` & `catch`) による呼び出しを行えるように、自力で `Promise` 化を行う場合は、次のような実装をすることになります。

```js
function divPromise(num1, num2) {
  return new Promise((resolve, reject) => {
    div(num1, num2, (err, value) => {
      if (err) {
        reject(err);
      } else {
        resolve(value);
      }
    });
  });
}
```

これで、次のようなチェーン呼び出しができるようになります。

```js
divPromise(5, 2).then(value => {
  console.log(`Result: ${value}`);
}).catch(err => {
  console.error(err);
});
```

この例はとてもシンプルなので、ありがたみが感じられませんが、コールバック呼び出しの構造が複雑になってくると `Promise` 化しないとやっていられなくなります。
でも、上記のような `Promise` 化のためのコードをたくさん書くのはとても面倒です。
そこで、`util.promisify()` の出番です。


util.promisify による Promise 化
----

`util.promisify()` 関数を使用すると、コールバックベースの関数を簡単に `Promise` 化する（戻り値を `Promise` オブジェクトにする）ことができます。

例えば、前述の `div()` 関数を `Promise` 化するには、下記のようにするだけで済みます。

```js
const util = require('util');
const divPromise = util.promisify(div);
```

`util.promisify()` で `Promise` 化する関数は、次のような条件を満たすエラーファーストコールバックスタイルの関数でなければいけません。

* 最後のパラメータとしてコールバック関数を受け取る
* コールバック関数の第 1 パラメータはエラー情報を示す `(err, value, ...)`
* 関数の呼び出しが正常に終わった場合、コールバック関数の第 1 パラメータは `null` で呼び出される

`util.promisify()` で `Promise` 化した関数は、自力で `Promise` 化したものと同様に、次のようにチェーン呼び出しできるようになります。

```js
divPromise(5, 2).then(result => {
  console.log(`Result: ${result}`);
}).catch(err => {
  console.error(err);
});
```

ECMAScript 2017 の `async/await` 構文を使えば、次のようにも書けます。

```js
async function main() {
  try {
    const result = await divPromise(5, 2);
    console.log(`Result: ${result}`);
  } catch (err) {
    console.error(err);
  }
}

main();
```

コールバックのコードが消えてとてもスッキリします。


おまけ: TypeScript で util.promisify を使う
----

上記では JavaScript のサンプルを示しましたが、TypeScript でもう少しタイプセーフに記述したときのサンプルも載せておきます。

まずは、題材とした `div` 関数の実装から。

```typescript
function div(num1: number, num2: number,
    callback: (err: any, value: number | null) => void) {
  if (num2 == 0) {
    callback('ERROR: Zero division', null);
  } else {
    callback(null, num1 / num2);
  }
}
```

次に、`Promise` 化とそれを使用するコードです。

```typescript
// npm install --save-dev @types/node
import * as util from 'util';

// div 関数を Promise 化
const divPromise = util.promisify(div);

// Promise 化した関数を使う
divPromise(5, 2).then(value => {
  console.log(`Result: ${value}`);
}).catch(err => {
  console.error(err);
});
```

