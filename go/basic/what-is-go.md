---
title: "Go 言語とは？／Go をインストールする"
url: "p/wxhzfvc/"
permalink: "p/wxhzfvc/"
date: "2017-08-30"
lastmod: "2022-04-08"
tags: ["Go"]
description: "Go 言語 (Golang) は、Google が 2009 年に発表したオープンソースのプログラミング言語です。ここでは Go 言語の特徴と、インストール方法を紹介します。"
redirect_from:
  - /hugo/go/what-is-go
---

Go 言語の特徴
----

Go 言語は下記のような特徴を持っています。

* __マスコットキャラクター（Gopher: ホリネズミ）が ~~気持ち悪い~~ かわいい__
* 開発者
  * Ken Thompson（ケン・トンプソン）（C 言語の開発者）
  * Rob Pike（ロブ・パイク）（UTF-8 の開発者）
  * Brad Fitzpatrick（ブラッド・フィッツパトリック）（memcached の開発者）
* コンパイル型の言語
  * コンパイルが非常に高速
  * コンパイルせずにソースコードを指定して実行することも可能（`go run sample.go`)
  * クロスコンパイルをサポートしており、Windows、Linux、macOS 用の実行ファイルを容易に生成できる
  * 今まで C/C++ で行っていたシステムレイヤのプログラミングをより効率的に行う目的で作成された（ミドルウェアなどの実装に最適）
