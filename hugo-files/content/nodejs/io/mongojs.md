---
title: "Node.jsメモ: mongojs で MongoDB を扱う"
url: "p/exs9wor/"
date: "2013-10-25"
tags: ["nodejs"]
aliases: /nodejs/io/mongojs.html
---

Node.js で実行する JavaScript から MongoDB にアクセスするには、Node.js Driver をインストールする必要があります。
Node.js Driver の一つに、[`mongojs`](https://github.com/mongo-js/mongojs) があります。
**mongojs** を使うと、`mongo` クライアントと同様のインタフェースで MongoDB にアクセスすることができます。

mongojs のインストール
----

mongojs は下記のように `npm` コマンドで簡単にインストールすることができます。

```console
$ npm install mongojs  # インストール
$ npm list             # 確認
```


mongojs の使用例
----

{{< code lang="js" title="main.js" >}}
import mongojs from 'mongojs';

const db = mongojs.connect('mydb', ['mycollection']);

// すべての Document を削除
db.mycollection.remove();

// Document 追加
db.mycollection.insert({name: 'maku'});
db.mycollection.insert({name: 'moja'});

// Document を検索
db.mycollection.find((err, docs) => {
  if (err) {
    console.log('Error!');
    return;
  }

  docs.forEach((doc) => {
    console.log(doc.name);
    console.log(doc);
  });

  db.close();
});
{{< /code >}}

{{< code lang="console" title="テスト" >}}
$ node main.js
maku
{ name: 'maku', _id: 526a57ebf2e32ca423000001 }
moja
{ name: 'moja', _id: 526a57ebf2e32ca423000002 }
{{< /code >}}

コラム
----

こんな感じで、DB への接続部分だけ module として分離しておくのもよいですね。

{{< code lang="js" title="db.js（DB 接続部分を分離）" >}}
import mongojs from 'mongojs';

const db = 'mydb';
const collections = ['mycollection'];
export default mongojs.connect(db, collections);
{{< /code >}}

{{< code lang="js" title="main.js" >}}
import db from './db.js';

db.mycollection.find(/* ... */);
{{< /code >}}

