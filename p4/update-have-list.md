---
title: "ローカルのワークスペース上でのファイル編集情報をサーバへ通知する（have list の更新）"
date: "2006-06-16"
---

クライアントがローカルのワークスペースに持っているファイル情報 (Have List) はサーバ上で保持されているので、ワークスペース内のファイルをローカルでいぢくりまわした場合は、その変更をサーバに伝えなければ次回からの sync に影響が出てしまいます。
ローカルで持っているファイル情報をサーバに伝えるには、`p4 synk` コマンドを `-k` オプションを付けて実行します。

```
$ p4 synk -k
```

あるいは、上記のエイリアスである `flush` を使います。

```
$ p4 flush
```

以下 p4 コマンドリファレンスから、`-k` オプションについての抜粋です。

```
-k Option

   Keep existing workspace files; update the have list without updating
   the client workspace. Use p4 sync -k only when you need to update
   the have list to match the actual state of the client workspace.
```

