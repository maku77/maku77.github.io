---
title: MetaTrader メモ
layout: category-index
---

インストール
====
* [MetaTrader を Mac OSX にインストールする（PlayOnMac を使う方法）](install-play-on-mac.html)
* [MetaTrader を Mac OSX にインストールする（XM の Mac 用インストーラを使う方法）](install-xm.html)


MQL
====

MT4 Build 574 からは、MQL5 と MQL4 の構文が統一されました。このサイトでは、この新しい構文を使ったコードを示します。

はじめに
----
* [組み込み関数の実行に失敗したときにエラー情報を表示する](basic/show-error.html)

カスタムインジケータ (Custom Indicator)
----
* [カスタムインジケータを作成する](create-indicator.html)
* [カスタムインジケータの OnCalculate 関数を最適化する](optimize-on-calculate.html)
* [カスタムインジケータで使用できるイベント](event-for-indicator.html)
* [カスタムインジケータに水平線を表示する](indicator-level-line.html)

### カスタムインジケータのサンプル
* 単純移動平均線 (SMA: Simple Moving Average)
  * [移動平均線のカスタムインジケータを作成する (1)](moving-average1.html)
  * [移動平均線のカスタムインジケータを作成する (2) 移動平均計算の最適化](moving-average2.html)
  * [移動平均線のカスタムインジケータを作成する (3) 3 本の移動平均線を表示する](moving-average3.html)
* [加重移動平均線 (WMA: Weighted Moving Average) のカスタムインジケータを作成する](indicator-wma.html)
* [モメンタム (Momentum) のインジケータを作成する](indicator-momentum.html)

エキスパートアドバイザ (EA: Expert Advisor)
----
* [Expert Advisor を作成する](create-advisor.html)

チャートの情報
----
* [現在のチャートの通貨ペア（シンボル）を取得する](chartinfo/get-current-symbol.html)
* [現在のチャートのタイムフレーム（H1 など）を取得する](chartinfo/get-current-timeframe.html)
* [チャートのローソク足の数を取得する](chartinfo/get-bar-count.html)

チャートの操作
----
* [任意の通貨ペア、時間足のチャートを開く](chart-open.html)
* [MetaTrader (MQL) における色の表現方法](colors.html)
* [チャートの描画スタイルを設定する](drawing-style.html)
* [表示中のすべてのチャートに対して処理する](for-each-chart.html)

描画オブジェクトの表示
----
* [チャート上にラベルを表示する](draw/label.html)
* [チャート上に矩形を表示する](draw/rect.html)
* [サンプル: 通貨ペアとタイムフレームを大きく表示する](draw/large-symbol.html)

デバッグ
----
* [画面上にテキスト出力する (1) Alert, Comment, Print の基本](print-text1.html)
* [画面上にテキスト出力する (2) Comment 関数を使いやすくする](print-text2.html)
* [メッセージボックスを表示する](messagebox.html)
* [音声を再生する](play-sound.html)
* [デバッグ用のマクロを定義する](debug-macro.html)

時刻
----
* [現在時刻を取得する](current-time.html)

プリプロセッサ
----
* [#property strict について](property-strict.html)
* [デバッグモードでコンパイルされているか調べる](check-debug-mode.html)
* [MQL4 と MQL5 のどちらでコンパイルされているか調べる](check-mql4-or-mql5.html)


雑多
----
* [スクリプトを実行する前に確認画面を表示する](confirmation.html)
* [MetaTrader のターミナル情報を取得する](terminal-info.html)
* [スプレッドを表示する](show-spread.html)
* [関数を実行しているのがスクリプトか、EA なのか、インジケータなのかを判別する](program-type.html)



リンク
====
* [カスタム指標（インジケータ）をダウンロードできる Web サイト](link-indicator.html)
