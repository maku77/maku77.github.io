---
title: "JavaScriptメモ: ある処理を 1 秒間に何回実行できるかプロファイリングする"
url: "p/zy772in/"
date: "2012-12-24"
tags: ["javascript"]
aliases: /js/debug/profile-function.html
---

瞬間的に終わってしまうような処理のパフォーマンスを調べるには、その処理を 1 回実行するのにかかった時間を調べるより、1 秒間に何回その処理を行えるかを調べた方が正確な結果が得られます。

```javascript
const startTime = Date.now();
let timeElapsed = 0;
let count = 0;

while (timeElapsed < 1000) {
  // do something here

  timeElapsed = Date.now() - startTime;
  count++;
}

console.log(`${count} times per second`);
```

以下のような関数を用意しておけば、ある関数が 1 秒間に何度実行できるかを簡単に調べることができます。

```javascript
function howManyTimes(func) {
  const startTime = Date.now();
  let timeElapsed = 0;
  let count = 0;
  while (timeElapsed < 1000) {
    func();
    timeElapsed = Date.now() - startTime;
    count++;
  }
  return count;
}
```

以下のように使います。

```javascript
const count = howManyTimes((() => {
  let i = 0;
  return () => {
    i ^= 1;
  };
})());

console.log(count);
```

