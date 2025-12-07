---
title: "RequireJS の使い方メモ"
url: "p/nouqw33/"
date: "2013-07-18"
tags: ["RequireJS"]
---

RequireJS を使うと、JavaScript のコードで、C/C++ の include のようなことができるようになります。
しかも、非同期にロードされるので、パフォーマンス上の利点もあります。


RequireJS で Hello World
----

RequireJS 本体 (`require.js`) は下記サイトからダウンロードできます。

- https://requirejs.org/docs/download.html

ここでは、以下のようなディレクトリ構成でファイルを配置することにします。

- index.html
- js/main.js
- js/vendor/require.js

`require.js` を `script` 要素でロードするときに、**`data-main`** 属性の値として、任意の JavaScript ファイルを指定しておくと、`require.js` がロードされた後にその JavaScript ファイルを自動的に実行してくれます。
つまり、`data-main` で指定した JavaScript ファイルが、アプリケーションのエントリポイントとなります。

{{< code lang="html" title="index.html" >}}
<!DOCTYPE html>
<html>
<head>
  <title>RequireJS Test</title>
</head>
<body>
  <h1>Hello RequireJS</h1>
  <script data-main="js/main.js" src="js/vendor/require.js"></script>
</body>
</html>
{{< /code >}}

{{< code lang="js" title="js/main.js" >}}
require([], function() {
    alert('Hello World');
});
{{< /code >}}

上記の `index.html` をブラウザで開くと **`js/main.js`** が自動的にロードされ、Hello World と表示されます。
エントリポイントとなる JavaScript ファイルには、**`require()`** を使って main 関数となる function を定義しておきます。
`require()` の第 1 引数には依存するモジュールを配列で指定できますが、今回は依存するモジュールはないので空にしてあります。


RequireJS モジュールを定義する
----

### 簡単なユーティリティ・モジュールの作成

エントリポイントとなる JavaScript ファイルからロードする RequireJS モジュールを定義するには、**`define()`** 関数を使用します。
ここでは、足し算 (add)、引き算 (subtract) の関数を提供する `util/MathUtil` モジュールを作成してみます。

{{< code lang="javascript" title="js/util/MathUtil.js" >}}
define({
    add: function(a, b) {
        return a + b;
    },
    subtract: function(a, b) {
        return a - b;
    }
});
{{< /code >}}

上記の `util/MathUtil` モジュールを使うには、エントリポイントとなる関数を設定する `require()` 関数の第 1 引数でそのモジュール名を指定します。
すると、`util/MathUtil.js` 内で `define()` 関数に渡したオブジェクトを、main 関数の引数として参照することができるようになります。

{{< code lang="html" title="index.html" >}}
<script data-main="js/main.js" src="js/vendor/require.js"></script>
{{< /code >}}

{{< code lang="js" title="js/main.js" >}}
require(['util/MathUtil'], function(MathUtil) {
    console.log('add: ' + MathUtil.add(100, 200));
    console.log('sub: ' + MathUtil.subtract(100, 200));
});
{{< /code >}}

上記のモジュール名の指定方法 (`'util/MathUtil'`) からも分かるように、ロードするモジュール名は、script タグの **`data-main`** 属性で渡したメイン JavaScript ファイルのディレクトリからの相対パスで指定します。
拡張子は必要ありません。

### RequireJS モジュールから他のモジュールをロードする

もちろん、RequireJS モジュールの中からも別の RequireJS モジュールをロードすることができます。
`require()` 関数でモジュールをロードするときと同様に、`define()` 関数の第 1 引数でロードしたいモジュールを指定します。

下記の例では、`util/Util1` モジュールから、`util/Util2` モジュールと `util/Util3` モジュールを参照しています。

{{< code lang="js" title="js/main.js" >}}
// Entry point.
require(['util/Util1'], function(Util1) {
    console.log('Util1 says: ' + Util1.message);
});
{{< /code >}}

{{< code lang="js" title="js/util/Util1.js" >}}
define(['util/Util2', 'util/Util3'], function(Util2, Util3) {
    console.log('Util2 says: ' + Util2.message);
    console.log('Util3 says: ' + Util3.message);

    return {
        message: 'I am Util1'
    };
});
{{< /code >}}

{{< code lang="js" title="js/util/Util2.js" >}}
define({message: 'I am Util2'});
{{< /code >}}

{{< code lang="javascript" title="js/util/Util3.js" >}}
define({message: 'I am Util3'});
{{< /code >}}

実行すると、コンソールに以下のように表示されます。

```
Util2 says: I am Util2
Util3 says: I am Util3
Util1 says: I am Util1
```

