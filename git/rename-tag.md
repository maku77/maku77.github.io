---
title: タグの名前を変更する
date: "2015-06-29"
---

すでに中央リポジトリに push 済みのタグをリネームするには下記のようにします。

1. 新しいタグを作成
2. 新しいタグを中央リポジトリに push する
3. ローカルから古いタグを削除する
4. 中央リポジトリから古いタグを削除する

下記はタグ old を new にリネームする例です。

```
$ git tag new old
$ git push --tags
$ git tag -d old
$ git push origin :old  （:refs/tags/old と書いてある例も見かけるけど :old だけで OK）
```

