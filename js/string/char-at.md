---
title: "文字列内の１文字を取得する／１文字ずつループ処理する (charAt)"
date: "2012-10-26"
---

文字列内の１文字を取得する
----

ECMAScript の仕様に従うのであれば、文字列内の任意の位置の文字を取得するには `charAt()` を使用します。

```javascript
var s = 'ABCDE';
console.log(s.charAt(2));  //=> 'C'
```

ただ、JavaScript の独自拡張では、以下のように数値プロパティで任意の位置の文字を取り出せますので、環境依存のコードでもよいのであれば、こちらのシンプルな方法もありです。

```javascript
console.log(s[2]);  //=> 'C'
```


１文字ずつループ処理する
----

文字列の長さだけ for ループを回せば、文字列内の文字を１文字ずつ処理することができます。

~~~javascript
var s = 'ABCDE';
for (var i = 0; i < s.length; ++i) {
  console.log((i + 1) + '文字目: ' + s.charAt(i));
}
~~~

#### 実行結果

~~~
1文字目: A
2文字目: B
3文字目: C
4文字目: D
5文字目: E
~~~

`split` メソッドで１文字ずつの配列に分割してしまってから、その配列をループ処理するという方法もありますね。

~~~javascript
var s = 'ABCDE';
var arr = s.split('');
for (var i = 0; i < arr.length; ++i) {
  console.log((i + 1) + '文字目: ' + arr[i]);
}
~~~


文字列内の１文字だけ変更することはできない
----

JavaScript の文字列は不変 (immutable) なので、以下のように文字列内の１文字を変更するような操作はできません。

```javascript
s[2] = 'X';  // NG
```


