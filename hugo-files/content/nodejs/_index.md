---
title: "Node.js"
url: "/nodejs/"

categoryName: "まくまく Node.js ノート"
categoryUrl: "/nodejs/"
categoryIcon: "logo-nodejs.svg"
---

Node.js 実行環境など
----

- [Node.js とは／Node.js をインストールする](/p/z5ap5bf/)
- [`nvm` で複数の Node.js バージョンを切り替えて使用する (Node Version Manager)](/p/3x95seb/)
- [`corepack` を使ってプロジェクト内で使用する `pnpm`/`yarn` のバージョンを管理する](/p/tzwio3y/)
- [Node.js スクリプトにシェバング (`#!`) を付けてコマンドとして起動できるようにする](/p/ptactsj/)
- [Node.js で assert を使用する](assert.html)
- [Node.js で環境変数を参照する (`process.env`)](/p/e44uun8/)
- [環境変数の代わりに `.env` ファイルを使用する (`dotenv`)](/p/6kruhwy/)
- [Node.js がどのバージョンの V8 エンジンで動作しているか調べる](/p/cf7eoyo/)
- [`nodemon` で Node.js サーバの再起動を自動化する](/p/rvghobv/)

### モジュール／パッケージ
- [Node.js 用モジュールの作り方（`require` でロード可能な Node モジュールを作成する）](/p/9fapzbb/)
- [Node.js が `require()` で検索するパスのまとめ](/p/ysmmmia/)
- [外部の JSON ファイルを読み込む (`require`)](/p/uf6zrk6/)
- [モジュール自身のコードにテストコードを記述する (`require.main`)](/p/3b52x28/)

### npm (Node Package Manager)
- [`package.json` による依存パッケージの管理方法まとめ](/p/9m96xqi/)
- [npm コマンドで Node.js のパッケージをインストール／アンインストールする (`npm install`, `uninstall`)](/p/2dua65s/)
- [npm コマンドでインストールされている Node.js 用パッケージの一覧を表示する (`npm list`, `ll`)](/p/7srzpt3/)
- [npm コマンドでインストール可能なパッケージを検索する (`npm search`)](/p/akf8eh9/)
- [npm コマンド用に HTTP プロキシを設定する (`npm config`)](/p/45tu88x/)
- [npm でインストール可能なパッケージのバージョンを調べる](/p/6u8xexa/)
- [npm run で任意のコマンドを実行する (`npm run`, `npm start`)](/p/9kiba2c/)
- [npm run のスクリプトの中でファイルのコピーを行う (`cpx`)](/p/j9dkm52/)
- [npm run のスクリプトの中でディレクトリを削除する (`rimraf`)](/p/wzufzn6/)
- [npm run のスクリプトを連続実行・並列実行する (`npm-run-all`)](/p/fzudboe/)


入出力 (I/O)
----
- [標準出力 (stdout)、標準エラー出力 (stderr) への出力](io/stdio.html)
- [コマンドライン引数（パラメータ）を取得する (process.argv)](io/cmdline-args.html)
- [キーボードからの入力を取得する (reaqline.question)](io/readline-from-keyboard.html)
- [外部プログラムを実行してその出力を取得する](io/child-process.html)

### ファイル／ディレクトリ
- [実行中の JavaScript ファイルのパスやディレクトリ名を取得する (`__filename`, `__dirname`)](io/filename.html)
- [エントリポイントとなった JavaScript ファイルのパスやディレクトリ名を取得する (`require.main.filename`)](io/main-filename.html)
- [パスを結合する (`path.join`)](io/path-join.html)
- [指定したパスがディレクトリかどうか調べる](io/is-directory.html)
- [カレントディレクトリ以下のファイルを列挙する](io/walk-dir.html)

#### CSV
- [CSV ファイルを読み込む](io/csv.html)

#### Yaml
- [Yaml ファイルを読み書きする (js-yaml)](io/yaml.html)

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
- [mongoose と Express で RESTful Web API を作成する](io/mongoose-rest.html)
- [mongojs で MongoDB を扱う](io/mongojs.html)


