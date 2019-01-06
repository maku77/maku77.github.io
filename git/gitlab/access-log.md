---
title: "GitLab サーバのアクセスログを調べる"
date: "2014-05-16"
---

GitLab サーバ関連のログファイルの場所
----

GitLab サーバのログファイルは、下記のようなディレクトリに出力されるようになっています。

```
/var/log/gitlab/gitlab-rails/
/var/log/gitlab/gitlab-shell/
/var/log/gitlab/nginx/
/var/log/gitlab/postgresql/
/var/log/gitlab/redis/
/var/log/gitlab/sidekiq/
/var/log/gitlab/unicorn/
```

これらのログの一部は、Admin 権限の付いている GitLab ユーザであれば、ブラウザ上で **Admin area** の **Logs** タブから確認することができます。


nginx サーバへのアクセスログ
----

GitLab を deb パッケージでインストールした場合は、GitLab ウェブサーバ (nginx) へのアクセスログは以下のファイルに記録されています。

```
/var/log/gitlab/nginx/gitlab_access.log
/var/log/gitlab/nginx/gitlab_error.log
```

上記のパス設定は、以下のファイルで行われています。

```
/var/opt/gitlab/nginx/etc/gitlab-http.conf
```


実行された Git コマンドのログ (gitlab-shell.log)
----

下記にログファイルの位置が設定されています。

#### /opt/gitlab/embedded/service/gitlab-shell/config.yml

```yml
# Log file.
# Default is gitlab-shell.log in the root directory.
log_file: "/var/log/gitlab/gitlab-shell/gitlab-shell.log"
```

