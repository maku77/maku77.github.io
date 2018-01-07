---
title: タグを中央リポジトリに push する
date: "2011-08-08"
---

指定したタグを push する
====

ローカルで作成したタグ情報を、中央リポジトリのサーバにも反映するには、タグの push を実行しておく必要があります。

#### 例: タグ v1.0.0 を push

```
$ git push origin refs/tags/v1.0.0
```

#### 例: すべてのタグを push する

```
$ git push --tags
$ git push --tags origin master
```

