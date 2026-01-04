---
title: "Node.jsメモ: Express で静的ファイル（static ファイル）をホスティングする"
url: "p/sururg6/"
date: "2013-10-28"
tags: ["nodejs"]
aliases: /nodejs/express/static-file.html
---

Express で static ファイルを使用するには、標準で組み込まれている **`static`** ミドルウェアを使用します。
例えば、以下のようなディレクトリ構成で、`public` ディレクトリに JavaScript や CSS などの static ファイルを格納するとします。

```
app.js
public/
    +-- images/
    +-- js/
    +-- css/
```

static ミドルウェアの基本的な使い方は、次のように Application オブジェクトの **`use()`** を使ってミドルウェアの設定を行うだけです。
とっても簡単です（Express 4 から、Express 用の各種ミドルウェアが独立したパッケージに分かれましたが、static モジュールだけは Express に含まれています）。

{{< code lang="js" title="public ディレクトリを静的ファイルのルートに設定する" >}}
app.use(express.static(__dirname + '/public'));
{{< /code >}}

例えば、サーバ上の `public` ディレクトリに `images/hoge.jpg `を置いた場合は、`http://localhost:3000/images/hoge.jpg` という URI でアクセスできるようになります。
URL には `public` というパスは含まれないことに注意してください。
URL にプレフィックスを入れたい場合は、下記のようにミドルウェア設定時にプレフィックスを指定します。

```javascript
app.use('/assets', express.static(__dirname + '/public'));
```

このようにすると、サーバ上の `public/images/hoge.jpg` にアクセスする URL は、`http://localhost:3000/assets/images/hoge.jpg` となります。

下記は全体のコードです。

{{< code lang="javascript" title="app.js" >}}
var express = require('express');
var app = express();

app.set('port', process.env.PORT || 3000);
app.use(express.static(__dirname + '/public'));

app.listen(app.get('port'), function() {
    console.log('Listening on port ' + app.get('port'));
});
{{< /code >}}

静的ファイルの含まれているディレクトリへの相対パスは、上記のように **`__dirname`** を使用して作成するようにしてください。
単純に `./public` としてしまうと、node コマンドを実行したときのカレントディレクトリからの相対パスになってしまいます。

