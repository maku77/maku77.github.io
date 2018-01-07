---
title: 配列をソートする
date: "2014-03-17"
---

昇順ソート
----

```javascript
var arr = [3, 1, 2];
arr.sort();  //=> [1, 2, 3]
```

降順ソート
----

### 方法1（sort してから reverse）

```javascript
var arr = [3, 1, 2];
arr.sort().reverse();  //=> [3, 2, 1]
```

### 方法2（比較関数を渡す）

```javascript
var arr = [3, 1, 2];
arr.sort(function(a, b) { return b-a; });  //=> [3, 2, 1]
```

単純に逆順にする
----

```javascript
var arr = [3, 1, 2];
arr.reverse();  //=> [2, 1, 3]
```

