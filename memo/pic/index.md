---
title: PIC めもめも（PIC アセンブラ）
created: 2002-08-01
---

アセンブラ MPASM (MPLAB に含まれる)
----

- [PIC アセンブラ (PIC16Fxxx) 命令セット](asm/instructions.html)
- [ラベルの定義](asm/label.html)
- [バンクとページの切り替え](asm/bank.html)
  - バンク ･･･ 汎用レジスタ (RAM) の区分け
  - ページ ･･･ プログラムメモリ (ROM) の区分け
- [変数の割り当て](asm/general_register.html)
  - ユーザが変数領域として自由に使用できる汎用レジスタの範囲
  - 汎用レジスタに変数領域を確保する
- [遅延時間の作成](asm/delay.html)
  - 1 サイクル (1 命令) にかかる時間
  - 遅延時間を作るサブルーチン
  - 遅延時間サブルーチン自動生成 Script
- [USART によるシリアル通信](asm/usart.html)
  - PC 側の通信プログラム
  - USART によるシリアル通信
  - サンプルコード
- [A/D 変換を実行する](asm/ad_conversion.html) A/D 変換用レジスタ、変換結果が格納されるレジスタ、A/D 変換手順、A/D 変換サンプルプログラム
- [タイマ 0 を使用する](asm/timer0.html)
  - タイマ 0 による割り込みの仕組み
  - タイマ 0 用のクロック
  - カウント値の決定
  - 割り込み時の処理
  - インターバルタイマーとしてタイマ 0 を使う
  - タイマーの開始
  - タイマ 0 サンプルプログラム
- [タイマ 1 を使用する（ミッドレンジシリーズ以上）](asm/timer1.html)
  - T1CON レジスタの構成
  - タイマ 1 による割り込みの仕組み
  - タイマ 1 用のクロック
  - 割り込み時の処理
  - CCP モジュールのコンペアモードを利用した割り込み
- [CCP (Compare/Capture/PWM)](asm/ccp.html)
  - コンペア・モード
  - キャプチャ・モード
  - パルス幅変調モード (PWM: Pulse Width Modulation)
- [C 言語 → アセンブラ言語](asm/c_to_asm.html)
  - C 言語の構文からアセンブラの構文に直す
- [汎用雑多メモ](asm/misc.html)
  - どの Page にあるか分からないサブルーチンにジャンプする
  - 特定のマスクで 2 つの値を比較
  - W レジスタの値 (0～F) を 7 セグメント LED 用の値に変換する
- サンプルプログラム
  - [RA0 の出力を ON/OFF する](sample/ra0.html)
  - [３軸加速度センサの入力を 200Hz でサンプリング＆シリアル通信](sample/ad_200hz.html)
  - [PIC16F84 用テンプレート](sample/16f84_temp.asm.txt)
  - [PIC16F873 用テンプレート](sample/16f873_temp.asm.txt)


電子回路・部品メモ
----

- [RS-232C (Recommend Standard number 232)](circuit/rs232c.html)
- [LED（Light Emitting Diode: 発光ダイオード）](circuit/led.html)
- [水晶振動子（クリスタル振動子、オシレーター）](circuit/oscillator.html)
- [３端子レギュレータ](circuit/regulator.html)
- [その他、用語メモ、未分類メモ](circuit/misc.html)


統合開発環境 MPLAB
----

- [MPLAB のインストール](mplab/install.html)
  - MPLAB のダウンロード
  - MPLAB のインストール
  - 関連ドキュメントへのリンク
- [MPLAB によるプロジェクト作成](mplab/project.html)
  - MPLAB の設定
  - 新規プロジェクトの作成
  - プログラム作成＆ビルド
- [MPLAB-SIM Software Simulator によるデバッグ](mplab/sim.html)

