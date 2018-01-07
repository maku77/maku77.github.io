---
title: package.json の雛形を生成する
date: "2014-03-18"
---

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

