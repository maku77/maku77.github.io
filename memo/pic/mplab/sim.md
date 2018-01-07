---
title: "MPLAB-SIM Software Simulator によるデバッグ ─ PIC めもめも"
date: "2002-08-01"
---

MPLAB-SIM Software Simulator を使うと、ブレークポイントやトレース機能などを用いてプログラムのデバッグを行なうことができます。


Absolute List の表示
----

1. 「Window」 → 「Absolute Listing」

記述したアセンブラソースと、アセンブル後の機械語を照らし合わせて表示することができます。
このコードは、デバッグを進めていくうちに次のように色が付いて作業の目安になるので、デバッグ中はずっと表示しておくとよいです。

- ブレークポイント ・・・ 赤色
- トレース範囲 ・・・ 緑色
- ブレークポイント ＆ トレース範囲 ・・・ マゼンタ


File Register Window の表示
----

1. 「Window」 → 「File Registers」

File Register Window では、SFC (Special Function Register) やユーザ定義の変数の現在の値を確認したり変更したりすることができます。

#### レジスタ内容の変更

1. File Register Window で変更したい範囲を選択
2. 「右クリック」 → 「Fill Register(s)」

#### ブレークポイントの設定

1. Absolute List を表示
2. ブレークポイントを設定したい行で右クリック → 「Break Point(s)」

（あるいは、「Debug」 → 「Break Settings...」）

#### トレースポイントの設定

1. Absolute List を表示
2. トレースポイントを設定したい行で右クリック → 「Trace Point(s)」

（あるいは、「Debug」 → 「Trace Settings...」）

