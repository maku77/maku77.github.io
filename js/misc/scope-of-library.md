---
title: "外部スクリプトはなぜ (function(){})(); のような書き方をするのか？"
date: "2012-12-12"
---

オープンソースで出回っている JavaScript コード（jQuery など）の中身を見ると、下記のように、無名関数を作って即実行という書き方をしてあります。

```javascript
(function(window, undefined) {
  var foo = ...;
  var bar = ...;
  var PublicClass = {};
  PublicClass.prototype.hoge = function() {
  };
  ...
  window.PublicClass = PublicClass;
})(window);
```

これは、グローバルな名前空間を汚染するのを防ぐためです。
JavaScript には、ファイルスコープや、ブロックスコープがなく、関数スコープしかありません。
グローバルな名前空間に、必要のないオブジェクトを作成するのを防ぐためには、

* 全体を関数にして、関数スコープに閉じ込める。
* 公開するオブジェクトだけを、グローバルなプロパティとして見せる。

といった考慮が必要になってきます。

