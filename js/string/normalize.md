---
title: "全角文字と半角文字を含んだ文字列を正規化して表記ゆれを吸収する (normalize)"
date: "2019-04-08"
description: "ECMAScript2015 で定義された String#normalize() は、Unicode 正規化を行うためのメソッドです。これを利用すると、文字列中の全角文字を半角文字に変換することができます。"
---

[String.prototype.normalize() - JavaScript｜MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/String/normalize)

**`String#normalize()`** のパラメータとして、**`NFKC`**（正規化形式 KC）を指定して文字列を正規化すると、ある文字列中に含まれる半角文字や全角文字を正規化して表現を統一することができます。
これを利用すると、テキスト検索などのプログラムにおける表記ゆれの問題を（ある程度）解決できます。

下記のサンプルコードでは、半角カタカナ（`ｱｲｳｴｵ`）と、全角アルファベット（`ＡＢＣ`）を `normalize()` を使って正規化しています。

```js
const s1 = 'ｱｲｳｴｵ';
const s2 = 'ＡＢＣ';
console.log(`${s1} => ${s1.normalize('NFKC')}`);
console.log(`${s2} => ${s2.normalize('NFKC')}`);
```

#### 実行結果

```
ｱｲｳｴｵ => アイウエオ
ＡＢＣ => ABC
```

半角カタカナは全角に、全角アルファベットは半角に変換されていることがわかります。


いろいろな正規化フォーマット
----

`String#normalize()` では、下記のような Unicode 正規化フォーマットを指定できるようになっています。

- `'NFC'` ... Normalize Format C: 正規化形式 C （デフォルト）
- `'NFD'` ... Normalize Format D: 正規化形式 D
- `'NFKC'` ... Normalize Format KC: 正規化形式 KC
- `'NFKD'` ... Normalize Format KD: 正規化形式 KD

正規化フォーマットの詳細仕様に関しては下記を参照してください。

- [Unicode Standard Annex #15, Unicode Normalization Forms](https://www.unicode.org/reports/tr15/)
- [Unicode正規化 - Wikipedia](https://ja.wikipedia.org/wiki/Unicode%E6%AD%A3%E8%A6%8F%E5%8C%96)

下記のようなサンプルコードで、それぞれの正規化フォーマットによる変換を試すことができます。

```js
const TEXTS = [
  'ｱｲｳｴｵ',
  'パピプペポ',
  'ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ',
  'ａｂｃＡＢＣ',
  '１２３',
  '＋－．～）｝',
];
const FORMS = ['NFC', 'NFD', 'NFKC', 'NFKD'];

TEXTS.forEach(function(t) {
  FORMS.forEach(function(f) {
    console.log(`${t} ==(${f})==> ${t.normalize(f)}`);
  });
});
```

#### 実行結果

```
ｱｲｳｴｵ ==(NFC)==> ｱｲｳｴｵ
ｱｲｳｴｵ ==(NFD)==> ｱｲｳｴｵ
ｱｲｳｴｵ ==(NFKC)==> アイウエオ
ｱｲｳｴｵ ==(NFKD)==> アイウエオ
パピプペポ ==(NFC)==> パピプペポ
パピプペポ ==(NFD)==> パピプペポ
パピプペポ ==(NFKC)==> パピプペポ
パピプペポ ==(NFKD)==> パピプペポ
ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ ==(NFC)==> ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ
ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ ==(NFD)==> ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ
ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ ==(NFKC)==> パピプペポ
ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ ==(NFKD)==> パピプペポ
ａｂｃＡＢＣ ==(NFC)==> ａｂｃＡＢＣ
ａｂｃＡＢＣ ==(NFD)==> ａｂｃＡＢＣ
ａｂｃＡＢＣ ==(NFKC)==> abcABC
ａｂｃＡＢＣ ==(NFKD)==> abcABC
１２３ ==(NFC)==> １２３
１２３ ==(NFD)==> １２３
１２３ ==(NFKC)==> 123
１２３ ==(NFKD)==> 123
＋－．～）｝ ==(NFC)==> ＋－．～）｝
＋－．～）｝ ==(NFD)==> ＋－．～）｝
＋－．～）｝ ==(NFKC)==> +-.~)}
＋－．～）｝ ==(NFKD)==> +-.~)}
```


参考
----

Java にも同様の Unicode 正規化を行うためのクラス `java.text.Normalizer` が用意されています。

- [全角文字と半角文字を含んだ文字列を正規化して表記ゆれを吸収する｜まくまくJavaノート](/java/numstr/normalize-string.html)

