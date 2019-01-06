---
title: "GitLab のデータをバックアップ／リストアする"
date: "2014-05-17"
---

GitLab データのアーカイブ
----

以下のようにすると、バックアップファイルが作成されます。

```
$ sudo gitlab-rake gitlab:backup:create
...
done
```

ファイ名のプレフィックスとしてタイムスタンプが付くため、前回のバックアップファイルが上書きされることはありません。

```
$ sudo ls /var/opt/gitlab/backups
1402312561_gitlab_backup.tar
```


適切なメディアへバックアップ
----

`/var/opt/gitlab/backups` に作成されたアーカイブファイルは、適切な場所に退避しておきましょう。
下記は HOME ディレクトリにコピーする例ですが、できれば、サーバ PC が故障したときに備えて外部のメディアなどにバックアップしておくことをお勧めします。

```
$ mkdir -p ~/gitlab_backup
$ sudo cp -u /var/opt/gitlab/backups/1402312561_gitlab_backup.tar ~/gitlab_backup/
```

以下のようなシェルスクリプトを作っておくのもよいでしょう。

#### gitlab-backup.sh

```shell
#!/bin/bash
sudo gitlab-rake gitlab:backup:create
mkdir -p ~/gitlab_backup
sudo sh -c 'cp -u /var/opt/gitlab/backups/*.tar ~/gitlab_backup/'
```

