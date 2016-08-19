---
title: GitLab が使用する Unicorn 用のポート番号を変更する
created: 2014-05-16
---

GitLab 内部では、Rails サーバの Unicorn を使用しています。
このサーバはデフォルトで `8080` ポートで待ち受けるため、別の用途で `8080` ポートを使う Web サーバを立てていたりすると、GitLab にアクセスしたときに **HTTP 502 (Bad Gateway)** のエラーが発生します。
Unicorn の使用するポート番号を変更するには、以下のファイルを修正します。

#### /var/opt/gitlab/gitlab-rails/etc/unicorn.rb

```
listen "127.0.0.1:8080", :tcp_nopush => true
```

上記の `8080` というところを `8090` などに変更します。

さらに、各クライアントからの ssh 接続などを処理する `gitlab-shell` が、上記のサーバが提供する Web API を使用していたりするので、`gitlab-shell` の設定も書き換える必要があります。
これをやっておかないと、`git clone` するときに、fatal error が発生して **Access denied** と言われてしまいます。

#### /opt/gitlab/embedded/service/gitlab-shell/config.yml

```
gitlab_url: "http://localhost:8080"
```

上記の `8080` というところを `8090` などに変更します。

あとは、GitLab のサービス郡を再起動すれば設定が反映されます。

```
$ sudo gitlab-ctl restart
```

