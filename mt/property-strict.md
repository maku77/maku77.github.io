---
title: "#property strict について"
date: "2015-06-09"
---

MetaTrader Build 765 以降は、EA 上で `#property strict` を指定していると、`OnInit()` が `INIT_SUCCEEDED` 以外を返した場合に、チャートから自動的に EA が取り除かれるようになりました。

* `#property strict` ありで OnInit() 失敗 → EA がチャートから取り除かれる
* `#property strict` なしで OnInit() 失敗 → EA はチャートに関連付けられたまま

