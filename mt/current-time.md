---
title: 現在時刻を取得する
date: "2015-10-25"
---


ローカル PC 上の現在時刻
====
ホスト PC 上の現在時刻は **TimeLocal()** 関数を使って取得できます（1970-01-01 00:00:00 からの経過秒数）。
取得した **datetime** 値を **TimeToString()** 関数に渡すと、文字列表現の時刻に変換することができます。

```mql
datetime now = TimeLocal();
string s1 = TimeToString(now, TIME_DATE);     // 2015.10.23
string s2 = TimeToString(now, TIME_MINUTES);  // 21:00
string s3 = TimeToString(now, TIME_SECONDS);  // 21:00:00
string s4 = TimeToString(now, TIME_DATE | TIME_MINUTES);  // 2015.10.23 21:00
string s5 = TimeToString(now, TIME_DATE | TIME_SECONDS);  // 2015.10.23 21:00:00
```

サーバ上の現在時刻
====
MetaTrader を実行しているホスト PC 上の現在時刻ではなく、ブローカーのサーバーから最後に取得した現在時刻 (time of the last quote receipt) を取得するには、**TimeCurrent()** 関数を使用します。
使い方は `TimeLocal` 関数と同様です。

```mql
datetime serverTime = TimeCurrent();
```

参考
====
- [MQL5 - TimeLocal()](https://www.mql5.com/en/docs/dateandtime/timelocal)
- [MQL5 - TimeCurrent()](https://www.mql5.com/en/docs/dateandtime/timecurrent)
- [MQL5 - TimeToString()](https://www.mql5.com/en/docs/convert/timetostring)

