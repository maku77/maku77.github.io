---
title: Git でブランチ名を変更する
created: 2010-07-19
---

Git のブランチ名を変更するには以下のように `branch` コマンドに `-m` オプションを指定して実行します。

```
$ git branch -m new        # 現在チェックアウトしているブランチの名前を new に変更
$ git branch -m new old    # old ブランチの名前を new に変更
```

共有リポジトリに `old` リポジトリがすでに Push されている場合は、共有リポジトリ側も変更する必要があります。

```
$ git push origin new   # 新しいブランチ new を push
$ git push origin :old  # 古いブランチ old を削除
```


実行例
----

下記は、もともと存在するブランチ `br1` を `br2` にリネームする手順の例です。

最初は `br1` ブランチと `master` ブランチがある状態です。

```
$ git branch
  br1
* master
```

リネームします。

```
$ git branch -m br1 br2
```

リネームされました。

```
$ git branch
  br2
* master
```

