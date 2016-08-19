---
title: GitLab リポジトリの Clone URL として表示されるホスト名を変更する
created: 2014-05-19
---

GitLab の Clone URL などに表示されているアドレス内のホスト名は、`gitlab.yml` の設定ファイルに記述されたものが使用されます。
この設定ファイルは、Package を使って GitLab をインストールした場合は、以下の場所にあります。

- `/opt/gitlab/embedded/service/gitlab-rails/config/gitlab.yml`

#### gitlab.yml（抜粋）

```yml
...
host: YourHostName  # これを変更
```

変更後、以下のように GitLab 関連のサービス郡を再起動すれば、新しいホスト名が反映されます。

```
$ sudo gitlab-ctl restart
```

