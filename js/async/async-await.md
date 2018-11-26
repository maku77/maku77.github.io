---
title: "Promise な非同期処理を async/await でさらに読みやすくする"
date: "2018-11-26"
---

async, await による非同期処理の直列化
----

ECMAScript 2017 の **async、await** は、Promise をベースに設計された非同期処理のためのシンタックスシュガーです。
`async`、`await` の仕組みを理解するには、Promise の仕組みを理解しておく必要があります。
Promise に関しての詳細は下記の記事を参考にしてください。

- 参考: [Promise オブジェクトで連続するコールバック処理を簡潔に記述する](promise.html)

ECMAScript 2017 では、従来の `function` による関数定義の前に、**`async`** キーワードをつけることで、非同期関数であることを示すことができます。
`async` の付いた非同期関数の中で、**`await`** キーワードをつけて `Promise` 対応した関数を呼び出す（あるいは `await` に `Promise` オブジェクトそのものを渡す）ことで、`Promise` による非同期処理が終了するのを同期的に待機することができるようになります。

`Promise` による非同期処理の結果は、通常は `Promise.then` に渡した 1 つ目のコールバック関数で受け取りますが、`await` を付けて非同期関数を呼び出すと、通常の戻り値のように処理結果を受け取れるようになります。

下記のサンプルコードは、`Promise` 対応した非同期関数と、そのコールバックを連鎖させて呼び出す例です。

~~~ javascript
// 非同期のファイルダウンロード関数もどき
function fetchFile(url) {
  return new Promise((resolve, reject) => {
    if (url.endsWith('.png')) {
      resolve('success: ' + url);
    } else {
      reject('failure: ' + url + ' not found');
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
~~~

#### 実行結果

~~~
success: file1.png
success: file2.png
failure: file3.jpg not found
~~~

`Promise.then`、`Promise.catch` を使用した非同期処理の連結は、従来のコールバックだけで構成した入れ子処理（コールバック地獄）と比べて非常に分かりやすくなっていますが、結局はコールバックを使用しているという点で、若干分かりにくさが残っています。

そこで、`async`/`await` キーワードを使用したシンタックスシュガーを用いることで、このような `Promise` コールバックの連鎖を、あたかも同期処理のように記述することができるようになります。
下記は、上記の `fetchFile` 関数呼び出しの連鎖を `async`/`await` を使って書き直したものです。
どこにもコールバックが使われていないことが分かると思います。

~~~ javascript
async function fetchFiles() {
  try {
    let result1 = await fetchFile('file1.png');
    console.log(result1);
    let result2 = await fetchFile('file2.png');
    console.log(result2);
    let result3 = await fetchFile('file3.jpg');
    console.log(result3);
  } catch (err) {
    console.error(err);
  }
}

fetchFiles();
~~~

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

~~~ javascript
async function fetchFiles() {
  try {
    let promise1 = fetchFile('file1.png');
    let promise2 = fetchFile('file2.png');
    let promise3 = fetchFile('file3.jpg');
    console.log(await promise1);
    console.log(await promise2);
    console.log(await promise3);
  } catch (err) {
    console.error(err);
  }
}
~~~

この例では、`fetchFile` の非同期関数を普通に呼び出しているため、戻り値は非同期関数の処理結果ではなく、内部で生成された `Promise` オブジェクトそのものになります（そのため、変数名を `result1` から `promise1` に変更しています）。
つまり、この瞬間に非同期処理が開始され、その処理の完了を待たずに、次の `fetchFile` 呼び出しに進んでいきます。
非同期処理が完了するのを待機するのは、`Promise` オブジェクトに対して `await` を呼び出しているところです。

このように記述することで、複数の非同期処理をほぼ同時に開始し、すべての非同期処理の完了を待機するということが実現できます。
ただし、`await` はあくまでシーケンシャルに実行を待機することになるため、本当の意味ですべての非同期処理の完了を待ちたいのであれば、[Promise.all メソッド](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Promise/all) を使用して非同期処理を並列実行すべきです。

~~~ javascript
var p1 = fetchFile('file1.png');
var p2 = fetchFile('file2.png');
var p3 = fetchFile('file3.jpg');
var promises = [p1, p2, p3];

Promise.all(promises).then(values => {
  console.log(values);
}).catch(err => {
  console.error(err);
});
~~~

`Promise.all` で非同期処理の完了をまとめて待機する場合、いずれかの非同期処理が失敗した時点で `catch` によりエラー捕捉されます（すべての非同期処理が成功しないと `then` のコールバックは呼び出されません）。


async, await のその他いろいろ
----

### async 関数は普通に呼び出すと Promise オブジェクトを返す

`async` 関数の戻り値は、自動的に `Promise` オブジェクトでラップされて返されます（`Promise.then` の戻り値の仕組みと同じです）。
この仕組みがあるため、`async` 関数の中で、別の `async` 関数を `await` 付きで呼び出すことができます。

~~~
async function foo() {
  // ...
  return 'hoge';
}

async function bar() {
  // ...
  var result = await foo();
  // ...
}

bar();
~~~

`async`, `await` によって、非同期処理を連結するときに、処理の内容ごとにグルーピングしたい場合などは、このように複数の `async` 関数に分けて連結していくとよいでしょう。


### async, await で同期の sleep 関数のようなものを作る

JavaScript はシングルスレッドの思想で設計されているため、その他の多くの言語に存在する同期型の sleep 関数が用意されていません。
しかし、`Promise` オブジェクトによる非同期処理のラップと、`async`、`await` シンタックスシュガーによる非同期処理の書き換えを用いれば、同期型の sleep 関数のようなものを実現することができます。

下記のコードを実行すると、1 秒スリープしてから Hello Wolrd と表示します。

~~~ javascript
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

(async function main() {
  await sleep(1000);
  console.log('Hello world');
})();
~~~

