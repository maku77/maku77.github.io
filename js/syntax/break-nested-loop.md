---
title: "入れ子になったループを一度に抜ける（多重ループからの break）"
date: "2012-10-30"
lastmode: "2019-10-24"
---

外側の `while` ループにラベルを設定しておくと、内側のループから一気に `break` して抜けることができます。

```javascript
outer_loop:
while (true) {
  console.log('AAA');
  while (true) {
    console.log('BBB');
    break outer_loop;
  }
}
```

ラベル部分は次のように一行で書いちゃった方がそれっぽいかも。

```javascript
OUTER: while (true) {
  // ...
}
```

