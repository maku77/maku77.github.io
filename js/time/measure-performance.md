---
title: "処理速度を計測する (performance)"
date: "2018-09-14"
---

処理速度計測の基本
----

下記は、典型的なパフォーマンス計測のプログラムです。
計測したい処理の前後で `performance.now()` を呼び出して、その差分を求めることで処理にかかった時間を計測しています。

ここでは、文字列を `+` 演算子によって何度も結合する関数 `concat` の処理時間を計測しています。

#### concat 関数の処理速度を計測する

~~~ javascript
// 処理速度を計測したい関数
function concat() {
  var s = '';
  for (var i = 0; i < 1000 * 1000; ++i) {
    s += i;
  }
}

var start = performance.now();
concat();
console.log(performance.now() - start);
~~~

#### 実行結果

~~~
175.96613097190857
~~~

`performance.now()` の戻り値の整数部分はミリ秒を示しているので、上記の結果は、約176msの処理時間がかかっていることを示しています。
パフォーマンス計測は、このように `Date.now()` ではなく `performance.now()` を使用してください。

- 参考: [精度の高いタイムスタンプを取得する (performance.now())](performance-now.html)


### Node.js で実行する場合

`performance.now()` は、Web ブラウザ上の JavaScript ではそのまま呼び出すことができますが、Node.js の環境で呼び出すには、下記のように `require` を実行しておく必要があります。

~~~ javascript
const { PerformanceObserver, performance } = require('perf_hooks');
~~~

- 参考: [Performance Timing API｜Node.js Documentation](https://nodejs.org/api/perf_hooks.html)


処理速度計測用のユーティリティ関数を作成する
----

例えば、下記のようなユーティリティ関数 (`measurePerf`) を作成しておけば、任意の関数の処理速度を簡単に計測できるようになります。

#### 任意の関数の処理速度を計測する関数

~~~ javascript
function measurePerf(func) {
  var start = performance.now();
  func();
  console.log(performance.now() - start);
}
~~~

#### 使用例（concat1 と concat2 の処理速度を計測する）

~~~ javascript
var COUNT = 10000000;

function concat1() {
  var s = '';
  for (var i = 0; i < COUNT; ++i) {
    s += i;
  }
  return s;
}

function concat2() {
  var arr = [];
  for (var i = 0; i < COUNT; ++i) {
    arr.push(String(i));
  }
  return arr.join();
}

measurePerf(concat1);
measurePerf(concat2);
~~~

#### 実行結果

~~~
2813.2432450056076
2035.0246240496635
~~~


### 引数を持つ関数の速度を計測する

上記のパフォーマンス計測用の `measurePerf` 関数は、任意の関数オブジェクトを渡せますが、引数を必要とする関数を計測する場合はちょっとした工夫が必要です。
下記のフィボナッチ関数 `fibonacci` は、引数を受け取るため `measurePerf` にそのまま渡すことができません。
このような場合は、`fibonacci` 関数を実行する関数オブジェクトを作成してそれを渡してやるようにします。

~~~ javascript
function fibonacci(n) {
  if (n <= 1) {
    return 1;
  }
  return fibonacci(n - 2) + fibonacci(n - 1);
}

measurePerf(function () {
  fibonacci(40)
});
~~~

