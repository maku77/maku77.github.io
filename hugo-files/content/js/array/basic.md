---
title: "JavaScriptメモ: 配列の基本"
url: "p/o2o6p9w/"
date: "2012-10-27"
tags: ["javascript"]
aliases: [/js/array/basic.html]
---

配列の生成
----

JavaScript には組み込みの配列型はありませんが、`Array` オブジェクトが用意されています。
`Array` オブジェクトの生成には、通常は下記のような配列リテラルを使用します（速度的にもこの方法が最速であることが多いです）。

```javascript
const arr = [100, 200, 300];
print(typeof arr);  //=> "object"
```

配列の要素には、任意の型の値を格納できます。

```javascript
const arr = [100, "str", [10, 20], {"x":1, "y":2}, () => { print("Hello");}];
print(arr[0]);  //=> 100
print(arr[1]);  //=> str
print(arr[2][0]);  //=> 10
print(arr[3].x);  //=> 1
arr[4]();  //=> Hello
```


配列のサイズを取得する
----

`length` プロパティで、配列のサイズを取得できます。

```javascript
const arr = [10, 20, 30];
print(arr.length);  //=> 3
```


配列に要素を追加する
----

`Array` オブジェクトの `push` メソッドを使用すると、配列の末尾に要素を追加できます。

```javascript
const arr = [];
arr.push(10);
arr.push(20);
arr.push(30);
print(arr.length);  //=> 3
print(arr);  //=> 10,20,30
```

