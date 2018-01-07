---
title: PHP のコーディングスタイル
date: "2012-07-24"
---

参考にすべき PHP のコーディングスタイル
----

現状、PHP のコーディングスタイルのデファクトスタンダードは、Zend Framework あるいは、PEAR が定義しているコーディングスタイルのようです。
Zend のコーディングスタンダードはシンプルにまとまっているので、一度目を通しておくとよいでしょう。

- Zend:
  - [http://framework.zend.com/manual/en/coding-standard.html](http://framework.zend.com/manual/en/coding-standard.html)
  - [http://framework.zend.com/manual/1.12/en/coding-standard.naming-conventions.html](http://framework.zend.com/manual/1.12/en/coding-standard.naming-conventions.html)
- Pear:
  - [http://pear.php.net/manual/en/standards.php](http://pear.php.net/manual/en/standards.php)
  - [http://pear.php.net/manual/en/standards.naming.php](http://pear.php.net/manual/en/standards.naming.php)
- Wordpress:
  - [http://codex.wordpress.org/WordPress_Coding_Standards](http://codex.wordpress.org/WordPress_Coding_Standards)
- CodeIgniter:
  - [http://codeigniter.com/user_guide/general/styleguide.html](http://codeigniter.com/user_guide/general/styleguide.html)


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

### サンプルコード

~~~ php
/**
 * PHPDocumentor 用の Documentation Block を記述する。
 * phpDocumentor format は http://phpdoc.org/ を参照。
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
~~~


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
