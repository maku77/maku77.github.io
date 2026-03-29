---
title: "TradeStationメモ: Print 関数によるデバッグ"
url: "p/5jciift/"
date: "2017-09-17"
tags: ["tradestation"]
description: "Print 関数を使用すると、プログラムの実行中に任意のテキストを印刷ログウィンドウに表示することができます。"
aliases: /tradestation/io/print.html
---

{{< image src="img-001.png" >}}

Print 関数と印刷ログ
----

`Print` 関数（ステートメント）は、インジケーターや自動売買ストラテジーの動作を確認するのに非常に便利です。
`Print` 関数による出力結果は**印刷ログウィンドウ**に表示されるため、まずこのウィンドウを表示しておく必要があります。

1. メニューから「表示(V)」→「EasyLanguage印刷ログ(E)」を選択<br>（あるいは **Ctrl+Shift+E**）

下記は、インジケーターの中での使用例です。

{{< code title="例: 現在処理中のバーのインデックス番号（1 始まり）を表示" >}}
Print("BarNumber=", BarNumber:0:0);
{{< /code >}}

カンマで区切って渡された複数の値は、出力時に連結して表示されます。
`BarNumber:0:0` の `0:0` は、整数部の最低桁数と小数点以下の桁数を指定する書式です。これにより `BarNumber` が整数値として表示されます。
インジケーターはバーごとに呼び出されるため、上記をチャートに適用すると下記のように繰り返し出力されます。

```
BarNumber=1
BarNumber=2
BarNumber=3
...
BarNumber=2447
BarNumber=2448
BarNumber=2449
```

一度だけ表示する
----

デバッグ用に、インジケーター中で一度だけ実行したい処理は `Once begin ～ end` で囲みます。

```
once begin
    Print("BarNumber=", BarNumber:0:0);
end;
```

このブロックで囲まれた部分は、チャートにインジケーターを適用したときの最初のバー処理時にだけ呼び出されます。

{{< code title="実行結果" >}}
BarNumber=1
{{< /code >}}

出力先をファイルに切り替える
----

`Print` 関数の最初のパラメータに `File` ステートメントを指定すると、出力先を印刷ログウィンドウからテキストファイルに切り替えられます。
大量のログを出力してじっくり分析する際に便利です。

{{< code title="例: テキストファイルに各バーの終値を出力する" >}}
Print(File("c:\data\mydata.txt"), Date, Time, Close:0:0);
{{< /code >}}

ただし、出力先の `c:\data` ディレクトリはあらかじめ作成しておく必要があります。
ディレクトリが存在しない場合は、下記のような例外が発生します。

```
elsystem.io.WriteFileException: Error creating file: c:\data\mydata.txt
```

相対パスでファイル名を指定すると、トレードステーションのインストールディレクトリ内の `Program` ディレクトリにファイルが出力されます。

{{< code title="よくない例" >}}
Print(File("mydata.txt"), Date, Time, Close:0:0);
{{< /code >}}

この `Program` ディレクトリにはトレードステーション本体用のファイルが多数格納されているため、ここへの出力は避けるべきです。
`Print` 関数の出力先をファイルに切り替えるときは、フルパスで指定するようにしましょう。
