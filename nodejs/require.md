---
title: "Node.js が require() で検索するパスのまとめ"
date: "2014-09-22"
---

require が探すモジュール
----

`app/main.js` の中で `require()` を実行したときに、Node.js がどのようにモジュールを検索するかのまとめです。
先頭に `./` というプレフィックスを付けた場合、拡張子を付けた場合など、少しずつ検索するファイルが異なります。

### `require('./sample.js');` とした場合

1. app/sample.js

### `require('./sample');` とした場合

1. app/sample.js
2. app/sample.json
3. app/sample.node

### `require('sample');` とした場合

1. コアモジュール
2. app/node_modules/sample.js
3. app/node_modules/sample/index.js
4. app の親ディレクトリの node_modules/sample.js
5. app の親ディレクトリの node_modules/sample/index.js
6. （見つからなければ、さらに親ディレクトリを辿っていく）


グローバルフォルダからの読み込み
----

Node は上記で説明したパスからだけでなく、下記のようなディレクトリからもモジュールを検索します（参考: [Loading from the global folders](https://nodejs.org/api/modules.html#modules_loading_from_the_global_folders)） 。

1. `$HOME/.node_modules`
2. `$HOME/.node_libraries`
3. `$PREFIX/lib/node`

また、**`NODE_PATH`** 環境変数にディレクトリ名をコロン区切り（Windows の場合はセミコロン区切り）で列挙しておくと、そのディレクトリからもモジュールを検索するようになります。

