---
title: "Docker Compose で環境変数を使用する (env_file, environment)"
url: "p/8r3cmu5/"
date: "2022-07-03"
tags: ["Docker"]
---

環境変数を参照する
----

Docker Compose の Compose ファイル (`docker-compose.yml`) 内では、次のように OS（シェル）の環境変数の値を参照することができます。

{{< code lang="yaml" title="例: 環境変数 TAG の値を参照する" >}}
services:
  web:
    image: "webapp:${TAG}"
{{< /code >}}

この仕組みを利用すると環境に依存する値をハードコードしなくて済むようになるため、汎用的な `docker-compose.yml` を記述できるようになります。
上記の例では、`${TAG}` のように記述していますが、多くのケースでは `$TAG` のようにカッコを省略することができます。
ドル記号 (`$`) そのものを扱いたい場合は、`$$` のように記述する必要があります。


.env ファイル
----

### .env ファイルは自動で読み込まれる

`docker compose up` コマンドは、プロジェクトディレクトリに置いてある環境ファイル __`.env`__ を読み込んでくれます。
`.env` ファイルには、次のように複数の環境変数を定義しておくことができます。

{{< code lang="ini" title=".env の記述例" >}}
# この行はコメント
MYAPP_PORT=3000
DB_PASSWORD=mypassword
{{< /code >}}

### 別の .env ファイルを参照する

`docker compose` コマンドの __`--env-file`__ オプションを指定すると、参照する `.env` ファイルを切り替えることができます。

{{< code lang="console" title="例: .env ではなく .env.prod を参照する" >}}
$ docker compose --env-file .env.prod up
{{< /code >}}

### 優先順位

シェル環境で同じ名前の環境変数がセットされている場合（例: `export MYAPP_PORT=4000`）は、そちらが優先して使われます。
つまり、`.env` ファイルで定義されている値は、シェル環境変数がセットされていない場合のデフォルト値のように扱われます。

環境変数の優先順位:

1. OS の環境変数（シェル環境）でセットした値。例えば、bash シェルであれば `export FOO=BAR` のように設定した値
2. プロジェクトディレクトリの `.env` ファイルで定義した値


環境変数が定義されていない場合の振る舞い
----

環境変数 `VARIABLE` が定義されていないときに、Compose ファイルの中で `${VARIABLE}` のように参照すると、空文字列 ("") として評価されます。
この振る舞いは次のような参照方法によって変更することができます。

### 環境変数が設定されていないときにデフォルト値を使用する

- {{< label-code "${VARIABLE:-default}" >}} 変数 `VARIABLE` がセットされていないか空文字のときに、`default` で指定された値を使用します。
- {{< label-code "${VARIABLE-default}" >}} 変数 `VARIABLE` がセットされていないときに、`default` で指定された値を使用します（`VARIABLE` に空文字がセットされているときは、そのまま空文字を使います）。

次の例では、web サービスのデフォルトのホスト側ポート番号を 8000 番に設定しています（コンテナ側のポート番号は 80 で固定です）。

{{< code lang="yaml" title="docker-compose.yml" >}}
services:
  web:
    image: nginx
    ports:
      - "${APP_PORT:-8000}:80"
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ docker compose up  # localhost:8000 でアクセスできるようになる
{{< /code >}}

次のように `APP_PORT` の値を明示すると、ホスト側のポート番号は 8000 ではなく、3000 が使われるようになります。

```console
$ APP_PORT=3000 docker compose up  # localhost:3000 でアクセスできるようになる
```

### 環境変数が設定されていないときにエラーにする

- {{< label-code "${VARIABLE:?err}" >}} 変数 `VARIABLE` がセットされていないか空文字のときに、`err` メッセージを表示して終了します（実際には `err` を省略して `${VARIABLE:?}` と書くだけでもそれっぽいエラーメッセージが表示されます）
- {{< label-code "${VARIABLE?err}" >}} 変数 `VARIABLE` がセットされていないときに、`err` メッセージを表示して終了します（`VARIABLE` に空文字がセットされているときは空文字を使います）

次の例では、PostgreSQL のパスワードを環境変数 `POSTGRES_PASSWORD` でセットしておくことを強制しています。

{{< code lang="yaml" title="docker-compose.yml" >}}
services:
  db:
    image: postgres
    restart: always
    environment:
      - POSTGRES_USER=${POSTGRES_USER:-root}
      - POSTGRES_PASSWORD=${POSTGRES_PASSWORD:?}
      - POSTGRES_DB=${POSTGRES_DB:-mydb}
{{< /code >}}

環境変数 `POSTGRES_PASSWORD` を設定せずにサービスを起動しようとすると、次のようにエラーになります。

```console
$ docker compose up

invalid interpolation format for services.db.environment.[]:
  "required variable POSTGRES_PASSWORD is missing a value: ".
  You may need to escape any $ with another $
```


各コンテナの環境変数を設定する
----

Compose ファイル (`docker-compose.yml`) の中では OS（シェル）の環境変数を参照できますが、それらがそのまま各コンテナに渡されるわけではありません。

### environment プロパティによる指定

各コンテナの環境変数を設定するには、各サービスの定義で __`environment`__ プロパティを指定する必要があります。
次の例では、`web` サービス（コンテナ）の環境変数 `DEBUG` の値を `1` にセットしています。
これは、`docker run -e DEBUG=1 ...` のようにコンテナを起動するのと同じ効果があります。

{{< code lang="yaml" title="docker-compose.yml" >}}
services:
  web:
    image: nginx
    ports:
      - "80:80"
    environment:
      - DEBUG=1
{{< /code >}}

ホスト側の環境変数（`.env` で定義したものを含む）の値をそのままコンテナに渡したいときは、次のように環境変数名だけを記述します。
これは、`docker run -e DEBUG ...` のようにコンテナを起動するのと同じ効果があります。

```yaml
    environment:
      - DEBUG
```

### env_file プロパティによる指定

コンテナに渡す環境変数の一覧を `.env` ファイルに記述しておき、そのファイル名を __`env_file`__ プロパティで指定する、という方法もあります。

{{< code lang="ini" title="web-vars.env" >}}
DB_NAME=mydb
DB_USER=root
DB_PASSWORD=mypassword
{{< /code >}}

{{< code lang="yaml" title="docker-compose.yml" >}}
services:
  web:
    image: nginx
    ports:
      - "80:80"
    env_file:
      - web-vars.env
{{< /code >}}

### 優先度

次のように、`env_file` と `environment` が両方指定されていて、同じ環境変数がセットされている場合は、Compose ファイルに直接記述された `environment` の方が優先されます。

```yaml
services:
  web:
    image: nginx
    ports:
      - "80:80"
    env_file:
      - web-vars.env
    environment:
      - DB_NAME=xxxxx
      - DB_USER=yyyyy
```

__`docker compose config`__ コマンドを実行すると、実際にどのような環境変数がコンテナに設定されるかを確認できます。

```console
$ docker compose config
name: myproject
services:
  web:
    environment:
      DB_NAME: xxxxx
      DB_PASSWORD: mypassword
      DB_USER: yyyyy
...（省略）...
```

