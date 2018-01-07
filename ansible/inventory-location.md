---
title: インベントリファイルの場所を指定する
date: "2016-10-22"
---

インベントリファイルは、下記のようなファイルが読み込まれます。

1. コマンドラインオプション `-i` で指定したファイル
2. コンフィグファイル `ansible.cfg` 内の `hostfile` で指定したファイル（参考: [ansible.cfg ファイルの検索パス](path-of-ansible-config.html)）
3. `/etc/ansible/inventry`

以下の例は、いずれもカレントディレクトリ内の `hosts` というファイルをインベントリファイルとして使用するように指定しています。

#### 例: コマンドラインオプションで指定する方法

```
$ ansible myserver -i hosts -m ping
```

#### 例: ./ansible.cfg で指定する方法

```ini
[defaults]
hostfile = hosts
```

