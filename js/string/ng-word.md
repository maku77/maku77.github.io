---
title: "文字列内に NG ワード（禁止語句）が含まれていないか調べる (RegExp#test)"
date: "2019-07-23"
---

下記の記事の応用例ですが、

- [文字列の中から文字列を検索する (String#search, RegExp#test)](./search.html)

ある文字列内に NG ワードが含まれているかを調べるには、正規表現クラス (`RegExp`) の `test` メソッドを使用して OR 検索すると簡単です。


```js
// ユーザー入力が input 変数に含まれていると仮定
// var input = 'あんたバカ？';

const NG_WORDS = /アホ|まぬけ|バカ/;

if (NG_WORDS.test(line)) {
  console.log('アホ言うやつがアホや！');
}
```

ちなみに、正規表現リテラルのオプションとして **`i`** フラグを指定しておけば、大文字と小文字を区別しない比較が可能です。

```js
const NG_WORDS = /fxxk/i;
```

- 参考: [RegExp.prototype.test() - JavaScript｜MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/RegExp/test)

