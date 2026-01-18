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

### 標準入出力／外部プログラム実行
- [標準出力 (stdout)、標準エラー出力 (stderr) への出力](/p/sffabwx/)
- [コマンドライン引数（パラメータ）を取得する (`process.argv`)](/p/9i67itz/)
- [キーボードからの入力を取得する (`readline.question`)](/p/9pjvmzj/)
- [外部プログラムを実行してその出力を取得する (`child_process`)](/p/8kgdvvd/)

### ファイル／ディレクトリ
- [実行中の JavaScript ファイルのパスやディレクトリ名を取得する (`__filename`, `__dirname`)](/p/d48fxh9/)
- [エントリポイントとなった JavaScript ファイルのパスやディレクトリ名を取得する (`require.main.filename`)](/p/jm48fwe/)
- [パスを結合する (`path.join`)](/p/32q8w98/)
- [指定したパスがディレクトリかどうか調べる (`fs.Stats.isDirectory`)](/p/af2r6mq/)
- [カレントディレクトリ以下のファイルを列挙する (`fs.readdir`)](/p/ke25m82/)

#### CSV / Yaml
- [CSV ファイルを読み込む (`csv`)](/p/r7ckr36/)
- [Yaml ファイルを読み書きする (`js-yaml`)](/p/fpxsu6v/)

### SQLite データベース
- [`node-sqlite3` で SQLite データベースを扱う](/p/oxxpf3h/)

### MongoDB データベース
- [`mongoose` で MongoDB を扱う](/p/wfrqz8r/)
  - [実装例: `mongoose` と Express で RESTful Web API を作成する](/p/ttx5vvz/)
- [`mongojs` で MongoDB を扱う](/p/exs9wor/)


デバッグ／テスト
----
- [プログラムの実行時間を計測する (`console.time`, `console.timeEnd`)](time/console-time.html)
- [現在位置のスタックトレースを出力する (`console.trace`)](/p/oeka5t6/)


ネットワーク
----

### URL
- [Node.js で URL のパスを結合する (`url-join`)](https://maku.blog/p/oj9nzgt/)

### HTTP 通信 / HTTP サーバ
- [http 標準モジュールを使用して HTTP サーバを立てる](/p/7cmeoeh/)
- [http 標準モジュールを使用して HTTP 通信を行う](/p/vo8hdpa/)
- [node-fetch モジュールを使用して HTTP 通信を行う](/p/6hndyxs/)
- [request モジュールを使用して HTTP 通信を行う (deprecated)](/p/5y9mne4/)
- [request-promise モジュールを使用して HTTP 通信を行う (deprecated)](/p/r8qw3qs/)
- [プロキシ経由の HTTP 通信を行う（global-agent モジュール）](/p/vwu58as/)
- [プロキシ経由の HTTP 通信を行う（request モジュール）](/p/y2oxwej/)

### WebSocket 通信
- [WebSocket のクライアントを作成する](/p/iuah33v/)
- [Node.js で WebSocket サーバを作る（簡単な echo サーバ）](/p/zrifcfx/)
- [Node.js で WebSocket サーバを作る（簡単な chat サーバ）](/p/hfrkkyx/)
- [WebSocket サーバに接続してきたクライアントの情報を調べる](/p/hn2896q/)

### Web サーバ (Express)
- [Express の特徴とインストール](/p/h5yxgh9/)
- [Express で Web サーバを作成する](/p/7ee8e5h/)
- [Express で GET/POST リクエストをハンドルする](/p/7wjhpud/)
- [Express のルート定義の URL のパラメータで数字のみを受け付けるようにする](/p/f2ow3kr/)
- [Express の `next()` メソッドで次のハンドラへ処理を委譲する](/p/izz3fkg/)
- [Express で検索用の URL を定義する (`req.query`)](/p/ryqsrbv/)
- [jQuery クライアントと Express サーバで JSON データを送受信する例](/p/twrqbe3/)
- [Express サーバの HTTP アクセスログを表示する (`express.logger()`, `morgan`)](/p/i5h4yx6/)
- [Express サーバの Internal Server Error を分かりやすく HTTP ページで表示する](/p/4duroxg/)
- [Express の動作を環境ごとに切り替える（`NODE_ENV` 変数）](/p/3eqz2eo/)
- [Express で静的ファイル（static ファイル）をホスティングする](/p/sururg6/)
- [リバースプロキシ経由で Express サーバにアクセスしたときのクライアントのアドレスを取得する (`x-forwarded-for`)](/p/hvoegru/)
- [Express でテンプレートエンジンを使用する (Jade)](/p/gnewjs4/)
- [Express で Basic 認証を有効にする (`basicAuth`)](/p/mv6umxp/)
- [Express で CORS 設定を行う（クロスドメインでのアクセスを許可する）](/p/unsuyqg/)

### リバースプロキシ
- [Node.js でお手軽リバースプロキシを作る](/p/7i26pjh/)


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

