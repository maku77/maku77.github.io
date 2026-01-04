---
title: "Node.jsメモ: Express の特徴とインストール"
url: "p/h5yxgh9/"
date: "2013-10-21"
tags: ["nodejs"]
aliases:
  - /nodejs/express/features.html
  - /nodejs/express/install.html
---

Express の特徴
----

Express は Node.js で簡単に Web サーバを構築できるようにするためのモジュールで、下記のような機能を備えています。

* URI ルーティング / RESTful インタフェース
* セッション / クッキー管理
* キャッシュ管理
* ロギング
* MIME タイプ
* テンプレート言語（Pug など）
* 認証

この記事は 2013 年頃に書き始めたものですが、2020 年になっても、Node.js 用の Web サーバーモジュールとして不動の地位を確立しています。
Express の開発経緯は以下の通りです。

* Express は Ruby の Sinatra にインスパイアされて開発されました。
* Node 自体に Web server API はありますが、Low level すぎて使いにくいです。Express モジュールの登場により、簡単に Web server を構築できるようになりました。
* かつて、似たようなプロジェクトとして、Ruby の Rack に影響された Connect という Node module もありましたが、2010 年に Express と Connect の両方のプロジェクトに参加していた T.J. Holowaychuk が統合して Express v1.0.0 になりました。

Express のメインモジュール (`express`) はごく基本的な機能だけを提供しており、拡張モジュールを組み合わせて、機能を追加していく方法を採用しています。


Express をインストールする
----

Express は Node Package Manager (NPM) を使って以下のようにインストールできます。

```console
$ npm install express     # カレントディレクトリにインストール
$ npm install express -g  # グローバルにインストール
```

{{< code lang="js" title="package.json に依存情報が追加される" hl_lines="4" >}}
{
  // ...
  "dependencies": {
    "express": "^1.0.0"
  }
}
{{< /code >}}

アプリケーションの雛形を作成するコマンド `express` を使用できるようにするには、`-g` オプションを付けて、グローバルモジュールとしてインストールしておく必要があります。

{{< code lang="console" title="express コマンドの動作確認" >}}
$ express -V
3.4.2
{{< /code >}}