* シンプルな言語仕様のため習得が用意で、大規模開発にも耐えられる
  * 最小限の構文により、コーディングのミスを防ぎ、コンパイルを最適化している
  * 例えば、ループ構文は for ループしかない、三項演算子もない
  * 行末のセミコロンは必要ない
  * マクロなどのプリプロセッサ構文はない（コンパイルの低速化の原因のため）
  * 暗黙の型変換はない
  * ポインタはあるが、危険なポインタ演算はない
  * GC による自動メモリ解放（C 言語と Java のいいとこ取り）
  * 継承、ジェネリックプログラミング、アサーション、オーバーロードなども存在しない
    * 2022-03-15 の [Go 1.18 リリース](https://go.dev/doc/go1.18) でジェネリクスがサポートされました
  * 不要なインポートがあるとコンパイルエラーになる（警告ではなくエラー）
  * try-catch による例外の仕組みがない（関数が多値を返すことでカバー）（panic と recover という仕組みがある）
  * クラスはないが、構造体＋レシーバ付き関数でカバー
* 豊富な標準パッケージを同梱（暗号化、圧縮、RDB、JSON、テスト用のライブラリなど）
* 並行処理もネイティブにサポート（ゴルーチンという軽量スレッド、チャネルによるスレッド間通信）
* テスト実行 (`go test`) やドキュメント生成 (`godoc`) のコマンドを標準搭載
* バージョン 1.5 以降は Go 言語自体が Go 言語で記述されている（一部アセンブラ）

Go 言語は、静的サイトジェネレータ Hugo や、コンテナ型仮想環境ツール Docker のベースとなる言語としても使用されており、これらのツールの高速な動作は、Go 言語によって支えられています。


Go をインストールする
----

Go は各環境用のインストールパッケージを使用して簡単にインストールできます。
下記から Windows、Linux、macOS 用のパッケージをダウンロードしてインストールを実行してください。

- [Getting Started - The Go Programming Language](https://golang.org/doc/install)

インストールが完了したら、下記のようにバージョン情報を表示することで動作確認できます。

```console
$ go version
go version go1.9 windows/amd64
```


Go のバージョン推移
----

- [go1.18 (released 2022-03-15)](https://go.dev/doc/go1.18)
  - __ジェネリクス__ をサポート。
  - Fuzzing テストのサポート。
  - `go get` はパッケージのビルドもインストールもしなくなり、`go.mod` ファイルの依存情報を更新するだけになった（= `-d` フラグが常に有効）。そのため、モジュールの外（`go.mod` がない）ディレクトリで `go get` を実行するとエラーになるようになった。
  - GOPATH モード (`GO111MODULE=off`) にすれば、`go get` コマンドは以前のようにパッケージのビルドとインストールを行う。
  - `go.mod` ファイルと `go.sum` ファイルは、`go get`、`go mod tidy`、`go mod download` コマンドのみで更新される。
- [go1.17 (released 2021-08-16)](https://go.dev/doc/go1.17)
  - `go get` によるコマンドのインストールが非推奨に。代わりに `go install cmd@ver` を使う。
  - Windows の 64-bit ARM アーキテクチャをサポート。
  - ビルド制御のための `//go:build` コメントをサポート（古い型式の `// +build` は `gofmt` で置換してくれる）。
- [go1.16 (released 2021-02-16)](https://go.dev/doc/go1.16)
  - パッケージビルドは module-aware モードがデフォルトになった（`go.mod` ファイルが存在しなくても）。今後、すべてのプロジェクトは module-aware モードでのビルドが推奨とされた。
  - `GO111MODULE` による振る舞い（未指定時が `on` とみなされるようになった）
    - 未指定 ... 常に module-aware モードで動作（下記 `on` と同様に動作）
    - `on` ... 常に module-aware モードで動作
    - `off` ... 常に GOPATH モードで動作（go1.10 までと同じ）
    - `auto` ... `$GOPATH/src` 以下で `go.mod` ファイルが存在しない場合のみ GOPATH モードで動作（go1.15 までのデフォルト動作）
- [go1.15 (released 2020-08-11)](https://go.dev/doc/go1.15)
  - `GOMODCACHE` でモジュールキャッシュ用のディレクトリを `$GOPATH/pkg/mod` から変更できるようになった。
- [go1.14 (released 2020-02-25)](https://go.dev/doc/go1.14)
- [go1.13 (released 2019-09-03)](https://go.dev/doc/go1.13)
- [go1.12 (released 2019-02-25)](https://go.dev/doc/go1.12)
  - `go mod init` で `go.mod` ファイルにデフォルトで go directive （go のバージョン情報）が入るようになった。
  - Go tour がメインバイナリに含まれなくなったので、`go tool tour` は `go get -u golang.org/x/tour` と実行するようになった。
- [go1.11 (released 2018-08-24)](https://go.dev/doc/go1.11)
  - __module-aware（モジュール対応）モード__ の導入。go コマンドは、コンテキストに応じて GOPATH モードと module-aware モードのどちらかで動作するようになった。
    - GOPATH モード ... go1.10 までのモードで、コード管理を GOPATH で指定されたディレクトリ内で行う
    - module-aware モード ... コード管理を任意のディレクトリ（モジュールディレクトリ）で行う
  - `GO111MODULE` による振る舞い
    - 未指定 ... 下記 `auto` と同様に動作
    - `on` ... 常に module-aware モードで動作
    - `off` ... 常に GOPATH モードで動作（go1.10 までと同じ）
    - `auto` ... `$GOPATH/src` 以下で `go.mod` ファイルが存在しない場合のみ GOPATH モードで動作（go1.15 までのデフォルト動作）
- go1.10 (released 2018-02-16)
- go1.9 (released 2017/08/24)
- go1.8 (released 2017/02/16)
- go1.7 (released 2016/08/15)
- go1.6 (released 2016/02/17) -- 64 ビット MIPS 上の Linux、32 ビット x86 上の Android をサポート
- go1.5 (released 2015/08/19) -- iOS をサポート
- go1.4 (released 2014/12/10) -- Android をサポート
- go1.3 (released 2014/06/18)
- go1.2 (released 2013/12/01)
- go1.1 (released 2013/05/13)
- go1 (released 2012/03/28) -- Linux、Mac OSX に加え、Windows をサポート

[Release History - The Go Programming Language](https://golang.org/doc/devel/release.html) より

