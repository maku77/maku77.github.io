---
title: カスタムインジケータで使用できるイベント
created: 2015-06-10
---

カスタムインジケータのプログラム内で、決まったシグネチャで関数を定義しておくと、特定のイベント発生時に自動的にその関数を呼び出してくれるようになります。

[int OnInit()](https://www.mql5.com/en/docs/basis/function/events#oninit)
====
* インジケータが最初にチャートにアタッチされたとき
* チャートのシンボル（USDJPY など）やタイムフレーム（時間足）が変更されたとき
* MetaEditor 上でインジケータが再コンパイルされたとき
* インジケータの入力パラメータがダイアログから変更されたとき

[void DeInit(const int reason)](https://www.mql5.com/en/docs/basis/function/events#ondeinit)
====
* チャートからインジケータをデタッチしたとき (REASON_REMOVE (1))
* MetaEditor 上でインジケータが再コンパイルされたとき (REASON_RECOMPILE (2))
* チャートのシンボル（USDJPY など）やタイムフレーム（時間足）が変更されたとき (REASON_CHARTCHANGE (3))
* チャートを閉じたとき (REASON_CHARTCLOSE (4))
* インジケータの入力パラメータがダイアログから変更されたとき (REASON_PARAMETERS (5))
* OnInit() が 0 以外の値を返して失敗したとき (REASON_INITFAILED (8))
* MetaTrader（ターミナル）を閉じたとき (REASON_CLOSE (9))

パラメータの `reason` にどのような値が渡されるかは、下記にまとめられています。

* [Uninitialization Reason Codes](https://www.mql5.com/en/docs/constants/namedconstants/uninit)

パラメータの `const` を省略して `int reason` と記述したりすると、ちゃんと呼び出されないので注意してください。

[void OnCalculate(...)](https://www.mql5.com/en/docs/basis/function/events#oncalculate)
====

`OnCalculate()` は Tick ごと（最新の価格が変化するごと）に呼び出されます。
インジケータを最初にチャートにアタッチしたときにも呼び出されます。

