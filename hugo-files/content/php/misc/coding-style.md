---
title: "PHPメモ: PHP のコーディングスタイル"
url: "/p/56p9dkj/"
date: "2012-07-24"
tags: ["php"]
aliases: [/php/misc/coding-style.html]
---

参考にすべき PHP のコーディングスタイル
----

現状、PHP のコーディングスタイルのデファクトスタンダードは、Zend Framework あるいは、PEAR が定義しているコーディングスタイルのようです。
Zend のコーディングスタンダードはシンプルにまとまっているので、一度目を通しておくとよいでしょう。

- Zend:
  - https://framework.zend.com/manual/1.11/en/coding-standard.coding-style.html
- Pear:
  - https://pear.php.net/manual/en/standards.php
- WordPress:
  - https://developer.wordpress.org/coding-standards/wordpress-coding-standards/php/
- CodeIgniter:
  - https://ci-trans-jp.gitlab.io/user_guide_4_jp/contributing/styleguide.html


Zend Framework のコーディングスタイルまとめ
----

### 命名規則

- クラス名: `ClassName`（抽象クラスは `XxxAbstract`、インタフェースは `XxxInterface`、という名前にする）
- メソッド名 (public) : `methodName`
- メソッド名 (private) : `_methodName`
- メンバ変数 (public): `publicVar`
- メンバ変数 (private): `_privateVar`
- 定数: `CONSTANT_VALUE`
- 省略形は先頭文字だけ大文字: ○`Pdf` ×`PDF`
- 予約語: `true`, `false`, `null` はすべて小文字で記述

### その他
- インデントは半角スペース 4 文字
- 一行は 80 文字まで（やむを得ない場合は最大 120 文字）
- 改行コードは LF (0x0A)
- 文字列リテラルはシングルクォートで囲む（例: `$a = 'Example String';`）。ただし、シングルクォートを含む場合、文字列展開をする場合はダブルクォートで囲む。
- 文字列リテラル内で変数展開する場合は、`$` マークは中括弧の中に入れる（例: `$greeting = "Hello {$name}!";`）
- グローバルな関数は作らない（クラスにスタティックメソッドを作成すればよい）

{{< code lang="php" title="サンプルコード" >}}
/**
 * PHPDocumentor 用の Documentation Block を記述する。
 * phpDocumentor format は https://phpdoc.org/ を参照。
 */
class SampleClass
{   // クラスの開き括弧は改行後
    // メンバ変数は、クラスの先頭にまとめて記述する
    const CONSTANT_NAME = 100;  // 定数
    public $publicVar;          // Public メンバ変数
    private $_privateVar;       // Private メンバ変数はアンダースコアで始める

    /**
     * Documentation Block Here.
     */
    public function publicMethod()  // Public メンバメソッド
    {
        // ...
    }

    private function _privateMethod()  // Private メンバメソッド
    {
        // ...
    }
}
{{< /code >}}


CodeIgniter のコーディングスタイルまとめ
----

CodeIgniter のコーディングスタイルは、Zend や PEAR のルールとかなり異なります。
重要なところをまとめておきます。

* クラス名: `Class_name` のように、先頭大文字で、アンダースコア区切り。
* メソッド名、変数名: `method_name` のように、全て小文字で、アンダースコア区切り。
* ファイル名 (Controller, Helper, View): `class_name.php` のように全て小文字で、アンダースコア区切り。
* ファイル名 (Library): `Class_name.php` のように先頭が大文字で、アンダースコア区切り。
* `TRUE`, `FALSE`, `NULL` は大文字で統一（Zend や PEAR のルールと逆）。
* Private メソッドはアンダースコアで始める (`_private_method`)
