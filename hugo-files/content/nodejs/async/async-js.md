---
title: "Node.jsメモ: async.js による非同期処理"
url: "p/4fzs5fs/"
date: "2013-12-16"
tags: ["nodejs"]
aliases:
  - /nodejs/async-js1.html
  - /nodejs/async-js2.html
  - /nodejs/async-js3.html
---

async.js とは
----

JavaScript のオープンソースライブラリである [async.js](https://github.com/caolan/async) を使用すると、

- 複数の非同期関数を指定した順に実行する
- 複数の非同期関数を同時に実行してすべての処理が終わるのを待つ

など、非同期処理のフロー制御を簡単に行うことができます。

`async` モジュールは次のようにインストールできます。

```console
$ npm install async
```


複数の非同期処理の結果を同時に取得する(async.parallel)
----

`async.parallel(tasks, [callback])` を使用すると、複数の非同期関数を並行して実行し、すべての処理が終了したときにその結果をまとめて受け取ることができます。

{{< code lang="js" title="sample.js" >}}
import async from 'async';

function func1(callback) {
    setTimeout(() => {
        callback(null, 'Result1');
    }, 2000);
}

function func2(callback) {
    setTimeout(() => {
        callback(null, 'Result2');
    }, 1000);
}

async.parallel([func1, func2], (err, results) => {
    console.log(`error = ${err}`);
    console.log(`results = ${results}`);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample
error = null
results = Result1,Result2
{{< /code >}}

実行する非同期関数は、第一引数としてコールバック関数を受け取るようにし、非同期処理が終わった時にそのコールバック関数を呼び出すように実装する必要があります。その際、コールバック関数の引数には、非同期処理の結果として、（発生した場合は）エラーあるいは、結果を渡します。

{{< code lang="js" title="各非同期関数の実装方法" >}}
function myAsyncFunc(callback) {
    ... 時間のかかる処理 ...
    callback('Error!');  // エラーが発生した場合
    callback(null, 'Result');  // 正常に終了した場合
}
{{< /code >}}

すべての非同期関数の実行が終わったとき（すべての非同期関数内で `callback` が呼ばれたとき）、`async.parallel()` の第 2 引数で渡したコールバック (main callback) が呼び出されます。
このコールバック関数の第2引数で、非同期関数の処理結果を配列で受け取ることができます。
処理結果の配列は、`async.parallel()` を呼び出したときに指定した関数の順序に対応するように格納されます。

```js
async.parallel([func1, func2], (err, results) => {
    console.log(`error = ${err}`);
    console.log(`results = ${results}`);
});
```

非同期処理のいずれかでエラーを返した場合、直ちに main callback がエラー内容を伴って呼び出されます。
このとき、非同期に実行中の処理が停止されることはありません。

`async.parallel()` の第 1 引数で、キー＆バリューのオブジェクト形式で関数を渡すと、結果もオブジェクトで取得することができます。

```js
async.parallel({key1:func1, key2:func2}, (err, results) => {
    console.log(results);
});
```

{{< code lang="js" title="実行結果" >}}
{ key2: 'Result2', key1: 'Result1' }
{{< /code >}}

### おまけ（非同期に呼び出す関数にパラメータを渡す例）

{{< code lang="js" title="sample.js" >}}
import async from 'async';

// 非同期に呼び出す関数にパラメータ (arg) を渡すためのクロージャ
function makeCallback(arg) {
    return (callback) => {
        callback(null, arg * 2);
    };
}

// コールバック関数の配列を作成
const callbacks = [];
for (let i = 0; i < 5; ++i) {
    callbacks.push(makeCallback(i));
}

async.parallel(callbacks, (err, results) => {
    console.log(`error = ${err}`);
    console.log(`results = ${results}`);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample
error = null
results = 0,2,4,6,8
{{< /code >}}


複数の非同期処理を指定した順に実行する (async.series, async.waterfall)
----

async.js の下記のメソッドを使用すると、複数の非同期関数を指定した順序で実行することができます。

* `async.series(tasks, [callback])`
* `async.waterfall(tasks, [callback])`


### async.series() による非同期処理

`async.series()` の非同期処理の実装方法は、`async.parallel()` における実装方法と同様です。

{{< code lang="js" title="sample.js" >}}
import async from 'async';

function func1(callback) {
    setTimeout(() => {
        callback(null, 'Result1');
    }, 100);
}

function func2(callback) {
    setTimeout(() => {
        callback(null, 'Result2');
    }, 100);
}

async.series([func1, func2], (err, results) => {
    console.log(`error = ${err}`);
    console.log(`results = ${results}`);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample
error = null
results = Result1,Result2
{{< /code >}}

いずれかの非同期処理において、エラーが発生（`callback` の第一引数を指定）すると、その後に続く関数は実行されません。
例えば、以下のコードでは、`func1`, `func2`, `func3` を順番に実行するように指定していますが、`func2` でエラーが発生しているため、`func3` は実行されません（それまでに実行された処理の結果は受け取ることができます）。

{{< code lang="js" title="sample.js" >}}
import async from 'async';

function func1(callback) { callback(null, 'Result1'); }
function func2(callback) { callback('Error', 'Result2'); }
function func3(callback) { callback(null, 'Result3'); }

async.series([func1, func2, func3], (err, results) => {
    console.log(`error = ${err}`);
    console.log(`results = ${results}`);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample
error = Error
results = Result1,Result2
{{< /code >}}

### async.waterfall() による非同期処理

前の実行した関数の結果を参照しつつ、次の処理を実行したいこともあるでしょう。
そのような場合は、`async.series()` の代わりに、`async.waterfall()` を使用します。

{{< code lang="js" title="sample.js" >}}
import async from 'async';

function func1(callback) {
    callback(null, 100);  // 次の関数に値を 1 つ渡す
}
function func2(arg1, callback) {  // 前の関数から値を 1 つ受け取る
    callback(null, arg1 + 200, 300);  // 次の関数に値を 2 つ渡す
}
function func3(arg1, arg2, callback) {  // 前の関数から値を 2 つ受け取る
    callback(null, arg1 + arg2);  // 最終結果として値を 1 つ渡す
}

async.waterfall([func1, func2, func3], (err, result) => {
    console.log(`error = ${err}`);
    console.log(`result = ${result}`);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample
error = null
result = 600
{{< /code >}}

`async.waterfall()` では、`async.series()` とは異なり、それぞれの `callback` の第 2 引数以降で、次の関数に渡す値を指定できます。
次の関数は、その値を 1 番目の引数から順番に受け取るように実装します。
`callback` 関数は最後の仮引数で受け取るように実装することに注意してください。

