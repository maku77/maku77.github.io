---
title: git-daemon による読み取り専用リポジトリの公開
date: "2014-05-14"
---

git-daemon ですべてのリポジトリを公開する
---

**git-daemon** を使用すると、特定の共有リポジトリを読み取り専用で簡単に公開できます。
読み取り専用というのは、`git clone`、`git fetch`、`git pull` はできるけど、`git push` はできないことを意味します。
例として、`/var/git` 以下に次のようなリポジトリが作成済みであるとします。

```
/var/git/repo1.git
/var/git/repo2.git
```

これらのリポジトリを全て公開するには、以下のように git-daemon を起動します。
リポジトリの格納されているディレクトリのパスは、絶対パスで指定する必要があります。
当然ですが、git-daemon を起動するプロセスは、公開するリポジトリへのアクセス権限を持っている必要があります。

```
$ git daemon --export-all /var/git
```

これにより、git-daemon はポート番号 9418 (`DEFAULT_GIT_PORT`) での待ち受けを開始し、各クライアントから `git clone` できるようになります。

```
$ git clone git://192.168.0.1/var/git/repo1.git
```

（参考）git-daemon のヘルプは以下のように確認できます。

```
$ man git-daemon
$ git help daemon
```


ベースパスを指定してリポジトリの clone URL を短くする
----

git-daemon を起動する際に、`--base-path` オプションを指定しておくと、`git clone` に使用する URL のパスから、指定した部分のプレフィックスを除くことができます。

```
$ git daemon --export-all --base-path=/var/git /var/git
```

これによって、`/var/git/repo1.git` にあるリポジトリに以下のようにアクセスできるようになります。
clone URL から、`/var/git` というプレフィックスを省略することができています。

```
$ git clone git://192.168.0.1/repo1.git
```


指定したリポジトリのみを git-daemon で公開する
----

`git daemon` を起動するときに、`--export-all` オプションを指定すると、指定したディレクトリ以下のリポジトリをすべて公開しますが、指定したリポジトリのみを git-daemon の公開対象にすることもできます。
公開したいリポジトリのルートに、`git-daemon-export-ok` というファイルを作成することで、git-daemon による公開対象であることを示します。

```
$ > /var/git/repo1.git/git-daemon-export-ok   # 空っぽのファイルを作成
$ git daemon --base-path=/var/git /var/git
```

こうすると、`repo1.git` は clone できるけど、`repo2.git` は clone できない状態になります。

```
$ git clone git://192.168.0.1/repo1.git  #==> OK
$ git clone git://192.168.0.1/repo1.git  #==> NG (not exported)
```

