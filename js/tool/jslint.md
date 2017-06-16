---
title: JSLint で JavaScript コードの静的解析を行う
created: 2015-04-07
---

jslist コマンドのインストール
----

JSLint は [Web サイト上 (http://jslint.com/)](http://jslint.com/) でコードを張り付けて実行することができますが、普段の開発では `jslint` コマンドとして実行できるようにしておいた方が便利です。
多くのスクリプトエンジンによるラッパ実装がありますが、ここでは手軽な Node.js 版の **node-jslist** をインストールしてみます（あらかじめ Node.js をインストールして、`npm` コマンドを使えるようにしておく必要があります）。

~~~
$ npm install -g jslint
~~~

インストールが終わったら、動作確認します。

~~~
$ jslint --version
node-jslint version: 0.9.0-pre006  JSLint edition 2013-08-26
~~~

使い方は簡単で、以下のように実行します。

~~~
$ jslint sample.js
$ jslint **/*.js  # カレントディレクトリ以下の全ての JS ファイルに対して実行
~~~


グローバル変数、関数を参照するときのエラーを抑制する
----

jQuery の `$` や、`console`、`document` などのグローバル変数（別のファイルで定義されている変数）を参照しようとすると、JSLint は未定義エラーと認識してしまいます。
グローバル変数を参照してもエラーにならないようにするには、`global` ディレクティブを使用します。
下記の例では、`$` と `console` を参照できるようにしています。

~~~ js
/*global $: false, jQuery: false, console: false */

$(function () {
  'use strict';
  console.log('hello');
});
~~~

`global` というキーワードの前に、スペースを入れてはいけないことに注意してください。
各変数の後ろの、`false` という値は、そのグローバル変数に対して、このファイルからは代入を行っていないということを示しています。
複数の JavaScript ファイルから、グローバル変数の値を変更することは、メンテナンス性を著しく下げることになるため、通常は上記のように `false` しておくのがよいでしょう。

