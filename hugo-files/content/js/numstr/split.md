---
title: "JavaScript で文字列をデリミタで分割する／1文字ずつに分割する (String#split)"
url: "p/dpp4v8n/"
date: "2012-01-20"
lastmod: "2023-12-01"
changes:
  - 2023-12-01: モダンな JavaScript の書き方に修正
tags: ["javascript"]
aliases: /js/string/split.html
---

JavaScript の文字列を任意の区切り文字で分割するには、[`split()`](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/String/split) メソッドを使用します。


カンマで文字列を分割する
----

```javascript
const s = ' aaa, bbb, ccc ';
const arr = s.split(',');  // => [" aaa", " bbb", " ccc "]
```

区切り文字列の前後の空白を削除したい場合は、以下のように正規表現で区切り文字を指定するとよいでしょう。

```javascript
const s = ' aaa, bbb, ccc ';
const arr = s.split(/\s*,\s*/);  // => [" aaa", "bbb", "ccc "]
```

元のテキストの先頭や末尾にある空白が残ってしまっていることに注意してください。
これらを削除するには、以下のように最初に `trim()` で消してしまうのが早いです。

```javascript
const s = ' aaa, bbb, ccc ';
const arr = s.trim().split(/\s*,\s*/);  // => ["aaa", "bbb", "ccc"]
```

あるいは、以下のように分割した後の各文字列に対して `trim()` を実行するという方法もありますが、元の文字列に 1 回だけ `trim()` をかけた方が効率がよいでしょう。

```javascript
const arr = s.split(',').map((val) => val.trim());
```

文字列を一文字ずつに分割する
----

`split` メソッドのパラメータに空文字列 (`''`) を指定すると、文字列を一文字ずつに分割することができます。

```javascript
const s = 'ABCDE';
const arr = s.split('');  // => ["A", "B", "C", "D", "E"]

for (let i = 0; i < arr.length; ++i) {
  console.log(arr[i]);
}
```

