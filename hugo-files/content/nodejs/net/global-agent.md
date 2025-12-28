---
title: "Node.jsメモ: プロキシ経由の HTTP 通信を行う（global-agent モジュール）"
url: "p/vwu58as/"
date: "2020-08-03"
tags: ["nodejs"]
aliases: [/nodejs/net/global-agent.html]
---

[global-agent モジュール](https://www.npmjs.com/package/global-agent) を使用すると、Node.js プログラム内の HTTP 通信をプロキシ経由で行うように設定できます。
具体的には、Node.js 標準の `http` モジュールによる通信がプロキシ経由になるように設定されます。
サードパーティ製のネットワークモジュールも、ベースは `http` モジュールであることが多いため、`global-agent` でプロキシ設定を行うことで、Node.js アプリ全体の通信をプロキシ経由にすることができます。


global-agent のインストール
----

`global-agent` は次のようにインストールします。
TypeScript を使用する場合は、型定義ファイルも一緒にインストールしてください。

```
$ npm install --save global-agent
$ npm install --save-dev @types/global-agent
```


global-agent によるプロキシ設定
----

### GLOBAL_AGENT_HTTP_PROXY

`global-agent` モジュールが提供する __`bootstrap()`__ 関数を実行すると、__`GLOBAL_AGENT_HTTP_PROXY`__ 環境変数で設定されたプロキシが、それ以降の HTTP 通信で使われるようになります。
環境変数は OS の設定画面から設定しておくこともできますし、次のようにプログラム内で設定することもできます。

```typescript
// Setup HTTP proxy
import {bootstrap} from 'global-agent';
process.env.GLOBAL_AGENT_HTTP_PROXY = 'http://proxy.example.com:10080';
bootstrap();
```

内部的には、`bootstrap()` 実行時に `GLOBAL_AGENT_HTTP_PROXY` 環境変数の値が `global.GLOBAL_AGENT.HTTP_PROXY` という変数にコピーされ、その後の HTTP 通信で参照されるようになります。

### GLOBAL_AGENT_HTTPS_PROXY

__HTTPS__ 通信用のプロキシを別アドレスにしたい場合は、__`GLOBAL_AGENT_HTTPS_PROXY`__ という環境変数を指定します。

```
export GLOBAL_AGENT_HTTPS_PROXY='https://proxy.example.com:10080'
```

この環境変数を設定しない場合は、`GLOBAL_AGENT_HTTP_PROXY` で指定したプロキシが HTTPS 通信においても使用されます。

### GLOBAL_AGENT_NO_PROXY

プロキシ経由でアクセスしたくないアドレスは、__`GLOBAL_AGENT_NO_PROXY`__ 環境変数で指定できます。
複数のアドレスを指定する場合は、カンマで区切って並べます。
ワイルドカードとしてアスタリスク (`*`) を使用できます。

```
export GLOBAL_AGENT_NO_PROXY='*.foo.com,baz.com'
```

