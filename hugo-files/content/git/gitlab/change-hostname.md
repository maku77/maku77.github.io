---
title: "Gitメモ: GitLab リポジトリの Clone URL として表示されるホスト名を変更する"
url: "p/b9fmgxq/"
date: "2014-05-19"
tags: ["git"]
aliases: [/git/gitlab/change-hostname.html]
---

GitLab の Clone URL などに表示されているアドレス内のホスト名は、設定ファイル `gitlab.yml` に記述されたものが使用されます。
この設定ファイルは、パッケージを使って GitLab をインストールした場合は、以下の場所にあります。

- `/opt/gitlab/embedded/service/gitlab-rails/config/gitlab.yml`

{{< code lang="yaml" title="gitlab.yml の抜粋" >}}
...
host: YourHostName  # これを変更
{{< /code >}}

変更後、以下のように GitLab 関連のサービス群を再起動すれば、新しいホスト名が反映されます。

```console
$ sudo gitlab-ctl restart
```

