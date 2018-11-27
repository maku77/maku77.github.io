---
title: "package.json ファイルを作成する (npm init)"
date: "2014-03-18"
---

package.json ファイルのひな型を生成する
----

下記のコマンドを実行すると、対話形式で `package.json` ファイルを作成することができます。

```
$ npm init
```

何も入力せずに Enter を連打していくと、以下のようなファイルが生成されます。

#### package.json

```json
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
```

すでにカレントディレクトリに `package.json` がある場合でも、`npm init` は、単純な上書きはしないで、足りないプロパティだけ補ってくれます。
副次的な作用として、フォーマットの整形にも使えます。


最小限の package.json
----

Node.js アプリケーションのマニフェストファイルである `package.json` には、最低限以下のような情報を記述しておきます。

#### package.json

```json
{
  "name": "my-app",
  "version": "0.0.1",
  "private": true,
  "dependencies": {
    "express": "3.2.x",
    "jade": "*"
  }
}
```

アプリの名前 (`name`)、バージョン (`version`)、npm registry に公開しないこと (`"private": true`)、依存するモジュール (`dependencies`) などを記載しています。
上記のようなファイルを作成し、同じディレクトリ内で下記のように実行すれば、`dependencies` プロパティ（と `devDependencies` プロパティ）に記載された依存モジュールが自動的にインストールされます。

```
$ npm install
```

