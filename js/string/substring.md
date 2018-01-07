---
title: 部分文字列を取得する (substring, slice)
date: "2012-01-20"
---

n 文字目から m 文字目までの部分文字列を取得する
----

`substring()` メソッド（あるいは `slice()` メソッド）を使用すると、文字列から位置を指定して部分文字列を抽出することができます。

```javascript
var s = 'ABCDEFGHIJ';

console.log(s.substring(5));     // 'FGHIJ' （５文字目以降）
console.log(s.slice(5));         // 'FGHIJ' （５文字目以降）

console.log(s.substring(5, 8));  // 'FGH' （５文字目から８文字目）
console.log(s.slice(5, 8));      // 'FGH' （５文字目から８文字目）
```

後ろから n 文字の部分文字列を取得する
----

上記のように `0` 以上のインデックスを指定した場合は、`substring()` も `slice()` も同様に振る舞いますが、負のインデックスを指定した場合は動作が異なります。
`substring()` の場合は、`0` を指定したのと同様に振る舞うため、文字列の先頭を指定したことになります。
`slice()` の場合は、例えば `-3` と指定すれば、末尾から３文字目を指定したことになります。

```javascript
var s = 'ABCDEFGHIJ';

console.log(s.substring(-3));    // 'ABCDEFGHIJ'（負の値は０を指定したのと同じ）
console.log(s.slice(-3));        // 'HIJ' （末尾の３文字）
```

