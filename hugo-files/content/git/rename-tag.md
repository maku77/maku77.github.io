---
title: "Gitメモ: タグの名前を変更する (git tag new old)"
url: "p/h4dnw6e/"
date: "2015-06-29"
tags: ["git"]
lastmod: "2022-05-09"
aliases: [/git/rename-tag.html]
---

ローカルリポジトリでタグの名前を変更する
----

Git には直接タグをリネームするコマンドはないため、「新しい名前のタグを作成 → 古いタグを削除」という手順で実現します。

`git tag <新しいタグ名> <古いタグ名>` とすると、古いタグが指しているコミットと同じコミットを指す新しいタグが作成されます。
その後、古いタグを削除すれば、実質的にリネームしたことになります。

{{< code lang="console" title="例: タグ old を new にリネームする" >}}
$ git tag new old  # old と同じコミットを指す新しいタグ new を作成
$ git tag -d old   # 古いタグ old を削除
{{< /code >}}


中央リポジトリに push 済みのタグをリネームする
----

GitHub などの中央リポジトリ（リモート）に push 済みのタグをリネームする場合は、ローカルだけでなくリモート側のタグも更新する必要があります。
以下の 4 ステップで行います。

1. ローカルで新しいタグを作成する（古いタグと同じコミットを指すようにする）
2. 中央リポジトリに新しいタグを push する
3. ローカルの古いタグを削除する
4. 中央リポジトリの古いタグを削除する

{{< code lang="console" title="例: タグ old を new にリネームする" >}}
$ git tag new old           # 1. 新しいタグを作成
$ git push origin new       # 2. 新しいタグをリモートに push
$ git tag -d old            # 3. ローカルの古いタグを削除
$ git push origin :old      # 4. リモートの古いタグを削除
{{< /code >}}

最後の `git push origin :old` は、リモートの `old` タグを削除するコマンドです。
`:old` は `:refs/tags/old` の省略形で、「空の内容をリモートの `old` に push する＝削除する」という意味になります。
次のように `--delete` オプションを使って書くこともできます。

```console
$ git push origin --delete old
```

- 参考: [コミットに対してタグをつけて中央リポジトリにプッシュする (git tag)](/p/y2cmv5d/)

