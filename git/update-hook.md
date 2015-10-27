---
title: Git の update フックの基本
created: 2010-08-23
---

`.git/hooks/update` スクリプトは、クライアントが `git push` しようとしたときに、クライアント側で実行されるフックスクリプトです。
このスクリプトが `0` 以外の値を返すと、`git push` は失敗するようになっています。

例えば、以下のような shell スクリプトを push される側の `.git/hooks/update` として置いて実験してみましょう。

#### .git/hooks/update

```bash
echo "--------------------------------------------------"
echo USER = $USER
echo refname = $1
echo old rev = $2
echo new rev = $3
echo "--------------------------------------------------"

exit 1
```

あくまでスクリプトなので、忘れずに実行権限を付けておきます。

```
$ chmod 755 update
```

そして、そのリモートリポジトリに対して `git push` をすると、上記のスクリプトは、ブランチ名やコミット番号などの情報を表示した後、`0` 以外の値で終了しているので `git push` がエラーになり失敗します。

#### git push 実行結果
```
Counting objects: 4, done.
Compressing objects: 100% (2/2), done.
Writing objects: 100% (3/3), 292 bytes, done.
Total 3 (delta 1), reused 0 (delta 0)
--------------------------------------------------
USER = joe
refname = refs/heads/master
old rev = 49af424ee70414b55f232e2761d2dbbbe392a40a
new rev = c2ac08c5fceb715c408e62c79ec814388581c1c0
--------------------------------------------------
error: hooks/update exited with error code 1
error: hook declined to update refs/heads/master
To /home/maku/git_shared/test
 ! [remote rejected] master -> master (hook declined)
error: failed to push some refs to '/home/maku/git_shared/test'
```

