---
title: "ansible.cfg ファイルの検索パス"
date: "2016-10-23"
---

Ansible の実行環境が参照する設定ファイル (`ansible.cfg`) は、下記のようなパスから検索されます。

1. 環境変数 `ANSIBLE_CONFIG` で指定されたファイル
2. `./ansible.cfg`（カレントディレクトリ以下のファイル）
3. `~./.ansible.cfg`（ホームディレクトリ以下のファイル）
4. `/etc/ansible/ansible.cfg`

２番目の方法であれば、Playbook と一緒にカレントディレクトリ以下に格納しておけるため、Git などのバージョン管理ツールで管理しやすくなります。

