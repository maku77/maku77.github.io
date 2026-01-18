---
title: "Node.jsメモ: 外部プログラムを実行してその出力を取得する (child_process)"
url: "p/8kgdvvd/"
date: "2016-07-11"
tags: ["nodejs"]
aliases: /nodejs/io/child-process.html
---

child_process.exec による外部プログラムの実行
----

Node.js の標準モジュールである [`child_process` モジュール](https://nodejs.org/api/child_process.html) を使用すると、任意のシェルコマンドを実行し、その結果（標準出力や標準エラー出力）を取得することができます。
下記のサンプルでは、シェルコマンドとして `echo Hello World` を実行して、その出力である `Hello World` というテキストを取得しています。

{{< code lang="js" title="sample.js" >}}
import { exec } from 'node:child_process';

// シェル上で実行するコマンド
const COMMAND = 'echo Hello World';

exec(COMMAND, (error, stdout, stderr) => {
  // シェル上でコマンドを実行できなかった場合のエラー処理
  if (error !== null) {
    console.log(`exec error: ${error}`);
    return;
  }

  // シェル上で実行したコマンドの標準出力が stdout に格納されている
  console.log(`stdout: ${stdout}`);
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
C:\> node sample.js
stdout: Hello World
{{< /code >}}


stdout のバッファがあふれてしまう場合
----

`child_process.exec()` によって起動された外部プログラムの出力は stdout バッファに保持されますが、デフォルトの最大バッファサイズ (`maxBuffer`) が 200KB (200 * 1024) に設定されているため、あまり大量の出力を行うプログラムを実行するとバッファあふれのエラーが出てしまうことがあります。

```
stdout maxBuffer exceeded
```

このバッファサイズは、`child_process.exec` の第 2 引数のオプションパラメータで変更することができます。

{{< code lang="js" title="stdout のバッファサイズを 1MB に拡張" >}}
exec(COMMAND, { maxBuffer: 1024 * 1024 }, (error, stdout, stderr) => {
  // ...
});
{{< /code >}}

