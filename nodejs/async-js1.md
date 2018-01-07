---
title: "async.js で非同期処理のフロー制御を行う (1) async.js を使う準備"
date: "2013-12-16"
---

async.js とは
----

JavaScript のオープンソースライブラリである [async.js](https://github.com/caolan/async) を使用すると、

- 複数の非同期関数を指定した順に実行する
- 複数の非同期関数を同時に実行してすべての処理が終わるのを待つ

といったことが簡単に行えるようになります。

Node.js のモジュールとして使用できるようにする
----

Node.js で使用する場合は、npm で Node モジュールをインストールします。

```
$ npm install async
```

RequireJS から使用できるようにする
----

RequireJS で AMD モジュールとして使用する場合は、`async.js` を直接ダウンロードして使用します。

- [async.js](https://raw.githubusercontent.com/caolan/async/master/dist/async.js)
- [async.min.js](https://raw.githubusercontent.com/caolan/async/master/dist/async.min.js)
- [async.min.map](https://raw.githubusercontent.com/caolan/async/master/dist/async.min.map)

#### RequireJS での使用例

```javascript
require(['async'], function(async) {
    ...
});
```

HTML ファイルの中の `script` 要素で `async.js` をロードして使用することもできます。

```html
<script type="text/javascript" src="async.js"></script>
```

