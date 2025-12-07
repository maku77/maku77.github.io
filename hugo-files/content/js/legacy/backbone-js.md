---
title: "Backbone.jsの使い方メモ"
url: "p/wobqnsc/"
date: "2013-03-10"
tags: ["Backbone.js"]
---

Backbone.js の特徴
----

* 軽量（5KB くらい）な MVC ライブラリ。
* MIT ライセンス。
* DocumentClould の Jeremy Ashkenas 氏（CoffeeScript 作った人）によって作成。
* Underscore.js が必須。他には jQuery などが必要（選択肢あり）。
* SPI (Single Page Interface) の構築に最適。
* 使用実績: SoundCloud、DocumentCloud、Foursquare、LinkedIn Mobile、Pandora、Qiita。


js 使うための準備（backbone.js のロード）
----

Backbone.js を使用するには、あらかじめ `underscore.js` や `jQuery` をロードしておく必要があります。

以下のサンプルでは、`https://cdnjs.com` から JS ファイルをロードしています。

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Backbone.js sample</title>
</head>
<body>

<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.4/underscore-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/0.9.10/backbone-min.js"></script>
<script>

$(function() {
    'use strict';
    var MyModel = Backbone.Model.extend({
        defaults: {
            name: ''
        }
    });
    var model = new MyModel();
    model.set('name', 'Maku');
    alert(model.get('name'));
});

</script>
</body>
</html>
```

必要な JavaScript ライブラリを、ローカルから読み込む場合は、以下のような感じ書いておけば大丈夫です。

```html
...
<script src="js/lib/jquery.js"></script>
<script src="js/lib/underscore.js"></script>
<script src="js/lib/backbone.js"></script>
<script>
    ...
</script>
</body>
</html>
```

Backbone.Router を実装する
----

### Backbone.Router の基本

**`Backbone.Router`** を使用すると、Ruby on Rails や CakePHP のように、URI をアクション（JavaScript の関数呼び出し）にマッピングすることができます。
URI のマッピングは、以下のような **ハッシュフラグメント** 部分をもとに行うため、画面遷移（Web サーバへのアクセス）を発生させずに内部状態管理ができるようになります。

```
https://example.com/index.html#users/12345/edit → editUser(12345) の呼び出し
```

独自の Router クラスは、下記のように **`Backbone.Router`** を継承することで定義します。
**`Backbone.Router.extend()`** に渡すオブジェクトの、**`routes`** プロパティで、URI マッピングを設定します。

```js
var MyRouter = Backbone.Router.extend({
    routes: {
        'books': 'showAllBooks',  // index.html#books
        'books/:id': 'showBook'   // index.html#books/789
    },
    showAllBooks: function() {
        document.write('showAllBooks() was called');
    },
    showBook: function(id) {
        document.write('showBook(' + id + ') was called');
    }
});
```

URI の変更監視を開始するには以下のようにします。

```js
new MyRouter();
Backbone.history.start();
```

### routes プロパティで使用できるいろいろな記号

**`routes`** プロパティに設定するオブジェクトのキーには、ハッシュフラグメントとしてマッチさせる URI のパターンを指定しますが、ここには以下のような特殊な記号を使用できます。
URI パターンの論理的なセグメントの分割は、慣例としては **`/`** を使用します（部分的に別の記号を使うこともできますが、任意のパラメータを渡す場合に区切り文字が曖昧になって動かないケースもあるのでスラッシュを使っておくのが無難です）。

**`:param`** は、（スラッシュを含まない）任意の文字列にマッチし、ハンドラ関数のパラメータとして渡されます。
URI 中の任意の文字列は、正規表現として **`/w+`** にマッチします（つまり、`/` や `-` などの記号で終端します）。

```js
routes: {
    'users/:id': 'user',       // '#users/123abc' で user('123abc') の呼び出し
    'users/:id/edit': 'edit',  // '#users/123abc/edit' で edit('123abc') の呼び出し
    'AAA-:id-BBB': 'test',     // '#AAA-123-BBB' で test('123') の呼び出し
    ':def': 'def'              // '#hello' で def('hello') の呼び出し（'#hello/123' では呼ばれない）
}
```

**`*param`** は任意の文字列にマッチし、ハンドラ関数のパラメータとして渡されます。
`:param` との違いは、`*param` の場合は、スラッシュを含む任意の文字列にマッチするところです。
つまり、これは URI の最後の部分にしか指定できません。

```js
routes: {
    'download/*path': 'download',  // '#download/aa/bb/cc' で download('aa/bb/cc') の呼び出し
    '*path': 'defaultRoute',       // '#aa/bb/cc' で defaultRoute('aa/bb/cc') の呼び出し
}
```

**`(optional)`** のように括弧で囲むと、その部分が省略された URI にも一致します。
URI でオプショナルなパラメータを指定できるようにしたい場合に使用できます。

```js
var MyRouter = Backbone.Router.extend({
    routes: {
        'test(/aaa)': 'test',  // '#test' あるいは '#test/aaa' で test() の呼び出し
        'show(/:id)': 'show'  // '#show' で show()、'#show/123' で show('123') の呼び出し
    },
    test: function() {
        console.log('test');
    },
    show: function(id) {
        if (typeof id === 'undefined') {
            id = 0;  // use the default ID
        }
        console.log('show: ' + id);
    }
});
```

### View 切り替え時に元の View のイベント処理を削除する

Backbone の View を切り替えるときに、前に使用していた View のイベントハンドラ（`events` プロパティで指定したもの）を削除しないと、イベントハンドラが残っていて意図しない動作をすることがあります。
Router で View を切り替える前に、前の View に対して **`undelegateEvents()`** を呼び出してやることで、このような動作を抑制できます。

```js
var currentView;

