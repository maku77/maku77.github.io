---
title: "Perforceメモ: p4 client -d でクライアントを削除できない"
url: "p/693qet3/"
date: "2010-08-02"
tags: ["perforce"]
aliases: ["/p4/cannot-delete-client.html"]
---

あるクライアントで作成したチェンジリストが残っている (pending) 場合や、default のチェンジリストでファイルが open 状態になっている場合は、そのクライアントを削除しようとすると、以下のようなエラーが出て削除できない場合があります。

```console
$ p4 client -d hemu.temp
Client 'hemu.temp' has pending changes; use -f to force delete.
```

```console
$ p4 client -d hemu.temp
Client 'hemu.temp' has files opened; use -f to force delete.
```

has pending changes と出る場合は、まず `changelists` コマンドでそのクライアントで pending になっているチェンジリストを確認し、削除します。

```console
$ p4 changelists -c hemu.temp -s pending
Change 234859 on 2009/06/05 by hemu@hemu.temp *pending* 'Temporary changes.'

$ p4 -c hemu.temp changelist -d 234859
Change 234859 deleted.
```

has files opened と出る場合は、デフォルトチェンジリストでの変更を破棄します（変更が削除されてしまうので要注意）。

```console
$ p4 -c hemu.temp revert -a
//aaa/bbb/Test.cpp#1 - was edit, reverted
//aaa/bbb/Test.h#1 - was edit, reverted
```

これで無事にクライアントを削除できるようになります。

```console
$ p4 client -d hemu.temp
Client hemu.temp deleted.
```

