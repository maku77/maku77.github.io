---
title: "文字列内のひらがなとカタカナを変換する"
date: "2020-02-17"
---

「ひらがな → カタカナ」の変換
----

次の関数 `hiraToKata()` は、渡された文字列内のすべてのひらがなをカタカナに変換します。

```js
/** 文字列内のひらがなをカタカナに変換します。 */
function hiraToKata(str) {
  return str.replace(/[\u3041-\u3096]/g, ch =>
    String.fromCharCode(ch.charCodeAt(0) + 0x60)
  );
}

// 使用例
console.log(hiraToKata('あいうえおABC'));  //=> アイウエオABC
console.log(hiraToKata('アイウエオ123'));  //=> アイウエオ123
```


「カタカナ → ひらがな」の変換
----

次の関数 `kataToHira()` は、渡された文字列内のすべてのカタカナをひらがなに変換します。

```js
/** 文字列内のカタカナをひらがなに変換します。 */
function kataToHira(str) {
  return str.replace(/[\u30A1-\u30FA]/g, ch =>
    String.fromCharCode(ch.charCodeAt(0) - 0x60)
  );
}

console.log(kataToHira('あいうえおABC'));  //=> あいうえおABC
console.log(kataToHira('アイウエオ123'));  //=> あいうえお123
```

解説
----

次のように、カタカナとひらがなの UTF-16 コードポイントの値を比較すると、**0x60** だけずれていることが分かります。

```js
const diff = 'ア'.charCodeAt(0) - 'あ'.charCodeAt(0);
console.log(diff.toString(16));  //=> 60
```

この 0x60 という値を、ひらがなに足してやればカタカナに、カタカナから引いてやればひらがなに変換することができます。
ある文字が、ひらがな、あるいはカタカナであることを調べるには、次のような正規表現が使えます（ただし全角文字のみ）。

- ひらがな: `[\u3041-\u3096]`
- カタカナ: `[\u30A1-\u30FA]`

### 参考

- [ひらがなとカタカナを正規表現で表す｜まくろぐ](https://maku.blog/p/kkaq7q5)
- [TypeScriptサンプル: 文字列内のひらがなとカタカナを変換する｜まくろぐ](https://maku.blog/p/tzjvcad)

