---
title: Ansible とは？ Ansible をインストールする
created: 2016-10-22
---

Ansible とは？
----

Ansible は、2012 年に Michael DeHaan 氏によって公開されたコンフィギュレーションツールです。
Ansible を実行するホスト自身の構成を行うこともできるし、複数のホストに対して一括して設定することもできます。
Chef や Puppet に比べて、導入や設定が容易という特徴があります。

Ansible の特徴:

* ツール自体は Python で記述されています。
* コントロールされる側のホストには、Python と SSH さえ入っていればよく、導入が非常に容易です（コントロールする側のホストから、SSH で Python スクリプトを流し込んで実行するという手法）。
* 複数のホストをプッシュ型でコントロールするので、大量のホスト（数千）の制御も問題なく行えます（複数のホストで並列にコンフィギュレーションが実行される）。ansible-pull というツールを導入すれば、プル型で動作させることも可能です（リモートホストがプロキシ環境内にある場合など）。
* 設定、構成情報は YAML 形式のテキストファイル (Playbook) で記述します。
* Playbook で定義する各種処理（タスク）はモジュールによって提供されており、モジュール自身は様々な言語で実装することができます（200 を超える組み込みモジュールは Python で記述されています）。
* 実行後の状態に関して冪等性（べきとうせい）が保証されており、何度実行しても同じ状態になるようになっています。Playbook には、「期待する状態」を記述する、と考えることができます。
* どのような環境でも実行可能な汎用的な Playbook を記述するというよりは、自分たちの組織用にカスタマイズされた Playbook を作成するという用途に向いています。たとえば、apt と yum のどっちのパッケージ管理ツールが使えるのかなどは意識して記述する必要があります。


Ansible のインストール
----

### apt でインストール (Ubuntu)

```
$ sudo apt-get install -y software-properties-common
$ sudo apt-get update
$ sudo apt-get install -y ansible
```

上記のようにすると、2016-10-22 時点では、Ansible 1.4 系がインストールされました。
Ansible 2 系をインストールしたい場合は、下記のように、拡張リポジトリ (PPA: Personal Package Archive) を登録してから `update` と `install` を実行する必要があります。

```
$ sudo apt-get install -y python-software-properties
$ sudo apt-add-repository ppa:ansible/ansible
$ sudo apt-get update
$ sudo apt-get install -y ansible
```

### yum でインストール (CentOS, RedHat Linux)

```
$ sudo yum install epel-release
$ sudo yum install ansible
```

yum の拡張リポジトリ (EPEL: Extra Package for Enterprise Linux) から Ansible をインストールするために、最初に `epel-release` をインストールしています。

### pip でインストール (Mac OSX)

```
$ sudo easy_install pip  # pip 入っていない場合
$ sudo pip install ansible
```

最後に、インストールがうまくいったか確認しましょう。

```
$ ansible --version
ansible 2.1.2.0
  config file = /etc/ansible/ansible.cfg
  configured module search path = Default w/o overrides
```

インストールに関しての詳細なドキュメントはこちら。

* [Installation - Ansible Documentation](http://docs.ansible.com/ansible/intro_installation.html)

