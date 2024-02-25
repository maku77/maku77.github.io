---
title: "Docker コンテキストを切り替えてリモートホスト上で Docker コマンドを実行する"
url: "p/qatbs9p/"
date: "2022-11-26"
tags: ["Docker", "SSH"]
---

{{% private %}}
- https://matsuand.github.io/docs.docker.jp.onthefly/engine/context/working-with-contexts/
- https://matsuand.github.io/docs.docker.jp.onthefly/cloud/aci-integration/
- https://matsuand.github.io/docs.docker.jp.onthefly/cloud/ecs-integration/
{{% /private %}}

Docker コンテキストとは？
----

Docker コンテキストは、ひとことで言うと、Docker CLI で入力したコマンド (`docker`) の操作対象ホストを切り替えるための仕組みです。

{{< image w="700" src="img-001.drawio.svg" title="Docker コンテキストの切り替え" >}}

例えば、{{< mm/ad/conoha-vps-text "Conoha" >}} などの __VPS サーバー__ や、__Azure (ACI)__、__AWS (ECS)__ といったクラウドサービス上の Docker エンジンに対して、ローカルホストから `docker` コマンドを実行できるようになります。
カレントコンテキストは、`docker compose use` コマンドで簡単に切り替えることができるので、ローカルでのコンテナのテストが終わったら、コンテキストを Azure (ACI) に切り替えてクラウド環境上で `docker compose up` するといったことが簡単にできます。

当然、接続先のコンテナサービスごとに接続プロトコルは異なるのですが、Docker は標準で Azure や AWS をターゲットとしたコンテキストの作成に対応しており、実際に `docker` コマンドを実行するときは接続プロトコルを意識しないで済むようになっています。
ここでは、より汎用的な SSH 接続を用いるコンテキストを作成し、リモートホスト上の Docker エンジンに対して Docker コマンドを実行してみます。

前提条件:

- リモートホストに Docker がインストールされていること
- リモートホストに SSH キーで SSH 接続できるようなっていること（参考: [SSH の使い方](https://maku.blog/p/gwnatcs/)）
- 接続先のユーザーが `sudo` なしで `docker` コマンドを実行できるようなっていること（参考: [docker グループへの追加](/p/an7o5m3/)）


デフォルト・コンテキスト
----

Docker Desktop をインストールすると、デフォルトのコンテキストとして、__`default`__ という名前のコンテキストが作成されます。
Docker コンテキストの一覧を表示するには、__`docker context ls`__ コマンドを使用します。

{{< code lang="console" title="Docker コンテキストの一覧を確認する" >}}
$ docker context ls
NAME        TYPE   DESCRIPTION                               DOCKER ENDPOINT               KUBERNETES ENDPOINT   ORCHESTRATOR
default *   moby   Current DOCKER_HOST based configuration   unix:///var/run/docker.sock                         swarm
{{< /code >}}

`default` コンテキストのエンドポイントは `unix:///var/run/docker.sock` となっており、これはローカルホスト上の Docker デーモンに対して Docker コマンドを実行することを示しています。


リモートホストへの SSH 接続設定
----

SSH 接続に使用するユーザー情報などは、__`~/.ssh/config`__ ファイルにまとめて記述しておくと便利です。
というか、これを使わないと、細かい接続設定を行うのが困難なので作成しておきましょう。

{{< code title="~/.ssh/config" >}}
Host conoha
    Hostname example.com
    Port 22
    User maku
    IdentityFile ~/.ssh/id_ed25519
{{< /code >}}

ここでは、{{< mm/ad/conoha-vps-text "Conoha VPS" >}} を使う想定で、設定名を `conoha` にしましたが、このあたりは自由に決めてください。
SSH キー（秘密鍵）にパスフレーズが設定されている場合は、SSH agent に秘密鍵とそのパスフレーズを設定しておく必要があります。

{{< code lang="console" title="SSH agent に秘密鍵を登録しておく" >}}
$ ssh-add ~/.ssh/id_ed25519
Enter passphrase for /Users/maku/.ssh/id_ed25519: （秘密鍵のパスフレーズを入力）
Identity added: /Users/maku/.ssh/id_ed25519 (maku@macbook.local)
{{< /code >}}


SSH 接続用の Docker コンテキストを作成する
----

新しい Docker コンテキストを作成するには、__`docker context create`__ コマンドを使用します。
`--docker` オプションを次のように指定すると、SSH 接続用のコンテキストを作成できます。

{{< code lang="console" title="Docker コンテキストを作成する" >}}
$ docker context create --docker "host=ssh://conoha" my-context
{{< /code >}}

`ssh://conoha` という接続 URI では、前述の `~/.ssh/config` で設定した名称を使用していることに注意してください。
`ssh://maku@example.com:22` のように、明示的な URI を指定することもできます。

次のように実行すると、新しいコンテキスト `my-context` が作成されていることを確認できます。

```console
$ docker context ls
NAME         TYPE   DESCRIPTION                               DOCKER ENDPOINT               KUBERNETES ENDPOINT   ORCHESTRATOR
default *    moby   Current DOCKER_HOST based configuration   unix:///var/run/docker.sock                         swarm
my-context   moby                                             ssh://conoha
```

この段階では、まだカレントコンテキストは `default` のままです。


コンテキストを切り替えて Docker コマンドを実行する
----

使用する Docker コンテキストを切り替えるには、__`docker context use`__ コマンドを使用します。

{{< code lang="console" title="コンテキストを my-context に切り替える" >}}
# コンテキストを切り替え
$ docker context use my-context
my-context

# カレントコンテキストを確認（アクティブなコンテキストに * 印が付いている）
$ docker context ls
NAME           ...(省略)...
my-context *   ...(省略)...
default        ...(省略)...

# 次のようにカレントコンテキスト名だけ表示することも可能
$ docker context show
my-context
{{< /code >}}

あとは、いつも通り `docker` コマンドを実行すると、SSH 接続先の Docker ホストで処理されるようになります。

```console
$ docker container run --rm hello-world
```

例えば、リモートホスト側で次のような感じで Web サーバーを起動し、ローカルホスト側からアクセスできるようになります（もちろんポートが適切に開放されている必要があります）。


```console
# リモートホスト上の Web サーバー（nginx コンテナ）を起動
$ docker container run --rm -d -p 8000:80 --name web nginx

# ローカルホストからアクセス
$ curl http://example.com:8000

# リモートホスト上の Web サーバーを停止
$ docker container stop web
```

上記の例では、あらかじめ `docker context use` コマンドで使用するコンテキストを切り替えておきましたが、`docker` コマンド実行時に __`--context`__ オプションでコンテキスト名を指定することもできます。
次のように実行すれば、各コンテキストで使用する Docker ホストの詳細情報を確認できます。

{{< code lang="console" title="コンテキストを明示して実行" >}}
$ docker --context default info     # デフォルトコンテキスト（ローカルホスト）で実行
$ docker --context my-context info  # 今回作成したコンテキスト（リモートホスト）で実行
{{< /code >}}

本番環境用のコンテキストは常に選択していると危険なので、`--context` オプションでのみ使用した方がよいかもしれません。

```console
$ docker --context production compose up -d
```


Docker コンテキストを削除する
----

必要なくなった Docker コンテキストは、__`docker context rm`__ コマンドで削除できます。

```console
$ docker context use default    # デフォルトコンテキストに戻しておく
$ docker context rm my-context  # 不要なコンテキストを削除
$ docker context ls             # コンテキストの一覧を確認
NAME         ...(省略)...
default *    ...(省略)...
```

