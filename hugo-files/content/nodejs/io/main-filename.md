---
title: "Node.jsメモ: エントリポイントとなった JavaScript ファイルのパスやディレクトリ名を取得する (require.main.filename)"
url: "p/jm48fwe/"
date: "2019-03-27"
tags: ["nodejs"]
aliases: /nodejs/io/main-filename.html
---

require.main.filename の基本
----

**`require.main.filename`** を参照すると、**プログラムのエントリポイントとなった JavaScript ファイルの絶対パス**を取得することができます。

下記のサンプルでは、`main.js` から `mylib/foo.js` を呼び出しており、呼び出される側で `require.main.filename` の値を出力しています。
参考のため、自分自身のファイル名を示す `__filename` の値も出力しています。

{{< code lang="javascript" title="main.js" >}}
import './mylib/foo.js';
{{< /code >}}

{{< code lang="javascript" title="mylib/foo.js" >}}
import path from 'node:path';

console.log(`エントリ: ${require.main.filename}`);
console.log(`自分自身: ${__filename}`);
{{< /code >}}

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

この仕組みを利用すると、**どのディレクトリ階層に置かれたライブラリの中からでも、エントリポイントとなる JavaScript が置いてあるディレクトリパスを取得することができます**。

例えば、アプリのルートディレクトリに設定ファイル (`app.config`) を置くという仕様にした場合、下記のようにして `app.config` のフルパスを構築することができます（エントリポイントの `index.js` が置かれたディレクトリをルートと呼んでいます）。

{{< code lang="javascript" title="mylib/aaa/bbb/hello.js" >}}
import path from 'node:path';

const configPath = path.join(path.dirname(require.main.filename), 'app.config');
{{< /code >}}

{{% note title="Azure 上で実行する場合の注意" %}}
Microsoft Azure 上の Node.js 実行環境でアプリケーションのホストを行う場合は、iisnode という特殊な環境で実行されるため、**`require.main.filename` の値が想定通りにならない** ケースがあるようです。
そのため、上記のようにアプリケーションの設定ファイルのパスを構築すると、「設定ファイルが見つかりません」というエラーになる可能性があります。
Azure 上で実行することを想定している場合は、現状は階層の深さを意識して `path.join(__dirname, '../../../app.config')` のようにパス構築するか、メインモジュールから `__dirname` の値を渡すようにした方がよさそうです。
{{% /note %}}


応用例: 自分自身がエントリポイントとなって実行されたか判別する
----

エントリポイントとなった JavaScript ファイルのパスは `require.main.filename` で参照できます。
また、実行中の JavaScript ファイルのパスは `__filename` で参照できます。
つまり、この 2 つが一致するかを調べることで、**自分自身がエントリポイントとして起動されたかどうかを調べることができます**。

```js
if (require.main.filename === __filename) {
  // 自分自身がエントリポイントとして実行された
}
```

このあたりの詳しい話は、下記の記事を参考にしてください。

- [モジュール自身のコードにテストコードを記述する (`require.main`)](/p/3b52x28/)

