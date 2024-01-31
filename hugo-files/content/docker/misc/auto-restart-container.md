---
title: "サーバー再起動時に Docker コンテナを自動起動する"
url: "p/rh8qm2n/"
date: "2024-01-31"
tags: ["Docker"]
---

VPS などのサーバーを再起動したときに Docker コンテナを自動起動するには、`docker container run` コマンドでコンテナを起動するときに、__`--restart`__ フラグを付けて __再起動ポリシー__ を設定します。

```console
$ docker run -d --restart always redis
```

正確には VPS サーバーの再起動時というより、Docker デーモンの再起動時に、コンテナの再起動ポリシーによりコンテナが再起動されるという流れになります。
VPS サーバーが起動したときに Docker デーモンを自動起動するようにする設定は、`systemd` などのプロセスマネージャーで別途設定しておく必要があります。

{{% note %}}
VPS サーバー再起動時の Docker コンテナー自動起動の流れ:

1. プロセスマネージャー (systemd) により Docker デーモンが起動する
2. Docker の再起動ポリシーによりコンテナが起動する
{{% /note %}}

