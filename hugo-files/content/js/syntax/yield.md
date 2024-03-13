---
title: "JavaScript の yield を使ってイテレート可能な関数（ジェネレーター関数）を定義する"
url: "p/q3a7jg4/"
date: "2024-03-13"
tags: ["JavaScript"]
---

JavaScript の関数内で __`yield`__ キーワードを使用すると、関数の呼び出し側でイテレート処理（ループ処理）が可能な関数を定義することができます。
値を生成する関数になるので、__ジェネレーター関数__ と呼ばれます。

{{% note %}}
__`yield`__ キーワードは ES2015 (ECMAScript 6) で導入されました。
{{% /note %}}


ジェネレーター関数の基本
----

下記は `number` 値を生成するジェネレーター関数の実装例です（TypeScript の型としては __`Generator`__ を使います）。
ジェネレーター関数を定義するときは、`function` の代わりに __`function*`__ を使います。

{{< code lang="ts" title="シンプルなジェネレーター関数" >}}
function* counter(): Generator<number> {
  yield 1;
  yield 2;
  yield 3;
}
{{< /code >}}

ジェネレーター関数が生成する次の値を取得するには、__`next()`__ メソッドを使用します。
`next()` メソッドは、__`value`__ プロパティと __`done`__ プロパティを持つ `IteratorResult` を返します。
すべての値の生成が終わると（ジェネレーター関数が `return` すると）、`value` プロパティの値は `undefined` になります。

```ts
const gen = counter();
console.log(gen.next().value); // 1
console.log(gen.next().value); // 2
console.log(gen.next().value); // 3
console.log(gen.next().value); // undefined
```

ジェネレーター関数内の処理は、`yield` が実行されたタイミングで一時停止します。
呼び出し側で `next()` メソッドが呼び出されたときに実行が再開されます。


ジェネレーターのループ処理
----

ジェネレーターを `for-of` ループで処理すると、生成された値を最後まで取得することができます。
このとき、自動的に `value` プロパティの値を取り出してくれるので、次のようなシンプルなコードで処理できます。

```ts
const gen = counter();
for (const num of gen) {
  console.log(num);
}
```

{{< code title="実行結果" >}}
1
2
3
{{< /code >}}


非同期なジェネレーター関数
----

非同期に実行されるジェネレーター関数を定義することもできます。
戻り値の型は `Generator` ではなく、__`AsyncGenerator`__ になります。
`AsyncGenerator` の `next()` メソッドを呼び出すと、次の値を取得するための `Promise` オブジェクトが返されます。

{{< code lang="ts" title="非同期ジェネレーター関数" >}}
async function* stringGenerator(): AsyncGenerator<string> {
  const strs = ['This', 'is', 'a', 'pen'];
  for (const s of strs) {
    yield await Promise.resolve(s);
  }
}

const gen = stringGenerator();
for await (const s of gen) {
  console.log(s);
}
{{< /code >}}

下記はもう少し非同期っぽく、1 秒おきに `yield` するようにした例です。

```ts
async function* stringGenerator(): AsyncGenerator<string> {
  const strs = ['This', 'is', 'a', 'pen'];
  for (const s of strs) {
    // 1 秒おきに yield
    yield await new Promise((resolve) => {
      setTimeout(() => resolve(s), 1000);
    });
  }
}

const gen = stringGenerator();
for await (const s of gen) {
  console.log(s);
}
```

