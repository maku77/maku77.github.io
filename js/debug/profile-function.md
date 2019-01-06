---
title: "ある処理を 1 秒間に何回実行できるかプロファイリングする"
date: "2012-12-24"
---

瞬間的に終わってしまうような処理のパフォーマンスを調べるには、その処理を一回実行するのにかかった時間を調べるより、１秒間に何回その処理を行えるかを調べた方が正確な結果が得られます。

```javascript
var startTime = new Date().getTime();
var timeElapsed = 0;

for (var count = 0; timeElapsed < 1000; ++count) {
  // do something here

  timeElapsed = new Date().getTime() - startTime;
}

console.log(count + ' times per second');
```

以下のような関数を用意しておけば、ある関数が 1 秒間に何度実行できるかを簡単に調べることができます。

```javascript
function howManyTimes(func) {
  var startTime = new Date().getTime();
  var timeElapsed = 0;
  for (var count = 0; timeElapsed < 1000; ++count) {
    func();
    timeElapsed = new Date().getTime() - startTime;
  }
  return count;
}
```

以下のように使います。

```javascript
var count = howManyTimes(function() {
  var i = 0;
  return function() {
    i ^= 1;
  };
}());

alert(count);
```

