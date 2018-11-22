---
title: 標準出力 (stdout)、標準エラー出力 (stderr) への出力
date: "2014-07-13"
---

Node.js でグローバルに定義されている **`console`** オブジェクトを使用すると、標準出力、標準エラー出力への出力を行うことができます。

- 参考: [Node.js ドキュメント - console](https://nodejs.org/api/console.html)


console で文字列を出力する
----

`console` オブジェクトを使用して文字列を出力するときは、主に次のようなメソッドを使用します。

- <b>標準出力 (stdout) への出力</b>: `console.log()` あるいは `console.info()`
- <b>標準エラー (stderr) への出力</b>: `console.error()` あるいは `console.warn()`

`console.info()` と `console.warn()` は単なるエイリアスなので、`console.log()` と `console.error()` を覚えておけば十分です。

これらのメソッドは、単純な文字列を渡すこともできますし、C 言語の `printf` 関数のようにフォーマット文字列を指定することもできます。

```javascript
console.log('Hello World');
console.log('Count: %d', 100);
console.log('Hello: %s', 'Tom');
console.log('JSON: %j', obj);  // JSONテキストの出力
```

console でオブジェクトを直接出力する
----

`console.log` メソッドなどに、フォーマット文字列を指定せずに直接オブジェクトを渡すと、適切にフォーマットされてオブジェクトの内容が表示されます。

#### 実行例: パラメータに直接オブジェクトを渡した場合

```javascript
console.log(obj);  //=> { a: 100, b: 200 }
```

`console.dir()` というメソッドもあります。
`console.dir()` はオブジェクトを 1 つだけ受け取り、上記のように `console.log()` を呼び出した場合と同様の出力を行います（なので基本は `console.log()` を使えばよい）。
ちなみに、Firefox などの開発コンソール上で `console.dir(Object)` を使用すると、ツリー形式でプロパティを表示したり、その内容を編集したりと、そのオブジェクトに対して `console.log()` よりも高度な作業を行えます。