var AppRouter = Backbone.Router.extend({
    routes: {
        'show/:id': 'showWord',
        'edit/:id': 'editWord',
    },
    showWord: function (id) {
        if (currentView) {
            currentView.undelegateEvents();
            // 子要素も全て削除する場合
            // currentView.$el.children().remove();
        }
        currentView = new WordView({model: model});
    },
    editWord: function(id) {
        if (currentView) {
            currentView.undelegateEvents();
            // 子要素も全て削除する場合
            // currentView.$el.children().remove();
        }
        currentView = new EditView({model: model});
    },
});
```

Backbone.Model を実装する (1) Backbone.Model の基本
----

### モデルオブジェクトには Backbone.Model を使う

Backbone フレームワークにおけるモデルオブジェクトの作成には、**`Backbone.Model`** クラスを使用します。

```js
var m1 = new Backbone.Model();
var m2 = new Backbone.Model({key1: 'value1', key2: 'value2'});  // モデルの初期値を与えることも可能
```

`Backbone.Model` クラスは、内部的なモデルデータを指すプロパティ attributes（属性値）を持っています。
JavaScript の素のオブジェクトをモデルオブジェクトとして使うのとは異なり、`Backbone.Model` のインスタンスをモデルオブジェクトとして使用すると、以下のようなことを簡単に行えます。

* 属性値 (attributes) の取得、設定のための `set()`/`get()` の提供
* 属性値 (attributes) の変更通知（例えば、ビューが変更通知を受けて、再描画する）
* 属性値 (attributes) の永続化サポート
* サーバとのデータのやりとり

### モデルオブジェクトの属性値 は get()、set() で取得、設定する

Backbone.Model のインスタンスは、内部的なモデルデータを指す attributes プロパティを持っていますが、通常はこのプロパティを参照してデータを操作するのではなく、`get` 関数、`set` 関数を使用して間接的に属性値を操作します。

```js
var model = new Backbone.Model();
model.set('key1', 'value1');
var name = model.get('key1');

console.log(name);  //=> 'value1'
```

このように、間接的に属性値を変更することによって、変更時にイベント (change event) を発生させたり、`validate` 関数などを自動的に呼び出す仕組みになっています。
もちろん、現在の属性値を確認するために attributes オブジェクトを参照することは可能です。

```js
var model = new Backbone.Model({key1: 'value1', key2: 'value2'});
console.dir(model.attributes);  // key1  value1
                                // key2  value2
```

`Backbone.Model` は、純粋なモデルオブジェクトとして使用できるように、ビューとは依存関係を持っていません。
一方で、イベント発生の仕組みを備えることによって、特定のビューとモデルを連携させることができるようになっています。


Backbone.Model を実装する (2) 独自モデルクラスの作成
----

`Backbone.Model.extend()` 関数を使うと、独自のモデルクラスを定義することができます。

```js
// 最低限のモデルクラスの定義
var MyModel = Backbone.Model.extend();

