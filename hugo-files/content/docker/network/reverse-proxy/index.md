---
title: "Docker Compose でリバースプロキシを立てて別の Docker Compose 内のコンテナに接続する"
url: "p/5f2j2hz/"
date: "2022-11-27"
tags: ["Docker"]
---

何をするか？
----

複数の Web アプリを 1 つの VPS（レンタルサーバー）でホスティングする場合、一般的にはリバースプロキシ（nginx など）を立てて、各 Web アプリのバックエンドに繋ぐことになります。
例えば、[nginx のバーチャルホスト機能](https://maku.blog/p/q8tw6c4/)を使って、`app1.example.com` というアドレスと `app2.example.com` というアドレスでアクセスされたときに、それぞれ別の Web アプリサーバー（バックエンド）に処理を振り分けます。
ここでは、リバースプロキシと 2 つの Web アプリを別々の Docker Compose で立ち上げて連携する方法を説明します。

{{< image w="1000" src="img-001.drawio.svg" title="リバースプロキシによる Docker Compose 連携" >}}

1 セットの Web アプリであれば、1 つの Docker Compose 内に関連するコンテナをすべて含めてしまうのが楽ですが、ここでは、独立した 2 つの Web アプリを 1 つの物理サーバー (VPS) 上で運用することを考えているので、別々の Docker Compose に分けています。
もちろん、各アプリが使用する DB コンテナなどは、それぞれの Docker Compose 内にある想定ですが、上記の図では省略しています。

ここで紹介しているソースコードは [GitHub](https://github.com/maku77/p-5f2j2hz) に置いてあります。


テスト用の事前準備（hosts ファイル）
----

今回使用する nginx の設定ファイルでは、`app1.example.com` と `app2.example.com` という仮のドメインを使用していますが、本来は実在するドメインでなければいけません。
この設定のままテストしたい場合は、OS の __`hosts`__ ファイルに次のようなエントリを追加して、ローカルホスト (127.0.0.1) を指すように設定しておきます。

```
# For development
127.0.0.1	app1.example.com
127.0.0.1	app2.example.com
```

例えば、macOS の場合は、次のように `hosts` ファイルを開いて編集します。

{{< code lang="console" title="hosts ファイルを編集（macOS の場合）" >}}
$ sudo vi /private/etc/hosts
{{< /code >}}

これで、Web ブラウザで `http://app1.example.com/` や `http://app2.example.com/` にアクセスしたときに、`http://localhost/` にアクセスしたのと同じように振る舞うようになります。


ブリッジネットワークの作成
----

Docker Compose で起動したコンテナ群は、デフォルトで `{ディレクトリ名}_default` という名前のブリッジネットワークに接続されますが、ここでは __`reverse-proxy-network`__ という名前のネットワークを生成して、そこにリバースプロキシと連携するすべてのコンテナを接続するようにします。

{{< code lang="console" title="Docker ネットワークの作成" >}}
$ docker network create reverse-proxy-network
{{< /code >}}

ネットワーク名は Docker ホスト内で一意になっている必要がありますが、1 つの Docker ホスト (= VPS) 内に複数のリバースプロキシを立てることはあまりないでしょうし、この名前 (`reverse-proxy-network`) で大丈夫だと思います。


Web アプリ用の Docker Compose
----

2 つの Web アプリ用の `docker-compose.yml` ファイルと、テスト用の `index.html` ファイルを、次のようなディレクトリ構造で用意します。

```
- webapp1/
   +-- docker-compose.yml
   +-- public/index.html  （I am webapp1 と表示するだけ）
- webapp2/
   +-- docker-compose.yml
   +-- public/index.html  （I am webapp2 と表示するだけ）
```

下記は、1 つ目の Web アプリの `docker-compose.yml` ファイルの設定例です。
2 つ目の Web アプリはほぼ同様の内容なので省略します（エイリアス名の `app1-container` というところが `app2-container` に変わるだけです）。

{{< code lang="yaml" title="webapp1/docker-compose.yml" >}}
services:
  app:
    image: nginx:alpine
    volumes:
      - ./public:/usr/share/nginx/html
    networks:
      default:
      reverse-proxy-network:
        aliases:
          - app1-container

networks:
  reverse-proxy-network:
    external: true
{{< /code >}}

ポイントは、外部で定義したネットワーク `reverse-proxy-network` に、nginx コンテナを接続しているところです。
__`external: true`__ を指定するのを忘れると、この Docker Compose 内に閉じたネットワーク (`webapp1_reverse-proxy-network`) が生成されてしまうので注意してください。
あと、バックエンド DB などのコンテナに接続できるようにするために、自動生成される `default` ネットワークにも繋いでおきます（実際のネットワーク名は `webapp1_default` などになります）。

さらに、リバースプロキシ側からこの nginx サーバーを参照しやすいように、コンテナのエイリアス名 __`app1-container`__ を設定しています。
これを設定しなくても、`webapp1-app-1` のようなディレクトリ名から自動生成されたコンテナ名でアクセスすることはできますが、ちょっと分かりにくいので、エイリアス名を付けておくことをおすすめします。
エイリアス名は、ネットワーク内で一意になっていれば十分です。
サービスのプロパティで `container_name: app1-container` のようにすると、コンテナ名自体を変更することができますが、コンテナ名は Docker ホスト内で一意でないといけないので、エイリアス名を使った方がよいでしょう。

ちなみに、この nginx サーバーは、同一ネットワーク内のリバースプロキシからのみ接続できれば良いので、Docker ホスト (VPS) 側へのポート公開設定は必要ありません。


リバースプロキシ用の Docker Compose
----

リバースプロキシ用の Docker Compose は次のようなディレクトリ構成で作成します。

```
- reverse-proxy/
   +-- docker-compose.yml
   +-- conf.d/example.com.conf （nginx の設定ファイル）
```

リバースプロキシの Compose ファイルでも、Web アプリ側と同じ `reverse-proxy-network` に接続するように設定します。
こちらは特にコンテナのエイリアス名などは設定する必要はありませんが、インターネット経由でアクセスできるように、Docker ホストのポートフォワード設定 (`80:80`) をしておく必要があります。

{{< code lang="yaml" title="reverse-proxy/docker-compose.yml" >}}
services:
  reverse-proxy:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./conf.d:/etc/nginx/conf.d
    networks:
      - reverse-proxy-network

networks:
  reverse-proxy-network:
    external: true
{{< /code >}}

nginx の設定ファイルは、バインドマウントで下記のファイルを使用するように設定しています。

{{< code lang="nginx" title="reverse-proxy/conf.d/example.com.conf" >}}
server {
    listen 80;       # IPv4
    listen [::]:80;  # IPv6
    server_name app1.example.com;
    location / {
        proxy_pass http://app1-container/;
    }
}

server {
    listen 80;       # IPv4
    listen [::]:80;  # IPv6
    server_name app2.example.com;
    location / {
        proxy_pass http://app2-container/;
    }
}
{{< /code >}}

この nginx のバーチャルホスト設定により、インターネット側からのアクセスが次のように各 Web アプリのコンテナに転送されるようになります。

- `http://app1.example.com/` でのアクセス → `http://app1-container/` へ転送
- `http://app2.example.com/` でのアクセス → `http://app2-container/` へ転送


Docker Compose の起動
----

2 つの Web アプリと、リバースプロキシの Docker Compose を次のように起動します。

{{< code lang="console" title="すべての Docker Compose を起動" >}}
# webapp1 ディレクトリで
$ docker compose up -d

# webapp2 ディレクトリで
$ docker compose up -d

# reverse-proxy ディレクトリで
$ docker compose up -d
{{< /code >}}

リバースプロキシ (nginx) の設定内で、Web アプリのコンテナ（エイリアス名）を参照しているため、上記のような順番で起動しなければいけないことに注意してください。

これで、Web ブラウザから `http://app1.example.com/` というアドレスでアクセスしたときは `I am webapp1`、`http://app2.example.com/` というアドレスでアクセスしたときは `I am webapp2` と表示されるはずです。

できたー ٩(๑❛ᴗ❛๑)۶ わーぃ

テストが終わって、Docker Compose をすべて停止したいときは、起動時と同様に各ディレクトリ内で `docker compose down` していくか、次のように `-p` オプションでプロジェクト名を指定して停止します。

{{< code lang="console" title="あと片づけ" >}}
$ docker compose -p reverse-proxy down
$ docker compose -p webapp1 down
$ docker compose -p webapp2 down
{{< /code >}}

おつかれ様でしたー。


（おまけ）コンテナ名ではなくポート番号で連携させる
----

上記の説明では、リバースプロキシとする nginx と、2 つの Web サーバーをコンテナ名で連携させましたが、Web サーバー側のポートを公開すれば、ポート番号で連携させることも可能です。
各 Web サーバーを単独で立ち上げてアクセスするケースがある場合は、こちらの方が都合がよいかもしれません。

例えば、2 つの Web サーバーをそれぞれ `8001` 番、`8002` 番ポートで公開すれば、リバースプロキシからは次のようなアドレスでアクセスできます（参考: [Docker コンテナからホスト側のサーバーにアクセスする (host.docker.internal)](/p/najs2ah/)）。

- `host.docker.internal:8001`
- `host.docker.internal:8002`

この場合、リバースプロキシの設定は次のようになります。

{{< code lang="nginx" title="reverse-proxy/conf.d/example.com.conf" >}}
server {
    listen 80;       # IPv4
    listen [::]:80;  # IPv6
    server_name app1.example.com;
    location / {
        proxy_pass http://host.docker.internal:8001/;
    }
}

server {
    listen 80;       # IPv4
    listen [::]:80;  # IPv6
    server_name app2.example.com;
    location / {
        proxy_pass http://host.docker.internal:8002/;
    }
}

{{< /code >}}

各コンテナ用の Compose ファイルは次のようになります。

{{< code lang="yaml" title="reverse-proxy/docker-compose.yml" >}}
services:
  reverse-proxy:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./conf.d:/etc/nginx/conf.d
{{< /code  >}}

{{< code lang="yaml" title="webapp1/docker-compose.yml" >}}
services:
  app:
    image: nginx:alpine
    volumes:
      - ./public:/usr/share/nginx/html
    ports:
      - "${PORT:-8001}:80"
{{< /code  >}}

{{< code lang="yaml" title="webapp2/docker-compose.yml" >}}
services:
  app:
    image: nginx:alpine
    volumes:
      - ./public:/usr/share/nginx/html
    ports:
      - "${PORT:-8002}:80"
{{< /code  >}}

{{% note title="Docker ポート公開に注意" %}}
現時点での Docker (ver.20) では、公開ポートの設定で `8001:80` と指定すると、iptables 設定に穴が開いてインターネット上にポート 8001 番が公開されてしまうことに注意してください。
内部的な設定の優先度の問題で、Ubuntu のファイアウォール (ufw) などで公開していないポートに関してもアクセス可能になってしまいます。
確実にホスト内からのアクセスに限定するには、`ports` フィールドの指定で、__`127.0.0.1:8001:80`__ のようにループバックアドレスを合わせて指定します。
{{% /note %}}

