---
title: Tera Term のマクロで特定の文字列を検出して処理を実行する
date: "2011-04-19"
---

Tera Term のマクロで、`waitln` 命令を使用すると、ターミナルに特定の文字列が出力されたことを検出することができます。
例えば、シリアル接続したデバイスなどからのメッセージを監視して、あるパターンに一致するメッセージを受信したときに任意の処理を行うことができます。

下記のサンプルマクロでは、シリアル接続された端末から "system booted" という文字列を受信したときに、"Hello" というメッセージを表示し、"mycommand &" というコマンドを実行しています。


#### auto_hello.ttl

```
do while 1
    timeout = 0  ;Timeout never occurs
    waitln 'system booted'
    pause 1

    dispstr #$0A #$0A
    dispstr 'Hello'#$0A
    dispstr #$0A #$0A

    sendln 'mycommand &'
loop
```

ところどころ出てくる `#$0A` というのは、改行することを表しています。

