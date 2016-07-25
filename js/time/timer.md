---
title: タイマーで任意の関数を呼び出す
created: 2012-12-08
---

単発のタイマー
----

タイマーによる非同期な関数呼び出しには、`window.setTimeout()` を使用します。

```javascript
function puts(text) {
  document.write(text);
}

setTimeout(function() {
  puts('*');
}, 1000);  // Call foo() after 1 second.
```

繰り返しのタイマー
----
一回だけでなく、繰り返し関数を呼び出したい場合は `window.setTimeout()` の代わりに `window.setInterval()` を使用します。

```javascript
function puts(text) {
  document.write(text);
}

setInterval(function() {
  puts('*');
}, 100);
```

以下のようなイディオムを使用すれば、`window.setTimeout()` でも繰り返しのタイマーを実現できます。

```javascript
function puts(text) {
  document.write(text);
}

(function loop() {
  puts('*');
  setTimeout(loop, 100);
})();
```

タイマーのキャンセル
----

タイマーをキャンセルするには、以下の関数を使用します。

- `window.clearTimeout()`: `window.setTimeout()` のタイマーをキャンセルする
- `window.clearInterval()`: `window.setInterval()` のタイマーをキャンセルする

#### setTimeout のキャンセル
```javascript
var id = setTimeout(function() { ... }, 5000);
clearTimeout(id);    // タイマーのキャンセル
```

#### setInterval のキャンセル
```javascript
var id = setInterval(function() { ... }, 5000);
clearInterval(id);    // タイマーのキャンセル
```

