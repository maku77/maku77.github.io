---
title: "連想配列にキーが存在するか調べる (in)"
date: "2019-04-08"
---

JavaScript の連想配列（あるいは通常の配列）が、指定したキーを含んでいるかを調べるには `in` キーワードを使用します。

下記の例では、連想配列 `a` の中が `key1` というキーを持っているかを調べています。

```js
const a = { 'key1': 100, 'key2': 200 };
if ('key1' in a) {
  console.log(`key1 が見つかりました。値は ${a.key1} です。`);
}
```

少し手を抜いた方法だと、オブジェクトの存在しないプロパティを参照したときに `undefined` が返されることを利用して、下記のように判定する方法もあります。

```js
if (a.key1) {
  console.log(`key1 が見つかりました。値は ${a.key1} です。`);
}
```

ただし、**この判定方法だと、キー `key1` の値として、`false`、`null`、`0`、`''`（空文字列） などが格納されている場合にも偽と判断されてしまいます**。
単純に「キーが存在するかどうか」を調べたいのであれば、`in` を使って判定する必要があります。
用途によって使い分けるようにしましょう。