### クラスモジュールを作成する

`define()` を使ってモジュールを定義するときに、コンストラクタとなる function オブジェクトを返すようにしておけば、クラスモジュールとして使えるようになります。

下記の例では、クラスモジュールとして `Counter` モジュールを定義しています。

{{< code lang="js" title="js/main.js" >}}
require(['Counter'], function(Counter) {
    var counter = new Counter(100);
    for (var i = 0; i < 3; ++i) {
        console.log(counter.get());
    }
});
{{< /code >}}

{{< code lang="js" title="js/Counter.js" >}}
define(function() {
    // Constructor
    var Counter = function(start) {
        this.count = start;
    };

    // Methods
    Counter.prototype.get = function() {
        return this.count++;
    }

    return Counter;
});
{{< /code >}}

これを実行すると、以下のように表示されます。

```
100
101
102
```


RequireJS の r.js コマンドで JavaScript ファイルを圧縮する
----

**`r.js`** コマンドを使用すると、単一、あるいは複数の JavaScript ファイルを圧縮してひとつのファイルにまとめることができます。
圧縮する各ファイルは、**RequireJS (AMD) モジュール** の形式でコーディングされている必要があります。
つまり、ファイル内のコードが以下のようになっている必要があります。

```
define(..., function(...) {...});
あるいは、
require(..., function(...) {...});
```

### r.js のインストール

`r.js` の実行環境としては Node.js を使うのが簡単です。
以下のように `npm` コマンドで `requirejs` パッケージをインストールすると、`r.js` コマンドも使用できるようになります。

```console
$ npm install -g requirejs
```

インストールが終わったら、以下のように実行できます。

```console
$ r.js ほげほげ
```

Windows 環境で `.js` ファイルがテキストエディタなどに関連付けられている場合は、以下のように `.cmd` 拡張子まで付けて実行します（`r.js` の実体は `r.js.cmd` です）。
これは、`r.js` というファイルをテキストエディタで開こうとしてしまうのを防ぐためです。

```
D:\> r.js.cmd ほげほげ
```

### r.js で 1 つの JavaScript ファイルを圧縮する

カレントディレクトリにある `main.js` を圧縮して `main.min.js` として出力するには以下のようにします。

```console
$ r.js -o name=main out=main.min.js baseUrl=.
```

複数の JavaScript ファイルを 1 つずつ圧縮することもできます。
以下のようにすると、`js` ディレクトリ内のファイルが 1 つずつ圧縮され、圧縮後のファイルが `js-min` ディレクトリに出力されます。

```console
$ r.js -o baseUrl=./js dir=./js-min
```

### r.js で複数の JavaScript ファイルを圧縮して 1 ファイルにまとめる

```
js/
  +-- main.js     # Entry point starting with 'require'
  +-- Module1.js  # Used from 'main.js'
  +-- Module2.js  # Used from 'Module1.js'
```

上記のような 3 つの JavaScript ファイルを圧縮して 1 ファイルにする例です。
JavaScript の内容は以下のようになっているとします。

{{< code lang="js" title="js/main.js" >}}
require(['Module1'], function(Module1) {
    console.log('I am main');
});
{{< /code >}}

{{< code lang="js" title="js/Module1.js" >}}
define(['Module2'], function(Module2) {
    console.log('I am Module1');
});
{{< /code >}}

{{< code lang="js" title="js/Module2.js" >}}
define(['Module2'], function(Module2) {
    console.log('I am Module1');
});
{{< /code >}}

次のように実行すると、全ての JavaScript ファイルが圧縮されて 1 ファイルにまとめられた `main.min.js` が出力されます。

```console
$ r.js -o baseUrl=js name=main out=main.min.js
```

できあがった `main.min.js` の内容は以下のようになっています。

{{< code lang="js" title="main.min.js" >}}
define("Module2",[],function(){console.log("I am Module2")}),define("Module1",["Module2"],function(e){console.log("I am Module1")}),require(["Module1"],function(e){console.log("I am main")}),define("main",function(){});
{{< /code >}}

コマンドラインで指定したオプションは、別ファイルに記述しておくこともできます。
以下の例では、`build.js` に設定を記述しています。

{{< code lang="js" title="build.js（r.js 用設定ファイル）" >}}
({
    baseUrl: "js",
    name: "main",
    out: "main.min.js"
})
{{< /code >}}

```console
$ r.js -o build.js
```

あとは、できあがった `main.min.js` を `script` タグで読み込むようにすれば OK です。

{{< code lang="html" title="index.html" >}}
<script data-main="main.min" src="js/vendor/require.js"></script>
{{< /code >}}