// モデルのインスタンス化
var model = new MyModel({name: 'Maku'});
console.log(model.get('name'));
```

独自のモデルクラスでは、データのデフォルト値（defaults プロパティ）や、モデルオブジェクト作成時の初期化関数（initialize プロパティ）などを定義することができます。

```js
var MyModel = Backbone.Model.extend({
    // デフォルト値
    defaults: {
        // 下記に加え、コンストラクタで指定された key & value ペアが
        // このモデルの属性値として保持される。
        key1: 'default1',
        key2: 'default2'
    },

    // 初期化処理
    initialize: function() {
        // initialize() が呼び出される時点で、デフォルト値は既に設定されているので、
        // 下記はデフォルト値を上書きすることになる。
        // コンストラクタで与えられた属性値に関しても同様。
        this.set('key1', 'init1');
    }
});

var model = new MyModel();
console.dir(model.attributes);  // key1 => 'init1'
                                // key2 => 'value2'
```

もちろん、独自モデルクラスのコンストラクタ呼び出しにも、初期値を与えることができます。

```js
var model = new MyModel({key3: 'value3', key4: 'value4'});
```


Backbone.Model を実装する (3) モデルの変更イベントをハンドルする
----

`Backbone.Model` オブジェクトに値をセットすると、Backbone フレームワーク内で `change` イベントが発生するようになっています。

- 参考: [Backbone.js の 組み込みイベント一覧](https://backbonejs.org/#Events-catalog)

`Backbone.Model` オブジェクトの `change` イベントに任意のコールバック関数をバインドすることで、モデルの変更を監視することができます。

```js
var model = new Backbone.Model();
model.on('change', function(obj) {
    console.log('changed to ' + obj.get('x'));
});
model.set({x:100});  //=> 'change' イベントが発生する
```

特定の属性値のみの変更を監視するには、以下のようにイベント名の後ろに属性名のプレフィックスを付けます。
下記の例では、属性 `x` のみの変更を監視しています。

```js
model.on('change:x', function(obj, x) {
    console.log('changed to ' + x);
});
model.set({x:100});
```

コールバック関数の第 2 引数に、指定した属性の変更後の値が渡されます。


Backbone.Collection を実装する
----

### コレクションクラスの定義

`Backbone.Collection` クラスを拡張 (`extend`) すると、複数のモデルオブジェクトを保持するコレクションクラスを定義することができます。

```js
// Book モデルの定義
var Book = Backbone.Model.extend();

// BookCollection コレクションの定義
var BookCollection = Backbone.Collection.extend({
    model: Book,
});
```

上記の例では、**`Book`** モデルを格納するための、**`BookCollection`** コレクションを定義しています。
`Backbone.Collection` の **`model`** プロパティには、そのコレクションが格納するモデルクラスを指定します。

### コレクションクラスを使ってみる

コレクションにモデルオブジェクトを追加するには、**`add`** メソッドに追加したいモデルオブジェクトを渡します。
配列で複数のモデルオブジェクトを追加することもできます。

```js
var books = new BookCollection();

// モデルを 1 つ追加する
books.add({title: 'Title A', author: 'Author A'});

// 複数のモデルを追加する
books.add([
  {title: 'Title A', author: 'Author A'},
  {title: 'Title B', author: 'Author B'},
  {title: 'Title C', author: 'Author C'}
]);
```

コレクションに対してモデルを追加された場合のイベントをハンドルするには以下のようにします。
複数のモデルオブジェクトを、配列の形で `add()` メソッドで追加した場合は、追加したモデルオブジェクトの数だけ **`add`** イベントが発生します。

```js
var books = new BookCollection();
books.on('add', function(book) {
  alert(book.get('title') + ' added');
});
```

モデルオブジェクトは次のようにインデックス指定で取得できます。

```js
var books = new BookCollection();
...
var book = books.at(0);
```

コレクションの各要素をループで処理するには次のようにします。

```js
collection.forEach(function(model) {
    console.log(model.get('name'));
});

