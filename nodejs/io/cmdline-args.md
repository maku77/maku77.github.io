---
title: "コマンドライン引数（パラメータ）を取得する (process.argv)"
date: "2019-02-28"
---

process.argv によるコマンドライン引数の取得
----

Node.js プログラムから、コマンドライン引数を取得するには、**`process.argv`** プロパティを参照します。
`process` モジュールはデフォルトで使用できるようになっているので、`require` によるモジュールの読み込みは必要ありません。

`process.argv` は配列になっており、`argv[2]` 以降にコマンドライン引数が格納されています。
下記のサンプルコードは、`process.argv` 配列の内容を列挙します。

#### args.js

```js
for (let i = 0; i < process.argv.length; ++i) {
  console.log(i + ': ' + process.argv[i]);
}
```

#### 実行例

```
$ node args.js AAA BBB CCC
0: C:\app\nodejs\node.exe
1: D:\sandbox\node\test.js
2: AAA
3: BBB
4: CCC
```

ちなみに、`argv[0]` には `node` 実行ファイル (node.exe) のフルパス、`argv[1]` にはスクリプトファイル (.js) のフルパスが格納されています。

コマンドライン引数部分だけを配列の形で取得したいのであれば、下記のように配列をスライスしてしまうのがよいかもしれません。

``` js
const args = process.argv.slice(2);
console.log('パラメータの数: ' + args.length);
console.log('パラメータの値: ' + args);
```

#### 実行例

```
$ node args.js AAA BBB CCC
パラメータの数: 3
パラメータの値: AAA,BBB,CCC
```

コマンドライン引数の数を確認して Usage を表示する
----

下記は、ユーザ名をコマンドライン引数で受け取り、挨拶を表示するだけの簡単なプログラムです。
コマンドライン引数が指定されなかった場合に、Usage（使用方法）を表示してプログラムを終了するようにしています。
Usage で出力するスクリプトファイル名には、`process.argv[1]` で取得できる値を使用しています。

#### greet.js

```js
function showUsageAndExit() {
  const path = require('path');
  const basename = path.basename(process.argv[1]);
  console.error(`Usage: node ${basename} <YourName>`);
  process.exit(1);
}

// コマンドラインパラメータの数を確認
const args = process.argv.slice(2);
if (args.length < 1) {
  showUsageAndExit();
}

// コマンドパラメータを使用したメイン処理
const name = args[0];
console.log(`Hello, ${name}!`);
```

#### 実行例

```
$ node greet.js
Usage: node greet.js <YourName>

$ node greet.js Maku
Hello, Maku!
```

