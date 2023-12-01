---
title: "JavaScript で文字列を指定の長さになるまでパディング（埋め合わせ）する (String#padStart, #String#padEnd)"
url: "p/buatano/"
date: "2023-12-01"
tags: ["javascript"]
---

padStart と padEnd の基本
----

JavaScript の __`String#padStart()`__ および __`String#padEnd()`__ メソッドを使うと、指定した文字数になるまで文字列を拡張してくれます。
どのような文字で埋め合わせるかは、第 2 引数で指定します。

{{< code lang="js" title="左側にパディング" >}}
const s = "ABC";
console.log(s.padStart(2, "x"));  //=> "ABC"
console.log(s.padStart(3, "x"));  //=> "ABC"
console.log(s.padStart(4, "x"));  //=> "xABC"
console.log(s.padStart(5, "x"));  //=> "xxABC"
console.log(s.padStart(6, "x"));  //=> "xxxABC"
{{< /code >}}

{{< code lang="js" title="右側にパディング" >}}
const s = "ABC";
console.log(s.padEnd(2, "x"));  //=> "ABC"
console.log(s.padEnd(3, "x"));  //=> "ABC"
console.log(s.padEnd(4, "x"));  //=> "ABCx"
console.log(s.padEnd(5, "x"));  //=> "ABCxx"
console.log(s.padEnd(6, "x"));  //=> "ABCxxx"
{{< /code >}}

第 2 引数に 2 文字以上の文字列を指定すると、各文字が順番に使われます。

```js
const s = "ABC";
console.log(s.padStart(10, "xyz"));  //=> "xyzxyzxyzxABC"
```


使用例
----

### byte 数値を二進数文字列にする（8 桁になるまで 0 パディング）

```js
function toBinaryString(byteNum) {
  return byteNum.toString(2).padStart(8, "0");
}

console.log(toBinaryString(0));    //=> "00000000"
console.log(toBinaryString(3));    //=> "00000011"
console.log(toBinaryString(255));  //=> "11111111"
```

### コンソール出力用の右寄せ（スペースでパディング）

```js
function alignRight(s) {
  return s.padStart(5);  // デフォルトでスペースでパディングされます
}

console.log(alignRight("AAA"));     //=> "  AAA"
console.log(alignRight("BBBB"));    //=> " BBBB"
console.log(alignRight("CCCCC"));   //=> "CCCCC"
console.log(alignRight("DDDDDD"));  //=> "DDDDDD"
```

### 末尾の 4 文字以外を隠す（カード番号や電話番号などに）

```js
function hideAllButTheEnd(s) {
  const last4Chars = s.slice(-4);
  const maskedStr = last4Chars.padStart(s.length, "*");
  return maskedStr;
}

const cardNumber = "2034399002125581";
console.log(hideAllButTheEnd(cardNumber));  //=> "************5581"
```

ちなみに上記のコードは、MDN の [`padStart()` のドキュメント](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/String/padStart) で紹介されているものです。

