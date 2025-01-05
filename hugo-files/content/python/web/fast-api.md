---
title: "Python の FastAPI フレームワークで Web API を実装する"
url: "p/mq4c72g/"
date: "2025-01-05"
tags: ["Python", "FastAPI"]
---

Python の [FastAPI フレームワーク](https://fastapi.tiangolo.com/)を使うと、高性能な Web API を簡単に実装できます。


FastAPI で Hello World
----

まずは、FastAPI で Hello World を返す Web API を実装してみます。
ここでは、プロジェクトのパッケージ管理に `uv` を使います。
`uv` を使うと、仮想環境 (venv) を意識せずに済むのでとても楽です（参考: [uv の基本](/p/fjsfjpw/)）。

{{< code lang="console" title="プロジェクトの作成と FastAPI のインストール（ライブラリ＆CLIツール）" >}}
$ uv init hello-api
$ cd hello-api
$ uv add fastapi --extra standard
{{< /code >}}

`uv add fastapi` するときに **`--extra standard`** オプションを指定すると、API サーバーを立ち上げるための **`fastapi` コマンド**（`fastapi-cli`、`uvicorn` パッケージ）なども一緒にインストールされます。
従来の `pip` でインストールするなら、`pip install fastapi[standard]` とします。

`uv run` 経由で `fastapi` コマンドが実行できるか確認しておきます。

{{< code lang="console" title="fastapi コマンドのバージョン確認" >}}
$ uv run fastapi --version
FastAPI CLI version: 0.0.7
{{< /code >}}

Web API 実装として、以下のようなファイルを作成します。

{{< code lang="python" title="main.py" >}}
from fastapi import FastAPI

app = FastAPI()


@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: str | None = None):
    return {"item_id": item_id, "q": q}
{{< /code >}}

FastAPI サーバーを立ち上げるには、次のように **`fastapi dev`** コマンド、あるいは **`fastapi run`** コマンドを使用します。
開発時は前者のコマンドを使って、development モードで立ち上げます。

{{< code lang="console" title="FastAPI サーバーの立ち上げ" >}}
$ uv run fastapi dev main.py  # development モードで起動
$ uv run fastapi run main.py  # production モードで起動
{{< /code >}}

これで、下記のようなアドレスにアクセスすることで、API のレスポンスとして JSON データを取得できます。

- `http://127.0.0.1:8000/`
- `http://127.0.0.1:8000/items/123`
- `http://127.0.0.1:8000/items/123?q=hello`

さらに、Web ブラウザで `http://127.0.0.1:8000/docs` にアクセスすると、自動生成された API ドキュメント ([Swagger UI](https://github.com/swagger-api/swagger-ui)) を参照できます。
FastAPI サーバーを development モードで立ち上げている場合は、ソースコードの更新時に自動的にリロードしてくれます。


Docker コンテナとして起動できるようにする
----

{{% private %}}
- 参考: https://github.com/astral-sh/uv-fastapi-example
{{% /private %}}

Web API サーバーをデプロイするときは、Docker コンテナとして起動できるようにしておくと便利です。
下記は `uv` で管理されている FastAPI アプリケーションの Docker イメージを作成するための `Dockerfile` です。

{{< code lang="dockerfile" title="Dockerfile" >}}
FROM python:3-slim-bullseye

# Install uv
COPY --from=ghcr.io/astral-sh/uv:latest /uv /bin/uv

# Copy the application into the container
COPY . /app

# Install the application dependencies
WORKDIR /app
RUN uv sync --frozen --no-cache

# Run the application
CMD ["/app/.venv/bin/fastapi", "run", "main.py", "--port", "8080"]
{{< /code >}}

`Dockerfile` を用意できたら、次のようにビルドして `hello-api` イメージを作成します。

{{< code lang="console" title="コンテナイメージのビルド" >}}
$ docker image build -t hello-api .
{{< /code >}}

次のようにコンテナを起動すると、`http://127.0.0.1:8080/` で Web API にアクセスできるようになります。

{{< code lang="console" title="コンテナの起動" >}}
$ docker container run --rm -p 8080:8080 hello-api
{{< /code >}}

イメージサイズは 300MB 弱ですが、必要なくなったら削除しておきます。

{{< code lang="console" title="コンテナイメージの削除" >}}
$ docker image rm hello-api
{{< /code >}}

