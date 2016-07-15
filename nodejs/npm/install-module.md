---
title: npm コマンドで Node.js のモジュールをインストールする
created: 2012-11-27
---

npm による Node モジュールのインストール
----

Node.js の環境で追加モジュールをインストールするには、**Node Package Manager**（`npm` コマンド）を使用します。
下記のようなコマンドで、Node モジュールをインストールできます。
公開されている Node モジュールは、[The NPM Registry](https://npmjs.org/) というサイトで検索することができます。

```
$ npm install <package_name>
```

上記のようにインストールしたモジュールは、カレントディレクトリ以下の `node_modules` ディレクトリに格納されます。
例えば、`socket.io` モジュールをインストールした場合は、`node_modeles/socket.io` ディレクトリ内に関連ファイルが格納されます。
また、`socket.io` が依存しているモジュールがある場合は、自動的に `node_modules/socket.io/node_modules` 以下にインストールされます。

`node_modules` ディレクトリ以下にすべてが格納されるので、`npm` コマンドによってそれ以外のディレクトリが汚染されてしまう心配をする必要がありません。
また、別の記事で紹介している `package.json` というファイルに必要なモジュールを記述しておくことにより、1 コマンドで依存モジュールをインストールできるため、Node.js を使用したアプリケーションを配布するときは、`node_modules` ディレクトリ以下の外部モジュールを配布する必要はありません。

#### 例: socket.io モジュールのインストール

```
$ npm install socket.io
...
socket.io@0.9.11 node_modules\socket.io
├── policyfile@0.0.4
├── redis@0.7.3
└── socket.io-client@0.9.11 (xmlhttprequest@1.4.2, uglify-js@1.2.5, active-x-obfuscator@0.0.1, ws@0.4.23)
```

#### 例: restify モジュールのインストール

```
$ npm install restify
restify@1.4.4 node_modules\restify
├── byline@2.0.2
├── lru-cache@1.1.0
├── semver@1.0.14
├── mime@1.2.5
├── retry@0.6.0
├── async@0.1.22
├── node-uuid@1.3.3
├── qs@0.5.0
├── formidable@1.0.11
├── bunyan@0.10.0
└── http-signature@0.9.9 (asn1@0.1.11, ctype@0.5.0)
```


グローバルモードで Node モジュールをインストールする
----

`npm install` コマンドでは、デフォルトではカレントディレクトリの `node_modules` ディレクトリ内にモジュールをインストールします。これは、実行環境の変化をそのアプリケーションに閉じるという意味で合理的な振る舞いといえます。

一方で、すべての Node.js アプリケーションから共通で使用したいモジュールがある場合は、グローバルモードでインストールすることができます。
グローバルモードでモジュールをインストールするには、`npm install` コマンドに `-g` オプションを指定します。

```
$ npm install -g <package_name>
```

グローバルモードでインストールしたモジュールは、デフォルトでは、以下のディレクトリにインストールされるようです。

* Windows: `%APPDATA%npm\node_modules` （`%APPDATA%` はユーザごとに異なる）
* Linux: `/usr/local/lib/node_modules`


Node.js 用モジュールのアンインストール
----

モジュールのアンインストールは、`npm install` コマンドの代わりに、`npm uninstall` コマンドを使用します。

```
$ npm uninstall <package_name>      # ローカルモードでインストールしたモジュールを削除
# npm uninstall -g <package_name>   # グローバルモードでインストールしたモジュールを削除
```


インストールされている Node.js 用モジュールの一覧を表示する
----

```
$ npm list      # ローカルモードでインストールしたモジュールを一覧表示
# npm list -g   # グローバルモードでインストールしたモジュールを一覧表示
```

各モジュールが依存しているモジュールに関しても、階層構造ですべて表示してくれます。

