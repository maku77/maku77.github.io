---
title: "mongoose で MongoDB (3) モデルクラスを作成する"
date: "2014-02-10"
---

モデルクラスを作成する
----

`mongoose` で MongoDB のドキュメントを扱うには、先にモデルクラスとして各フィールドの型などを定義しておく必要があります。
以下のサンプルでは、`Memo` という名前のモデルクラスを定義しています。

```javascript
var MemoSchema = new mongoose.Schema({
  title: String,
  body: String
});

var Memo = mongoose.model('Memo', MemoSchema);
```

これで、`title` と `body` というフィールドを持ったドキュメント扱う、`Memo` モデルクラスが作成されました。
上記では、`mongoose.model()` の戻り値として `Memo` モデルクラスのインスタンスを受け取っていますが、以下のようにして、定義とインスタンスの取得を分けて行うこともできます。

```javascript
mongoose.model('Memo', MemoSchema);  // モデルクラスの定義
var Memo = mongoose.model('Memo');   // モデルクラスの取得
```

mongoose では、`Memo` という名前のモデルクラスを定義することで、自動的に `memos` という複数形の名前のコレクションを扱うようになります。
コレクション名を明示的に指定したい場合は、Schema の作成時に以下のように指定します。

```javascript
var MemoSchema = new mongoose.Schema({
  title: String,
  body: String
}, {
  collection: 'memo_collection'  // コレクション名
});

var Memo = mongoose.model('Memo', MemoSchema);
```

スキーマの型指定方法いろいろ
----

モデルクラス用のスキーマを定義するときは、下記のように各カラムの型を柔軟に定義することができます。

```java
var MySchema = new Schema({
  name: { type: String, required: true },      // 必須フィールド
  age: { type: Number, min: 0, default: 0 },   // 最小値、デフォルト値
  created: { type: Date, default: Date.now },  // 日時のデフォルト値
  updated: { type: Date, default: Date.now },
  hidden: Boolean,  // Boolean 値
});
```

