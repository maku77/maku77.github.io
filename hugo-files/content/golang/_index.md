---
title: "Golang"
url: "/go/"

categoryName: "まくまく Golang ノート"
categoryUrl: "/go/"
categoryIcon: _index.svg
---

はじめに／実行環境 <!-- basic -->
----

### はじめに

* [Go 言語とは？／Go をインストールする](/p/wxhzfvc/)
* [Go 言語で Hello World をコンパイル、実行する](/p/nuz369c/)
* [Go 言語のコーディングスタイル（コーディング規約）](/p/rz47adg/)
* [Go ツアーを起動して Go 言語の基本を勉強する](/p/dkpsvz3/)
* [go install のコマンドインストール先にパスを通す (GOBIN, GOPATH/bin)](/p/s258beh/)
* [（旧）GOPATH によるコードの一元管理](/p/u48bfim/)

### パッケージとモジュール

* [パッケージの作成とインポート (import)](/p/t269cgj/)
* [Go でコマンドラインツールを作って GitHub で公開する](/p/whs2bjt/)
* [（旧）GitHub 上のパッケージを参照する／GitHub にパッケージを公開する](/p/xs3ahpw/)


Go の文法
----

### 型 <!-- grammer-type -->

* [Go 言語の組込み型一覧](/p/as29hpw/)
* [変数を定義する (var)／ゼロ値について](/p/5dhkoru/)
* [配列とスライスを扱う](/p/cjosvz3/)
* [マップを扱う (map)](/p/5cgjnqt/)
* [ポインタを扱う (＊)](/p/vpz8fnv/)
* [定数を定義する (const)](/p/qqy9gok/)
* [組込み型に独自の型名を付ける (type)](/p/cuxyj8c/)
* [型キャストと型アサーションによる型変換](/p/jruz369/)

### 制御構文 <!-- grammer-control -->

* [if による条件分岐](/p/pw258be/)
* [switch による条件分岐](/p/x6adgjn/)
* [for によるループ処理](/p/v58cfik/)
* [関数を定義する (func)](/p/kswy47a/)
* [パニックによるエラー処理 (panic, recover)](/p/j47aswy/)

### 構造体（クラス） <!-- grammer-struct -->

* [構造体を定義する (struct)](/p/8z2o63r/)
* [構造体のコンストラクタ（ファクトリ関数）を定義する](/p/6dhkoru/)
* [メソッドを定義する（レシーバ付き関数）](/p/4behkor/)
* [Go 言語のインタフェースの扱いを理解する (interface)](/p/aimpsvz/)
* [インタフェース埋め込みと構造体埋め込みによる拡張 (Embedding)](/p/tbf357g/)
* [構造体にタグ情報を追加する (struct tags)](/p/hxhzfbs/)

### その他 <!-- grammer-other -->

* [ジェネリクスで複数の型を扱える関数を定義する (Generics)](/p/cp8o6m2/)


標準パッケージ／外部パッケージの利用
----

### ファイル／ディレクトリ <!-- file -->

* [ファイルを読み書きする (os, io)](/p/6eimpsv/)
* [JSON 形式の文字列やファイルを扱う (encoding/json)](/p/dsbs9p5/)
* [CSV 形式の文字列やファイルを扱う (encoding/csv)](/p/6k5m3iz/)
* [テンプレート機能を使用する (text/template, html/template)](/p/z8behko/)
* [カレントディレクトリのパスを取得する (os.Getwd)](/p/egs2bjt/)

### サーバー <!-- server -->

* [HTTP サーバーを作成する (net/http)](/p/goruwy4/)
* [GraphQL サーバーを作成する (gqlgen)](/p/v48adgi/)
  * [gqlgen で子フィールドの情報を返すリゾルバーを実装する](/p/wdvdtap/)

### データベース (DB) <!-- database -->

* [RDB（リレーショナルデーターベース）を扱う (database/sql)](/p/kgzfwdt/)

### その他 <!-- misc -->

* [コマンドライン引数を扱う (os.Args, flags)](/p/o8vbo2f/)
* [時刻データを扱う (time)](/p/sy58beh/)
* [ベンチマークを行う (testing.B)](/p/29dgjnq/)
* [環境変数を扱う (os.Getenv, os.LookupEnv)](/p/4ox6dmu/)
* [ランダム値（乱数）を扱う (math/rand, crypto/rand)](/p/graq8o5/)

