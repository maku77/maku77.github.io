---
title: 別のサーバに Git リポジトリを移行する（GitLab から GitHub への移行）
created: 2015-06-29
---

リポジトリを別のリポジトリへ移動させるときは下記のようにします。
GitHub 上で、空のリポジトリ dst.git を作成済みだと仮定しています。

### 例: src.git から dst.git への移行
```git
$ git clone --mirror https://github.com/user/src.git
$ cd from.git
$ git push --mirror https://github.com/user/dst.git
```

ここでは、GitHub 内でのリポジトリの移行を行っていますが、別のサーバへの移行（例えば、GitLab から GitHub への移行）も可能です。
タグやブランチなどもまとめてお引越しできます。

