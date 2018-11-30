---
title: "Node.js で環境変数を参照する (process.env)"
date: "2012-12-07"
---

Node.js で環境変数を扱いときは、`process.env` オブジェクトを参照します。
`process` モジュールはデフォルトで使用可能になっているので、`require` する必要はありません。


すべての環境変数を取得する
----

`process.env` オブジェクトにどのように環境変数が格納されているかは、下記のようにインタラクティブモードで出力すれば簡単に確認することができます。

```
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

指定した環境変数だけ取得する
----

`process.env.環境変数名` とすれば、任意の環境変数を参照できます。

```
$ node
> console.log(process.env.HOME);
D:\x\home
```

下記は、環境変数 `HTTP_PROXY` の設定の有無に応じて処理を振り分ける例です。

```javascript
if (process.env.HTTP_PROXY) {
  // Proxy 環境で動作している場合
} else {
  // Proxy 環境ではない場合
}
```

### 環境変数が指定されていない場合のデフォルト値

環境変数が設定されていない場合にデフォルト値を設定したいのであれば、下記のように `||` 演算子を使用するのが手っ取り早いです。

```javascript
var port = process.env.MYSERVER_PORT || 50000;
```

環境変数 `MYSERVER_PORT` が設定されていない場合は、ポート番号として 50000 が使用されます。
これは、`undefined` が偽値とみなされることを利用しています。
ただ、このやり方ですと、環境変数 `MYSERVER_PORT` に 0 が指定された場合も偽値としてみなされてしまうので、正確性を求めるのであれば、下記のように実装するのがよいでしょう。

```javascript
var port = process.env.MYSERVER_PORT;
if (typeof port === 'undefined') port = 50000;
```

まぁ、ポート番号としては 0 という値はそもそも不正な値なので、ここまで厳密に `undefined` かどうかの判断をする必要はないと思いますが。

