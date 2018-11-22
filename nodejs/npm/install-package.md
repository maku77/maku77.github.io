---
title: "npm コマンドで Node.js のパッケージをインストール／アンインストールする (npm install, uninstall)"
date: "2012-11-27"
---

npm による Node パッケージのインストール (npm install)
----

Node.js の環境で追加パッケージをインストールするには、**Node Package Manager**（`npm` コマンド）を使用します。
下記のようなコマンドで、Node パッケージをインストールできます。
公開されている Node パッケージは、[The NPM Registry](https://npmjs.org/) というサイトで検索することができます。

```
$ npm install <package_name>
```

上記のようにインストールしたパッケージは、カレントディレクトリ以下の `node_modules` ディレクトリに格納されます。
例えば、`socket.io` パッケージをインストールした場合は、`node_modeles/socket.io` ディレクトリ内に関連ファイルが格納されます。
また、`socket.io` が依存しているパッケージがある場合は、自動的に `node_modules/socket.io/node_modules` 以下にインストールされます。

`node_modules` ディレクトリ以下にすべてが格納されるので、`npm` コマンドによってそれ以外のディレクトリが汚染されてしまう心配をする必要がありません。
また、別の記事で紹介している `package.json` というファイルに必要なパッケージを記述しておくことにより、1 コマンドで依存パッケージをインストールできるため、Node.js を使用したアプリケーションを配布するときは、`node_modules` ディレクトリ以下の外部パッケージを配布する必要はありません。

#### 例: socket.io パッケージのインストール

```
$ npm install socket.io
...
socket.io@0.9.11 node_modules\socket.io
├── policyfile@0.0.4
├── redis@0.7.3
└── socket.io-client@0.9.11 (xmlhttprequest@1.4.2, uglify-js@1.2.5, active-x-obfuscator@0.0.1, ws@0.4.23)
```

#### 例: restify パッケージのインストール

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

### バージョンを指定してインストールする

パッケージ名の後ろに `@4.16.4` という形でバージョンを指定すると、そのバージョンのパッケージをインストールすることができます。

#### 例: express のバージョン 4.16.4 をインストール

```
$ npm install express@4.16.4
```

また、`@">=4.0.0"` や `@">=4.0.0 <=4.16.4"` という書き方で、インストールするパッケージのバージョンを絞り込むこともできます。
このように、大なり記号 (`>`) や小なり記号 (`<`) を使用してバージョンを絞り込む場合は、シェルがリダイレクト指定と認識してしまわないように、バージョン部分をダブルクォート (`"`) で囲んで指定する必要があることに注意してください。

#### 例: express のバージョン 4.0.0 以上をインストール

```
$ npm install express@">=4.0.0"
```

#### 例: express のバージョン 4.0.0 以上 4.16.4 以下をインストール

```
$ npm install express@">=4.0.0 <=4.16.4"
```


グローバルモードで Node パッケージをインストールする
----

`npm install` コマンドでは、デフォルトではカレントディレクトリの `node_modules` ディレクトリ内にパッケージをインストールします。これは、実行環境の変化をそのアプリケーションに閉じるという意味で合理的な振る舞いといえます。

一方で、すべての Node.js アプリケーションから共通で使用したいパッケージがある場合は、グローバルモードでインストールすることができます。
グローバルモードでパッケージをインストールするには、`npm install` コマンドに `-g` オプションを指定します。

```
$ npm install -g <package_name>
```

グローバルモードでインストールしたパッケージは、デフォルトでは、以下のディレクトリにインストールされるようです。

* Windows: `%APPDATA%npm\node_modules` （`%APPDATA%` はユーザごとに異なる）
* Linux: `/usr/local/lib/node_modules`


npm による Node パッケージのアンインストール (npm uninstall)
----

パッケージのアンインストールは、`npm install` コマンドの代わりに、`npm uninstall` コマンドを使用します。

```
$ npm uninstall <package_name>      # ローカルモードでインストールしたパッケージを削除
# npm uninstall -g <package_name>   # グローバルモードでインストールしたパッケージを削除
```

