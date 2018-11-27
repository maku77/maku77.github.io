---
title: "EasyLanguage: if-else による条件分岐"
date: "2018-11-26"
description: "EasyLanguage の if-else 分岐処理は、条件分岐後に実行するステートメントが1つだけの場合と、複数の場合で書き方を変える必要があります。Easy といっておきながら若干わかりにくいですが、慣れるしかありません。"
---

分岐後のステートメントが 1 つの場合
----

~~~
If 条件式 then ステートメント;
If 条件式 then ステートメント1 else ステートメント2;
~~~

条件分岐は、この if-then-else の構成が基本となります。
各ステートメントの後ろにセミコロン (`;`) を記述せず、if-then-else 全体の最後にだけセミコロンを記述します。
全体を 1 つの英語文章のように捉えるのであれば、最初の I だけを大文字で記述するのがよいでしょう。

#### 使用例

~~~
If FastAvg crosses over SlowAvg then Buy 500 shares next bar at market;
~~~

ステートメント部分が長い場合は、下記のように複数行に分けて記述することもできます。
この場合も、セミコロンは最後の 1 つだけ記述することに注意してください。

~~~
If 条件式 then
    ステートメント;

If 条件式 then
    ステートメント1
else
    ステートメント2;
~~~

if-else 分岐を複数行に分けて記述する場合は、下記に説明するような `begin` ～ `end` セクションを使用した方が、書き方を統一できて読みやすいかもしれません。


分岐後のステートメントが 2 つ以上の場合
----

~~~
If 条件式 then begin
    ステートメント1;
    ステートメント2;
end;

If 条件式 then begin
    ステートメント1;
    ステートメント2;
end else begin
    ステートメント3;
    ステートメント4;
end;
~~~

条件分岐後に複数のステートメントを記述する場合は、それらを `begin` ～ `end` で囲む必要があります。
パッと見はかなり複雑に見えますが、ステートメントが 1 つだけだったときの書き方の、ステートメントの部分を `begin` ～ `end` セクションに置き換えたのだと考えると分かりやすいと思います。
`begin` ～ `end` セクションの中の各ステートメントの最後はセミコロンで終わる必要があります。

#### 使用例

~~~
If BarType = 2 then begin
    If FastAvg crosses over SlowAvg then Buy 500 shares next bar at market;
    If FastAvg crosses under SlowAvg then Sellshort 500 shares next bar at market;
end;
~~~


else if な条件分岐
----

ここまでを踏まえて、else if な条件分岐を記述する場合は、次のように記述することになります。

~~~
If 条件式1 then begin
    ステートメント1;
    ステートメント2;
end else if 条件式2 then begin
    ステートメント3;
    ステートメント4;
end else begin
    ステートメント5;
    ステートメント6;
end;
~~~

どっひゃ～。分かりにくい。。。


関係演算子
----

if-else の条件式部分では、以下のような関係演算子を使って数値比較を行うことができます。

| 関係演算子 | 説明 |
| ---- | ---- |
| `>` | より大きい |
| `<` | より小さい |
| `=` | 等しい |
| `<>` | 異なる |
| `>=` | より大きいか等しい |
| `<=` | より小さいか等しい |
| `crosses over` (`crosses above`) | 現在の足ではより大きいが、前の足ではより小さいか等しい |
| `crosses under` (`crosses below`) | 現在の足ではより小さいが、前の足ではより大きいか等しい |

#### 使用例

~~~
If Close > Open then ...;
If (High + L) /2 > Close then ...;
If CurrentBar > 1 and Close > HighestFC(High,Length)[1] then Buy on Close;
~~~

