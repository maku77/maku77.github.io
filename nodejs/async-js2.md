---
title: "async.js で非同期処理のフロー制御を行う (2) 複数の非同期処理の結果を同時に取得 (async.parallel)"
date: "2013-12-16"
---

`async.parallel(tasks, [callback])` を使用すると、複数の非同期関数を並行して実行し、すべての処理が終了したときにその結果をまとめて受け取ることができます。

参考: [async.js ドキュメント - parallel](http://caolan.github.io/async/docs.html#.parallel)

#### sample.js

```javascript
var async = require('async');

function func1(callback) {
    setTimeout(function() {
        callback(null, 'Result1');
    }, 2000);
}

function func2(callback) {
    setTimeout(function() {
        callback(null, 'Result2');
    }, 1000);
}

async.parallel([func1, func2], function(err, results) {
    console.log('error = ' + err);
    console.log('results = ' + results);
});
```

#### 実行結果

```
$ node sample
error = null
results = Result1,Result2
```

実行する非同期関数は、第一引数としてコールバック関数を受け取るようにし、非同期処理が終わった時にそのコールバック関数を呼び出すように実装する必要があります。その際、コールバック関数の引数には、非同期処理の結果として、（発生した場合は）エラーあるいは、結果を渡します。

#### 各非同期関数の実装方法

```javascript
function myAsyncFunc(callback) {
    ... 時間のかかる処理 ...
    callback('Error!');  // エラーが発生した場合
    callback(null, 'Result');  // 正常に終了した場合
}
```

すべての非同期関数の実行が終わったとき（すべての非同期関数内で `callback` が呼ばれたとき）、`async.parallel()` の第 2 引数で渡したコールバック (main callback) が呼び出されます。
このコールバック関数の第2引数で、非同期関数の処理結果を配列で受け取ることができます。
処理結果の配列は、`async.parallel()` を呼び出したときに指定した関数の順序に対応するように格納されます。

```javascript
async.parallel([func1, func2], function(err, results) {
    console.log('error = ' + err);
    console.log('results = ' + results);
});
```

非同期処理のいずれかでエラーを返した場合、直ちに main callback がエラー内容を伴って呼び出されます。
このとき、非同期に実行中の処理が停止されることはありません。

`async.parallel()` の第 1 引数で、キー＆バリューのオブジェクト形式で関数を渡すと、結果もオブジェクトで取得することができます。

```javascript
async.parallel({key1:func1, key2:func2}, function(err, results) {
    console.log(results);
});
```

#### 実行結果

```
{ key2: 'Result2', key1: 'Result1' }
```

おまけ（非同期に呼び出す関数にパラメータを渡す例）
----

#### sample.js

```javascript
var async = require('async');

// 非同期に呼び出す関数にパラメータ (arg) を渡すためのクロージャ
function makeCallback(arg) {
    return function(callback) {
        callback(null, arg * 2);
    }
}

// コールバック関数の配列を作成
var callbacks = []
for (var i = 0; i < 5; ++i) {
    callbacks.push(makeCallback(i));
}

async.parallel(callbacks, function(err, results) {
    console.log('error = ' + err);
    console.log('results = ' + results);
});
```

#### 実行結果

```
$ node sample
error = null
results = 0,2,4,6,8
```