デバッグ／テスト
----
- [プログラムの実行時間を計測する `(console.time`, `console.timeEnd`)](time/console-time.html)
- [現在位置のスタックトレースを出力する (`console.trace`)](/p/oeka5t6/)


ネットワーク
----

### URL
- [Node.js で URL のパスを結合する (url-join)](https://maku.blog/p/oj9nzgt/)

### HTTP 通信 / HTTP サーバ
- [http 標準モジュールを使用して HTTP サーバを立てる](net/http-server.html)
- [http 標準モジュールを使用して HTTP 通信を行う](net/http-module.html)
- [node-fetch モジュールを使用して HTTP 通信を行う](net/node-fetch.html)
- [request モジュールを使用して HTTP 通信を行う (deprecated)](net/request-module.html)
- [request-promise モジュールを使用して HTTP 通信を行う (deprecated)](net/request-promise-module.html)
- [プロキシ経由の HTTP 通信を行う（global-agent モジュール）](net/global-agent.html)
- [プロキシ経由の HTTP 通信を行う（request モジュール）](net/proxy-in-request.html)

### WebSocket 通信
- [WebSocket のクライアントを作成する](net/websocket-client.html)
- [Node.js で WebSocket サーバを作る（簡単な echo サーバ）](net/websocket-echo-server.html)
- [Node.js で WebSocket サーバを作る（chat サーバ）](net/websocket-chat-server.html)
- [WebSocket サーバに接続してきたクライアントの情報を調べる](net/websocket-client-info.html)

### Web サーバ (Express)
- [Express の特徴](express/features.html)
- [Express をインストールする](express/install.html)
- [Express で Web サーバを作成する](express/web-server.html)
- [GET/POST メソッドで送られてきたデータを取得する](express/handle-get-and-post-data.html)
- [Express のルート定義の URL のパラメータで数字のみを受け付けるようにする](express/regexp-in-routing.html)
- [Express で next() により次のハンドラへ処理を委譲する](express/next.html)
- [RESTful API として検索用の URL を定義する (req.query)](express/url-for-search.html)
- [jQuery クライアントと Express サーバで JSON データを送受信する](express/exchange-json.html)
- [Express サーバの HTTP アクセスログを表示する (express.logger() / morgan)](express/access-log.html)
- [Express サーバの Internal Server Error を分かりやすく HTTP ページで表示する](express/internal-server-error.html)
- [Express の動作環境を切り替える（NODE_ENV 変数）](express/switch-env.html)
- [Express で静的ファイル（static ファイル）をホスティングする](express/static-file.html)
- [リバースプロキシ経由で Express サーバにアクセスしたときのクライアントのアドレスを取得する](express/reverse-proxy-addr.html)
- [Express でテンプレートエンジンとして Jade を使用する](express/jade-with-express.html)
- [Express で Basic 認証を有効にする](express/basic-authentication.html)
- [Express で CORS の設定を行う（クロスドメインでの Ajax を許可）](express/cors.html)

### リバースプロキシ
- [Node.js でお手軽リバースプロキシを作る](net/reverse-proxy.html)


Node.js の制御構文
----

### 例外処理
- [try ～ catch による例外処理の基本](/p/uqx7hq7/)
- [Uncaught Exception（未捕捉例外）をハンドルする](/p/4zwjrzz/)

### 非同期処理
- [`util.promisify` でコールバックベースの関数を Promise 化する](/p/62d6wuc/)
- async.js による非同期処理
  - [非同期処理のフロー制御を行う (1) async.js を使う準備](async-js1.html)
  - [非同期処理のフロー制御を行う (2) 複数の非同期処理の結果を同時に取得 (async.parallel)](async-js2.html)
  - [非同期処理のフロー制御を行う (3) 複数の非同期処理を指定した順に実行する (async.series, async.waterfall)](async-js3.html)

