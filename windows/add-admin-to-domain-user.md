---
title: "ドメインユーザーにローカル PC の Administrator 権限を与える"
date: "2011-02-24"
---

下記は Windows 7 で確認しています。

Windows の世界では、同じユーザー名を使用していても、ドメインごとに Administrator 権限の設定が存在します。
ネットワーク上のドメインに参加しているユーザーに Administrator 権限が付いていても、ローカル PC の Administrator 権限が付与されていないと、ローカル PC に対するシステム系の操作が "Access is denied" になってしまいます。

ローカル PC 以外のドメインに参加しているユーザーに、ローカル PC に対する Administrator 権限を付与するには以下のようにします。

1. ログオフして、ローカル PC の Administrator 権限を持つユーザーでログイン。ユーザー名のところで、`<LocalPCName>\<UserName>` のように指定する。
2. スタートメニューで `lusrmgr.msc` と入力してユーザ管理のダイアログを開く。
3. `Groups` の中の `Administrators` をダブルクリックして、`Add` ボタンを押す。
4. `<DomainName>\<DomainUser>` を追加する。
5. ログオフして、上記で追加したドメインのユーザーでログインし直せば OK。