for (var i = 0; i < collection.length; ++i) {
    var model = collection.at(i);
    console.log(model.get('name'));
}
```


Model/Collection の Web サーバとの同期: URL 決定の仕組み
----

`Backbone.Model`/`Collection` には、Web サーバと通信してモデル情報をやりとりするための API が実装されています。

- `Backbone.Model`
  - `fetch()` ... Web サーバからのモデル取得 (GET)
  - `save()` ... Web サーバへのモデル保存/更新/部分更新 (POST/PUT/PATCH)
  - `destory()` ... Web サーバ上のモデル削除 (DELETE)
- `Backbone.Collection`
  - `fetch()` ... Web サーバからのコレクション取得 (GET)
  - `create(model)` ...  Web サーバへのモデル保存（同時にコレクションへモデル追加） (POST)

Web サーバ側では、上記の HTTP メソッドに対応する JSON ベースの RESTful API を実装しておく必要があります。
モデルの同期系メソッドを呼び出した場合に、実際にどの URL に対して HTTP リクエストを送るかは、以下のようなアルゴリズムによって決定されます。

1. モデルクラスの `url` 属性が設定されている場合は、それが絶対パスとして使われる（関数でもよい）。
2. モデルクラスに `urlRoot` 属性が設定されている場合は、それがベースパスとなり、末尾に `id` 属性が付加された URL が使われる。
3. コレクションオブジェクトにモデルオブジェクトが所有されている場合は、コレクションクラスの `url` 属性がベースパスとなり、末尾に `id` 属性が付加された URL が使われる（「所有」というのは、`collection.add(model)` のように実行されていること）
4. 上記のいずれにも当てはまらない場合はエラー。

コレクションの同期系メソッドを呼び出した場合は以下のように振る舞います。

1. コレクションクラスの `url` 属性が設定されている場合は、それが使われる（関数でもよい）。
2. url 属性が設定されていない場合はエラー発生。

ほとんどのケースでは、通常は以下のように定義しておけば問題ありません。

* モデルクラスの定義: **モデルクラスの `urlRoot` 属性** にベース URL を指定しておく（アクセス先は `[model.urlRoot]/[model.id]` となる）。
* コレクションクラスの定義: **コレクションクラスの `url` 属性** に URL を指定しておく（アクセス先は `[collection.url]` となる）。

各メソッドと、アクセス先 URL、HTTP メソッドの組み合わせは以下のような感じになります。

- 取得
  - `model.fetch()` ... `GET <base_url>/<モデルの id 属性>`
  - `collection.fetch()` ... `GET <base_url>/`
- 更新
  - `model.save()` ... `PUT <base_url>/<モデルの id 属性>`
- 作成
  - `model.save()` ... `POST <base_url>/`
  - `collection.create(model)` ... `POST <base_url>/`
- 部分更新
  - `model.save()` ... `PATCH <base_url>/`
- 破棄
  - `model.destory()` ... `DELETE <base_url>/<モデルの id 属性>`

`<base_url>` は一般的に **コレクションクラスの `url` プロパティ** です。
モデルオブジェクトからメソッド呼び出しした場合は、`<base_url>` として **モデルクラスの `urlRoot` プロパティ** を使用することもできます。

以下は、実際に GET リクエストでモデル情報を取得するサンプルです。

```js
// Memo モデルの定義
var Memo = Backbone.Model.extend({
    urlRoot: '/api/memos',
});

// Memo データの取得
var memo = new Memo({id: 'AAA'});
memo.fetch();
```

上記の `fetch()` 呼び出しにより、HTTP GET リクエストが `/api/memos/AAA` に対して発行されます。
現在のモデルがどの URL からデータを取得するかは、モデルの **`url()`** メソッドを使って確認できます。

```js
console.log(memo.url());  //=> /api/memos/AAA
```

{{< code lang="js" title="参考になるコード（Backbone 1.1.0 の Backbone.Model.fetch() の抜粋）" >}}
fetch: function(options) {
  ...
  return this.sync('read', this, options);
},
{{< /code >}}

モデルの `fetch()` を呼ぶと、最後に `Backbone.Model.sync()` が呼ばれます。

{{< code lang="js" title="Backbone.Collection.fetch() の抜粋" >}}
fetch: function(options) {
  ...
  return this.sync('read', this, options);
},
{{< /code >}}

コレクションの `fetch()` を呼ぶと、最後に `Backbone.Collection.sync()` が呼ばれます。

{{< code lang="js" title="Backbone.Model.sync() の抜粋" >}}
sync: function() {
  return Backbone.sync.apply(this, arguments);
},
{{< /code >}}

`Backbone.Model.sync()` も `Backbone.Collection.sync()` も、`Backbone.sync()` を呼び出すように実装されています。

{{< code lang="js" title="Backbone.sync() の抜粋" >}}
Backbone.sync = function(method, model, options) {
  ...
  if (!options.url) {
    params.url = _.result(model, 'url') || urlError();
  }
  ...
{{< /code >}}

HTTP リクエストで使われる URL は、上記の部分で決められています。
Underscore.js の `result()` メソッドにより、`url()` メソッドの結果、あるいは `url` プロパティの値そのものを最終的な URL として取得しています。

{{< code lang="js" title="Backbone.Model.url() デフォルト実装" >}}
url: function() {
  var base = _.result(this, 'urlRoot') ||
             _.result(this.collection, 'url') || urlError();
  if (this.isNew()) return base;
  return base + (base.charAt(base.length - 1) === '/' ? '' : '/') +
      encodeURIComponent(this.id);
}
{{< /code >}}

`Backbone.Model` にはデフォルトの `url()` 実装があり、上記のように、モデルの `urlRoot` 属性、あるいはコレクションに所有されている場合は、コレクションの `url` 属性がベース URL として使用されます。
このロジックは、モデルオブジェクトの同期系メソッドを呼び出した際に実行されます。


Model/Collection の Web サーバとの同期: 取得 (1)
----

### model.fetch() で Web サーバからコレクションデータを取得する

モデルオブジェクトの **`fetch()`** メソッドを使用すると、Web サーバから取得した情報を使って、モデルオブジェクトを初期化することができます。
通常、モデルオブジェクトに **`id`** 属性を指定した上で `fetch()` を呼び出すことにより、特定の ID を持つモデルの情報を取得します。

ここでは、`id=AAA` であるモデル情報を、`http://localhost:3000/api/memos/AAA` から取得することを考えます。
Web サーバは、以下のように JSON データを返すように準備されている必要があります。

