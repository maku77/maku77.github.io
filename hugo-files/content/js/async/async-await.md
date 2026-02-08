---
title: "JavaScriptメモ: Promise な非同期処理を async/await でさらに読みやすくする"
url: "p/sdq298e/"
date: "2018-11-26"
tags: ["javascript"]
aliases: [/js/async/async-await.html]
---

async, await の概要
----

ECMAScript 2017 の **async、await** は、Promise をベースに設計された非同期処理のためのシンタックスシュガーです。
`async`、`await` の仕組みを理解するには、Promise の仕組みを理解しておく必要があります。
Promise に関しての詳細は下記の記事を参考にしてください。

- 参考: [Promise オブジェクトで連続するコールバック処理を簡潔に記述する](/p/j9mgehj/)

ECMAScript 2017 では、従来の `function` による関数定義の前に、**`async`** キーワードをつけることで、非同期関数であることを示すことができます。
`async` の付いた非同期関数の中で、**`await`** キーワードをつけて `Promise` 対応した関数を呼び出す（あるいは `await` に `Promise` オブジェクトそのものを渡す）ことで、`Promise` による非同期処理が終了するのを同期的に待機することができるようになります。

`Promise` による非同期処理の結果は、通常は `Promise.then` に渡した 1 つ目のコールバック関数で受け取りますが、`await` を付けて非同期関数を呼び出すと、通常の戻り値のように処理結果を受け取れるようになります。

まとめるとこんなイメージです。

- `async function 関数名() { ... }`
    - この関数は非同期関数とみなされる。
    - 戻り値を返す場合は `Promise` オブジェクトを返すように実装する。
    - この中では `await Promiseオブジェクト` という呼び出しが許される。`Promiseオブジェクト` のところは、もちろん `Promise` を返す関数の呼び出しでもよい。
- `const result = await Promiseオブジェクト;`
    - この呼び出しは、`async` マークされた関数の中でのみ許される。
    - `Promise` が解決されるまで待機し、`Promise.then()` で得られる値を戻り値として取得できる。
    - `Promise.catch()` でハンドルしていた例外は、`try ~ catch` 構文でハンドルする。

関数の定義で `async` を付ける必要があるのは、その中で `await` を使用する場合のみであり、`Promise` オブジェクトを返すからといって `async` が必須というわけではないことに注意してください。


async, await を使わない場合
----

下記のサンプルコードは、`Promise` 対応した非同期関数と、そのコールバックを連鎖させて呼び出す例です。

```javascript
// 非同期のファイルダウンロード関数もどき
function fetchFile(url) {
  return new Promise((resolve, reject) => {
    if (url.endsWith('.png')) {
      resolve('success: ' + url);
    } else {
      reject(new Error(url + ' not found'));
    }
  });
}

fetchFile('file1.png').then(result => {
  console.log(result);
  return fetchFile('file2.png');
}).then(result => {
  console.log(result);
  return fetchFile('file3.jpg');
}).then(result => {
  console.log(result);
}).catch(reason => {
  console.error(reason);
});
```

{{< code title="実行結果" >}}
success: file1.png
success: file2.png
failure: file3.jpg not found
{{< /code >}}

`Promise.then`、`Promise.catch` を使用した非同期処理の連結は、従来のコールバックだけで構成した入れ子処理（コールバック地獄）と比べて非常に分かりやすくなっていますが、結局はコールバックを使用しているという点で、若干分かりにくさが残っています。


async, await を使う場合
----

`async`/`await` キーワードを用いることで、上述のような `Promise` コールバックの連鎖を、あたかも同期処理のように記述することができるようになります。
次のコードは、上記の `fetchFile` 関数呼び出しの連鎖を `async`/`await` を使って書き直したものです。
どこにもコールバックが使われていないことが分かると思います。

