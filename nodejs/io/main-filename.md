---
title: "エントリポイントとなった JavaScript ファイルのパスやディレクトリ名を取得する (require.main.filename)"
date: "2019-03-27"
---

require.main.filename の基本
----

**`require.main.filename`** を参照すると、プログラムのエントリポイントとなった JavaScript ファイルの絶対パスを取得することができます。

下記のサンプルでは、`main.js` から `mylib/foo.js` を呼び出しており、呼び出される側で `require.main.filename` の値を出力しています。
参考のため、自分自身のファイル名を示す `__filename` の値も出力しています。

#### main.js

```js
require('./mylib/foo.js');
```

#### mylib/foo.js

```js
const path = require('path');

console.log('エントリ: ' + require.main.filename);
console.log('自分自身: ' + __filename);
```

`main.js` ファイルをエントリポイントとして実行すると、下記のような結果になります。

```
$ node main.js
エントリ: /User/maku/main.js
自分自身: /User/maku/mylib/foo.js
```

`require.main.filename` の値は、正しく `main.js` のパスになっていることがわかります。
一方、`mylib/foo.js` をエントリポイントとして実行すると、下記のように結果が変わります。


```
$ node mylib/foo.js
エントリ: /User/maku/mylib/foo.js
自分自身: /User/maku/mylib/foo.js
```


応用例: アプリのルートディレクトリにある設定ファイルを読み込む
----

この仕組みを利用すると、どのディレクトリ階層に置かれたライブラリの中からでも、エントリポイントとなる JavaScript が置いてあるディレクトリパスを取得することができます。

例えば、アプリのルートディレクトリに設定ファイル (`app.config`) を置くという仕様にした場合、下記のようにして `app.config` のフルパスを構築することができます（エントリポイントの `index.js` が置かれたディレクトリをルートと呼んでいます）。

#### mylib/aaa/bbb/hello.js

```js
const path = require('path');
const configPath = path.join(path.dirname(require.main.filename), 'app.config');
```

