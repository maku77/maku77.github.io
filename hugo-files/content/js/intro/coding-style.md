---
title: "JavaScript のコーディングスタイル"
url: "p/pgw5j96/"
date: "2012-11-19"
tags: ["JavaScript"]
aliases: /js/intro/coding-style.html
---

一般的に採用されている JavaScript のコーディングスタイルのまとめです。


記述フォーマット
----

* 一行の最大文字数は __`79`__ 文字（改行を入れて 80 文字）
* インデントはスペース __`2`__ つ（複数行にまたがる時は、スペース 4 つにするか、前の行のドット等に合わせる）
* ファイルのエンコーディング形式は __`UTF-8`__、改行コードは __`LF`__ (0x0A)

ドキュメンテーションコメント
----

ドキュメンテーションコメントは __JSDoc__ で記述します。

```javascript
/**
 * Find a person who has a specified nickname.
 *
 * @param {String} nickname - The nickname to be searched
 * @return {Person} A person if found, null if not found
 */
function findPerson(nickname) {
  // ...
  return person;
}
```


命名規則
----

* クラス: `ClassName`
* メソッド (public/protected) : `methodName`
* メソッド (private) : `methodName_`
* プロパティ (public/protected): `propName`
* プロパティ (private): `propName_`
* 変数: `varName`
* 定数: `CONSTANT_NAME`

省略形は先頭文字だけ大文字にします。

* Good: `Pdf`, `Id`, `Xml`, `Http`
* NG: `PDF`, `ID`, `XML`, `HTTP`


文字列リテラル
----

文字列リテラルの定義には、シングルクォーテーションを使います。
これは HTML タグを記述する際に相性がよいからです。
文字列内にシングルクォーテーションが含まれるような場合は、例外的に全体をダブルクォーテーションで囲みます。


クラスの定義方法
----

__（2024 年追記: 現在は `class` キーワードを使ってクラス定義すればよいです）__

JavaScript でクラス（正確にはクラス風のオブジェクト）を定義する方法はいろいろありますが、基本は下記のような形式で定義します。

```javascript
/** @constructor */
function Foo() {
  this.bar = value;
}

Foo.prototype.methodName = function() {
  ...
};
```

プライベートなクラス、メソッド、プロパティは `@private` を付けて、アンダースコアで終えます。

```javascript
/**
 * @private
 * @constructor
 */
function PrivateClass_() {
  /** @private */
  this.privateProp_ = 2;
}

/** @private */
PrivateClass_.privateStaticMethod_ = function() {
  ..
};

/** @private */
PrivateClass_.prototype.privateMethod_ = function() {
  ..
};
```


その他
----

無名の関数オブジェクトで済ませられるような場所でも、デバッグしやすくするために、できるだけ関数には名前を付けておくようにします。


参考になるコーディングスタイル
----

- [Google JavaScript Style Guide](https://google.github.io/styleguide/javascriptguide.xml)
  - 一行原則 80 文字以内。
  - インデントはスペース 2 文字。
- [Node.js Style Guide](https://github.com/felixge/node-style-guide)
  - 一行原則 80 文字以内。
  - インデントはスペース 2 文字。
- [jQuery のコーディングスタイル](https://contribute.jquery.org/style-guide/js/)
  - カッコ内のスペースの使い方などが、マイナーなのでちょっと嫌。
  - 一行原則 80 文字以内。
  - インデントはスペース 4 つ（JavaScript の世界では少数派）。

