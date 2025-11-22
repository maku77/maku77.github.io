---
title: "Node.jsメモ: 環境変数を参照する (process.env)"
url: "p/e44uun8/"
date: "2012-12-07"
tags: ["nodejs"]
lastmod: "2021-06-20"
aliases: /nodejs/env/environment-variable.html
---

Node.js で環境変数を扱いたいときは、**`process.env`** オブジェクトを参照します。
`process` モジュールはデフォルトで参照できるようになっているので、`require` でモジュールを読み込む必要はありません。


指定した環境変数を取得する
----

`process.env.環境変数名` とすれば、任意の環境変数を参照できます。

```console
$ node
> console.log(process.env.HOME);
D:\x\home
```

下記は、環境変数 `HTTP_PROXY` の設定の有無に応じて処理を振り分ける例です。

```javascript
if (process.env.HTTP_PROXY != undefined) {
  // Proxy 環境で動作している場合
} else {
  // Proxy 環境ではない場合
}
```

### 環境変数が指定されていない場合のデフォルト値

環境変数が設定されていない場合（`undefined` の場合）にデフォルト値を設定したいのであれば、下記のように `??`（Null 合体演算子）を使います。

```javascript
const port = process.env.MYSERVER_PORT ?? 50000;
```

`??` 演算子は ES2020 で導入された [Nullish Coalescing](https://maku.blog/p/5oyaju5/) という仕組みで、ある変数の値を参照しつつ、その値が `undefined` あるいは `null` の場合に、右側に指定した値を代わりに返してくれます。
上の例の場合は、環境変数 `MYSERVER_PORT` が設定されていないときに、デフォルトのポート番号として 50000 が使用されます。

`??` 演算子が導入される前は、次のように `undefined` 判定を行う必要がありました。

```javascript
let port = process.env.MYSERVER_PORT;
if (typeof port === 'undefined') port = 50000;
// 簡易的に if (port != undefined) でも OK
```

これはちょっと冗長なので、次のように `||` 演算子を使う方法もよく使われていました。

```javascript
const port = process.env.MYSERVER_PORT || 50000;
```

ただ、このやり方ですと、環境変数 `MYSERVER_PORT` に 0 が指定された場合も偽値としてみなされてしまうという問題がありました。
ES2020 以降の環境では、Null 合体演算子 (`??`) を使えば万事解決です。


すべての環境変数を列挙する
----

`process.env` オブジェクトにどのように環境変数が格納されているかは、下記のようにインタラクティブモードで出力すれば簡単に確認することができます。

```console
$ node
> process.env
{
  ...
  OS: 'Windows_NT',
  Path: '...',
  PATHEXT: '.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC;.PY;.PYW',
  PROCESSOR_ARCHITECTURE: 'AMD64',
  PROCESSOR_IDENTIFIER: 'Intel64 Family 6 Model 60 Stepping 3, GenuineIntel',
  PROCESSOR_LEVEL: '6',
  PROCESSOR_REVISION: '3c03',
  ...
}
```

### キーでソートして表示

Node.js 環境の全ての環境変数を、変数名でソートして列挙したいときは次のようにします。

```javascript
Object.keys(process.env).sort().forEach(key => {
  console.log(`${key}: ${process.env[key]}`)
});
```

### 特定のプレフィックスで始まる環境変数を列挙

環境変数名の配列を `filter` 関数でフィルタしてやれば、注目している環境変数だけを列挙できます。
次の例では、`NODE` で始まる環境変数のみを列挙しています。

```javascript
const keys = Object.keys(process.env).filter((v) => v.startsWith('NODE'));
keys.sort().forEach(key => {
  console.log(`${key}: ${process.env[key]}`)
});
```


関連記事
----

- [環境変数の代わりに .env ファイルを使用する (dotenv)](/p/6kruhwy/)

