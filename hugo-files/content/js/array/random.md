---
title: "JavaScriptメモ: 配列要素をランダムで取り出す"
url: "p/47oqrf9/"
date: "2013-07-11"
tags: ["javascript"]
aliases: [/js/array/random.html]
---

下記の関数 `randomGet` は、渡した配列の中から、ランダムで要素を選んで返します。

```javascript
function randomGet(arr) {
  var index = Math.floor(Math.random() * arr.length)
  return arr[index];
}

var arr = [1, 2, 3, 4, 5];
console.log(randomGet(arr));
```

配列クラスに `randomGet()` メソッドを追加しちゃう方法もあります。

```javascript
Array.prototype.randomGet = function() {
  var index = Math.floor(Math.random() * this.length)
  return this[index];
};

var arr = [1, 2, 3, 4, 5];
console.log(arr.randomGet());
```

