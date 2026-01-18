---
title: "Node.jsメモ: 標準出力 (stdout)、標準エラー出力 (stderr) への出力"
url: "p/sffabwx/"
date: "2014-07-13"
tags: ["nodejs"]
aliases: /nodejs/io/stdio.html
---

Node.js でグローバルに定義されている **`console`** オブジェクトを使用すると、標準出力、標準エラー出力への出力を行うことができます。

- 参考: [Node.js ドキュメント - console](https://nodejs.org/api/console.html)


console で文字列を出力する
----

`console` オブジェクトを使用して文字列を出力するときは、主に次のようなメソッドを使用します。

- <b>標準出力 (stdout) への出力</b>: **`console.log()`** あるいは **`console.info()`**
- <b>標準エラー (stderr) への出力</b>: **`console.error()`** あるいは **`console.warn()`**

`console.info()` と `console.warn()` は単なるエイリアスなので、`console.log()` と `console.error()` を覚えておけば十分です。

これらのメソッドは、単純な文字列を渡すこともできますし、C 言語の `printf` 関数のようにフォーマット文字列を指定することもできます。

```js
console.log('Hello World');
console.log('Count: %d', 100);
console.log('Hello: %s', 'Tom');
console.log('JSON: %j', obj);  // JSONテキストの出力
```

console でオブジェクトを直接出力する
----

`console.log` メソッドなどに、フォーマット文字列を指定せずに直接オブジェクトを渡すと、適切にフォーマットされてオブジェクトの内容が表示されます。

{{< code lang="js" title="実行例: パラメータに直接オブジェクトを渡した場合" >}}
console.log(obj);  //=> { a: 100, b: 200 }
{{< /code >}}

**`console.dir()`** というメソッドもあります。
`console.dir()` はオブジェクトを 1 つだけ受け取り、基本的には `console.log()` と同様の情報を出力します。
ただし、Chrome や Firefox などの開発者ツール上で `console.dir(object)` を使用すると、そのオブジェクトのプロパティをツリー形式で展開して確認できるほか、一部の内容を直接編集することもできます。
`console.log()` よりも、オブジェクトの構造を詳しく調べたい場合に便利です。