```console
$ curl http://localhost:3000/api/memos/AAA
{
  "id": "AAA",
  "title": "タイトル1",
  "content": "本文1",
  "created": "2014-01-12T10:03:00.009Z"
}
```

モデル情報取得のためのコードは以下のようになります。

```js
// Memo モデルの定義
var Memo = Backbone.Model.extend({
    urlRoot: 'http://localhost:3000/api/memos',
});

// Memo データの取得
var memo = new Memo({id: 'AAA'});
memo.fetch({
    success: function(memo) {
        console.log(memo.get('id'));     //=> 'AAA'
        console.log(memo.get('title'));  //=> 'タイトル1'
    }
});
```

### collection.fetch() で Web サーバからコレクションデータを取得する

コレクションオブジェクトの **`fetch()`** を呼び出すことで、複数のモデル情報を Web サーバから取得して、コレクションに格納することができます。

ここでは、複数のモデル情報を `http://localhost:3000/api/memos/` から取得することを考えます。
Web サーバは、下記のような配列の JSON データを返すように準備されている必要があります。
配列の各要素は、モデルオブジェクトを構成する情報を含んでいます。

```console
$ curl http://localhost:3000/api/memos/
[
  {
    "id": "AAA",
    "title": "Title1",
    "content": "Content1",
    "created": "2014-01-13T12:31:42.144Z"
  },
  {
    "id": "BBB",
    "title": "Title2",
    "content": "Content2",
    "created": "2014-01-13T12:31:42.144Z"
  },
  {
    "id": "CCC",
    "title": "Title3",
    "content": "Content3",
    "created": "2014-01-13T12:31:42.144Z"
  }
]
```

コレクション情報取得のためのコードは以下のようになります。

```js
// Memo モデルクラスの定義
var Memo = Backbone.Model.extend();

// MemoCollection コレクションクラスの定義
var MemoCollection = Backbone.Collection.extend({
    model: Memo,
    url: 'http://localhost:3000/api/memos',
});

// コレクションの取得
var collection = new MemoCollection();
collection.fetch({
    success: function(col) { console.log(col.toJSON()); }
});
```

### model.fetch() 時の処理の流れ

あるモデルの `fetch()` を呼び出してサーバ上のデータと同期しようとするとき、以下のような流れで処理が進みます。

1. モデルオブジェクトの `fetch()` を呼び出す。
2. `Model.sync()` が呼び出され、`model.url()` が返す URL に対して GET リクエストを発行する。
   `Model.sync()` のデフォルト実装は `Backbone.sync()` 呼び出しへのプロキシとなっていて、このとき CRUD 操作を表すパラメータである `method` に `'read'` が指定されるため、それとマッピングされている HTTP GET リクエストが発行されることになる。
   - 取得に成功した場合
     1. モデルクラスの `parse()` が呼び出される。必要ならばここでサーバからのレスポンスを加工したうえでモデルにセットできる。
     2. `parse()` の戻り値が `model.set()` でセットされる。内容が変更される場合は、モデルの `'change'` イベントが発生する。
     3. `model.fetch()` の呼び出し時に `success` 関数を指定している場合は、それが呼び出される。
     4. モデルの `'sync'` イベントが発生する。
   - 取得に失敗した場合
     1. `model.fetch()` の呼び出し時に `error` 関数を指定している場合は、それが呼び出される。
     2. モデルの `'error'` イベントが発生する。

