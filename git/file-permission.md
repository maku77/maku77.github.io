---
title: Git で管理しているファイルの実行権限（パーミッション）を変更する
created: 2015-10-21
---

例えば、Windows 上で Linux のシェルスクリプトを作成していて、Git リポジトリにコミットするときに実行パーミッションを付けた状態でコミットしたい場合は、下記のように実行フラグを付けてからコミットします。

```
$ git update-index --add --chmod=+x <filename>
$ git commit
```

