---
title: "Hugo"
layout: category-index
---

Hugo
====

はじめに
----
* [Hugo とは／Hugo をインストールする](basic/install.html)
* [Hugo で新規の Web サイトを作成する](basic/create-site.html)

記事の作成
----
* [Hugo で記事を作成する](basic/create-page.html)
* [Hugo でドラフトページを作成する](basic/draft.html)
* [Hugo でドラフトページの一覧を簡単に確認できるようにする](basic/draft-list.html)
* [Hugo のテーマを設定する](basic/theme.html)

ショートコード (Shortcode)
----
* [ショートコードで本文内に HTML スニペットを埋め込む](shortcode/basic.html)
* [独自のショートコードを作成する](shortcode/create-shortcode.html)
* [ショートコードの中からフロントマターのパラメータを参照する ($.Page.Params)](shortcode/frontmatter-params.html)
* [ショートコードの中から設定ファイルのパラメータを参照する ($.Site.Params)](shortcode/site-params.html)
* [ローカルサーバで動作させているとき（開発時）のみ内容を出力する private ショートコードを作成する](shortcode/private.html)
* [Youtube の動画を表示する（Hugo 組み込みショートコード）](https://gohugo.io/content-management/shortcodes/#youtube)
* [ソースコードをハイライト表示する (highlight)](shortcode/highlight.html)

hugo コマンド
----
* [カレントディレクトリを気にせずに hugo コマンドを実行する](command/source-dir.html)
* [ポート番号を指定して Hugo サーバーを起動する](command/server-port.html)
* [複数の Hugo サーバーを１つのコマンドプロンプト上で立ち上げる](command/multi-server.html)
* [Hugo サーバーで記事の変更内容が反映されない場合](command/ignore-cache.html)

設定/config ファイル
----
* [日本語を正しく扱えるようにしてページサマリーが長くなるのを防ぐ](settings/japanese.html)
* [コンフィグファイルに設定した情報を参照する](settings/read-config.html)
* [Google アナリティクス用のトラッキングコードを埋め込む](settings/google-analytics.html)
* [ページ内に Tex 形式の数式を埋め込めるようにする (MathJax)](settings/math-jax.html)
* [リンクをページからの相対パスで出力するようにする (relativeurls)](settings/relativeurls.html)

テンプレート／テーマ
----
* [レイアウト用のテンプレートの種類を理解する](layout/template-types.html)
* [独自のテーマを作成する](basic/create-theme.html)
* [複数の Hugo サイトで同じテーマディレクトリを参照する](layout/same-theme-dir.html)
* [サイトのヘッダーとフッターをパーシャルファイルに分離する](template/partial-header.html)
* [ベーステンプレートを作成して、各種テンプレートの基本構成を統一する (baseof)](template/base-template.html)
* [Hugo Themes](https://themes.gohugo.io/) （色々な Hugo 用テーマが公開されています）

### Hugo のテンプレート文法
* [Hugo テンプレート内にコメントを記述する](template/comment.html)
* [Hugo テンプレート内で変数を扱う](template/variable.html)
* [Hugo テンプレート内で配列（スライス）変数を定義する (slice)](template/array.html)
* [Hugo テンプレート内でマップ（辞書）変数を定義する (dict)](template/dict.html)
* [Hugo テンプレートで if、with による分岐処理を行う](layout/grammer/if.html)
* [Hugo テンプレートで数値によるループ処理を行う](template/loop.html)
* [Hugo テンプレート内で define よる部分テンプレート定義を行う（関数もどき）](template/define.html)
* [Hugo のパーシャルテンプレートから値を返す（関数化）](template/return-from-partial.html)

### セクション機能
* [セクション機能を使って記事を階層化する](layout/section.html)
* [セクションテンプレート (section.html) の中でセクションのタイトルを表示する](layout/section-name.html)
* [セクションの階層構造を取得する (.CurrentSection、.Parent、.Sections)](layout/section-hierarchy.html)
* [ページタイトルに自動でセクションプレフィックスを付ける](layout/section-prefix.html)

### ページ一覧／セクション一覧を表示するサンプル
* [サイト内の全ページの一覧を表示する (.Site.Pages)](list/all-pages.html)
* [サイト内の全セクションの一覧を表示する (.Site.Sections)](list/all-sections.html)
* [カレントセクション直下のページ／セクションの一覧を表示する](list/section-children.html)
* [セクションを持たない記事ページ（ルートの記事ページ）の一覧を表示する (.Site.Home.RegularPages)](list/root-pages.html)
* [サイト内の全ページの一覧をセクションの階層構造に従って表示する](list/page-hierarchy.html)
* [サイドバー用のページツリーを表示する（現在表示しているページを考慮した階層表示）](list/sidebar-menu.html)
* [最近更新された記事（新着記事）のリストを表示する](list/recents.html)

### タクソノミー（タグ／カテゴリの仕組み）
* [タクソノミーの基本](taxonomy/basic.html)
* [タクソノミー関連のテンプレートを定義する](taxonomy/template.html)
* [サイト全体のターム一覧（タグ一覧）を表示する](taxonomy/list-all-taxonomies.html)
* [記事ページに付けられたターム一覧（タグ一覧）を表示する](taxonomy/terms-in-page.html)
* [記事ページに複数のターム（カテゴリ）を割り当てた場合にエラーにする](taxonomy/too-many-terms.html)

### data ディレクトリ
* [全ページから参照できるデータを用意する（data ディレクトリ）](data/basic.html)
* [使用するデータファイルをショートコードのパラメータで切り替える](data/specify-datafile.html)

### ページャー（ページネーター）
* [ページャー（ページネーター）で複数の記事を切り替えながら表示できるようにする](pager/basic.html)

### その他
* [パンくずリストを表示する](template/breadcrumbs.html)
* [各ページに目次を表示する (.TableOfContents)](template/table-of-contents.html)
* [ホームページの Page オブジェクトを取得する](template/homepage.html)
* [各種ページにおいて .Kind や .IsPage、.IsNode の値がどうなるかの一覧](template/page-types.html)
* [次のページ、前のページへのリンクを表示する](template/prev-next-link.html)
* [ファイルが存在する場合のみ処理するコードを記述する](template/if-exists.html)
* [Markdown (.md) ファイルのパス情報を取得する](template/markdown-path.html)
* [Markdown ファイルに本文が記述されていない場合だけ特別な出力をする](template/empty-content.html)
* [テンプレートの中で絵文字を使用する (emojify)](template/emojify.html)

応用
----
* [画像ファイルを Markdown ファイルと同じディレクトリに置く (Page Bundle)](misc/page-bundle.html)
* [大きな画像ファイルから自動的に小さなサムネイル画像を生成する (Image Processing)](misc/image-processing.html)
* [サイト構造を変えてもページの URL が変わらないようにする (Permalink)](misc/permalink.html)
* [Sass/SCSS スタイルシートを使用する](advanced/sass.html)
* [全文検索（インクリメンタルサーチ）の機能を付ける](advanced/full-text-search.html)
* [Google カスタム検索を設置して記事を検索できるようにする](advanced/google-custom-search.html)
* [特定の記事を常にリスト上方に表示する（weight 変数）](basic/weight.html)
* [Python で Markdown ファイルのフロントマターの記述が正しいかチェックする](advanced/validate-front-matter.html)
* [favicon.ico をサイトのルートに置く (static)](misc/favicon.html)
* [Facebook や Twitter の SNS で URL をシェアするときの表示設定 (OGP: Open Graph Protocol)](advanced/ogp.html)
* [hugo deploy コマンドで Azure などのクラウドサービス上にデプロイする](advanced/deploy.html)
* [このページを編集 (Edit this page) のリンクを作成する](advanced/edit-this-page.html)
* [ページ内リンク（アンカー）を張る](advanced/internal-link.html)

トラブルシューティング
----
* [ドラフト指定したセクションが公開されてしまう](misc/exclude-draft-section.html)
* [Markdown ファイルに記述した HTML コードが削除されてしまう](misc/include-html.html)

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

