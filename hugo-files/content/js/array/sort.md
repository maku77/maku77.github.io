---
title: "JavaScriptメモ: 配列をソートする (sort, reverse)"
url: "p/gb6xnyz/"
date: "2014-03-17"
lastmod: "2020-12-03"
tags: ["javascript"]
aliases: [/js/array/sort.html]
---

アルファベット順ソート
----

JavaScript の配列のソート ([Array.prototype.sort()](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array/sort)) は、デフォルトでは __アルファベット順のソート__（Unicodeのコードポイントの昇順）になることに注意してください。

```js
const nums = [1, 2, 3, 11, 12, 13];
nums.sort();
console.log(nums);  //=> [1, 11, 12, 13, 2, 3]
```

上記の例では、数値を要素に含む配列をソートしていますが、ソート結果は数値の昇順ではなく、アルファベット順になるため、`2` よりも前に `11` が来ています。
`sort()` 関数は、自分自身の配列の内容を書き換えることに注意してください。


数値順ソート
----

アルファベット順ではなく、数値順にソートするには、下記のように `sort()` 関数に数値用の比較関数を渡してやります。

```js
const nums = [1, 2, 3, 11, 12, 13];
nums.sort(function(a, b) {
  return a - b;
});
console.log(nums);  //=> [1, 2, 3, 11, 12, 13]
```

ES2015 以降は、比較関数の部分はアロー関数で記述するとスッキリします。

```js
nums.sort((a, b) => a - b);
```

比較関数は、２つの変数 `a`, `b` を受け取り、`a` を `b` より前に並べたいときは負の値、`b` を `a` より前に並べたいときは正の値、どちらでもよい場合は `0` を返すように実装します。
よって、`a - b` という計算結果を返すことで、数値の小さい順にソートされることになります（昇順ソート）。
`b - a` とすれば、降順ソートになります。


降順ソート
----

### 方法1（sort してから reverse）

```js
const arr = ['B', 'A', 'C'];
arr.sort().reverse();  //=> ['C', 'B', 'A']
```

### 方法2（降順ソート用の比較関数を渡す）

```js
const arr = ['B', 'A', 'C'];
arr.sort((a, b) => {
  if (a < b) return 1;
  if (a > b) return -1;
  return 0;
});
console.log(arr);  //=> ['C', 'B', 'A']
```


逆順にする (reverse)
----

```js
const arr = ['B', 'A', 'C'];
arr.reverse();
console.log(arr);  //=> ['C', 'A', 'B']
```

`reverse()` 関数も、`sort()` 関数と同様に、自分自身が指し示す配列の内容を変化させることに注意してください。


大文字・小文字を区別せずにソート
----

`sort()` 関数はデフォルトでは、Unicode コードポイントの昇順にソートするため、アルファベットは小文字よりも大文字の方が小さいと判断されます。

```js
const arr = ['hello', 'Hi', 'hack', 'Home'];
arr.sort();
console.log(arr);  //=> ['Hi', 'Home', 'hack', 'hello']
```

大文字、小文字を区別せずにアルファベット順でソートしたいときは、次のように、`toLowerCase()` で両方の引数を小文字に変換してから比較します。

```js
const arr = ['hello', 'Hi', 'hack', 'Home'];
arr.sort((a, b) => {
  a = a.toLowerCase();
  b = b.toLowerCase();
  if (a < b) return -1;
  if (a > b) return 1;
  return 0;
});
console.log(arr);  //=> ['hack', 'hello', 'Hi', 'Home']
```


独自オブジェクトのソート
----

独自クラスのオブジェクトをソートするときも、同様に 2 つのオブジェクトを比較する関数を定義してやれば、任意の順でソートできます。

下記は、`Book` クラスのオブジェクトを価格 (`price`) でソートする例です。
分かりやすいように TypeScript で型付けして記述しています。

```typescript
class Book {
  title: string;
  price: number;

  constructor(title: string, price: number) {
    this.title = title;
    this.price = price;
  }

  // ソート用の比較関数
  static comparePriceAscend(a: Book, b: Book): number {
    return a.price - b.price;
  }
}

// テスト
const books = [
  new Book('Title1', 300),
  new Book('Title2', 500),
  new Book('Title3', 100)
];

books.sort(Book.comparePriceAscend);  // 価格で昇順ソート
console.log(books);  // Title3, Title1, Title2 の順になる
```

