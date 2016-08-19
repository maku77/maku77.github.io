---
title: GitLab をインストールする
created: 2014-05-14
---


ここでは、一番楽な Package (GitLab 6.8.2) を使ったインストールを行います。
ダウンロードには時間がかかるけど、インストールは簡単に数分で終わります。

* 確認済み Version
  - Ubuntu 13.10 + GitLab 6.8.2
  - Ubuntu 12.04 + GitLab 6.8.2


Deb パッケージによる GitLab のインストール
----

- [https://www.gitlab.com/downloads/](https://www.gitlab.com/downloads/)

から、Ubuntu 用の deb パッケージをダウンロードして、以下のようにインストールします。

```
$ sudo dpkg -i gitlab_6.8.2-omnibus-1_amd64.deb
```

あるいは、

```
$ sudo gdebi gitlab_6.8.2-omnibus-1_amd64.deb
...
Thank you for installing GitLab!
You can configure GitLab for your system by running the following command:
sudo gitlab-ctl reconfigure
```

最後に設定しろと出るので設定を実行します（一分強で終わりました）。

```
$ sudo gitlab-ctl reconfigure
...
Chef Client finished, 112/120 resources updated in 72.205821345 seconds
gitlab Reconfigured!
```

Web ブラウザで [http://localhost/](http://localhost/) にアクセスすると、以下のようにログイン画面が表示されるはずです。
楽ちん！

管理用 ID とパスワードは、以下のようになっているので、一度サインインしてパスワードを変更しておきます。

```
ID: admin@local.host
PW: 5iveL!fe
```

この初期パスワードは、[https://gitlab.com/gitlab-org/gitlab-ce/blob/master/README.md](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/README.md) に掲載されています。


おまけ（どんなファイルが生成されたか）
----

インストールがすべて自動で終わってしまって、ちょっと気持ち悪いので、簡単にどんなファイルがインストールされているのかだけ確認しておきます。
`find` してみると、こんな感じのファイルがインストールされています。

```
$ sudo find . -name 'gitlab*'
（一部抜粋）
./var/opt/gitlab/gitlab-shell
./var/opt/gitlab/gitlab-rails
./var/opt/gitlab/nginx/etc/gitlab-http.conf
./var/log/gitlab/gitlab-shell
./var/log/gitlab/gitlab-rails
./var/log/gitlab/nginx/gitlab_access.log
./var/log/gitlab/nginx/gitlab_error.log
./var/chef/backup/var/opt/gitlab
./opt/gitlab/embedded/service/gitlab-shell
./opt/gitlab/embedded/service/gitlab-rails
./opt/gitlab/embedded/cookbooks/gitlab/recipes/gitlab-shell.rb
./opt/gitlab/embedded/cookbooks/gitlab/recipes/gitlab-rails.rb
./opt/gitlab/embedded/cookbooks/gitlab/libraries/gitlab.rb
./opt/gitlab/embedded/cookbooks/gitlab/templates/default/gitlab-shell-config.yml.erb
./opt/gitlab/embedded/cookbooks/gitlab/templates/default/gitlab.yml.erb
./opt/gitlab/bin/gitlab-rails
./opt/gitlab/bin/gitlab-ctl
./opt/gitlab/bin/gitlab-rake
./usr/bin/gitlab-ctl
./usr/bin/gitlab-rake
./home/ohta/z/temp/gitlab_6.8.2-omnibus-1_amd64.deb
./etc/gitlab
./etc/gitlab/gitlab-secrets.json
./etc/init/gitlab-runsvdir.conf
```

