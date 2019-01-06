---
title: "クライアントの have list をサーバから削除する"
date: "2006-03-22"
---

ローカルで、シェルコマンド `rm -fR *` などを実行してファイルをすべて削除しても、Perforce サーバ上ではそのクライアントはまだファイルを持っているという情報を保持しています（have list）。
この have list から、ファイルの保持情報を削除するには次のように `p4 sync` コマンドを実行します。

```
$ p4 sync //depot/...#0
$ p4 sync //depot/...#none
```

オープン状態にあるファイルがある場合は、次のようエラーが出ます。

```
//deport/hoge.txt#67 - is opened for edit and can't be deleted.
```

このような場合は、`p4 revert` コマンドで対象ファイルの編集を破棄してから `p4 sync` を実行します。

```
$ p4 revert -a
$ p4 sync //depot/...#none
```

