---
title: "GitHub で複数アカウントの SSH キーを使い分ける (~/.ssh/config)"
url: "p/vn9aqfb/"
date: "2024-04-11"
tags: ["Git", "GitHub", "SSH"]
---

1 つのクライアント PC で、複数の GitHub アカウントを使い分けたいことがあります。
典型的なのは以下のような 2 つのアカウントの切り替えです。

- プライベートの GitHub アカウント
- 仕事用の GitHub アカウント

GitHub の仕様上、複数の GitHub アカウントに同じ SSH キー（公開鍵）を登録することはできないようになっているため、GitHub アカウントごとに必ず異なる SSH キーペアを用意する必要があります。
つまり、クライアント側で接続時に使用する SSH キー（秘密鍵）を切り替えることが、すなわち GitHub アカウントの切り替えを意味します。

SSH クライアント（`ssh` コマンド）はデフォルトでは `~/.ssh/id_rsa` などのパスに配置された SSH 秘密鍵を参照するようになっているため、なんらかの方法で SSH 秘密鍵を切り替えて GitHub に接続する必要があります。
ここでは、__`~/.ssh/config`__ を使って、使用する SSH キー（秘密鍵）を切り替える方法を説明します。


（必要があれば）SSH キーペアの作成と GitHub への登録
----

すでに GitHub アカウントへの SSH キーの登録が済んでいる場合はこの説明は飛ばしてください。

### SSH キーペアの作成

