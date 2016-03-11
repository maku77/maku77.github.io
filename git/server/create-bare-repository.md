---
title: 共有リポジトリを作成する
created: 2014-05-13
---

ベアリポジトリを作成する
----

中央サーバとして公開する Git リポジトリは、通常、working ツリーを持たないベアリポジトリ (bare repository) として作成します。
通常の作業用リポジトリ（ノンベア）では、ルートディレクトリに `.git` ディレクトリが作成され、その中に管理情報ファイルが作成されますが、ベアリポジトリでは、ルートディレクトリ直下に、それらのファイルが生成されます。
ここでは、`myrepo.git` というディレクトリ名でリポジトリを作成します。
ベアリポジトリの名前には、慣例としてサフィックスに `.git` を付けます。

```
$ git init --bare myrepo.git
```

これで、`git clone` 可能な共有リポジトリができました。
以下のように clone することができます（空っぽなので warning が出ています）。

```
$ git clone myrepo.git
Cloning into 'myrepo'...
done.
warning: You appear to have cloned an empty repository.
```

最初のファイルをコミットして、共有リポジトリへ push してみます。

```
$ echo hoge > hoge.txt
$ git add hoge.txt
$ git commit -m "First commit."
$ git push origin master
```

初回の `git push` では、push 先のリモートリポジトリと、push するブランチを指定する必要があります。