```javascript
async function fetchAllFiles() {
  try {
    const result1 = await fetchFile('file1.png');
    console.log(result1);
    const result2 = await fetchFile('file2.png');
    console.log(result2);
    const result3 = await fetchFile('file3.jpg');
    console.log(result3);
  } catch (err) {
    console.error(err);
  }
}

fetchAllFiles();
```

まず、`await` による非同期関数呼び出しを行うため、対象となるコード全体を `async` 関数の中に入れています。
そして、`Promise` 対応された非同期処理である `fetchFile` 関数を、`await` を付けて順番に呼び出しています。
`await` を付けることで、`Promise` の非同期処理が完了する（resolved 状態になる）まで待機し、その結果を戻り値として取得できるようになります。
もし、`Promise` の非同期処理が失敗した場合（rejected 状態になった場合）は、内部的に実行時例外としてエラー情報がスローされます。
つまり、非同期関数の失敗は、上記のように `try` ～ `catch` でまとめて捕捉することができます。


非同期処理は Promise 生成時に開始され、await 時に終了を待機する
----

下記のコードは、上記のコードの `await` の位置をずらしたものです。
こうすることで、`fetchFile` 処理がほぼ同時に開始されることになり、全体としての処理時間はおそらく短縮されます。
ユースケースによりますが、こちらの方が本来のニーズ（ダウンロード処理を並列化する）に合っているかもしれません。

```javascript
async function fetchAllFiles() {
  try {
    const promise1 = fetchFile('file1.png');
    const promise2 = fetchFile('file2.png');
    const promise3 = fetchFile('file3.jpg');
    console.log(await promise1);
    console.log(await promise2);
    console.log(await promise3);
  } catch (err) {
    console.error(err);
  }
}
```

この例では、`fetchFile` の非同期関数を普通に呼び出しているため、戻り値は非同期関数の処理結果ではなく、内部で生成された `Promise` オブジェクトそのものになります（そのため、変数名を `result1` から `promise1` に変更しています）。
つまり、この瞬間に非同期処理が開始され、その処理の完了を待たずに、次の `fetchFile` 呼び出しに進んでいきます。
非同期処理が完了するのを待機するのは、`Promise` オブジェクトに対して `await` を呼び出しているところです。

このように記述することで、複数の非同期処理をほぼ同時に開始し、すべての非同期処理の完了を待機するということが実現できます。
ただし、`await` はあくまでシーケンシャルに実行を待機することになるため、本当の意味ですべての非同期処理の完了を待ちたいのであれば、[Promise.all メソッド](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Promise/all) を使用して非同期処理を並列実行すべきです。

```javascript
const p1 = fetchFile('file1.png');
const p2 = fetchFile('file2.png');
const p3 = fetchFile('file3.jpg');
const promises = [p1, p2, p3];

Promise.all(promises).then(values => {
  for (let x of values) {
    console.log(x.filename);
  }
}).catch(err => {
  console.error(err);
});
```

`then` のブロックに渡されるパラメータ `values` には、各 `Promise` からの結果が格納されるのですが、この順番は `Promise.all` で渡した `Promise` と同じ順番になります（どの非同期処理が先に終わるかは関係ありません）。
`Promise.all` で非同期処理の完了をまとめて待機する場合、いずれかの非同期処理が失敗した時点で `catch` によりエラー捕捉されます（すべての非同期処理が成功しないと `then` のコールバックは呼び出されません）。


async, await のその他いろいろ
----

### return new Promise の代わりに async キーワードを使う

JavaScript で非同期的な関数を作成するためには、`Promise` オブジェクトを return することになるのですが、毎回 `Promise` オブジェクトを生成するコードを記述するのは面倒です。
例えば、下記の `div` 関数は、3 秒間かけて割り算を行う関数です（`div` の呼び出し自体は `Promise` を返すことですぐに終了します）。
ここでは TypeScript で型付けしたコードを示します。

