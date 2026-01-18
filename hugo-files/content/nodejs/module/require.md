---
title: "Node.jsメモ: Node.js が require() で検索するパスのまとめ"
url: "p/ysmmmia/"
date: "2014-09-22"
tags: ["nodejs"]
aliases: /nodejs/module/require.html
---

`require()` によって Node.js がどのようにロードするモジュールを検索するかは、[Node.js の Modules のドキュメント](https://nodejs.org/api/modules.html#modules_all_together) に詳しく説明されていますが、若干複雑なのでここでまとめておきます。


require の使い分け
----

`require` でモジュールをロードするとき、多くは下記の 3 パターンのロード方法に分類できます。

```js
// コアモジュール、あるいは node_modules にインストールしたパッケージのロード
const crypto = require('crypto');

// ローカルモジュールのロード
const myLocalModule = require('./path/to/myLocalModule');

// JSON ファイルのロード
const jsonData = require('./path/to/data.json');
```

簡単にまとめると、

* 組み込みモジュールや、外部パッケージを使うときは名前そのものを指定する。
* 自分で作成したローカルモジュールや JSON ファイルは `./` で始まるパスで指定する。

と理解しておけばよいでしょう。


require がどのようにモジュールを検索するか？
----

`require` によってどのパスに置かれたファイルがロードされるかは、下記のようなアルゴリズムで決められます。

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

<div class="note">
上記の検索順序から分かるように、モジュール名を指定して <code>require</code> を呼び出した場合は、何よりも先にコアモジュールが検索されます。つまり、<code>express</code> という名前のコアモジュールが提供されるようになったら、<code>npm install</code> でインストールされた <code>express</code> の方は参照されなくなるということですね。格差社会。。。
</div>


### require('./name') としたとき

`require` のパラメータが `'./name'`、`'../name'`、`'/name'` というように、`./` や `/` で始まるパスで指定された場合は、そのファイルが置かれたディレクトリからの相対パス (あるいは絶対パス) で、下記のようにファイルが検索されます。

1. `name` という名前のファイルを探す。
2. `name.js` という名前のファイルを探す。
3. `name.json` という名前のファイルを探す（JSON ファイルとしてロード）。
4. `name.node` という名前のファイルを探す（バイナリ addon として dlopen でロード）。
5. `name/package.json` というファイルがあれば、その `main` フィールドを見て、`name/<mainフィールドの値>` というパスを使用して 1 からやり直し。`main` フィールドの記載がなければ、下記のステップへ続く。
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
5. `/aaa/bbb/mylib/package.json` に `main` フィールドがあれば、`mylib/<mainフィールドの値>` が指定されたものとして同様に検索
6. `/aaa/bbb/mylib/index.js` という JavaScript ファイル
7. `/aaa/bbb/mylib/index.json` という JSON ファイル
8. `/aaa/bbb/mylib/index.node` というバイナリ addon ファイル
9. 各グローバルフォルダ以下から同様に検索

ちなみに、`require` に渡す名前に `./sample.js` のように、拡張子まで含めて指定した場合も、上記のアルゴリズムはそのまま実行されます。
`.js` という拡張子を持つファイルだけが検索されるわけではありません。
つまり、`sample.js` というファイルが見つからなければ、`sample.js.js` や `sample.js.json` のようなファイル名で検索が行われます。


グローバルフォルダからのモジュールロード（非推奨）
----

Node は上記で説明したパスからだけでなく、下記のようなディレクトリからもモジュールを検索します（参考: [Loading from the global folders](https://nodejs.org/api/modules.html#modules_loading_from_the_global_folders)） 。

1. `NODE_PATH` 環境変数に列挙されたディレクトリ（Linux はコロン区切り、Windows はセミコロン区切りで列挙）
1. `$HOME/.node_modules`
2. `$HOME/.node_libraries`
3. `$PREFIX/lib/node`

ただし、これらの仕組みは現在のような高度なモジュール検索アルゴリズムが導入される前に作られたもので、互換性のために残されています。
環境毎の設定の違いにより、アプリケーションの振る舞いが大きく変わってしまう可能性があるので、できるだけグローバルフォルダの仕組みは使わないようにしましょう。


（コラム） require によりロードしたモジュールは指定した名前でキャッシュされる
----

Node.js は、`require` による同一モジュールの読み込みを効率化するため、読み込んだモジュールをキャッシュしています。
主にメモリ効率や速度向上のための仕組みですが、副次的な作用として、モジュールインスタンスが複数のクライアントモジュール間で共有されることに注意してください。
次のサンプルコードの動作を見るとわかりやすいです。

{{< code lang="js" title="mylib.js" >}}
exports.value = 100;
{{< /code >}}

{{< code lang="js" title="stranger.js" >}}
const mylib = require('./mylib');
mylib.value = 200;
{{< /code >}}

{{< code lang="js" title="main.js（エントリポイント）" >}}
const mylib = require('./mylib');
console.log(mylib.value);

const stranger = require('./stranger');
console.log(mylib.value);
{{< /code >}}

{{< code title="実行結果" >}}
$ node main.js
100
200  ★mylib.value の値が書き換わっている
{{< /code >}}

`mylib` モジュールは、`value` 変数を公開しています。
`mylib.value` の値は最初 100 ですが、`stranger.js` の中で間接的に 200 に書き換えられています。
`stranger.js` から読み込んだ `mylib` モジュールインスタンスは、`main.js` が参照しているインスタンスと同じものなので、`stranger.js` で `mylib` インスタンスの内容を書き換えると、その影響が `main.js` 側の `mylib` インスタンスにも及ぶことになります（`stranger.js` をロードするだけで `mylib.value` の値が 100 から 200 に変わってしまう）。

このキャッシュの仕組みを、モジュール間の値のやりとりに利用できそうだと思うかもしれませんが、そのような用途で使うのは避けるべきです。
なぜなら、**同一だと思われるモジュールでも別のキャッシュインスタンスが生成されることがある**からです。
Node.js は、キャッシュされたモジュールの識別子として、`require` のパラメータで指定された名前を使用しています。
ロードするモジュールの相対的なパスが変わったり、ファイル名の指定方法が変わるだけで別のインスタンスが生成される可能性があります。

例えば、Windows のファイルシステムは大文字と小文字を区別しませんが、Node.js がモジュールインスタンスをキャッシュするときは、大文字と小文字が区別された名前を識別子として使用します。
Windows 環境において、`mylib.js` と `MYLIB.js` は同一のファイルを示しますが、Node.js で `require('./mylib')` とした場合と、`require('./MYLIB')` とした場合は別々のモジュールインスタンスとしてキャッシュされることになります。

下記の例を見てください。
前述の `main.js` をちょっとだけ書き換えて、`require` パラメータで指定するモジュールのパスを大文字に変更しています。

{{< code lang="js" title="main.js" >}}
const mylib = require('./MYLIB');  // 大文字で指定
console.log(mylib.value);

const stranger = require('./stranger');
console.log(mylib.value);
{{< /code >}}

{{< code title="実行結果" >}}
$ node main.js
100
100  ★stranger.js の中の変更は main.js に影響しない
{{< /code >}}

このようにすると、`main.js` の中の `mylib` インスタンスと、`stranger.js` の中の `mylib` インスタンスは別々のインスタンスとして扱われるため、`stranger.js` の中での `mylib` インスタンスの変更は `main.js` 側に影響しません。

