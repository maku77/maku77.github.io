---
title: "文字列をデリミタで分割する／１文字ずつに分割する (split)"
date: "2012-01-20"
---

文字列の `split` メソッドを使用すると、文字列を複数の部分文字列に分割することができます。

- 参考: [String.prototype.split() - JavaScript｜MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/String/split)


カンマで文字列を分割する
----

```javascript
var s = ' aaa, bbb, ccc ';
var arr = s.split(',');  // => [" aaa", " bbb", " ccc "]
```

区切り文字列の前後の空白を削除したい場合は、以下のように正規表現で区切り文字を指定するとよいでしょう。

```javascript
var s = ' aaa, bbb, ccc ';
var arr = s.split(/\s*,\s*/);  // => [" aaa", "bbb", "ccc "]
```

元のテキストの先頭や末尾にある空白が残ってしまっていることに注意してください。
これらを削除するには、以下のように最初に `trim()` で消してしまうのが早いです。

```javascript
var s = ' aaa, bbb, ccc ';
var arr = s.trim().split(/\s*,\s*/);  // => ["aaa", "bbb", "ccc"]
```

あるいは、以下のように分割した後の各文字列に対して `trim()` を実行するという方法もありますが、元の文字列に 1 回だけ `trim()` をかけた方が効率がよいでしょう。

```javascript
arr = s.split(',').map(function(val, i){ return val.trim() });
```

文字列を一文字ずつに分割する
----

`split` メソッドのパラメータに空文字列 (`''`) を指定すると、文字列を一文字ずつに分割することができます。

~~~javascript
var s = 'ABCDE';
var arr = s.split('');  // => ["A", "B", "C", "D", "E"]

for (var i = 0; i < arr.length; ++i) {
  console.log(arr[i]);
}
~~~

