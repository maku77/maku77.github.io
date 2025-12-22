---
title: "Node.jsメモ: package.json による依存パッケージの管理方法まとめ"
url: "p/9m96xqi/"
date: "2014-03-18"
lastmod: "2018-11-27"
tags: ["nodejs"]
aliases:
  - /nodejs/npm/package-dependencies.html
  - /nodejs/npm/npm-init.html
  - /nodejs/npm/npm-help-json.html
---

Node.js アプリケーションが使用する依存パッケージの情報は、**`package.json`** というファイルに記述します。


package.json ファイルのひな型を生成する (npm init)
----

下記のコマンドを実行すると、対話形式で `package.json` ファイルを作成することができます。

```console
$ npm init
```

何も入力せずに Enter を連打していくと、以下のようなファイルが生成されます。

{{< code lang="json" title="package.json" >}}
{
  "name": "sample",
  "version": "0.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "repository": "",
  "author": "",
  "license": "BSD"
}
{{< /code >}}

すでにカレントディレクトリに `package.json` がある場合でも、`npm init` は、単純な上書きはしないで、足りないプロパティだけ補ってくれます。
副次的な作用として、フォーマットの整形にも使えます。


最小限の package.json
----

Node.js アプリケーションのマニフェストファイルである `package.json` には、最低限以下のような情報を記述しておきます。

{{< code lang="json" title="package.json" >}}
{
  "name": "my-app",
  "version": "0.0.1",
  "private": true,
  "dependencies": {
    "express": "3.2.x",
    "jade": "*"
  }
}
{{< /code >}}

アプリの名前 (`name`)、バージョン (`version`)、npm registry に公開しないこと (`"private": true`)、依存するモジュール (`dependencies`) などを記載しています。


依存パッケージは dependencies プロパティで指定する
----

`package.json` ファイルの `dependencies` プロパティには、現在開発中の Node パッケージ（アプリケーション）が依存するパッケージを列挙しておくことができます。
下記の例では、このパッケージ（アプリ）を使用するには、`request` パッケージと `request-promise` パッケージが必要であることを示しています（バージョンの指定方法は後述）。

{{< code lang="js" title="package.json" >}}
{
  "name": "myapp",
  // ...
  "dependencies": {
    "request": "^2.88.0",
    "request-promise": "^4.2.2"
  }
}
{{< /code >}}

カレントディレクトリにこの `package.json` ファイルがある状態で、下記のように実行すると、`dependencies` プロパティに列挙された依存パッケージが `node_modules` ディレクトリに自動的にインストールされます。

```console
$ npm install
```

正確には、`devDependencies` プロパティに列挙された依存パッケージも一緒にインストールされます（詳細は後述）。


バージョンの指定方法
----

バージョンの指定方法にはいろいろありますが、よく使われるのは下記のような指定方法です。

### `1.2.3`

指定したバージョンをインストールします。

### `>=1.2.3 <1.3.0`

指定した範囲のうち最新のものをインストールします。

### `1.2.x`

x の位置以下のレベルのバージョンは問わずにインストールします。

- `1.2.x` → `>=1.2.0 < 1.3.0`
- `1.x` → `>=1.0.0 <2.0.0`

### `^1.2.3`

後方互換性のある（だろうと思われる）バージョンのうち、最新のバージョンをインストールします。
**オススメの指定方法**です。
`npm install --save <pkg>` でパッケージをインストールした場合、このバージョン指定方法で `package.json` の `dependencies` プロパティの値が更新されます。

major、minor、patch レベルのバージョンを左から見て、最初に 0 じゃない数値がみつかったらそこのバージョンを固定し、それより下のレベルのバージョンが更新されます。

- `^1.2.3` → `>=1.2.3 <2.0.0`
- `^0.2.3` → `>=0.2.3 <0.3.0`
- `^0.0.3` → `>=0.0.3 <0.0.4`
- `^1.2.3-beta.2` → `>=1.2.3-beta.2 <2.0.0` （`1.2.3` も含む）
- `^0.0.3-beta` → `>=0.0.3-beta <0.0.4` （`0.0.3` も含む）
- `^1.2.x` → `>=1.2.0 <2.0.0`
- `^0.0.x` → `>=0.0.0 <0.1.0`
- `^0.0` → `>=0.0.0 <0.1.0`
- `^1.x` → `>=1.0.0 <2.0.0`
- `^0.x` → `>=0.0.0 <1.0.0`

