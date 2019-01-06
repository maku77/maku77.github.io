---
title: "入れ子になったループを一度に抜ける"
date: "2012-10-30"
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

