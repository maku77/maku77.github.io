---
title: 起動中の GitLab 関連サービスを調べる
created: 2014-05-15
---

`gitlab-ctl service-list` や `gitlab-ctl status` コマンドを使用すると、起動している GitLab 関連のサービスの一覧や状態を確認できます。

#### GitLab 関連サービスの一覧を表示

```
$ sudo gitlab-ctl service-list
nginx*
postgresql*
redis*
sidekiq*
unicorn*
```

#### GitLab 関連サービスの動作状態を確認

```
$ sudo gitlab-ctl status
run: nginx: (pid 11059) 597s; run: log: (pid 11058) 597s
run: postgresql: (pid 10971) 632s; run: log: (pid 10970) 632s
run: redis: (pid 10890) 638s; run: log: (pid 10889) 638s
run: sidekiq: (pid 11043) 599s; run: log: (pid 11042) 599s
run: unicorn: (pid 11031) 601s; run: log: (pid 11030) 601s
```

実際に `ps` コマンドで確認してみると、GitLab に関連するプロセスがたくさん起動していることが分かります。

```
$ ps axo '%U %c %a' | grep gitlab
root     runsvdir        runsvdir -P /opt/gitlab/service log: ......
root     svlogd          svlogd -tt /var/log/gitlab/redis
999      redis-server    /opt/gitlab/embedded/bin/redis-server 127.0.0.1:6379
root     svlogd          svlogd -tt /var/log/gitlab/postgresql
998      postgres        /opt/gitlab/embedded/bin/postgres -D /var/opt/gitlab/postgresql/data
root     svlogd          svlogd -tt /var/log/gitlab/unicorn
git      ruby            unicorn master -E production -c /var/opt/gitlab/gitlab-rails/etc/unicorn.rb /opt/gitlab/embedded/service/gitlab-rails/config.ru
root     svlogd          svlogd -tt /var/log/gitlab/sidekiq
git      ruby            sidekiq 2.17.0 gitlab-rails [0 of 25 busy]
root     svlogd          svlogd -tt /var/log/gitlab/nginx
root     nginx           nginx: master process /opt/gitlab/embedded/sbin/nginx -c /var/opt/gitlab/nginx/etc/nginx.conf
git      ruby            unicorn worker[0] -E production -c /var/opt/gitlab/gitlab-rails/etc/unicorn.rb /opt/gitlab/embedded/service/gitlab-rails/config.ru
git      ruby            unicorn worker[1] -E production -c /var/opt/gitlab/gitlab-rails/etc/unicorn.rb /opt/gitlab/embedded/service/gitlab-rails/config.ru
998      postgres        postgres: gitlab gitlabhq_production 127.0.0.1(60943) idle
maku     grep            grep --color=auto gitlab
```