```javascript
// スリープ用のユーティリティ
function sleep(millis: number): Promise<void> {
  return new Promise(resolve => setTimeout(resolve, millis));
}

// 時間をかけて割り算する関数
function div(num1: number, num2: number): Promise<number> {
  return new Promise(async (resolve, reject) => {
    await sleep(3000);
    if (num2 == 0) {
      reject(new Error('Zero division'));
    } else {
      resolve(num1 / num2);
    }
  });
}
```

たかがこれだけの実装のために、`new Promise ...` みたいな呪文コードを記述するのは煩わしいですよね？
そのような場合は、`async` キーワードを付けて関数を定義すると、その関数内での `new Promise ...` といった記述を省略できるようになります。
その代わりに、`return` や `throw` を呼び出すだけで、次のように振る舞ってくれます。

- `return 'success'` ... Promise で `resolve('success')` したように振舞う
- `throw new Error('fail')` ... Promise で `reject(new Error('fail'))` したように振舞う

つまり、`async` 関数からの戻り値や例外は、自動的に `Promise` オブジェクトでラップされるということです（`Promise.then` や `Promise.catch` の戻り値の仕組みと同じです）。
この仕組みを利用すると、前述の `div` 関数は次のようにシンプルに記述できます。

```javascript
async function div(num1: number, num2: number): Promise<number> {
  await sleep(3000);
  if (num2 == 0) {
    throw new Error('Zero division');
  } else {
    return num1 / num2;
  }
}
```

ステキすぎる。。。

ちなみに、呼び出し方は次のような感じになります。

```javascript
async function main() {
  try {
    const divPromise = div(5, 2);
    console.log('計算中だよ');
    console.log(await divPromise);  //=> 2.5
  } catch (err) {
    console.error(err);
  }
}

main();
```

### await のチェーン呼び出しで catch する

`await` キーワードを使って `Promise` を待機する場合、例外のハンドルは通常 `try ~ catch` で行いますが、このやり方だと気持ち悪いコードになることがあります。

例えば、次のサンプルコードは、非同期関数 `fetchUserName` を使って取得したユーザー名を表示するものですが、ユーザー名の取得に失敗した場合は「名無し」という名前を使うように実装しています（50% の確率で失敗するようにしています）。

```javascript
async function fetchUserName(id: number): Promise<string> {
  // 本当はここで時間のかかる処理をする想定
  if (Math.random() > 0.5) {
    return 'まく';
  } else {
    throw new Error('User not found');
  }
}

async function main() {
  let name: string;
  try {
    name = await fetchUserName(1);
  } catch (err) {
    name = '名無し';
  }
  console.log(`こんにちは、${name}さん!`);
}

main();
```

`try ~ catch` まわりが何だかイケてないですね。
何より、`name` 変数を `const` 定義できないのがよろしくないです。
そこで、`await ~ catch` のチェーン呼び出しを使うと次のように記述できます。

```javascript
async function main() {
  const name = await fetchUserName(1).catch(err => '名無し');
  console.log(`こんにちは、${name}さん!`);
}
```

ステキすぎる。。。（2回目）

ちなみに、Kotlin だと `try ~ catch` が値を返す「式」扱いになっているので、`try ~ catch` の評価結果をそのまま代入できちゃいます。


### async, await で同期の sleep 関数のようなものを作る

すでに上記のサンプルで使っていますが、sleep 関数の作り方の例です。

JavaScript はシングルスレッドの思想で設計されているため、その他の多くの言語に存在する同期型の sleep 関数が用意されていません。
しかし、`Promise` オブジェクトによる非同期処理のラップと、`async`、`await` シンタックスシュガーによる非同期処理の書き換えを用いれば、同期型の sleep 関数のようなものを実現することができます。

下記のコードを実行すると、1 秒スリープしてから Hello World と表示します。

```javascript
function sleep(millis) {
  return new Promise(resolve => setTimeout(resolve, millis));
}

async function main() {
  await sleep(1000);
  console.log('Hello world');
}

main();
```

