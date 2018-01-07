---
title: 配列をコピーする
date: "2012-01-27"
---

配列コピー用の copyArray 関数を作成する
----

```javascript
var a = [1, 2, 3];
var b = a;
```

JavaScript では、上記のような代入は、単なるリファレンスの代入になってしまいます。
配列の内容をコピーして新しいオブジェクトを作りたい場合は、以下のように新しい配列を生成する必要があります。

```javascript
function copyArray(arr) {
  return Array.apply(null, arr);
}
```

#### 使い方

```javascript
var a = [1, 2, 3];
var b = copyArray(b);
```


Array クラスを拡張して clone() メソッドを追加する
----

`Array` クラスの `prototype` を拡張して `clone()` メソッドのようなものを追加すれば、配列がもともとコピー機能を持っているかのように記述できるようになります。

```javascript
Array.prototype.clone = function() {
  return Array.apply(null, this);
}

var a = [1, 2, 3];
var b = a.clone();
```

多次元配列でもコピーできるようにするには、再帰的にコピーを行う必要があります。

```javascript
Array.prototype.clone = function() {
  if (this[0].constructor == Array) {
    var len = this.length;
    var arr = new Array(len);
    for (var i = 0; i < len; ++i) {
      arr[i] = this[i].clone();
    }
    return arr;
  }
  return Array.apply(null, this);
}
```