ちなみに大文字の `X` でも OK。

### `~1.2.3`

`1.2.3` とバージョン固定で指定する方法に似ていますが、下位レベルの更新だけを受け入れます。
つまり、軽微な変更だけを許可する指定方法です。

minor レベルのバージョンが指定されている場合は、patch レベルのバージョン更新のみを許可します。

- `~1.2.3` → `>=1.2.3 <1.3.0`
- `~0.2.3` → `>=0.2.3 <0.3.0`
- `~1.2` → `>=1.2.0 <1.3.0` （`1.2.x` と同じ）
- `~0.2` → `>=0.2.0 <0.3.0` （`0.2.x` と同じ）
- `~1.2.3-beta.2` → `>=1.2.3-beta.2 <1.3.0` （`1.2.3` も含む）

major レベルのバージョンしか指定されていない場合は、minor レベル以下のバージョン更新を許可します。

- `~1` → `>=1.0.0 <2.0.0` （`1.x` と同じ）
- `~0` → `>=0.0.0 <1.0.0` （`0.x` と同じ）

### `*` or `""`

どのバージョンでも OK。
ようするに必ず最新バージョンのパッケージをインストールするという指定です。


dependencies プロパティと devDependencies プロパティ
----

**開発時（テスト時）のみ使用するパッケージの依存関係は、`dependencies` プロパティではなく、`devDependencies` プロパティに記述**します。
`dependencies` プロパティの方には、そのパッケージ（やアプリ）を使用するユーザ環境で必要なパッケージのみを列挙してください。

パッケージそのものの開発者は、ローカルディレクトリに `package.json` ファイルがある状態で、次のようなコマンドを実行して依存パッケージを一括インストールするはずです。
この場合は、`dependencies` プロパティに書かれた依存モジュールに加え、**`devDependencies` プロパティに書かれた依存モジュールも一緒にインストールされます**。

{{< code lang="console" title="dependencies と devDependencies に書かれた依存パッケージがインストールされる" >}}
$ npm install
{{< /code >}}

`dependencies` プロパティに列挙された依存モジュールのみをインストールしたい場合は、下記のように **`--production` オプション**を使用します。

{{< code lang="console" title="dependencies に書かれた依存パッケージのみがインストールされる" >}}
$ npm install --production
{{< /code >}}


パッケージのインストール時に package.json を更新する
----

`npm install` コマンドを使ってパッケージをインストールするときに、**`--save` オプション**を付加すると、`package.json` の `dependencies` プロパティの記述も更新することができます。

{{< code lang="console" title="package.json の dependencies プロパティに追加" >}}
$ npm install --save <pkg>
{{< /code >}}

開発のみに使用するパッケージの依存情報 (`devDependencies`) を追加するときは、`--save` オプションの代わりに **`--save-dev` オプション** を使用します。

{{< code lang="console" title="package.json の devDependencies プロパティに追加" >}}
$ npm install --save-dev <pkg>
{{< /code >}}

{{% note title="--save は省略可能" %}}
npm 5.0.0 以降では `--save` オプションがデフォルトで有効になっているため、省略しても同じ効果があります。
`--save-dev` オプションは引き続き明示的な指定が必要です。
{{% /note %}}


package.json の書式、説明を確認する (npm help json)
----

Node パッケージに関する情報（モジュールの名前や説明、依存パッケージなどの情報）は、`package.json` というファイルに記述します。
以下のコマンドで、`package.json` の書き方のヘルプを見ることができます（Web ブラウザ上で表示されます）。

```console
$ npm help json
```

表示される内容は、下記のサイトのものと同様です。

- [https://docs.npmjs.com/files/package.json](https://docs.npmjs.com/files/package.json)


参考
----

- [package.json - dependencies](https://docs.npmjs.com/files/package.json#dependencies)
    - package.json ファイルの dependencies プロパティに関する説明。
- [npm-semver - The semantic versioner for npm](https://docs.npmjs.com/misc/semver)
    - npm コマンドは、この semver というモジュールを使って、バージョン情報をパースしています。