### 3rd party ライブラリのライセンスコメントを残して圧縮する

`r.js` による圧縮をかけると、デフォルトでは JavaScript コード内のコメントは全て削除されます。
ただ、オープンソースコードを使用する場合はライセンスのコメントが消えてしまっては困るので、これを防ぐためのコメント記述方法が用意されています。

```js
/*!
 * ...
 */
```

あるいは、

```js
/**
 * @license
 * ...
 */
```

という記述方法です。
どちらかの形でコメントを記述しておくと、`r.js` はそのコメントを残すようになります。
ただし、この方法で残されたコメントは全て **出力ファイルの先頭にまとまった形で出力されます**。
どのモジュールのライセンスなのかが分かるように、コメントの先頭にモジュール名を記載しておくのがよいかもしれません。

下記の例では、圧縮する 3 ファイル中、2 つのファイルがライセンスコメントを含んでいます。

{{< code lang="js" title="js/main.js" >}}
/**
 * Normal comment.
 * This should be removed after minifying.
 */
require(['Module1'], function(Module1) {
    console.log('I am main');
});
{{< /code >}}

{{< code lang="js" title="js/Module1.js" >}}
/*!=== Module 1 ===
 * License description for Module1.
 */
define(['Module2'], function(Module2) {
    console.log('I am Module1');
});
{{< /code >}}

{{< code lang="js" title="js/Module2.js" >}}
/*!=== Module2 ===
 * License description for Module2.
 */
define(function() {
    console.log('I am Module2');
});
{{< /code >}}

圧縮後のコードは以下のようになります。

{{< code lang="js" title="main.min.js" >}}
/*!=== Module2 ===
 * License description for Module2.
 */

/*!=== Module 1 ===
 * License description for Module1.
 */

define("Module2",[],function(){console.log("I am Module2")}),define("Module1",["Module2"],function(e){console.log("I am Module1")}),require(["Module1"],function(e){console.log("I am main")}),define("main",function(){});
{{< /code >}}

### require.js も含めて 1 ファイルにまとめる

ディレクトリ構成が次のようになっているとします。

- js/main.js
- js/Module1.js
- js/Module2.js
- js/vendor/require.js

`r.js` コマンドを実行するときに、次のようにインクルード指定することで、最終的に出力されるファイルに `require.js` のコードまで含めてしまうことができます。

```console
$ r.js -o baseUrl=js paths.requireLib=vendor/require name=main include=requireLib out=main.min.js
```

`main.min.js` に `require.js` が含まれていれば、HTML ファイルからのロードは以下のようにシンプルになります。

```html
<script src="main.min.js"></script>
```


RequireJS の r.js でプロジェクト全体を圧縮する
----

`r.js` コマンドは、単純に JavaScript ファイルを圧縮する機能の他に、CSS ファイルの連結や、プロジェクト全体のリリースファイルをまとめてディレクトリに出力する機能も持っています。
例えば、Web アプリの構成が以下のようになっているとします。

- sample/
  - webapp/  ← HTML/CSS/JavaScript が含まれている
  - build.js

プロジェクト単位で圧縮＆リリースファイル生成を行うには、`r.js` の設定 (`build.js`) で、**`appDir`（入力ディレクトリ）** と **`dir`（出力ディレクトリ）** を指定します。

{{< code lang="js" title="build.js" >}}
({
  // Input directory
  appDir: './webapp',

  // Output directory
  dir: './release',

  // Use shim settings in the require.config()
  mainConfigFile: './webapp/js/main.js',

  // Application's entry point
  name: 'main',

  // Remove files from the output directory that were combined
  removeCombined: true,
})
{{< /code >}}

あとは、`build.js` ファイルがあるディレクトリで `r.js` コマンドを実行すれば、リリース用のファイル郡が含まれた `release` ディレクトリができあがります。

```console
$ r.js -o build.js
```

出力される `release` ディレクトリには、必要な HTML/CSS ファイルがすべてコピーされ、JavaScript としては `require.js` と、圧縮された `main.js` ファイルの 2 ファイルだけが格納されます。


RequireJS の r.js の設定メモ (build.js)
----

### build.js ファイルの各設定の説明

- https://github.com/requirejs/r.js/blob/master/build/example.build.js


### 各設定値がどこからの相対パスになるか？

- appDir, mainConfigFile
  - カレントディレクトリからの相対パス
- baseUri
  - appDir がある場合はそこからの相対パス
  - 何も指定がない場合はカレントディレクトリからの相対パス
- paths, packages
  - baseUri からの相対パス