{{< code lang="js" title="model.fetch() の流れを追ってみる" >}}
// Backbone.sync() でログ出力する
var orgSync = Backbone.sync;
Backbone.sync = function(method, model, options) {
    console.log('Backbone.sync() method=' + method);
    orgSync(method, model, options);  // デフォルト実装を呼び出し
};

// Memo モデルクラスの定義
var Memo = Backbone.Model.extend({
    urlRoot: 'http://localhost:3000/api/memos',
    parse: function (resp, options) {
        console.log('parse');
        return resp;
    }
});

// Memo データの取得
var memo = new Memo({id: 'AAA'});
memo.fetch({
    success: function(model, resp, options) { console.log('success'); },
    error: function(model, resp, options) { console.log('error'); }
});

// モデルのイベントハンドラ
memo.on('change', function() { console.log('EVENT(change)'); });
memo.on('sync', function() { console.log('EVENT(sync)'); });
memo.on('error', function() { console.log('EVENT(error)'); });
{{< /code >}}

{{< code title="実行結果" >}}
// model.fetch() に成功した場合
Backbone.sync() method=read
parse
EVENT(change)
success
EVENT(sync)

// model.fetch() に失敗した場合
Backbone.sync() method=read
error
EVENT(error)
{{< /code >}}

### collection.fetch() 時の処理の流れ

コレクションオブジェクトの `fetch()` を呼び出して、サーバ上のデータと同期しようとするときも、モデルオブジェクトの `fetch()` と同様の流れで処理が進みます。

{{< code lang="js" title="collection.fetch() の流れを追ってみる" >}}
// Backbone.sync() でログ出力する
var orgSync = Backbone.sync;
Backbone.sync = function(method, model, options) {
    console.log('Backbone.sync() method=' + method);
    orgSync(method, model, options);  // デフォルト実装を呼び出し
};

// Memo モデルクラスの定義
var Memo = Backbone.Model.extend({
    parse: function (resp, options) {
        console.log('parse');
        return resp;
    }
});

// MemoCollection コレクションクラスの定義
var MemoCollection = Backbone.Collection.extend({
    model: Memo,
    url: 'http://localhost:3000/api/memos',
});

// コレクションの取得
var collection = new MemoCollection();
collection.fetch({
    success: function() { console.log('success'); },
    error: function() { console.log('error'); }
});

// コレクションのイベントハンドラ
collection.on('all', function(e) { console.log('EVENT ' + e); });
{{< /code >}}

{{< code title="実行結果" >}}
// collection.fetch() に成功した場合
Backbone.sync() method=read
parse
parse
parse
EVENT add
EVENT add
EVENT add
EVENT sort
success
EVENT sync

// collection.fetch() に失敗した場合
Backbone.sync() method=read
error
EVENT error
{{< /code >}}

ここでは、コレクションデータとして 3 つ分のモデルデータを受信しているため、モデルクラスの `parse()` メソッドや、コレクションへの追加イベント (add) が 3 回ずつ発生しています。


Model/Collection の Web サーバとの同期: 取得 (2) JSON ファイルをロード
----

`Backbone.Model` や `Backbone.Collection` の `fetch()` メソッドは、単純な JSON ファイルからデータを取得することもできます。
モデルに対して POST/PUT/DELETE メソッドなどに対応した RESTful な操作の必要がない場合は、単純なデータを JSON ファイルにしてしまうのもひとつの手です。
やり方は、モデルまたはコレクションの `fetch()` によって GET しにいく URL に、JSON 形式のファイルを置いておくだけです。

### 例: Word モデルに data/word1.json ファイルの内容をロードする

{{< code lang="json" title="data/word1.json" >}}
{
    "jp": "りんご",
    "en": "apple"
}
{{< /code >}}

```js
// Word モデルクラスの定義
var Word = Backbone.Model.extend({
    urlRoot: './data'
});

// data/word1.json の読み込み
var word = new Word({id: 'word1.json'});
word.fetch({
    success: function(word) {
        console.log(word.get('jp'), word.get('en'));
    },
    error: function() {
        console.log('Load error');
    }
});
```

### 例: WordCollection コレクションに data/words.json ファイルの内容をロードする

配列データの入った JSON ファイルを用意しておけば、コレクションにそのままロードできます。

{{< code lang="json" title="data/words.json" >}}
[
    {
        "jp": "りんご",
        "en": "apple"
    },
    {
        "jp": "バナナ",
        "en": "banana"
    }
]
{{< /code >}}

