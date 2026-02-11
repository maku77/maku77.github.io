---
title: "JSLint で JavaScript コードの静的解析を行う"
url: "p/os3jvi6/"
date: "2015-04-07"
tags: ["JavaScript", "JSLint"]
aliases: /js/tool/jslint.html
---

jslist コマンドのインストール
----

JSLint は [Web サイト上 (https://jslint.com)](https://jslint.com) でコードを張り付けて実行することができますが、普段の開発では __`jslint`__ コマンドとして実行できるようにしておいた方が便利です。
多くのスクリプトエンジンによるラッパ実装がありますが、ここでは手軽な Node.js 版の **`node-jslist`** をインストールしてみます（あらかじめ Node.js をインストールして、`npm` コマンドを使えるようにしておく必要があります）。

{{< code lang="console" title="jslint のインストール" >}}
$ npm install -g jslint
{{< /code >}}

インストールが終わったら、動作確認します。

```console
$ jslint --version
node-jslint version: 0.9.0-pre006  JSLint edition 2013-08-26
```

使い方は簡単で、以下のように解析対象の `.js` ファイルを指定するだけです。

```console
$ jslint sample.js
$ jslint **/*.js  # カレントディレクトリ以下の全ての JS ファイルに対して実行
```


グローバルな変数や関数を参照するときのエラーを抑制する
----

jQuery の `$` や、`console`、`document` などのグローバル変数（別のファイルで定義されている変数）を参照しようとすると、JSLint は未定義エラーと認識してしまいます。
グローバル変数を参照してもエラーにならないようにするには、__`global`__ ディレクティブを使用します。
下記の例では、`$` と `console` を参照できるようにしています。

```js
/*global $: false, jQuery: false, console: false */

$(() => {
  'use strict';
  console.log('hello');
});
```

`global` というキーワードの前に、スペースを入れてはいけないことに注意してください。
各変数の後ろの、`false` という値は、そのグローバル変数に対して、このファイルからは代入を行っていないということを示しています。
複数の JavaScript ファイルからグローバル変数の値を変更することは、メンテナンス性を著しく下げることになるため、通常は上記のように `false` にしておくのがよいでしょう。

