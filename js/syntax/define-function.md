---
title: 関数を定義する
created: 2012-11-13
---

関数定義の基本
----

### 名前付き関数の定義
```javascript
function add(a, b) {
  return Number(a) + Number(b);
}
print(add(100, 200));  // 300
```

### 名前なし関数（関数オブジェクト）

無名の関数リテラルが返す関数オブジェクトの参照を、変数で保持する方法もあります。
JavaScript の関数はオブジェクトです。

```javascript
var add = function(a, b) {
  return Number(a) + Number(b);
}
print(add(100, 200));  // 300
```

関数の仮引数と実引数の数は異なっていてもよい
----

関数の定義に記述した仮引数と、実際に渡す実引数の数は異なっていてもエラーにはなりません。
実引数の方が多ければ、余分な実引数は無視され、仮引数の方が多ければ、指定されなかった引数は `undefined` になります。

```javascript
function hoge(a, b) {
  print(a);
  print(b);
}
hoge('AAA', 'BBB', 'CCC');  //=> AAABBB
hoge('AAA');                //=> AAAundefined
```

このような仕様のため、JavaScript には引数の数が異なる関数を定義する仕組み（オーバーロード）はありません。