```js
// Word モデルクラス、WordCollection クラスの定義
var Word = Backbone.Model.extend({});
var WordCollection = Backbone.Collection.extend({
    model: Word,
    url: 'data/words.json'
});

// data/words.json の読み込み
var words = new WordCollection();
words.fetch({
    success: function(col) {
        col.forEach(function(w) {
            console.log(w.get('jp'), w.get('en'));
        });
    },
    error: function() {
        console.log('Fetch error');
    }
});
```


Model/Collection の Web サーバとの同期: 取得 (3) モデルのランダム取得機能を作る
----

下記のサンプルでは、単語データを表す `Word` モデルを定義しています。
`urlRoot` がセットされているので、`Word#fetch()` を呼び出すことで `api/words/[id] ` という URL から GET リクエストでデータを取得できます。

```js
var Word = Backbone.Model.extend({
    urlRoot: 'api/words/'
});

var word = new Word({id: 'apple'});
word.fetch({  //=> GET api/words/apple
    success: function(w) {
        console.log(w.jp + ' ' + w.en);
    }
});
```

明示的に取得するデータの `id` を指定できる場合はこれでよいのですが、ここでは `id` を指定せずに、ランダムに単語データを取得する `randomFetch()` メソッドを追加してみます。
サーバ側に、あらかじめランダムな単語データを返す RESTful API が定義されていると仮定します（URL は `api/random` とします）。

```js
Word.prototype.randomFetch = function(options) {
    options.url = 'api/random';
    this.fetch(options);
};

var word = new Word();
word.randomFetch({  //=> GET api/random
    success: function(w) {
        console.log(w.jp + ' ' + w.en);
    }
});
```

もともと、`Model#fetch()` メソッドには、オプションパラメータとしてアクセス先の URL を指定できる機能があります。
これを指定すると、`urlRoot` で指定した URL よりも優先されて使用されます。
上記では、その URL を `api/random` に設定して `fetch()` メソッドを呼び出すようにしています。


Model/Collection の 永続化処理のカスタマイズ
----

モデルオブジェクトの `fetch()` や、`save()`、`destory()` メソッドを呼び出すと、`Backbone.sync()` メソッドが呼び出されるようになっており、この中で、RESTful な Web API を想定した通信が行われます。
`Backbone.sync` に任意の関数を設定すれば、永続化処理をカスタマイズすることができます。
例えば、RESTful API 呼び出しではなく、`sessionStorage` にモデル情報を保存するようなことができます。
また、`sync()` メソッドはプロトタイプチェインの仕組みに従って呼び出されるため、モデルクラスごとに永続化処理をカスタマイズすることもできます。

```js
// Backbone 全体の永続化処理をカスタマイズ
Backbone.sync = function(method, model, options) { ... };

// モデルクラス、コレクションクラスごとに永続化処理をカスタマイズ
YourModel.prototype.sync = function(method, model, options) { ... };
YourCollection.prototype.sync = function(method, model, options) { ... };
```


Backbone.View を実装する (1) 独自ビュークラスの作成
----

**`Backbone.View`** は、ある DOM 要素の描画内容を定義する役割を果たします。
独自の View クラスを作成するには、`Backbone.View.extend()` を使って `Backbone.View` を継承します（`Model` や `Router` の継承方法と同様です）。

```js
var MyView = Backbone.View.extend({
  el: '#output'
});
```

`Backbone.View` オブジェクトは、必ず 1 つの **`el`** プロパティを持ち、描画ターゲットとなる DOM 要素を参照します。
上記の例では、`#output` という ID を持つ要素を描画対象としています。
`el` プロパティは、View オブジェクトを生成するときに指定することもできます。

```js
var view = new MyView({el: '#output'});
```

View で実際にどのように描画を行うか（どう HTML 要素を組み立てるか）は、`render()` メソッドで定義します。
`el` プロパティで参照設定した要素は、**`$el`** プロパティで jQuery オブジェクトとして参照できるようになっているため、`render()` メソッドは `$el` を使って実装するのがお手軽です。

```js
var MyView = Backbone.View.extend({
  el: '#output',
  render: function() {
    this.$el.html('<h1>Hello</h1>');
    return this;
  }
});

var view = new MyView();
view.render();
```

上記の例では、明示的に View オブジェクトの `render()` メソッドを呼び出すことにより、対象の要素に Hello というメッセージを表示しています。
View オブジェクトを生成したときに、自動的に `render()` メソッドを呼び出すようにするには、以下のように **`initialize()`** メソッドを定義します。

