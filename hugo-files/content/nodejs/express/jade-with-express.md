---
title: "Node.jsメモ: Express でテンプレートエンジンを使用する (Jade)"
url: "p/gnewjs4/"
date: "2013-10-28"
tags: ["nodejs"]
aliases: /nodejs/express/jade-with-express.html
---

Jade テンプレートエンジンを使用すると、HTML をより簡潔に記述することができます。
Jade フォーマットでの記述はインデントベースで行うため、HTML タグの開始・終了タグを明示的に記述する必要がなくなります。
Express では、Jade をはじめとする様々なテンプレートエンジンを利用することができますが、ここでは Jade を使用する方法について説明します。

Node パッケージのインストール
----

まず、Express と Jade を使用するために、関連パッケージをインストールしておきます。

```console
$ npm install express
$ npm install jade
```

Express から Jade を使用する
----

Express では Node.js で使用可能なテンプレートエンジンをすべて利用することができます。
例えば、テンプレートエンジンとして Jade を使用するには、以下のように **`Application#set('view engine', ...)`** を使って変数設定しておく必要があります。

```javascript
// view engine の指定。Jade テンプレートエンジンを使用する場合は 'jade'
app.set('view engine', 'jade');

// テンプレートファイル (*.jade) を格納するディレクトリの指定
app.set('views', path.join(__dirname, 'views'));
```

View ファイル（テンプレートファイル）を使って HTML を出力するには、**`Response#render()`** メソッドを使用します（拡張子 `.jade` は省略できます）。

```javascript
app.get('/', function(req, res) {
    // Render by using /views/index.jade
    res.render('index');
});
```

Jade で使用する View ファイルは、以下のような感じでインデントで HTML 要素の入れ子構造を表現します。

{{< code title="views/index.jade" >}}
html
    head
    title Hello
    body
        h1 Hello!
{{< /code >}}

View ファイルを修正したときに、Web サーバを再起動する必要はありません。
Web ブラウザ側で再読み込みするだけで反映されます。

{{< code lang="javascript" title="app.js" >}}
var express = require('express');
var app = express();

app.set('view engine', 'jade');
app.set('views', path.join(__dirname, 'views'));

// Define routes.
app.get('/', function(req, res) {
    res.render('index');
});

// Start the server
app.listen(3000, function() {
    console.log('Listening on port 3000');
});
{{< /code >}}

{{< code lang="console" title="起動方法" >}}
$ node app.js
Listening on port 3000
{{< /code >}}

