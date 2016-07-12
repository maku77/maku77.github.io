---
title: 標準出力 (stdout)、標準エラー出力 (stderr) への出力
created: 2014-07-13
---

[参考: Node.js ドキュメント - console](http://nodejs.org/api/console.html)

標準出力、標準エラー出力への出力を行うときには、`console` オブジェクトを使用します。

- 標準出力 (stdout) への出力
  - `console.log()`
  - `console.info()` -- 同上
- 標準エラー (stderr) への出力
  - `console.error()`
  - `console.warn()` -- 同上

`console.info()` と `console.warn()` は単なるエイリアスなので、`console.log()` と `console.error()` を覚えておけば十分です。
C 言語の `printf()` のようなフォーマット文字列を指定することができます。

```javascript
console.log('Count: %d', 100);
console.log('Hello: %s', 'Tom');
console.log('JSON: %j', obj);  // JSONテキストの出力
```

フォーマット文字列を指定せずに、直接オブジェクトを渡すと、適切にフォーマットされてオブジェクトの内容が表示されます。

```javascript
console.log(obj);  //=> { a: 100, b: 200 }
```

`console.dir()` というメソッドもあります。
`console.dir()` はオブジェクトを 1 つだけ受け取り、上記のように `console.log()` を呼び出した場合と同様の出力を行います（なので基本は `console.log()` だけ使えばよい）。
ちなみに、Firefox などの開発コンソール上で `console.dir(Object)` を使用すると、ツリー形式でプロパティを表示したり、その内容を編集したりと、そのオブジェクトに対して `console.log()` よりも高度な作業を行えます。

