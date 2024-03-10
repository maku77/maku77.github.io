---
title: "JSHint で JavaScript コードの静的解析を行う"
url: "p/y7o9g7a/"
date: "2015-04-07"
tags: ["JavaScript", "JSHint"]
aliases: /js/tool/jshint.html
---

JSHint は JavaScript の静的解析ツールのひとつです。
JSLint を元に作成されていますが、より柔軟な設定ができるようになっています。


JSHint のインストール
----

JSHint は [JSHint の Web サイト](https://jshint.com) 上に直接コードを記述して実行することもできますが、通常は Node.js によるコマンドライン版（__`jshint`__ コマンド）を使用します。
Node.js がインストールされている環境であれば、`npm` (Node Package Manager) コマンドを使用して簡単にインストールすることができます。

{{< code lang="console" title="jshint のインストール" >}}
$ npm install -g jshint
{{< /code >}}

Windows 7 の場合、上記のようにインストールしたパッケージは、以下のディレクトリに保存されるようなので、このディレクトリに PATH が通っていない場合はコントロールパネルから PATH に追記しておくようにします。

```
C:\Users\<UserName>\AppData\Roaming\npm\
```

インストールが終わったら、`jshint` コマンドが実行できるようになっているはずです。

{{< code lang="console" title="jshint コマンドの実行" >}}
$ jshint --version
jshint v2.6.3
{{< /code >}}


JSHint の実行方法
----

実際に解析を行う場合は、以下のように `.js` ファイルを指定して実行します。

{{< code lang="console" title="sample.js を jshint で解析" >}}
$ jshint sample.js
sample.js: line 8, col 6, Missing semicolon.

1 error
{{< /code >}}

問題が見つからなかった場合は、何も表示されません。

ファイル名の代わりにディレクトリ名を指定すれば、そのディレクトリ以下のすべての `.js` ファイルに対して実行することもできます。

{{< code lang="console" title="カレントディレクトリ以下の .js ファイルを解析" >}}
$ jshint .
{{< /code >}}


JSHint の設定
----

JSHint はデフォルトの設定では緩いチェックしか行ってくれないので、本格的に使用するには、適切な設定ファイルを用意する必要があります。
JSHint のオプション一覧は下記のサイトで確認することができます。

- [JSHint Options](https://jshint.com/docs/options/)

JavaScript コードのあるディレクトリ内に、JSON 形式の __`.jshintrc`__ ファイルを置いておくと、`jshint` コマンドを実行したときにその設定が使われるようになります。
`.jshintrc` ファイルが見つからない場合は、上位のディレクトリを見つかるまで上りながら探索してくれます。
つまり、プロジェクトの最上位のディレクトリに `.jshintrc` を置いておけば、下位のディレクトリからその設定を利用して `jshint` を実行できるということです。

{{< code lang="js" title=".jshintrc の記述例" >}}
{
  // Enforcing options
  "camelcase" : true,    // 変数名は camelCase で
  "curly" : true,        // if や for の後ろのブロックを表す括弧 {} を強制する
  "forin": true,         // オブジェクトの for-in では hasOwnProperty でフィルタ
  "indent" : 2,          // インデントはスペース 2 文字
  "loopfunc": true,      // ループの中で関数の定義禁止
  "maxdepth": 4,         // 関数内の最大ネスト数
  "strict" : true,       // 'use strict'; の強制
  "trailing": true,      // 行末の無駄なスペース禁止
  "undef": true,         // 未定義のオブジェクトへのアクセスを禁止
  "unused": true,        // 未使用のオブジェクトが見つかったら警告

  // Environments
  "browser" : true,      // ブラウザ用の変数参照を許可 (例: document オブジェクト)
  "devel" : true,        // alert、console へのアクセスは許す
  "jquery" : true,       // jQuery のグローバルは許す
  "prototypejs" : true   // prototypejs のグローバルは許す
}
{{< /code >}}

別の名前の設定ファイルを使いたい場合は、__`--config`__ オプションでファイル名を指定することができます。

```console
$ jshint --config config.json main.js
```


コードの一部分だけ JSHint のチェックを無効にする
----

ファイル内の一部のコードだけ、JSHint によるチェックを無効にするには、以下のようなコメントでコードを囲みます。

```js
/* jshint ignore:start */

  ...ここに記述したコードは JSHint によるチェックの対象外となる...

/* jshint ignore:end */
```

行末コメントにより、一行だけチェックを無効にすることもできます。

```js
ignoreThis(); // jshint ignore:line
```

