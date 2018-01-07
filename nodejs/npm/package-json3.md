---
title: 最小限の package.json を作成する
date: "2014-09-21"
---

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

アプリの名前、バージョン、npm registry に公開しないこと、依存するモジュールなどを記載しています。
上記のようなファイルを作成し、同じディレクトリ内で下記のように実行すれば、必要なモジュールが自動的にインストールされます。

```
$ npm install
```

