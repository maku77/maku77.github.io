---
title: Amazon EC2 に Jenkins をインストールする
created: 2016-10-14
---

ここでは、EC2 インスタンスとして、**Amazon Linux AMI (t2.micro)** を選択した場合の Jenkins インストール方法を示します。

EC2 サーバへ Jenkins をインストールする
----

EC2 インスタンスのコンソール上で下記のようにインストールできます（基本的に CentOS におけるセットアップ方法と同様です）。

### システム全体の更新

```
$ sudo yum -y update
```

### OpenJDK 1.8 のインストール

```
$ sudo yum install -y java-1.8.0-openjdk-devel.x86_64
$ sudo alternatives --set java /usr/lib/jvm/jre-1.8.0-openjdk.x86_64/bin/java
```

yum でインストールできる JDK のバージョンは `yum search openjdk-devel` で確認できます。
`alternatives` コマンドでの切り替え先の JDK パスは、`sudo alternatives --config java` で確認できます（そこから数字を指定して選択することもできます）。
OpenJDK 1.8 だけ入っていればよいのであれば、先に `yum remove java` で OpenJDK 1.7 をアンインストールしちゃうって手もありです。

### Jenkins のインストール

```
$ sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo
$ sudo rpm --import http://pkg.jenkins-ci.org/redhat/jenkins-ci.org.key
$ sudo yum install jenkins
```

### Jenkins の起動と自動起動設定

```
$ sudo service jenkins start
$ sudo chkconfig jenkins on
```

インターネットから Jenkins への接続テスト
----

Web サーバへのアクセスするときに指定する、インターネット側の IP アドレスは下記のように調べることができます（EC2 上で `ifconfig` を実行して表示されるアドレスは、ローカルアドレスですのでインターネット経由でのアクセスには使用できません）。

```
[ec2-user@ip-xxx]$ curl http://169.254.169.254/latest/meta-data/public-ipv4
54.214.194.23
```

あるいは、[Amazon EC2 コンソール](https://console.aws.amazon.com/ec2/) のサイト上で、インスタンスごとの Public IP アドレスを確認することもできます。
IP アドレスが分かったら、あとは Web ブラウザからアクセスするだけです。

```
http://54.214.194.23:8080/
```

アクセスできない場合は、EC2 のコンソールのセキュリティポリシーから、Jenkins 用のポート 8080 が解放されているかを確認してください。