```js
var MyView = Backbone.View.extend({
    ...
    initialize: function() {
        this.render();
    }
    ...
});
var view = new MyView();    // 自動的に描画される
```


Backbone.View を実装する (2) el プロパティの設定方法いろいろ
----

`Backbone.View` オブジェクトの **`el`** プロパティは、以下のいずれかの方法で指定し、既存の DOM 要素を参照するように設定します。
CSS セレクタで指定するのが一般的です。

- **`el: '#elem'`** ... CSS のセレクタ文字列で指定
- **`el: $('#elem')`** ... jQuery オブジェクトで指定
- **`el: document.getElementById('elem')`** ... DOM 要素への参照を直接指定

`el` プロパティを設定しただけで、`$el` プロパティとして jQuery オブジェクトを参照できるようになるのは、内部的に呼び出される `Backbone.View.setElement()` の中で以下のように設定されているからです。

{{< code lang="js" title="Backbone.View.setElement() 抜粋" >}}
this.$el = element instanceof Backbone.$ ? element : Backbone.$(element);
this.el = this.$el[0];
{{< /code >}}

要するに、`el` プロパティに設定した要素は、どんな指定方法をした場合でも、

- **`this.$el`** で jQuery オブジェクトとして参照できる
- **`this.el`** で先頭の DOM 要素を参照できる

ということになります。


Backbone.View を実装する (3) el 要素以下で発生するイベントをハンドルする
----

`Backbone.View` オブジェクトの、**`events`** プロパティを設定しておくと、click/dblclick/submit/mouseover などのイベントを簡単にイベントハンドラに割り当てられます。
イベントのマッピングは、以下のようなキー＆バリューの形で定義します。

```
"<イベント名> <CSSセレクタ>": "<ハンドラ名>"
```

下記の例では、`$el` 以下に生成した `button` 要素のクリックイベントをハンドルしています。

```js
var MyView = Backbone.View.extend({
    initialize: function() {
        this.render();
    },
    render: function() {
        this.$el.html('<button>Test</button>');
        return this;
    },
    events: {
        'click button': 'onClicked'
    },
    onClicked: function(event) {
        alert('Hello');
    }
});

var view = new MyView({el: '#output'});
view.render();
```

`events` プロパティによってマッピングすることのできるイベントは、`el` 要素以下の要素で発生するイベントであることに注意してください。


Backbone.View を実装する (4) Model の内容を View で描画する
----

### ビューにモデルを関連付ける

モデルの内容を、ビューで描画するには、何らかの方法でモデルオブジェクトをビューに設定する必要があります。
以下の例では、ビューのコンストラクタでモデルオブジェクトを渡し、`model` プロパティとして設定しています。

```js
// モデルの定義
var MyModel = Backbone.Model.extend({
  defaults: { 'message': 'Hello' }
});

// ビューの定義
var MyView = Backbone.View.extend({
  el: '#output',
  initialize: function() {
    this.render();
  },
  render: function() {
    this.$el.text(this.model.get('message'));
    return this;
  }
});

// インスタンス生成
var model = new MyModel();
var view = new MyView({model: model});
```

上記の例からも分かるように、ビューオブジェクトを生成するときに指定した `model` プロパティは、ビュークラスの中で、**`this.model`** で参照できるようになっています。

### モデルの変化時に自動的にビューを再描画する

モデルの値が変化したとき（`set` メソッドを呼び出したとき）に、自動的にビューの **`render`** メソッドを呼び出すようにするには、**`Backbone.Model`** の **`change`** イベントに **`Backbone.View`** の **`render`** メソッドをバインドします。
下記の例では、`MyView` は最初に `MyModel` のデフォルト値である **`Hello`** を表示していますが、2 秒後に `MyModel` の値が **`World`** に変更され、それに連動して自動的に `MyView` の表示が切り替わるようになっています。

```js
var MyModel = Backbone.Model.extend({
  defaults: { 'message': 'Hello' }
});

var MyView = Backbone.View.extend({
  el: '#output',
  initialize: function() {
    // モデルの変化時に render() を呼ぶ
    this.model.on('change', this.render, this);
    this.render();
  },
  render: function() {
    this.$el.text(this.model.get('message'));
    return this;
  }
});

var model = new MyModel();
var view = new MyView({model: model});

// 2 秒後にモデルの内容を変更 => 自動的にビューが再描画される
setTimeout(function() {
  model.set({message: 'World'});
}, 2000);
```

