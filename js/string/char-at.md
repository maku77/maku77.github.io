---
title: 文字列内の１文字を取得する
created: 2012-10-26
---

ECMAScript の仕様に従うのであれば、文字列内の任意の位置の文字を取得するには `charAt()` を使用します。

```javascript
var s = 'ABCDE';
console.log(s.charAt(2));  //=> 'C'
```

ただ、JavaScript の独自拡張では、以下のように数値プロパティで任意の位置の文字を取り出せますので、環境依存のコードでもよいのであれば、こちらのシンプルな方法もありです。

```javascript
console.log(s[2]);  //=> 'C'
```

ちなみに、JavaScript の文字列は不変 (immutable) なので、以下のように文字列内の１文字を変更するような操作はできません。

```javascript
s[2] = 'X';  // NG
```

