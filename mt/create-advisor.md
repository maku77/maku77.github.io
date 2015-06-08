---
title: Expert Advisor を作成する
created: 2015-01-29
---

MetaTrader を使って自動売買を行うには、__EA (Expert Advisor)__ を作成する必要があります。
ここでは最初のステップとして、価格が変わる (Tick) ごとに Ask（買値）、Bid（売値）を表示する EA を作成してみます。　
Meta Editor 上で `Control + N` を押して、Expert Advisor を新規作成します。

#### HelloEA.mq4
```mql
void OnTick() {
    Print("Ask=", Ask, " Bid=", Bid);
}
```

EA をあるチャートにセットすると、そのチャート上でティックごとに `OnTick()` が呼び出されます。
MQL4 では、その時点での価格を Ask、Bid というグローバル変数から取得することができます。

Expert Advisor の実行
====

作成した Expert Advisor をあるチャート上で実行するには、以下のようにします。

### 1) Meta Trader の全体設定で Live Trading を有効にする

1. メニューから Tools => Options を選択
2. Expert Advisor のタブの Allow automated trading にチェックを入れる
  （あるいはツールバー上の AutoTrading ボタンを押して有効にできます）

### 2) 任意のチャート上に EA をセットする

1. Navigator ビューの Expert Advisor から対象の EA をチャートにドラッグ＆ドロップ
2. ダイアログが表示されるので、Allow live trading にチェックを入れて OK

これで、EA が動き始めます。
チャートの右上に EA 名とにっこりマークが表示されていれば、ちゃんと動作しています。

![create-advisor.png](img/create-advisor.png)
