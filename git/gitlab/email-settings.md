---
title: GitLab からの通知メールを設定する
created: 2014-05-20
---

設定ファイルのパス
----

GitLab サーバからの通知メールの設定は、以下のファイルで行います。

* パッケージでインストールした場合
  * `/opt/gitlab/embedded/service/gitlab-rails/config/environments/production.rb`
* 手動でインストールした場合
  * `/home/git/gitlab/config/environments/production.rb`

sendmail でメール送信する場合の設定方法
----

GitLab のデフォルト設定では、通知メールはローカルにインストールされた `sendmail` で送信されるようになっています。
`sendmail` がインストールされていない場合はインストールしておく必要があります。

```
$ sudo apt-get install sendmail
```

そして、`production.rb` に下記のように記述して有効化します（GitLab 6.8.2 のデフォルト設定）。

```ruby
config.action_mailer.delivery_method = :sendmail
config.action_mailer.sendmail_settings = {
  location: '/usr/sbin/sendmail',
  arguments: '-i -t'
}
```

設定を終えたら、GitLab のサービス群を再起動して完了です。

```
$ sudo gitlab-ctl restart
```


外部の SMTP サーバでメール送信する場合の設定方法
----

ホスト PC 内の sendmail を使用するのではなく、外部の SMTP サーバを使用して GitLab の通知メールを送ることもできます。
ここでは、Gmail の SMTP サーバを使用する設定例を示します。
`production.rb` に下記のように記述します。

```ruby
config.action_mailer.delivery_method = :smtp
config.action_mailer.smtp_settings = {
  :address              => "smtp.gmail.com",
  :port                 => 587,
  :domain               => 'gmail.com',
  :user_name            => 'sample@sample',  # 省略可能
  :password             => 'password',       # 省略可能
  :authentication       =>  :plain,          # 省略可能
  :enable_starttls_auto => true              # 省略可能
}
```


メールの送信者情報を変更する
----

GitLab からの通知メールで使われる送信者アドレスは、以下の場所で設定されています。

#### /opt/gitlab/embedded/service/gitlab-rails/config/gitlab.yml

```ruby
## Email settings
# Email address used in the "From" field in mails sent by GitLab
email_from: gitlab@host.name
```


参考
---

* http://elijahpaul.co.uk/using-an-smtp-server-with-gitlab/