- 参考: [SSH キーの管理: SSH キーを作成する (`ssh-keygen`)](https://maku.blog/p/ftducs9/)

プライベートのアカウントと、仕事用のアカウントの SSH キーペアを作成します。
キータイプとしては、高速かつ強度のある Ed25519 鍵を使うことにしましょう。
それぞれのキーペアは、`~/.ssh/github-maku`、`~/.ssh/github-work` というディレクトリに保存することにします。

```console
$ mkdir ~/.ssh/github-maku
$ mkdir ~/.ssh/github-work
$ ssh-keygen -t ed25519 -f ~/.ssh/github-maku/id_ed25519
$ ssh-keygen -t ed25519 -f ~/.ssh/github-work/id_ed25519
```

{{< accordion title="コマンドプロンプトの場合" >}}
{{< code >}}
cd %USERPROFILE%
mkdir .ssh\github-maku
mkdir .ssh\github-work
ssh-keygen -t ed25519 -f .ssh\github-maku\id_ed25519
ssh-keygen -t ed25519 -f .ssh\github-work\id_ed25519
{{< /code >}}
{{< /accordion >}}

これで次のようなファイルが作成されます。

- `~/.ssh/github-maku/id_ed25519` （プライベート用の秘密鍵）
- `~/.ssh/github-maku/id_ed25519.pub` （プライベート用の公開鍵）
- `~/.ssh/github-work/id_ed25519` （仕事用の秘密鍵）
- `~/.ssh/github-work/id_ed25519.pub` （仕事用の公開鍵）

### GitHub アカウントに SSH 公開鍵を登録

生成した SSH キーペアのうち、公開鍵の方をそれぞれの GitHub アカウントに登録します。

1. GitHub にサインインした状態で、[`Setting` → `SSH and GPGkeys`](https://github.com/settings/keys) のページを開く。
2. __`New SSH Key`__ のボタンを押す。
3. __`Key`__ の欄に公開鍵 (`id_ed25519.pub`) の内容を貼り付ける。
   - `Title` は何でもよいですが、接続元がわかるように付けておくとよいです（例: `WORK-DESKTOP`、`PRIVATE-LAPTOP`）。


SSH クライアントの設定
----

### `~/.ssh/config` の設定

- 参考: [SSH キーの管理: SSH の接続先ごとにキーを使い分ける (`~/.ssh/config`)](https://maku.blog/p/szajt4d/)

SSH クライアントの設定ファイル (__`~/.ssh/config`__) に次のように設定を追加します。

{{< code title="~/.ssh/config" >}}
Host github-maku
    Hostname github.com
    User git
    IdentityFile ~/.ssh/github-maku/id_ed25519

Host github-work
    Hostname github.com
    User git
    IdentityFile ~/.ssh/github-work/id_ed25519
{{< /code >}}

{{% accordion title="Windows の場合" %}}
Windows に搭載された `ssh` コマンド (`C:\Windows\System32\OpenSSH\ssh.exe`) を使う場合も、`IdentityFile` に Unix 流のパス指定を行えます（`~` や `/` が使えます）。
ただし、Windows 版の [git.exe](https://git-scm.com/downloads) では、組み込みの `ssh` を使用することができるようになっており、こちらの `ssh` は内部的な振る舞いが若干異なります。
例えば、Windows 流のパス指定（ディレクトリセパレータはバックスラッシュ `\`）を使わなければいけなかったり、`HOME` 環境変数が指定されているときに `%USERPROFILE%/.ssh/config` ではなく `%HOME%/.ssh/config` を参照したりします。
このような統一感のない振る舞いは厄介なので、Windows の `git.exe` をインストールするときは、外部の `ssh` コマンドを使用するオプションを選択することをおすすめします。

{{< image src="img-001.png" >}}

これで、単独の `ssh` コマンドを実行したときの振る舞いと、`git` コマンドで SSH 接続をしたときの振る舞いが共通化されます。
{{% /accordion %}}

__`Host`__ の行に指定した名前は、接続先のホストと SSH 秘密鍵をまとめて切り替えるための「接続名」として機能します。
名前の付け方は自由ですが、上記のように `<接続先>-<ユーザー>` という感じで付けておくとわかりやすいです。
ここでは、`~/.ssh` 以下に作成したディレクトリ名と合わせて `github-maku`、`github-work` としました。

__`Hostname`__ と __`User`__ の行は、上記のように GitHub 用の固定値 (`github.com` と `git`）を指定しておく必要があります。
__`IdentityFile`__ には、それぞれの GitHub アカウントで使用する SSH 秘密鍵のパスを指定します。

{{% note title="GitHub アカウント名は指定しなくてよいの？" %}}
`~/.ssh/config` ファイルの中に GitHub アカウント名の記述がないことに気付いたかもしれません。
GitHub アカウントと SSH キーは、GitHub 内部で関連付けられて保持されています。
そのため、SSH 接続時に使用した SSH 秘密鍵から、どの GitHub アカウントで接続したのかが自動的に判別されるようになっています。
{{% /note %}}

### 接続テスト

`~/.ssh/config` で定義した接続名で、GitHub に SSH 接続できるかを確認するには次のようにします。

```console
$ ssh -T github-maku
Hi maku77! You've successfully authenticated, but GitHub does not provide shell access.
```

レスポンスメッセージを見ると、どの GitHub アカウントとして接続されたかが分かります（上記の例では `maku77` です）。

ちなみに、`github-maku` の部分を通常のホスト名 `github.com` に変えると、デフォルトのパスに置かれた SSH キー（`~/.ssh/id_rsa` や `~/.ssh/id_ed25519`）が使われます。


git コマンドで使用する GitHub アカウントを切り替える
----

`git` コマンドの接続先は、origin URL として設定されています。

```console
$ git config --get remote.origin.url
git@github.com:maku77/private-repo
```

この URL 内の `github.com` の部分を、`~/.ssh/config` で定義した接続名に置き換えることで、使用する SSH 秘密鍵（≒ GitHub アカウント）を切り替えることができます。

### git clone 時に接続名を指定する

もっとも単純なのは、`git clone` 時に接続名を指定する方法です。

{{< code lang="console" title="git clone 時に接続名を指定する" >}}
# プライベートのアカウントで接続する
$ git clone github-maku:maku77/private-repo

# 仕事用のアカウントで接続する
$ git clone github-work:yourcompany/private-repo
{{< /code >}}

`github-maku` の部分を `git@github-maku` と記述してもよいですが、ユーザー ID は `~/.ssh/config` で指定しているので省略できます。

### origin URL を書き換える

すでに clone 済みのリポジトリがある場合は、その origin URL だけ変更するという方法があります。

```console
$ git remote set-url origin github-maku:maku77/private-repo
```

このコマンドが覚えられない場合は、エディタで `.git/config` を開いて編集してしまうのでもよいです（こっちの方が楽かも）。
ちゃんと設定されたかは、次のように確認します。

```console
$ git config --get remote.origin.url
github-maku:maku77/private-repo

# あるいは
$ git remote -v
origin  github-maku:maku77/private-repo (fetch)
origin  github-maku:maku77/private-repo (push)
```


コミットログ用の名前とメールアドレスの設定
----

ここまでの説明は、あくまで SSH 接続で使う秘密鍵の切り替え方法であり、`git commit` 時にログとして残す「名前」や「メールアドレス」は、別途 Git クライアントの設定として切り替える必要があります。

```console
$ git config --local user.name "Taro Yamada"
$ git config --local user.email "taro.yamada@example.com"
```

この設定をリポジトリ毎に行うのが面倒な場合は、ディレクトリパスに応じて自動的に切り替える方法があります。

- 参考: [Git ユーザーをディレクトリごとに自動で切り替える (.gitconfig, includeIf)](/p/hxyiu7g/)

