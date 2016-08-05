---
title: Node.js
layout: category-index
---

Node.js 実行環境など
----

- [Node.js とは／Node.js をインストールする](env/install-nodejs.html)
- [node アプリをシェバングで起動するようにする](shebang.html)
- [Node.js で assert を使用する](assert.html)

### モジュール
- [Node.js 用のモジュールを作成する](env/create-module.html)
- [Node.js が require() で検索するパスのまとめ](require.html)

### npm (Node Package Manager)
- [npm コマンドで Node.js のモジュールをインストールする](npm/install-module.html)
- [npm の HTTP プロキシ設定](npm/proxy.html)
- [npm でインストール可能なパッケージの最新バージョンを調べる](npm/package-version.html)

### package.json ファイル
- [package.json の書式、説明を確認する](npm/package-json1.html)
- [package.json の雛形を生成する](npm/package-json2.html)
- [最小限の package.json を作成する](npm/package-json3.html)
- [npm start でアプリを起動できるようにする](npm/package-json4.html)

Express（Web サーバ）
----
- [Express の特徴](express/features.html)
- [Express をインストールする](express/install.html)
- [Express で Web サーバを作成する](express/web-server.html)
- [GET/POST メソッドで送られてきたデータを取得する](express/handle-get-and-post-data.html)
- [Express のルート定義の URL のパラメータで数字のみを受け付けるようにする](express/regexp-in-routing.html)
- [next() により次のハンドラへ処理を委譲する](express/next.html)
- [RESTful API として検索用の URL を定義する](express/url-for-search.html)
- [jQuery クライアントと Express サーバで JSON データを送受信する](express/exchange-json.html)
- [Express サーバの HTTP アクセスログを表示する (express.logger() / morgan)](express/access-log.html)
- [Express サーバの Internal Server Error を分かりやすく HTTP ページで表示する](express/internal-server-error.html)
- [Express の動作環境を切り替える（NODE_ENV 変数）](express/switch-env.html)
- [Express で静的ファイル（static ファイル）をホスティングする](express/static-file.html)
- [リバースプロキシ経由で Express サーバにアクセスしたときのクライアントのアドレスを取得する](express/reverse-proxy-addr.html)
- [Express でテンプレートエンジンとして Jade を使用する](express/jade-with-express.html)
- [Express で Basic 認証を有効にする](express/basic-authentication.html)
- [Express で CORS の設定を行う（クロスドメインでの Ajax を許可）](express/cors.html)

入出力 (I/O)
----
- [標準出力 (stdout)、標準エラー出力 (stderr) への出力](io.html)
- [外部プログラムを実行してその出力を取得する](io/child-process.html)

### ファイル／ディレクトリ
- [様々なディレクトリパスの取得やパスの結合方法](io/path.html)
- [指定したパスがディレクトリかどうか調べる](io/is-directory.html)
- [カレントディレクトリ以下のファイルを列挙する](io/walk-dir.html)
- [CSV ファイルを読み込む](io/csv.html)

### SQLite データベース
- [SQLite を使用する (1) node-sqlite3 のインストール](io/sqlite1.html)
- [SQLite を使用する (2) node-sqlite3 による逐次処理](io/sqlite2.html)
- [SQLite を使用する (3) SELECT 文を実行する](io/sqlite3.html)

### MongoDB データベース
- [mongoose (1) mongoose をインストールする](io/mongoose1.html)
- [mongoose (2) データベースへ接続する](io/mongoose2.html)
- [mongoose (3) モデルクラスを作成する](io/mongoose3.html)
- [mongoose (4-1) DB にドキュメントを追加する](io/mongoose4-1.html)
- [mongoose (4-2) DB からドキュメントを検索する](io/mongoose4-2.html)
- [mongoose (4-3) DB のドキュメントを更新する](io/mongoose4-3.html)
- [mongoose (4-4) DB からドキュメントを削除する](io/mongoose4-4.html)
- [mongojs で MongoDB を扱う](io/mongojs.html)

ネットワーク
----

### HTTP 通信 / HTTP サーバ
- [http 標準モジュールを使用して HTTP サーバを立てる](net/http-server.html)
- [http 標準モジュールを使用して HTTP 通信を行う](net/http-module.html)
- [request モジュールを使用して HTTP 通信を行う](net/request-module.html)

### WebSocket 通信
- [WebSocket のクライアントを作成する](net/websocket-client.html)
- [Node.js で WebSocket サーバを作る（簡単な echo サーバ）](net/websocket-echo-server.html)
- [Node.js で WebSocket サーバを作る（chat サーバ）](net/websocket-chat-server.html)
- [WebSocket サーバに接続してきたクライアントの情報を調べる](net/websocket-client-info.html)

### リバースプロキシ
- [Node.js でお手軽リバースプロキシを作る](net/reverse-proxy.html)


Node.js の制御構文
----

### 例外処理
1. [try ～ catch による例外処理](exception/try-and-catch.html)
1. [Uncaught Exception（未捕捉例外）をハンドルする](exception/uncaught-exception.html)
1. [ドメインごとに Uncaught Exception（未捕捉例外）をハンドルする](exception/domain-for-exception.html)

### async.js による非同期処理
1. [非同期処理のフロー制御を行う (1) async.js を使う準備](async-js1.html)
1. [非同期処理のフロー制御を行う (2) 複数の非同期処理の結果を同時に取得 (async.parallel)](async-js2.html)
1. [非同期処理のフロー制御を行う (3) 複数の非同期処理を指定した順に実行する (async.series, async.waterfall)](async-js3.html)

