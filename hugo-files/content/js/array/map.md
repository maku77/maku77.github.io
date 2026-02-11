---
title: "JavaScriptメモ: 配列の各要素に対して同じ処理を行う (map)"
url: "p/cv582g2/"
date: "2018-03-12"
tags: ["javascript"]
aliases: [/js/array/map.html]
---

[Array.prototype.map()](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array/map) を使用すると、配列の各要素に対して、渡した関数を適用した新しい配列を作成することができます。

{{< code lang="javascript" title="例: 配列の各要素を2倍にした配列を作成する" >}}
const arr = [1, 2, 3];
const arr2 = arr.map((x) => { return x*2; });
console.log(arr2);  //=> [2, 4, 6]
{{< /code >}}

`map()` 関数は、元の配列を変更しないため、`arr` の値は `[1, 2, 3]` のままであることに注意してください。

最新の JavaScript では、**アロー関数式**を使用して、次のようにシンプルに記述することができます。

```javascript
const arr = [1, 2, 3];
const arr2 = arr.map(x => x*2);  //=> [2, 4, 6]
```


マップに渡す関数の第二引数には要素のインデックス番号が渡されます。
次の例では、これを利用して、要素に連番を振っています（あまり意味のない使い方ですが）。

{{< code lang="javascript" title="例: インデックス番号も利用する" >}}
const arr = ['AAA', 'BBB', 'CCC'];
const arr2 = arr.map((x, i) => i + ':' + x);  //=> ['0:AAA', '1:BBB', '2:CCC']
{{< /code >}}

