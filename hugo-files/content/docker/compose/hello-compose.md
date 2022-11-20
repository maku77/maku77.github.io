---
title: "Docker Compose の基本"
url: "p/qm5k2hx/"
date: "2022-07-03"
tags: ["Docker"]
---

Docker Compose とは？
----

Docker Compose (__`docker compose`__) を使うと、複数の Docker コンテナをまとめてコントロールすることができます。
各コンテナの起動時に必要となるパラメーターや連携方法を __`docker-compose.yml`__ という YAML ファイルにまとめて記述できるため、すべてのコンテナの起動を __`docker compose up`__ というシンプルなコマンドで行うことができます。

Docker Compose は複数のコンテナをコントロールすることを想定していますが、__単一のコンテナを起動するときにも便利__ です。
例えば、`Dockerfile` を使ったイメージビルド、ポート番号のマッピング、ネットワークの定義などを `docker-compose.yml` で定義しておいて、`docker compose up` コマンド一発で実行できます。

`docker-compose.yml` の中では[環境変数の値を参照できる](/p/8r3cmu5/)（例: `${APP_PORT}`）ため、環境ごとに異なる値をハードコードしなくて済みます。
また、`docker compose up` コマンドは、環境変数を定義した `.env` ファイルを自動的に読み込んでくれます。


単一のコンテナを起動してみる
----

Docker Compose で簡単な nginx サーバーを立ち上げてみます。
Docker Compose は、デフォルトで __カレントディレクトリ名をプロジェクト名として使用します__（`-p` オプションで任意のプロジェクト名を付けることもできます）。
ここでは、`myproject` というディレクトリを作って、その中に Compose ファイル (`docker-compose.yml`) を配置することにします。

```console
$ mkdir myproject
$ cd myproject
```

次のようなシンプルな Compose ファイルを作成します。

{{< code lang="yaml" title="docker-compose.yml" >}}
services:
  web:
    image: nginx
    ports:
      - "80:80"
{{< /code >}}

各サービス（コンテナ）の定義は、__`services`__ プロパティの下に記述していきます。
この例では、`web` という名前のサービスを 1 つだけ定義しており、その下の __`image`__ プロパティで `nginx` のイメージを使うよう指示しています。
さらに、__`ports`__ プロパティで、ホストの 80 番ポートへのアクセスをコンテナの 80 番ポートへ転送しています。
この定義で生成されるコンテナの名前は `web` ではなく、プロジェクト名や連番が付加された __`myproject-web-1`__ という名前になります。

Compose ファイル (`docker-compose.yml`) のあるディレクトリで __`docker compose up`__ コマンドを実行すると、コンテナを作成＆起動できます。

```console
$ docker compose up
```

停止したいときは、<kbd>Ctrl-C</kbd> を入力するとすべてのコンテナが停止します（今回は 1 コンテナだけですが）。
初回起動時には、`nginx` のイメージなどがダウンロードされるため少し時間がかかりますが、しばらくすると `myproject-web-1` コンテナが起動し、`http://localhost/`（あるいは `http://127.0.0.1/`）で Web ページにアクセスできるようになります。

別のターミナル（端末）を開いて次のように実行すると、`myproject-web-1` というコンテナが起動していることを確認できます。

```console
$ docker container ls
CONTAINER ID  IMAGE  COMMAND                 CREATED         STATUS        PORTS               NAMES
cf2aa5a2b831  nginx  "/docker-entrypoint.…"  10 minutes ago  Up 5 minutes  0.0.0.0:80->80/tcp  myproject-web-1
```

- 参考: [Docker Compose をデタッチモードで起動する (docker compose up -d)](/p/94m3izf/)


応用
----

### Dockerfile からイメージをビルドする

独自の `Dockerfile` を使ってイメージを構築してコンテナを起動したいときは、サービスの定義の中で `image` プロパティの代わりに __`build`__ プロパティを使用します。
設定する値は、`Dockerfile` があるディレクトリのパスです。
次の `docker-compose.yml` は、カレントディレクトリ (`.`) にある Dockerfile を使ってイメージをビルドし、コンテナを起動します。

```yaml
services:
  web:
    build: .
    ports:
      - "3000:3000"
```

### 複数のコンテナを起動する

`services` プロパティ以下には、複数のサービスを定義することができます。
次の `docker-compose.yml` では、`web` と `redis` の 2 つのサービスを定義しています。

```yaml
services:
  web:
    build: .
    ports:
      - "3000:3000"
  redis:
    image: "redis:alpine"
```

`web` サービスは独自の `Dockerfile` からイメージをビルドし、`redis` サービスは Docker Hub レジストリ上の `redis:alpine` イメージを使うよう指定しています。
`redis` サービスは同一の Docker ネットワーク内にある `web` サービスから参照できればよいので、`ports` プロパティによるポート転送設定は必要ありません。

`docker compose up` を実行すると、次のような名前の 2 つのコンテナが起動します。

- `<PROJECT-NAME>-web-1`
- `<PROJECT-NAME>-redis-1`

ひとつの Compose ファイルで定義したサービスは同一の Docker ネットワーク（例: `myproject_default`）に配置されるため、それぞれのコンテナは互いにコンテナ名で参照することができます。
上記の例でいうと、`web` サービスの中では、`redis` というホスト名で Redis サービスを参照できます。
Web アプリの実装の中から、Redis コンテナに割り当てられた IP アドレスを意識しなくて済みます（逆に言うと、変化しやすい IP アドレスをもとに通信すべきではありません）。

- 参考: [Docker のネットワークについて理解する](/p/7fjnqtw/)

