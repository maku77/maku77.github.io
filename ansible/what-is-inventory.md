---
title: インベントリファイルとは
date: "2016-10-20"
---

Ansible で制御したいホストは、**インベントリファイル (inventory file)** に列挙しておく必要があります。
デフォルトでは、インベントリファイルとして `/etc/ansible/hosts` というファイルが読み込まれます。

#### /etc/ansible/hosts の記述例

```
localhost
192.168.100.1
red.example.com
blue.example.com

[webservers]
192.168.100.2
red.example.com
green.example.com

[dbservers]
db1.example.com
db2.example.com
```

上記のように、ホスト名や IP アドレスで制御対象のホストを列挙します。
`[webservers]` や `[dbservers]` という部分ではグループを定義しており、複数のホストに対して同時に命令する場合に、ホスト名の代わりにこのグループ名を使用することができるようになります。

インベントリファイルに記述されたホスト名やグループ名を、`ansible` コマンドや `ansible-playbook` コマンドの第一パラメータとして渡すことで、制御対象のホストを指定することになります。
上記では、`localhost` を明示的に指定していますが、Ansible 2.0 以降は `localhost` というホスト名は暗黙的に使用できるようになっています。

#### 例: localhost に対して ping を実行（ping モジュールを起動）

```
$ ansible localhost -c local -m ping
localhost | success >> {
    "changed": false,
    "ping": "pong"
}
```

オプションの意味:

-c local
: SSH 接続を使用せずにホストを制御します。Ansible による制御は通常 SSH 経由で行われますが、自分自身のホストを制御する場合は SSH 接続する必要はないのでこのオプションを指定しています。Ansible 2.0 以降は、`localhost` に対する制御はデフォルトで SSH 接続を使用しないようになっているので、このオプションを指定する必要はありません。

-m ping
: ping モジュールを使用してタスクを実行することを示しています。

インベントリファイルに定義されていないホスト名やグループ名を指定すると、`ansible` コマンドや `ansible-playbook` コマンドは下記のようなエラーを出力します。

#### 例: インベントリに登録されていないホストを指定した場合

```
$ ansible unknown-host -m ping
No hosts matched
```

