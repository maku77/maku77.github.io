---
title: "Promise オブジェクトで連続するコールバック処理を簡潔に記述する"
date: "2018-11-26"
description: "ES6 で導入された Promise を使用すると、連続して実行しなければいけない非同期処理（コールバック）を簡潔に記述することができます。"
---

Promise の基本
----

`Promise` オブジェクトは、非同期に実行されるコールバック処理の結果を受け取るためのプレースホルダとして機能します。
`Promise` オブジェクトが提供する `then` メソッドを使用すると、`Promise` オブジェクトによってラップされたコールバック処理の結果を、別のコールバック処理の入力に繋げることができます。
簡単に言うと、非同期処理 A が終わった後に、その結果を使って非同期処理 B を進める、という処理を分かりやすく記述できるようになるということです（処理結果を次の処理へ繋げることを約束 (Promise) してくれる）。

下記のサンプルコードは、足し算の結果をコールバックのパラメータとして返す簡単な例です（ここでは同期でコールバックしていますが、非同期処理っぽくしたければ、`setTimeout` を挟むのもよいでしょう）。

~~~ javascripot
// 足し算の結果をコールバックする
function asyncAdd(val1, val2, callback) {
  callback(val1 + val2);
}

asyncAdd(100, 200, function(result) {
  console.log(result);  //=> 300
});
~~~

これくらいであれば、簡潔に記述できますが、非同期処理で受け取った結果を使って次の処理を行う、ということを繰り返そうとすると、下記のように入れ子構造がどんどん深くなるという**コールバック地獄**にハマります。

~~~ javascript
asyncAdd(100, 200, function(result) {
  asyncAdd(result, 300, function(result) {
    asyncAdd(result, 400, function(result) {
      asyncAdd(result, 500, function(result) {
        console.log(result);  //=> 1500
      });
    });
  });
});
~~~

そこで、`Promise` オブジェクトの出番です。

~~~ javascript
function asyncAdd(val1, val2) {
  return new Promise(resolve => {
    resolve(val1 + val2);
  });
}

asyncAdd(100, 200).then(result => console.log(result));  //=> 300
~~~

`Promise` を使って処理結果を受け取れるようにするには、パラメータとしてコールバック用のオブジェクトを受け取るのではなく、上記の `asyncAdd` 関数のように、`Promise` オブジェクトを返すように実装します。
そして、実際の処理結果は `Promise` オブジェクトのコンストラクタで渡された `resolve` コールバックメソッド経由で返すようにします。
こうしておくと、`Promise.then` を使用することで、非同期処理の結果をコールバックで受け取ることができるようになります。

上記はシンプルな例なので大した効果はないのですが、コールバック処理を連鎖させた処理を記述するときに効果を発揮します。
次のコードは、`asyncAdd` の処理結果を、次の `asyncAdd` の処理に繋げるということを繰り返し行っています。

~~~ javascript
asyncAdd(100, 200)
  .then(result => asyncAdd(result, 300))
  .then(result => asyncAdd(result, 400))
  .then(result => result + 500)
  .then(result => console.log(result));  //=> 1500
~~~

このような記述方法を **Promise チェーン**と呼び、ES6 時代のコールバック処理の記述方法として広まってきています。

ここで、注目したいのは 3 つ目の `then` 呼び出しで、コールバック処理の結果として `result + 500` という単純なスカラ値を返しており、`Promise` オブジェクトを返していないことがわかります。
それなのに、次の `then` メソッド呼び出しにチェーンすることができているのは、`then` メソッドがコールバック処理の戻り値を `Promise` オブジェクトにラップして返してくれているからです。
つまり、Promise チェーンを記述するには、チェーンの最初だけ `Promise` オブジェクトになっていればよいことになります。


Promise.resolve() による省略記法
----

~~~ javascript
function asyncAdd(val1, val2) {
  return new Promise((resolve) => {
    resolve(val1 + val2);
  });
}
~~~

という `resolve` コールバックだけを扱う `Promise` オブジェクトを生成するときは、次のように `Promise.resolve` を使って簡潔に記述することができます。

