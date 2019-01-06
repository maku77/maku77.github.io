---
title: "共有リポジトリを作成する"
date: "2014-05-13"
---

ベアリポジトリを作成する
----

中央サーバとして公開する Git リポジトリは、通常、**working ツリーを持たないベアリポジトリ (bare repository)** として作成します。
通常の作業用リポジトリ（ノンベア）では、ルートディレクトリに `.git` ディレクトリが作成され、その中に管理情報ファイルが作成されますが、ベアリポジトリでは、ルートディレクトリ直下に、それらのファイルが生成されます。
ここでは、`myrepo.git` というディレクトリ名でリポジトリを作成します。
ベアリポジトリの名前には、慣例としてサフィックスに `.git` を付けます。

~~~
$ git init --bare myrepo.git
~~~

これで、`git clone` 可能な共有リポジトリができました。
以下のように clone することができます（空っぽなので warning が出ています）。

~~~
$ git clone myrepo.git
Cloning into 'myrepo'...
done.
warning: You appear to have cloned an empty repository.
~~~

最初のファイルをコミットして、共有リポジトリへ push してみます。

```
$ echo hoge > hoge.txt
$ git add hoge.txt
$ git commit -m "First commit."
$ git push origin master
```

初回の `git push` では、push 先のリモートリポジトリと、push するブランチを指定する必要があります。


存在する作業リポジトリから共有リポジトリを作成する
----

すでに存在する作業リポジトリ（ノンベアリポジトリ）から、共有リポジトリ（ベアリポジトリ）を作成するには下記のようにします。
ここでは、`private_repo` ディレクトリから `public_repo.git` を作成しています。

~~~
$ git clone --bare path/to/private_repo path/to/public_repo.git
Cloning into bare repository 'public_repo.git'...
done.
~~~

このとき、clone 元になったリポジトリ (`private_repo`) から見ると、`public_repo.git` 側との関連を認識できていません。
`public_repo.git` から clone したのと同様に振る舞って欲しいので、`origin` というリモートトラッキングブランチ名で `public_repo.git` を指し示すように設定しておくとよいでしょう。
これにより、`git pull` などで取得元を省略した場合に、`public_repo.git` を参照してくれるようになります。

~~~
$ cd path/to/private_repo
$ git remote add origin path/to/public_repo.git     # リモートブランチの作成
$ git config branch.master.remote origin            # デフォルトの git pull 時の参照先サーバを origin に
$ git config branch.master.merge refs/heads/master  # デフォルトで master ブランチにマージ
~~~

と、上記のように設定することもできるのですが、いったん `private_repo` を削除してしまって、`public_repo.git` から `git clone` した方が手っ取り早いかもしれません（clone の規模によりますが）。

