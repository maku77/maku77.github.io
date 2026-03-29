---
title: "TradeStationメモ: if-else による条件分岐"
url: "p/2dbn8q9/"
date: "2018-11-26"
tags: ["tradestation"]
description: "EasyLanguage の if-else 分岐は、条件分岐後に実行するステートメントが 1 つだけの場合と複数の場合で書き方が異なります。"
aliases: /tradestation/syntax/if-else.html
---

分岐後のステートメントが 1 つの場合
----

```
if 条件式 then ステートメント;
if 条件式 then ステートメント1 else ステートメント2;
```

条件分岐は、この if-then-else の構成が基本となります。
各ステートメントの後ろにセミコロン (`;`) は記述せず、if-then-else 全体の最後にのみセミコロンを記述します。
全体を 1 つの英語文章として捉えると理解しやすく、先頭の `if` だけ大文字で記述するのが慣例です。

{{< code title="使用例" >}}
if FastAvg crosses over SlowAvg then Buy 500 shares next bar at market;
{{< /code >}}

ステートメントが長い場合は、下記のように複数行に分けて記述することもできます。
この場合もセミコロンは最後の 1 つだけです。

```
if 条件式 then
    ステートメント;

if 条件式 then
    ステートメント1
else
    ステートメント2;
```

複数行で記述する場合は、後述の `begin` ～ `end` セクションを使う方が書き方を統一できて読みやすくなります。


分岐後のステートメントが 2 つ以上の場合
----

```
if 条件式 then begin
    ステートメント1;
    ステートメント2;
end;

if 条件式 then begin
    ステートメント1;
    ステートメント2;
end else begin
    ステートメント3;
    ステートメント4;
end;
```

条件分岐後に複数のステートメントを記述する場合は、`begin` ～ `end` で囲む必要があります。
一見複雑に見えますが、1 つのステートメントだったときの「ステートメント」の部分を `begin` ～ `end` セクションに置き換えたと考えると理解しやすいです。
`begin` ～ `end` セクション内の各ステートメントの末尾にはセミコロンが必要です。

{{< code title="使用例" >}}
if BarType = 2 then begin
    if FastAvg crosses over SlowAvg then Buy 500 shares next bar at market;
    if FastAvg crosses under SlowAvg then Sellshort 500 shares next bar at market;
end;
{{< /code >}}


else if な条件分岐
----

else if を使った条件分岐は次のように記述します。

```
if 条件式1 then begin
    ステートメント1;
    ステートメント2;
end else if 条件式2 then begin
    ステートメント3;
    ステートメント4;
end else begin
    ステートメント5;
    ステートメント6;
end;
```


関係演算子
----

if-else の条件式では、以下の関係演算子を使って数値を比較できます。

| 関係演算子 | 説明 |
| ---- | ---- |
| `>` | より大きい |
| `<` | より小さい |
| `=` | 等しい |
| `<>` | 異なる |
| `>=` | 以上 |
| `<=` | 以下 |
| `crosses over` (`crosses above`) | 現在の足ではより大きいが、前の足ではより小さいか等しい（上抜け） |
| `crosses under` (`crosses below`) | 現在の足ではより小さいが、前の足ではより大きいか等しい（下抜け） |

{{< code title="使用例" >}}
if Close > Open then ...;
if (High + L) / 2 > Close then ...;
if CurrentBar > 1 and Close > HighestFC(High, Length)[1] then Buy on Close;
{{< /code >}}
