---
title: "Node.jsメモ: mongoose で MongoDB (1) mongoose をインストールする"
url: "p/wfrqz8r/"
date: "2014-02-10"
tags: ["nodejs"]
aliases:
  - /nodejs/io/mongoose1.html
  - /nodejs/io/mongoose2.html
  - /nodejs/io/mongoose3.html
  - /nodejs/io/mongoose4-1.html
  - /nodejs/io/mongoose4-2.html
  - /nodejs/io/mongoose4-3.html
  - /nodejs/io/mongoose4-4.html
---

mongoose のインストール
----

**`mongoose`** は Node.js で MongoDB を使用するためのモジュールです。
`npm` コマンドを使用して簡単にインストールすることができます。

```console
$ npm install mongoose
```


mongoose で MongoDB データベースへ接続する
----

下記は、`mongoose` を使用して MongoDB のデータベースに接続する例です。

{{< code lang="js" title="sample.js" >}}
import mongoose from 'mongoose';

// 接続後のコールバック
mongoose.connection.once('open', (e) => {
  console.log('connected');
});

// エラー発生時のコールバック
mongoose.connection.on('error', (err) => {
  console.log(`ERROR: ${err.message}`);
});

mongoose.connect('mongodb://localhost/testdb');
{{< /code >}}

`mongoose.connect()` により MongoDB へ接続し、指定したデータベースにアクセスできるようになります。
データベースへ接続するときの接続パスは、以下のように指定します。

```
mongodb://user:pass@host:port/dbname
```

上記のサンプルコードでは、接続時とエラー発生時のイベントハンドラを設定しています。
`mongod` などが起動していない場合などはエラーが発生し、以下のように表示されます。

```console
$ node sample.js
ERROR: failed to connect to [localhost:27017]
```


mongoose のモデルクラス
----

### モデルクラスを作成する

`mongoose` で MongoDB のドキュメントを扱うには、先にモデルクラスとして各フィールドの型などを定義しておく必要があります。
以下のサンプルでは、`Memo` という名前のモデルクラスを定義しています。

```js
const MemoSchema = new mongoose.Schema({
  title: String,
  body: String
});

const Memo = mongoose.model('Memo', MemoSchema);
```

これで、`title` と `body` というフィールドを持ったドキュメントを扱う、`Memo` モデルクラスが作成されました。
上記では、`mongoose.model()` の戻り値として `Memo` モデルクラスのインスタンスを受け取っていますが、以下のようにして、定義とインスタンスの取得を分けて行うこともできます。

```js
mongoose.model('Memo', MemoSchema);  // モデルクラスの定義
const Memo = mongoose.model('Memo');   // モデルクラスの取得
```

mongoose では、`Memo` という名前のモデルクラスを定義することで、自動的に `memos` という複数形の名前のコレクションを扱うようになります。
コレクション名を明示的に指定したい場合は、Schema の作成時に以下のように指定します。

```js
const MemoSchema = new mongoose.Schema({
  title: String,
  body: String
}, {
  collection: 'memo_collection'  // コレクション名
});

const Memo = mongoose.model('Memo', MemoSchema);
```

### スキーマの型指定方法いろいろ

モデルクラス用のスキーマを定義するときは、下記のように各カラムの型を柔軟に定義することができます。

```js
const MySchema = new Schema({
  name: { type: String, required: true },      // 必須フィールド
  age: { type: Number, min: 0, default: 0 },   // 最小値、デフォルト値
  created: { type: Date, default: Date.now },  // 日時のデフォルト値
  updated: { type: Date, default: Date.now },
  hidden: Boolean,  // Boolean 値
});
```


mongoose で MongoDB にドキュメントを追加する
----

DB のコレクションにドキュメントを追加するには、`Model` インスタンスのメソッド `save()`、あるいはクラスメソッドの `create()` を使用します。

