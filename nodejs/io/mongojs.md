---
title: "mongojs で MongoDB を扱う"
date: "2013-10-25"
---

Node.js で実行する JavaScript から MongoDB にアクセスするには、Node.js Driver をインストールする必要があります。
Node.js Driver の一つに、**mongojs** があります。

- [mongojs (https://github.com/mafintosh/mongojs)](https://github.com/mafintosh/mongojs)

**mongojs** を使うと、`mongo` クライアントと同様のインタフェースで MongoDB にアクセスすることができます。

mongojs のインストール
----

mongojs は下記のように `npm` コマンドで簡単にインストールすることができます。

```
$ npm install mongojs  # インストール
$ npm list             # 確認
```

mongojs の使用例
----

#### main.js

```javascript
var mongojs = require('mongojs');
var db = mongojs.connect('mydb', ['mycollection']);

// すべての Document を削除
db.mycollection.remove();

// Document 追加
db.mycollection.insert({name: 'maku'});
db.mycollection.insert({name: 'moja'});

// Document を検索
db.mycollection.find(function(err, docs) {
    if (err) {
        console.log('Error!');
        return;
    }

    docs.forEach(function(doc) {
        console.log(doc.name);
        console.log(doc);
    });

    db.close();
});
```

#### テスト

```
$ node main.js
maku
{ name: 'maku', _id: 526a57ebf2e32ca423000001 }
moja
{ name: 'moja', _id: 526a57ebf2e32ca423000002 }
```

コラム
----

こんな感じで、DB への接続部分だけ module として分離しておくのもよいですね。

#### db.js（DB 接続部分を分離）

```javascript
var db = 'mydb';
var collections = ['mycollection'];
module.exports = require('mongojs').connect(db, collections);
```

#### main.js

```javascript
var db = require('./db');
db.mycollection.find(...);
```

