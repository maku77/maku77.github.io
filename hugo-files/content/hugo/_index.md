---
title: "Hugo"
url: "/hugo/"

categoryName: "まくまく Hugo ノート"
categoryUrl: "/hugo/"
categoryIcon: logo-hugo.png
---

高速な静的サイトジェネレータ Hugo の使い方です。
[Go 言語に関する記事はこちら](/go/)へ分離しました。

はじめに <!-- basic -->
----
* [Hugo とは／Hugo をインストールする](/p/r8ufyk5/)
* [Hugo で新規の Web サイトを作成する](/p/bt5enw6/)

記事の作成 <!-- basic -->
----
* [Hugo で記事を作成する](/p/q7sdwgy/)
* [Hugo でドラフトページを作成する](/p/m2oatdw/)
* [Hugo でドラフトページの一覧を簡単に確認できるようにする](/p/qpcvfzi/)
* [Hugo のテーマを設定する](/p/h2cku5d/)
* [Hugo で独自のテーマを作成する](/p/s4qbuez/)
* [Hugo Themes（Hugo テーマの一覧サイト）](https://themes.gohugo.io/)

hugo コマンドと設定ファイル
----

### hugo コマンド <!-- cli -->
* [カレントディレクトリを気にせずに hugo コマンドを実行する (`hugo server -s`) ](/p/wdyk5n7/)
* [ポート番号を指定して Hugo サーバーを起動する (`hugo server -p`)](/p/jj7rcvf/)
* [Hugo サーバーで記事生成のキャッシュを無効にする (`hugo server --ignoreCache`)](/p/taxh3m7/)
* [複数の Hugo サーバーを 1 つのコマンドプロンプト上で立ち上げる](/p/yg4o9rb/)
* [複数の Hugo サイトで同じテーマディレクトリを参照する (`themesDir`)](/p/4m7gqfx/)

### 設定ファイル (config) <!-- config -->
* [日本語を正しく扱えるようにしてページサマリーが長くなるのを防ぐ (`hasCJKLanguage`, `isCJKLanguage`)](/p/rqcwgyj/)
* [コンフィグファイルに設定した情報を参照する](/p/5m9tdwg/)
* [Google アナリティクス用のトラッキングコードを埋め込む (`googleAnalytics`)](/p/zxk6pat/)
* [ページ内に Tex 形式の数式を埋め込めるようにする (MathJax)](/p/dsfzi4n/)
* [リンクをページからの相対パスで出力するようにする (`relativeurls`)](/p/32n9scv/)

Hugo Modules
----

* [Hugo Modules の仕組みで Hugo サイト用のコンテンツを分割して管理する](/p/bqar8o6/)

ショートコード (Shortcode) <!-- shortcode -->
----

### ショートコードの使い方と作り方
* [ショートコードで本文内に HTML スニペットを埋め込む](/p/tsfzj4n/)
* [独自のショートコードを作成する](/p/ttfyk5o/)
* [ショートコードの中からフロントマターのパラメータを参照する (`$.Page.Params`)](/p/t4sdxi3/)
* [ショートコードの中から設定ファイルのパラメータを参照する (`$.Site.Params`)](/p/53patex/)
* [ソースコードをハイライト表示する (highlight)](/p/gxk6qat/)
* [ショートコードをエスケープ処理してそのまま表示する](/p/9tg2m7q/)
* [あるショートコードが使われている場合のみ JavaScript を読み込む (`.HasShortcode`)](/p/3j6qate/)

### ショートコードの例
* [ローカルサーバーで動作させているとき（開発時）のみ内容を出力する `private` ショートコードを作成する](/p/jbr6kzd/)
* [SVG ファイルをインラインで埋め込む `svg` ショートコードを作成する](/p/kyn8rcv/)
* [クリックで開閉する `accordion` ショートコードを作成する](/p/w5gs4ep/)
* [Youtube の動画を表示する `youtube` ショートコード（Hugo 組み込み）](https://gohugo.io/content-management/shortcodes/#youtube)

テンプレート
----

### テンプレートによるレイアウトの基本 <!-- template -->
* [レイアウト用のテンプレートの種類を理解する](/p/zg4n7q9/)
* [記事ファイルのフロントマターで使用するレイアウトを制御する (`type`, `layout`)](/p/m2n8rbu/)
* [サイトのヘッダーとフッターをパーシャルファイルに分離する (`partials/header.html`, `partials/footer.html`)](/p/wvi3n7q/)
* [ベーステンプレートを作成して、各種テンプレートの基本構成を統一する (`baseof.html`)](/p/bbxj5pa/)

### Hugo のテンプレート文法 <!-- template -->
* [Hugo テンプレート内にコメントを記述する (`{{/* ... */}}`)](/p/5zwytgx/)
* [Hugo テンプレート内で変数を扱う (`{{ $x := ... }}`)](/p/y39gzkc/)
* [Hugo テンプレート内で配列（スライス）変数を扱う (`slice`, `index`, `range`)](/p/7bvjywy/)
* [Hugo テンプレート内でマップ（辞書）変数を扱う (`dict`, `index`, `range`)](/p/yhqogz6/)
* [Hugo テンプレート内で `if` や `with` で分岐処理する](/p/ewoqwrk/)
* [Hugo テンプレートで数値によるループ処理を行う (`range`, `seq`)](/p/8vm6xqm/)
* [Hugo テンプレート内で `define` による部分テンプレート定義を行う（関数もどき）](/p/pkww45p/)
* [Hugo のパーシャルテンプレートから値を返す（関数化）(`return`)](/p/4anjern/)

### セクションとページリスト <!-- template -->
* [Hugo のセクション機能の基本（記事を階層化する）](/p/8ihz7es/)
* [セクションテンプレート (`section.html`) の中でセクションのタイトルを表示する](/p/mn4ji9o/)
* [セクションの階層構造を取得する (`.CurrentSection`、`.Parent`、`.Sections`)](/p/8vrj4ui/)
* [ページタイトルに自動でセクションプレフィックスを付ける](/p/dunigdz/)
* [ページの階層構造を取得する関数を作成する (`get-hierarchy`)](/p/v9t62ux/)
* 一覧表示のサンプルコード <!-- list -->
  * [サイト内の全ページの一覧を表示する (`.Site.Pages`)](/p/sgrjpfu/)
  * [サイト内の全セクションの一覧を表示する (`.Site.Sections`)](/p/vczuozw/)
  * [カレントセクション直下のページ／セクションの一覧を表示する](/p/pgub54h/)
  * [セクションを持たない記事ページ（ルートの記事ページ）の一覧を表示する (`.Site.Home.RegularPages`)](/p/4sxmnfi/)
  * [サイト内の全ページの一覧をセクションの階層構造に従って表示する](/p/xuwd7tn/)
  * [サイドバー用のページツリーを表示する（現在表示しているページを考慮した階層表示）](/p/7o7ymst/)
  * [最近更新された記事（新着記事）のリストを表示する](/p/pocxi4n/)
  * [ページリスト（記事一覧）に列挙されないページを作る (`_build.list`)](/p/4ziyhxe/)
  * [セクションページでいろいろな方法でページソートする (`.ByDate`, `.ByTitle`, `.ByWeight`)](/p/9gjnqtw/)

### タクソノミー（タグとカテゴリー） <!-- taxonomy -->
* [タクソノミーの基本（タグやカテゴリなど）](/p/mtfmaxr/)
* [タクソノミー関連のテンプレートを定義する](/p/aqchnnq/)
* [サイト全体のターム一覧（タグ一覧）を表示する](/p/tfk4tdg/)
* [記事ページに付けられたターム一覧（タグ一覧）を表示する](/p/topd7uy/)
* [記事ページに複数のターム（カテゴリ）を割り当てた場合にエラーにする](/p/5v7o2xp/)

### data ディレクトリ
* [全ページから参照できるデータを用意する（`data` ディレクトリ）](/p/5ru4kte/)
* [参照するデータファイルをショートコードのパラメータで切り替える](/p/jbi9ojq/)

### ページャー（ページネーター）
* [ページャー（ページネーター）で複数の記事を切り替えながら表示できるようにする](/p/8ogzaxd/)

### その他
* [パンくずリストを表示する](/p/vemn3c4/)
* [JSON-LD のパンくずリストを出力する（SEO 対策）](/p/5fzgwdt/)
* [各ページに目次を表示する (`.TableOfContents`)](/p/x9tbr8o/)
* [ホームページの `Page` オブジェクトを取得する](/p/ffr2bku/)
* [各種ページにおいて `.Kind` や `.IsPage`、`.IsSection`、`.IsNode` の値がどうなるかの一覧](/p/co8p6n4/)
* [次のページ、前のページへのリンクを表示する](/p/sc9t737/)
* [ファイルが存在する場合のみ処理するコードを記述する (`fileExists`)](/p/q4o6n4k/)
* [Markdown (.md) ファイルのパス情報を取得する](/p/8env4bi/)
* [Markdown (.md) ファイルを VS Code で開くリンクを表示する](/p/9hkprvx/)
* [Markdown ファイルに本文が記述されていない場合だけ特別な出力をする](/p/7m6n4j2/)
* [テンプレートの中で絵文字を使用する (`emojify`)](/p/88e7tiz/)

Hugo 応用
----
* [画像ファイルを Markdown ファイルと同じディレクトリに置く (Page Bundle)](misc/page-bundle.html)
* [大きな画像ファイルから自動的に小さなサムネイル画像を生成する (Image Processing)](misc/image-processing.html)
* [サイト構造を変えてもページの URL が変わらないようにする (Permalink)](/p/u9r9p7n/)
* [Sass/SCSS スタイルシートを使用する](/p/k7jv7hs/)
* [全文検索（インクリメンタルサーチ）の機能を付ける](advanced/full-text-search.html)
* [Google カスタム検索を設置して記事を検索できるようにする](advanced/google-custom-search.html)
* [特定の記事を常にリスト上方に表示する（`weight` プロパティ）](/p/utmg42x/)
* [Python で Markdown ファイルのフロントマターの記述が正しいかチェックする](advanced/validate-front-matter.html)
* [favicon.ico をサイトのルートに置く (static)](misc/favicon.html)
* [Facebook や Twitter の SNS で URL をシェアするときの表示設定 (OGP: Open Graph Protocol)](advanced/ogp.html)
* [hugo deploy コマンドで Azure などのクラウドサービス上にデプロイする](advanced/deploy.html)
* [このページを編集 (Edit this page) のリンクを作成する](advanced/edit-this-page.html)
* [ページ内リンク（アンカー）を張る](advanced/internal-link.html)
* [サーチエンジン用に robots.txt や sitemap.xml ファイルを配置する](advanced/sitemap.html)
* [mermaid.js で Markdown 中に UML 図を埋め込む](/p/xg3n7qa/)
* [Hugo のテンプレートやショートコードでランダムな文字列を生成する](/p/9qexh2q/)
* [記事ページで TypeScript ファイルをインクルードして使用する](/p/3adgjnq/)
* [新しいページや別のサイトへ自動でリダイレクトする (aliases, redirectTo)](/p/oj3izfu/)

トラブルシューティング
----
* [ドラフト指定したセクションが公開されてしまう](misc/exclude-draft-section.html)
* [Markdown ファイルに記述した HTML コードが削除されてしまう](misc/include-html.html)
* [Hugo サーバーで記事の変更内容が反映されない (`hugo server --ignoreCache`)](/p/taxh3m7/)