- [`Model#save()`](https://mongoosejs.com/docs/api/model.html#Model.prototype.save%28%29) ... そのモデルを DB に保存
- [`Model.create()`](https://mongoosejs.com/docs/api/model.html#Model.create%28%29) ... 指定したモデルを DB に保存

新しいモデルオブジェクトを生成し、DB のコレクション (`memos`) に追加するには以下のようにインスタンスメソッドの `save()` を使用します（ここでは、既に `Memo` という名前のモデルクラスが定義されているとします）。

```js
// モデルオブジェクトの作成
const m = new Memo({title: 'Title1', body: 'Body1'});

// DB に保存（コレクション 'memos' に上記のドキュメントを格納）
m.save((err, model) => {
  if (err) {
    console.log(`ERROR: ${err.message}`);
    return;
  }
  console.log('ADDED:', model);
});
```

モデルオブジェクトを生成せずに、直接 DB のコレクションに追加するには以下のようにクラスメソッドの `create()` を使用します。

```js
Memo.create({ title: 'Title2' }, (err, model) => {
  if (err) {
    console.log(`ERROR: ${err.message}`);
    return;
  }
  console.log('ADDED:', model);
});
```

mongoose で MongoDB のドキュメントを検索する
----

DB のコレクションからドキュメントを検索するには、`Model` クラスの下記のようなクラスメソッドを使用します。

- **`Model.find()`** ... 指定した条件ですべてのドキュメントを取得
- **`Model.findOne()`** ... 指定した条件で 1 つだけドキュメントを取得
- **`Model.findById()`** ... 指定した ID のドキュメントを取得

コレクション (`memos`) に格納されている全てのドキュメントを取得するには以下のようにします（ここでは、既に `Memo` という名前のモデルクラスが定義されているとします）。

```js
Memo.find((err, memos) => {
  if (err) {
    console.log(`ERROR: ${err.message}`);
    return;
  }
  memos.forEach((m) => {
    console.log(m);
  });
});
```

モデルクラスの `find()` メソッドには、特定のフィールドを正規表現で検索する機能もあります。

```js
// title フィールドに Hoge を含むドキュメントを検索
Memo.find({ title: /Hoge/ }, callback);

// title フィールドが Hoge と完全一致するものを検索
Memo.find({ title: 'Hoge' }, callback);
```


mongoose で MongoDB のドキュメントを更新する
---

DB のコレクションに格納されているドキュメントの内容を更新するには、以下のようなインスタンスメソッド、あるいはクラスメソッドを使用します。

- **`Model#save()`** ... そのモデルが表すドキュメントを更新
- **`Model.update()`** ... 条件に一致するドキュメントのフィールドをすべて更新
- **`Model.findByIdAndUpdate()`** ... 指定した ID のドキュメントのフィールドを更新

`save()` メソッドは、まだ DB に存在しないモデルに対して実行すると、ドキュメントの追加になりますが、既に存在するモデルに対して実行すると、ドキュメントの更新になります。

{{< code lang="js" title="例: title フィールドに 'Hoge' を含むドキュメントを 1 つ更新" >}}
Memo.findOne({title: /Hoge/}, (err, model) => {
  if (err) {
    console.log(err.message);
    return;
  }
  if (!model) {
    console.log('Not found');
    return;
  }

  // 内容を更新
  model.title = 'Title Modified';
  model.save((err, model) => {
    if (err) {
      console.log(err.message);
      return;
    }
    console.log('UPDATED:', model);
  });
});
{{< /code >}}


mongoose で MongoDB のドキュメントを削除する
----

DB のコレクションからドキュメントを削除するには、以下のようなクラスメソッド、あるいはインスタンスメソッドを使用します。

- **`Model.remove()`** ... 条件に一致するドキュメントを DB からすべて削除
- **`Model.findOneAndRemove()`** ... 条件に一致するドキュメントを 1 つだけ削除
- **`Model#remove()`** ... そのモデルが表すドキュメントを削除

{{< code lang="js" title="例: title フィールドに 'Hoge' を含むドキュメントをすべて削除" >}}
Memo.remove({ title: /Hoge/ }, (err, count) => {
  if (err) {
    console.log(`ERROR: ${err.message}`);
    return;
  }
  console.log('REMOVED:', count);
});
{{< /code >}}

