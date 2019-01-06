---
title: "ESLint で JavaScript コードの静的解析を行う"
date: "2015-04-07"
---

eslint コマンドのインストール
----

ESLint のインストールは、JSHint と同様に Node.js の `npm` コマンドで簡単に行うことができます。

~~~
$ npm install -g eslint
~~~

インストールできたか確認します。

~~~
$ eslint --version
v0.18.0
~~~

JavaScript コードのチェックは下記のように実行します。

~~~
$ eslint sample.js
~~~

ESLint の設定ファイル
----

ESLint の設定は、JSON or YAML 形式の `.eslintrc` ファイルで行います。
以下のような構成で、前提とする実行環境、参照可能なグローバルオブジェクト、適用する Lint ルールの設定、を記述していきます。

#### .eslintrc ファイルの構成

~~~ js
{
  "env": {
    (1) 実行環境
  },
  "globals": {
    (2) 参照するグローバルオブジェクト
  },
  "rules": {
    (3) Lint ルールの設定
  }
}
~~~

Node モジュールとして作成する JS ファイルに対しては、`package.json` の中に ESLint の設定を記述してしまうことができます。
その場合は、下記のように、`eslintConfig` プロパティの中に同様の設定を記述します。

#### package.json

~~~ js
{
  "name": "myapp",
  "version": "0.0.1",
  "eslintConfig": {
    "env": {
      "browser": true,
      "node": true
    }
  }
}
~~~

それでは、各項目の順番に設定方法を見ていきます。

### (1) Environments - 実行環境の指定

このセクションでは、その JS ファイルがどのような環境で実行されるかを指定します。
例えば、「ブラウザ」上で実行する、「AMD モジュール」として作る、「Node モジュール」として作る、といった前提環境を指定します。
ここで環境を指定することにより、その環境に合わせたグローバルオブジェクトの参照許可や、Lint ルールなどが自動的に設定されます。
指定できる環境の一覧は、

- [http://eslint.org/docs/user-guide/configuring](http://eslint.org/docs/user-guide/configuring)

の Specifying Environments の節に、次のように列挙されています。

~~~
browser - browser global variables.
node - Node.js global variables and Node.js-specific rules.
amd - defines require() and define() as global variables as per the amd spec.
mocha - adds all of the Mocha testing global variables.
jasmine - adds all of the Jasmine testing global variables for version 1.3 and 2.0.
phantomjs - phantomjs global variables.
jquery - jquery global variables.
prototypejs - prototypejs global variables.
shelljs - shelljs global variables.
es6 - enable all ECMAScript 6 features except for modules.
~~~

例えば、ブラウザ上で実行する JavaScript コードであり、その中で jQuery と RequireJS を使用したいのであれば、下記のように定義します。

#### .eslintrc 抜粋

~~~ js
{
  "env": {
    "amd": true,      // Refer to define() and require() as AMD modules
    "browser": true,  // Refer to document object etc. as web browser
    "jquery": true    // Refer to $ object to use jQuery library
  },
  "globals": { ... },
  "rules": { ... }
}
~~~

このように環境設定しておくことで、jQuery オブジェクト (`$`) などのグローバルオブジェクトにアクセスしても、未定義エラーとして検出されないようにすることができます。

#### (2) Globals - 参照するグローバルオブジェクト

このセクションでは、参照可能なグローバル変数を、ひとつずつ明示的に設定することが可能です。
環境設定セクションに `"amd": true` と書いてあれば、`define()` と `require()` はアクセス可能になるのですが、下記のように明示的に指定することも可能です。

#### .eslintrc 抜粋

~~~ js
{
  "env": { ... },
  "globals": {
    "define": true,
    "require": true
  },
  "rules": { ... }
}
~~~

JSLint と同様に、ソースコードの中で、参照可能なグローバルオブジェクトを定義しておくこともできます（変数名の後ろの `false` は、このファイルで代入を行わないということを示します）。

#### JS ファイルの中で

~~~ js
/*global var1:false, var2:false*/
~~~


### (3) Rules - 適用する Lint ルールの設定

ルールのセクションでは、実際に適用する Lint ルールの設定を行います（ここが設定が重要）。
各ルールに対しての設定を、下記のようなプロパティの形でひとつずつ指定していきます。

~~~
"ルール名": 適用方法,
~~~

適用方法の部分には、そのルールをどう反映するかを表す、0、1、2 のいずれかの値を指定します。

- 適用方法
  - 0 - off（ルールを無効にする）
  - 1 - warning（警告として検出する）
  - 2 - error（エラーとして検出する）

ルール設定には、追加のパラメータを持つものもあり、その場合は配列の形で設定値を記述します。
配列の 1 番目の値として、上記と同様に 0、1、2 のいずれかの適用方法を指定します。

~~~
"ルール名": [適用方法, 追加パラメータ1, 追加パラメータ2]
~~~

#### .eslintrc 抜粋

~~~ js
{
  "env": { ... },
  "globals": { ... },
  "rules": {
    /*
     * Possible Errors
     */
    "valid-jsdoc": 2,  // Ensure JSDoc comments are valid
    "no-duplicate-case": 2,  // Disallow a duplicate case label
    "no-empty": 2,  // Disallow empty statements
    "no-irregular-whitespace": 2,  // Disallow irregular whitespace
    "no-unreachable": 2,  // Disallow unreachable statements
    "use-isnan": 2,  // Disallow comparisons with the value NaN

    /*
     * Stylistic Issues
     */
    "camelcase": 1,  // Require camel case names
    "indent": [1, 2],  // An indent should be two spaces
    "quotes": [1, "single", "avoid-escape"],  // Surround a string with single quotation

    /*
     * Legacy
     */
    "max-len": [1, 80, 2],  // The maximum length of a line
  }
}
~~~

デフォルトで有効になっているルールに関しては、適用方法として 2（エラーとして検出）が設定されます。
組み込みで用意されているルールの一覧は、下記のサイトで確認することができます。

- [ESLint - Rules (http://eslint.org/docs/rules/)](http://eslint.org/docs/rules/)

