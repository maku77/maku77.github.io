---
title: "Node.js が require() で検索するパスのまとめ"
date: "2014-09-22"
---

`require()` によって Node.js がどのようにロードするモジュールを検索するかは、[Node.js の Modules のドキュメント](https://nodejs.org/api/modules.html#modules_all_together) に詳しく説明されていますが、若干複雑なのでここでまとめておきます。


require の使い分け
----

`require` でモジュールをロードするとき、多くは下記の 3 パターンのロード方法に分類できます。

~~~ javascript
// コアモジュール、あるいは node_modules にインストールしたパッケージのロード
const crypto = require('crypto');

// ローカルモジュールのロード
const myLocalModule = require('./path/to/myLocalModule');

// JSON ファイルのロード
const jsonData = require('./path/to/data.json');
~~~

簡単に言うと、

* 組み込みモジュールや、外部パッケージを使うときは名前そのものを指定する。
* 自分で作成したローカルモジュールは `./` で始まるパスで指定する。

と理解しておけばよいでしょう（厳密には `./` で始まっていも `node_module` 内のパッケージが検索されますが、それはフォールバック的な仕組みなので気にしなくてよいです）。


require がどのようにモジュールを検索するか？
----

厳密ではないですが、`require` によってどのパスに置かれたファイルがロードされるかは、下記のようなアルゴリズムで決められます。

### require('name') としたとき

`require` のパラメータでモジュール名そのもの（`express` など）を指定した場合は、下記のようなアルゴリズムでロードするモジュールが決められます。

1. `name` というコアモジュールを探す。
2. 同じディレクトリの `node_modules/name` というパッケージを探す。
3. より上位のディレクトリの `node_modules/name` というパッケージを探していく。
4. 見つからなければ not found エラーを投げる。

例えば、`/aaa/bbb/main.js` 内で `require('express')` とすると、下記のように検索されていきます。

1. コアモジュール（Node の組み込みモジュール）の `express`
2. `/aaa/bbb/node_modules/express`
3. `/aaa/node_modules/express`
4. `/node_modules/express`

ちなみに、`name` というモジュール名の部分に、`path/to/name` のようにディレクトリパスが含まれていれば、そのような階層でインストールされた `name` モジュールがロードされます（`node_modules/path/to/name` などが検索される）。


### require('./name') としたとき

`require` のパラメータが `'./name'`、`'../name'`、`'/name'` というように、`.` あるいは `/` で始まるパスで指定された場合は、下記のようにファイルが検索されます。

1. `name` という名前のファイルを探す。
2. `name.js` という名前のファイルを探す。
3. `name.json` という名前のファイルを探す（JSON ファイルとしてロード）。
4. `name.node` という名前のファイルを探す（バイナリ addon として dlopen でロード）。
5. `name/package.json` というファイルがあれば、その `main` フィールドを見て、`name/<mainフィールドの値>` というパスを使用して 1 からやり直し。
6. `name/index.js` という名前のファイルを探す。
7. `name/index.json` という名前のファイルを探す（JSON ファイルとしてロード）。
8. `name/index.node` という名前のファイルを探す（バイナリ addon として dlopen でロード）。
9. グローバルフォルダから検索する（後述）。
10. 見つからなければ not found エラーを投げる

例えば、`/aaa/bbb/main.js` 内で `require('./mylib')` とすると、下記のように検索されていきます。

1. `/aaa/bbb/mylib` という JavaScript ファイル
2. `/aaa/bbb/mylib.js` という JavaScript ファイル
3. `/aaa/bbb/mylib.json` という JSON ファイル
4. `/aaa/bbb/mylib.node` というバイナリ addon ファイル
5. `/aaa/bbb/mylib/package.json` に `name` フィールドがあれば、`mylib/<mainフィールドの値>` が指定されたものとして同様に検索
6. `/aaa/bbb/mylib/index.js` という JavaScript ファイル
7. `/aaa/bbb/mylib/index.json` という JSON ファイル
8. `/aaa/bbb/mylib/index.node` というバイナリ addon ファイル
9. 各グローバルフォルダ以下から同様に検索

ちなみに、`require` に渡す名前に `./sample.js` のように、拡張子まで含めて指定した場合も、上記のアルゴリズムはそのまま実行されます。
`.js` という拡張子を持つファイルだけが検索されるわけではありません。
つまり、`sample.js` というファイルが見つからなければ、`sample.js.js` や `sample.js.json` のようなファイル名で検索が行われます。


グローバルフォルダからのモジュールロード
----

Node は上記で説明したパスからだけでなく、下記のようなディレクトリからもモジュールを検索します（参考: [Loading from the global folders](https://nodejs.org/api/modules.html#modules_loading_from_the_global_folders)） 。

1. `NODE_PATH` 環境変数に列挙されたディレクトリ（Linux はコロン区切り、Windows はセミコロン区切りで列挙）
1. `$HOME/.node_modules`
2. `$HOME/.node_libraries`
3. `$PREFIX/lib/node`

ただし、これらの仕組みは現在のような高度なモジュール検索アルゴリズムが導入される前に作られたもので、互換性のために残されています。
環境ごとの設定により、アプリケーションの振る舞いが大きく変わってしまう可能性があるので、できるだけグローバルフォルダの仕組みは使わないようにしましょう。