~~~ javascript
function asyncAdd(val1, val2) {
  return Promise.resolve(val1 + val2);
};
~~~


Promise によるエラー処理の伝搬
----

実行したい非同期処理に、成功と失敗の2種類の結果があるケースでは、`Promise` による非同期処理のチェーンがさらに役に立ちます。
例えば、下記のようなファイルダウンロードのための疑似関数 `fetchFile` があるとします。
シンプル化のため、`fetchFile` は `.png` 拡張子を持つファイルを要求された場合のみ成功することとします。

~~~
function fetchFile(url, successCallback, failureCallback) {
  if (url.endsWith('.png')) {
    successCallback('success: ' + url);
  } else {
    failureCallback('failure: ' + url + ' not found');
  }
}
~~~

この（疑似非同期）関数を使い、次のようなファイルを順番に取得していき、取得に失敗した時点で処理を止めることを考えてみます。

1. `file1.png` （取得成功する）
2. `file2.png` （取得成功する）
3. `file3.jpg` （ここで取得に失敗する）

従来のコールバックの仕組みを使用して、`fetchFile` を実行するプログラムは下記のようになります。

~~~ javascript
function myFailureCallback(err) {
  console.error(err);
}

fetchFile('file1.png', function(result) {
  console.log(result);
  fetchFile('file2.png', function(result) {
    console.log(result);
    fetchFile('file3.jpg', function(result) {
      console.log(result);
    }, myFailureCallback);
  }, myFailureCallback)
}, myFailureCallback)
~~~

#### 実行結果

~~~
success: file1.png
success: file2.png
failure: file3.jpg not found
~~~

このように、コールバックの連鎖にエラー処理を加えると、さらに悲惨なコールバック地獄に陥ります。
上記の例では、エラーハンドラとして共通の `myFailureCallback` を使用しているのにもかかわらず、3 箇所もそのエラーハンドラを渡しています。

非同期関数を `Promise` 化することで、このようなエラー処理もスッキリ記述できるようになります。
`Promise` コンストラクタで渡されるコールバック関数の第2パラメータ `reject` はコールバック関数となっており、これを呼び出すことで非同期処理が失敗したことを示します。

下記は、上記のプログラムを `Promise` 化した例です。

~~~ javascript
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
~~~

いずれかの非同期メソッドの実行が失敗に終わり、`reject` が呼び出されると、非同期メソッドの呼び出し側では `Promise.catch` メソッドが呼び出されて Promise チェーンの実行が止まります。
エラー処理の内容はコールバック関数として実装し、Promise チェーンの最後の `Promise.catch` に渡してやります。
上記のエラー処理は、ちょっとシンプルすぎかもしれませんが、`Promise` を使って非同期処理をチェーンさせることで、エラーハンドル処理に関しても読みやすく記述できるようになることが分かると思います。


Promise 化した関数の処理はいつ実行されるのか？
----

`Promise` のインスタンスを生成すると、コンストラクタに渡されたコールバック関数が直ちに実行されます（正確には `Promise` オブジェクトがインスタンス化されるよりも前に実行されます）。
つまり、ある非同期関数を Promise 対応させたい場合は、単純にその関数の中身をすべて `Promise` コンストラクタに渡すコールバック関数の中に移動させてしまえば OK です。

~~~ javascript
function hoge() {
  return new Promise((resolve, reject) => {
    // ...
    // これまでと同様非同期処理
    // ...
    resolve(result);
  });
}

hoge().then(result => console.log(result));
~~~

上記のようにしておけば、`hoge()` を呼び出した直後にこれまでと同様の非同期処理が開始され、`Promise.then` のコールバックを介して結果を受け取れるようになります。
`Promise.then` を呼び出したときに非同期処理が開始されるわけではありません。
あくまで非同期処理が開始されるタイミングは `Promise` を導入する前と変わらないということです。


参考
----

- [Promise な非同期処理を async/await でさらに読みやすくする](async-await.html)

