---
title: "Perforceメモ: p4admin で Perforce サーバを設定する"
url: "p/ca7r99j/"
date: "2014-07-30"
tags: ["perforce"]
aliases: ["/p4/p4admin.html"]
---

## p4admin を起動する

Perforce サーバの設定を行うには、**p4admin** を使って、サーバに接続します。
最初に接続したユーザーが、管理ユーザーとして登録されます。

例えば、ローカルホストで動いているサーバに対して、ユーザー ohta で接続するのであれば、以下のように実行します。

```console
$ p4admin -p localhost:1666 -u ohta
```

{{< image src="img-001.png" >}}

## Perforce サーバにユーザーアカウントを追加する

最初に p4admin に接続したユーザーは、すでに作成された状態になっていますが、さらに Perforce サーバに接続可能なユーザーを追加するには、Home タブの `Create new user` を選択するか、メニューから `File => New => User...` と辿ります。

{{< image src="img-002.png" >}}

ここでは初期パスワードを設定できますが、ユーザー作成後のパスワード変更は、各ユーザーが `p4 passwd` コマンドを使用して行います（p4admin ツールの中ではパスワード変更できません）。

```console
$ p4 -p localhost:1666 -u maku passwd
```
