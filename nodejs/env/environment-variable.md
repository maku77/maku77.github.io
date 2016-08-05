---
title: Node.js で環境変数を参照する
created: 2012-12-07
---

環境変数は、`process.env` オブジェクトに格納されています。

#### すべての環境変数を取得する

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

#### 指定した環境変数だけ取得する

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

