---
title: "async.js で非同期処理のフロー制御を行う (3) 複数の非同期処理を指定した順に実行する (async.series, async.waterfall)"
date: "2013-12-16"
---


async.js の下記のメソッドを使用すると、複数の非同期関数を指定した順序で実行することができます。

* [async.series(tasks, [callback])](http://caolan.github.io/async/docs.html#.series)
* [async.waterfall(tasks, [callback])](http://caolan.github.io/async/docs.html#.waterfall)


async.series() による非同期処理
----

`async.series()` の非同期処理の実装方法は、`async.parallel()` における実装方法と同様です。

参考: [async.js で非同期処理のフロー制御を行う (2) 複数の非同期処理の結果を同時に取得 (async.parallel)](async-js2.html)


#### sample.js

```javascript
var async = require('async');

function func1(callback) {
    setTimeout(function() {
        callback(null, 'Result1');
    }, 100);
}

function func2(callback) {
    setTimeout(function() {
        callback(null, 'Result2');
    }, 100);
}

async.series([func1, func2], function(err, results) {
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

いずれかの非同期処理において、エラーが発生（`callback` の第一引数を指定）すると、その後に続く関数は実行されません。
例えば、以下のコードでは、`func1`, `func2`, `func3` を順番に実行するように指定していますが、`func2` でエラーが発生しているため、`func3` は実行されません（それまでに実行された処理の結果は受け取ることができます）。

#### sample.js

```javascript
var async = require('async');

function func1(callback) { callback(null, 'Result1'); }
function func2(callback) { callback('Error', 'Result2'); }
function func3(callback) { callback(null, 'Result3'); }

async.series([func1, func2, func3], function(err, results) {
    console.log('error = ' + err);
    console.log('results = ' + results);
});
```

#### 実行結果

```
$ node sample
error = Error
results = Result1,Result2
```


async.parallel() による非同期処理
----

前の実行した関数の結果を参照しつつ、次の処理を実行したいこともあるでしょう。
そのような場合は、`async.parallel()` の代わりに、`async.waterfall()` を使用します。

#### sample.js

```javascript
var async = require('async');

function func1(callback) {
    callback(null, 100);  // 次の関数に値を 1 つ渡す
}
function func2(arg1, callback) {  // 前の関数から値を 1 つ受け取る
    callback(null, arg1 + 200, 300);  // 次の関数に値を 2 つ渡す
}
function func3(arg1, arg2, callback) {  // 前の関数から値を 2 つ受け取る
    callback(null, arg1 + arg2);  // 最終結果として値を 1 つ渡す
}

async.waterfall([func1, func2, func3], function(err, result) {
    console.log('error = ' + err);
    console.log('result = ' + result);
});
```

#### 実行結果

```
$ node sample
error = null
result = 600
```

`async.waterfall()` では、`async.parallel()` とは異なり、それぞれの `callback` の第 2 引数以降で、次の関数に渡す値を指定できます。
次の関数は、その値を 1 番目の引数から順番に受け取るように実装します。
`callback` 関数は最後の仮引数で受け取るように実装することに注意してください。

