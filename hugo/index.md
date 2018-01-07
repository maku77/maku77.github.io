---
title: Hugo
layout: category-index
---

Hugo
====

はじめに
----

* [Hugo とは／Hugo をインストールする](install.html)
* [Hugo で新規の Web サイトを作成する](create-site.html)

記事の作成
----
* [Hugo で記事を作成する](create-page.html)
* [Hugo でドラフトページを作成する](draft.html)
* [Hugo のテーマを設定する](theme.html)

ショートコード (Shortcode)
----
* [ショートコードで本文内に HTML スニペットを埋め込む](shortcode/basic.html)
* [独自のショートコードを作成する](shortcode/create-shortcode.html)
* [ショートコードの中からフロントマターのパラメータを参照する ($.Page.Params)](shortcode/frontmatter-params.html)
* [ショートコードの中から設定ファイルのパラメータを参照する ($.Site.Params)](shortcode/site-params.html)

hugo コマンド
----
* [カレントディレクトリを気にせずに hugo コマンドを実行する](command/source-dir.html)
* [ポート番号を指定して Hugo サーバーを起動する](command/server-port.html)
* [複数の Hugo サーバーを１つのコマンドプロンプト上で立ち上げる](command/multi-server.html)

設定
----
* [日本語を正しく扱えるようにしてページサマリーが長くなるのを防ぐ](settings/japanese.html)

テンプレート／テーマ
----
* [レイアウト用のテンプレートの種類を理解する](layout/template-types.html)
* [独自のテーマを作成する](create-theme.html)
* [複数の Hugo サイトで同じテーマディレクトリを参照する](layout/same-theme-dir.html)

### Hugo のテンプレート文法
* [Hugo テンプレート内にコメントを記述する](template/comment.html)
* [Hugo テンプレート内で変数を扱う](template/variable.html)
* [Hugo テンプレート内で配列（スライス）変数を定義する (slice)](template/array.html)
* [Hugo テンプレート内でマップ（辞書）変数を定義する (dict)](template/dict.html)
* [Hugo テンプレートで if、with による分岐処理を行う](layout/grammer/if.html)
* [Hugo テンプレートで数値によるループ処理を行う](template/loop.html)
* [Hugo テンプレート内で define よる部分テンプレート定義を行う](template/define.html)

### セクション機能
* [セクション機能を使って記事を階層化する](layout/section.html)
* [セクションテンプレート (section.html) の中でセクションのタイトルを表示する](layout/section-name.html)
* [セクションの階層構造を取得する (.CurrentSection、.Parent、.Sections)](layout/section-hierarchy.html)

### ページ一覧を表示するサンプル
* [全ページの一覧を表示する (.Site.Pages)](list/all-pages.html)
* [全セクションの一覧を表示する (.Site.Sections)](list/all-sections.html)
* [カレントセクション直下のページ／セクションの一覧を表示する](list/section-children.html)
* [セクションを持たない記事ページ（ルートの記事ページ）の一覧を表示する](list/root-pages.html)

### タクソノミー（タグ／カテゴリの仕組み）
* [タクソノミーの基本](taxonomy/basic.html)
* [タクソノミー関連のテンプレートを定義する](taxonomy/template.html)
* [タクソノミーごとにターム一覧を表示する](taxonomy/list-all-taxonomies.html)
* [記事ページに付けられたターム一覧（タグ一覧）を表示する](taxonomy/terms-in-page.html)
* [記事ページに複数のターム（カテゴリ）を割り当てた場合にエラーにする](taxonomy/too-many-terms.html)

### その他
* [サイトのヘッダーとフッターをパーシャルファイルに分離する](template/partial-header.html)
* [パンくずリストを表示する](template/breadcrumbs.html)
* [ホームページの Page オブジェクトを取得する](template/homepage.html)

応用
----
* [特定の記事を常にリスト上方に表示する（weight 変数）](weight.html)

Links
----
* [Hugo Themes](https://themes.gohugo.io/)
  - たくさんの Hugo 用テーマが公開されています。

Go 言語入門（Golang 入門）
====

はじめに
----
* [Go 言語とは？／Go をインストールする](go/what-is-go.html)
* [Go 言語で Hello World をコンパイル、実行する](go/hello-world.html)
* [Go 言語のコーディングスタイル（コーディング規約）](go/coding-style.html)
* [Go ツアーを起動して Go 言語の基本を勉強する](go/go-tour.html)
* [Go のワークスペースの考え方 (GOPATH)](go/workspace.html)

Go の文法
----
* [パッケージを参照する (import)](go/import.html)
* [GitHub 上のパッケージを参照する／GitHub にパッケージを公開する](go/github.html)
* [Go 言語の組込み型一覧](go/types.html)
* [変数を定義する (var)／ゼロ値について](go/var.html)
* [配列とスライスを扱う](go/array.html)
* [マップを扱う (map)](go/map.html)
* [ポインタを扱う](go/pointer.html)
* [定数を定義する (const)](go/const.html)
* [if による条件分岐](go/if.html)
* [switch による条件分岐](go/switch.html)
* [for によるループ処理](go/for.html)
* [関数を定義する (func)](go/func.html)
* [パニックによるエラー処理 (panic, recover)](go/panic.html)

Go の型システムと構造体（クラスもどき）
----
* [組込み型に独自の型名を付ける (type)](go/type.html)
* [構造体を定義する (struct)](go/struct.html)
* [構造体のコンストラクタ（ファクトリ関数）を定義する](go/constructor.html)
* [メソッドを定義する（レシーバ付き関数）](go/method.html)
* [Go 言語のインタフェースの扱いを理解する (interface)](go/interface.html)
* [型のキャストと、型アサーションによる型変換](go/cast.html)
* [インタフェース埋め込みと構造体埋め込みによる拡張 (Embedding)](go/embedding.html)

Go の標準パッケージ
----
* [ファイルからの読み出し／ファイルへの書き込みを行う (os, io)](go/file.html)
* [テンプレート機能を使用する (text/template, html/template)](go/template.html)
* [時刻データを扱う (time)](go/time.html)
* [ベンチマークを行う (testing.B)](go/benchmark.html)

外部リンク
----
* [公式サイト](https://golang.org/)
  * [Language Specification（Go 言語仕様）](https://golang.org/ref/spec)
  * [Command Documentation（コマンドラインツール説明）](https://golang.org/doc/cmd)
  * [Effective Go（よいコーディング指針）](https://golang.org/doc/effective_go.html)
  * [Packages（標準パッケージ一覧）](https://golang.org/pkg/)
