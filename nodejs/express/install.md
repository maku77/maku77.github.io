---
title: "Express をインストールする"
date: "2013-10-22"
---

Express は Node Package Manager (NPM) を使って以下のようにインストールできます。

```
$ sudo npm install express     # カレントディレクトリにインストール
$ sudo npm install express -g  # グローバルにインストール
```

下記のように、`--save` オプションを指定しておくと、`package.json` の `dependencies` セクションに、`express` の項目を自動的に追加してくれます（これにより、このアプリケーションの配布先で `npm install` と実行するだけで express を自動インストールしてくれるようになります）。

```
$ sudo npm install --save express  # package.json も更新する場合
```

#### package.json

```json
{
  ...
  "dependencies": {
    "express": "^1.0.0"  /* 自動的に追加される */
  }
}
```

アプリケーションの雛形を作成するコマンド `express` を使用できるようにするには、`-g` オプションを付けて、グローバルモジュールとしてインストールしておく必要があります。

#### 動作確認

```
$ express -V
3.4.2
```

