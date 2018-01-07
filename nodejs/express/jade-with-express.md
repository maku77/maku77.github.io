---
title: Express でテンプレートエンジンとして Jade を使用する
date: "2013-10-28"
---

Express では Node.js で使用可能なテンプレートエンジンをすべて利用することができます。
例えば、テンプレートエンジンとして Jade を使用するには、以下のように `Application` オブジェクトの `set` メソッドで指定します。

```javascript
app.set('view engine', 'jade');  // Jade テンプレートエンジンの指定
app.set('views', './views');     // View ファイルを格納するディレクトリ
```

View ファイルを使って HTML を出力するには、`Response` オブジェクトの `render()` メソッドを使用します。

```javascript
app.get('/', function(req, res) {
    res.render('index');    //=> views/index.jade
});
```

Jade で使用する View ファイルは、以下のような感じでインデントをベースにして記述します。

#### views/index.jade

```
html
    head
    title Hello
    body
        h1 Hello!
```

View ファイルを修正した場合は、Web サーバは再起動する必要はありません。Web ブラウザで再読み込みするだけで反映されます。

#### app.js

```javascript
var express = require('express');
var app = express();

app.set('view engine', 'jade');
app.set('views', './views');

app.get('/', function(req, res) {
    res.render('index');
});

app.listen(3000, function() {
    console.log('Listening on port 3000');
});
```

#### 起動方法

```
$ node app.js
Listening on port 3000
```

