---
title: "タグの名前を変更する"
url: "p/h4dnw6e"
permalink: "p/h4dnw6e"
date: "2015-06-29"
lastmod: "2022-05-09"
tags: ["Git"]
redirect_from:
  - /git/rename-tag
---

GitHub などの中央リポジトリに push 済みのタグをリネームするには次のようにします。

1. ローカルで新しいタグをする（古いタグと同じコミットを指すようにする）
2. 中央リポジトリに新しいタグを push する
3. ローカルの古いタグを削除する
4. 中央リポジトリの古いタグを削除する

#### 例: タグ old を new にリネームする

```console
$ git tag new old
$ git push --tags
$ git tag -d old
$ git push origin :old  （:old は :refs/tags/old の省略形）
```

ローカルリポジトリでタグ名を変更するだけであれば、上記手順から `git push` を省けば OK です。

```console
$ git tag new old  # 新しいタグ new を作成
$ git tag -d old   # 古いタグ old を削除
```

- 参考: [コミットに対してタグをつけて中央リポジトリにプッシュする (git tag)](/p/y2cmv5d/)