- name, include, exclude, excludeShallow, deps
  - ファイルパスではなく、モジュール ID で指定

### JavaScript を 1 ファイルに圧縮した場合にどのファイルのコードが結合されるか

JavaScript ファイルを、**`name`** に指定したモジュールに結合する場合、基本的には `define()` や `require()` で定義されているモジュールはすべて含まれます。
逆に、RequireJS (AMD) 形式のモジュールじゃないライブラリ（例えば Backbone.js 1.0.0 や Underscore 1.5.1）はデフォルトでは圧縮後に結合されません。
これらの外部ライブラリも含めてしまいたい場合は、以下のような方法があります。

* **`mainConfigFile`** で設定する JavaScript ファイルの `require.config()` の **`shim`** プロパティでモジュール名を指定する方法（これが推奨されている）
* `build.js` 内の **`shim`** プロパティでモジュール名を指定する方法（結局 `main.js` にも `shim` の設定しているなら、そちらを参照するように `mainConfigFile` を使ったほうがよい）
* `include`/`exclude` オプションで明示的にモジュール名を指定する方法

例えば、`build.js` で以下のように `mainConfigFile` を指定し、

{{< code lang="js" title="build.js" >}}
({
  appDir: './webapp',
  dir: './release',  // output dir
  mainConfigFile: './webapp/js/main.js',  // including require.config()
  name: 'main',  // app entry point
})
{{< /code >}}

指定した `main.js` が以下のような `shim` 設定を含んでいる場合、

{{< code lang="javascript" title="webapp/js/main.js" >}}
require.config({
  baseUrl: 'js',
  paths: {
    backbone: 'vendor/backbone-1.0.0.min',
    jquery: 'vendor/jquery-2.0.3.min',
    underscore : 'vendor/underscore-1.5.1.min'
  },
  shim: {
    backbone: {
      deps: ['jquery', 'underscore'],
      exports: 'Backbone'
    },
    underscore: {
      exports: '_'
    }
  }
});
require(..., function() {...main code...});
{{< /code >}}

最終的に結合された `main.js` に、Backbone.js や Underscore.js のコードも含まれるようになります。

`mainConfigFile` で参照している JavaScript ファイル内の **`paths`** 設定で、CDN から jQuery などのライブラリをロードするようにしているときは注意点が必要です。
この場合は、それらのファイルは結合せずに都度インターネット上から取得するようにしておきたいので、以下のようにして結合対象から外しておく必要があります。

{{< code lang="javascript" title="build.js" >}}
({
  ...
  paths: {
    jquery: "empty:"
  }
})
{{< /code >}}

- 参考: [empty: paths for network/CDN resources](https://requirejs.org/docs/optimization.html#empty)


RequireJS モジュールとして jQuery、Backbone.js、Underscore.js を使う
----

以下のようなディレクトリ構成のときに、RequireJS 経由で Backbone.js を使用する例です。
Backbone.js は jQuery や Underscore.js に依存しているので、それらを使用する設定も必要です。

```
- index.html
- js/
  - main.js
  - vendor/
    - backbone-min.js
    - jquery-2.0.3.min.js
    - require.js
    - underscore-min.js
```

{{< code lang="html" title="index.html" >}}
<!DOCTYPE html>
<html>
<head>
  <title>r.js test</title>
</head>
<body>
  <h1>index.html</h1>
  <script data-main="js/main.js" src="js/vendor/require.js"></script>
</body>
</html>
{{< /code >}}

AMD モジュールのフォーマットに対応していない Backbone.js、Underscore.js を RequireJS で扱うには、以下のように **`require.config()`** の **`shim`** 設定をしておく必要があります（jQuery は AMD に既に対応しています）。

{{< code lang="js" title="js/main.js" >}}
require.config({
  baseUrl: 'js',
  paths: {
    backbone: 'vendor/backbone-1.0.0.min',
    jquery: 'vendor/jquery-2.0.3.min',
    underscore : 'vendor/underscore-1.5.1.min'
  },
  shim: {
    backbone: {
      deps: ['jquery', 'underscore'],
      exports: 'Backbone'
    },
    underscore: {
      exports: '_'
    }
  }
});

require(['jquery', 'MyModel'], function($, MyModel) {
  $(function() {
    console.log('DOM is ready!');
    var model = new MyModel();
    alert(model.get('message'));
  });
});
{{< /code >}}

{{< code lang="js" title="js/MyModel.js" >}}
define(['backbone'], function(Backbone) {
  var MyModel = Backbone.Model.extend({
    defaults: { message: 'Hello, I am MyModel' }
  });
  return MyModel;
});
{{< /code >}}

