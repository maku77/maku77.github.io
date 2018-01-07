---
title: 口座残高／証拠金の情報を取得する
date: "2016-03-25"
---


通貨を取得する
----

口座の入出金に使用される通貨（デポジット通貨）は、下記のようにして取得することができます。

```mql
// 口座の通貨（"JPY" など）
string currency = AccountInfoString(ACCOUNT_CURRENCY);
```


口座残高、純資産の情報を取得する
----

口座の証拠金情報は、`AccountInfoDouble` を使用して取得することができます。
通貨が日本円 (JPY) の場合は小数点数以下の情報は必要ありませんが、いろいろな通貨を扱えるようにするために戻り値は `double` 型になっています。


```mql
// 証拠金残高（ポジションを取っても変化せず、決済した時点で増減する）
double balance = AccountInfoDouble(ACCOUNT_BALANCE);

// 評価損益（ポジションを閉じると証拠金残高に反映される）
double profit = AccountInfoDouble(ACCOUNT_PROFIT);

// 純資産（証拠金残高＋評価損益）
double equity = AccountInfoDouble(ACCOUNT_EQUITY);

Alert(StringFormat(
    "Balance: %.0f, Profit: %.0f, Equity: %.0f",
    balance, profit, equity));
```

純資産は評価損益を加味したものですので、`balance + profit = equity` が成り立っているはずです。


必要証拠金や有効証拠金の情報を取得する
----

現在のポジションのために使用している**必要証拠金**や、さらにどれだけのエントリができるかを表す**有効証拠金**の情報を取得するには、下記のようにします。

```mql
// 純資産
double equity = AccountInfoDouble(ACCOUNT_EQUITY);

// 必要証拠金（ポジションや予約注文のために使用している証拠金）
double margin = AccountInfoDouble(ACCOUNT_MARGIN);

// 有効証拠金（使用可能な証拠金の残り金額）
double freeMargin = AccountInfoDouble(ACCOUNT_MARGIN_FREE);

Alert(StringFormat(
    "Equity: %.0f, Margin: %.0f, FreeMargin: %.0f",
    equity, margin, freeMargin));
```

有効証拠金は、純資産から必要証拠金を引いた残りの金額なので、上記の結果は `equity = margin + freeMargin` となっているはずです。


証拠金維持率を取得する
----

証拠金維持率 (%) に関する情報を取得するには下記のようにします。
マージンコールがかかる維持率の情報を取得することもできます。

```mql
// 証拠金維持率 (%)（純資産／必要証拠金）
double marginLevel = AccountInfoDouble(ACCOUNT_MARGIN_LEVEL);

// マージンコールがかかる証拠金維持率 (Margin call level: %)
double marginSoCall = AccountInfoDouble(ACCOUNT_MARGIN_SO_CALL);

// 強制ロスカットがかかる証拠金維持率 (Stop out level: %)
double marginSoSo = AccountInfoDouble(ACCOUNT_MARGIN_SO_SO);

Alert(StringFormat(
    "MarginLevel: %.2f%%, MarginSoCall: %.2f%%, MarginSoSo: %.2f%%",
    marginLevel, marginSoCall, marginSoSo));
```

ちなみに、So というのは Stop Out の略。

証拠金維持率は、現在の必要証拠金 (margin) に対する純資産 (equity) の割合ですから、下記のように計算することもできます。

```mql
double equity = AccountInfoDouble(ACCOUNT_EQUITY);
double margin = AccountInfoDouble(ACCOUNT_MARGIN);
Alert(StringFormat("MarginLevel: %.2f%%", equity * 100 / margin));
```


レバレッジ情報を取得する
----

現在の口座のレバレッジ設定を取得するには下記のようにします。
例えば、レバレッジ 25 倍の口座であれば、25 という値が取得できます。

```mql
long leverage = AccountInfoInteger(ACCOUNT_LEVERAGE);
Alert(StringFormat("Levevrage: %d", leverage));
```

