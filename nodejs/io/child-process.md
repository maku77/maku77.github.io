---
title: "外部プログラムを実行してその出力を取得する"
date: "2016-07-11"
---

child_process.exec による外部プログラムの実行
----

Node.js の標準モジュールである [child_process モジュール](http://nodejs.jp/nodejs.org_ja/api/child_process.html#child_process_child_process_exec_command_options_callback) を使用すると、任意のシェルコマンドを実行し、その結果（標準出力や標準エラー出力）を取得することができます。

`ChildProcess` クラスの `exec` メソッドで実行したシェルコマンドの結果は、非同期に呼び出されるコールバック関数のパラメータから取得することができます。
下記のサンプルでは、シェルコマンドとして `echo Hello World` を実行して、その標準出力として得られる `Hello World` を取得しています。

#### sample.js

```javascript
var exec = require('child_process').exec;

// シェル上で実行するコマンド
var COMMAND = 'echo Hello World';

exec(COMMAND, function(error, stdout, stderr) {
  // シェル上でコマンドを実行できなかった場合のエラー処理
  if (error !== null) {
    console.log('exec error: ' + error);
    return;
  }

  // シェル上で実行したコマンドの標準出力が stdout に格納されている
  console.log('stdout: ' + stdout);
});
```

#### 実行結果

```
C:\> node sample.js
stdout: Hello World
```

stdout のバッファがあふれてしまう場合
----

`child_process.exec` によって起動された外部プログラムの出力は stdout バッファに保持されますが、デフォルトの最大バッファサイズ (`maxBuffer`) が 200KB (200 * 1024) に設定されているため、あまり大量の出力を行うプログラムを実行するとバッファあふれのエラーが出てしまうことがあります。

```
stdout maxBuffer exceeded
```

このバッファサイズは、`child_process.exec` の第二引数のオプションパラメータで変更することができます。

#### stdout のバッファサイズを 1MB に拡張

```javascript
exec(COMMAND, { maxBuffer: 1024 * 1024 }, function(error, stdout, stderr) {
  ...
});
```

