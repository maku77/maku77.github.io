---
title: "Git ユーザーをディレクトリごとに自動で切り替える (.gitconfig, includeIf)"
url: "p/hxyiu7g/"
date: "2022-09-12"
tags: ["Git"]
---

何をするか？
----

会社用と個人用の GitHub ユーザーを使い分けている場合、リポジトリへのコミット時に正しいユーザーでコミットログを残すように注意しなければいけません。
多くのリポジトリを扱っている人は、次のような感じで GitHub organization ごとにディレクトリを分けて管理するなどの工夫をしていると思います（リポジトリ名だけだと重複するので）。

```
~/gitwork/
  +-- company/ ... 会社用のリポジトリ（会社用のユーザー Rei Ayanami を使いたい）
  |    +-- repo1/
  |    +-- repo2/
  |    +-- repo3/
  +-- ojisancancode/ ... 個人用のリポジトリ（個人用のユーザー ojisancancode を使いたい）
       +-- repo4/
       +-- repo5/
       +-- repo6/
```

`company` は自分の会社で使っている GitHub organization 名、`ojisancancode` は自分の GitHub アカウント名だと考えてください。
ここでは、上記のようにディレクトリを階層化してリポジトリを管理しているときに、ディレクトリ単位で自動的に Git クライアントの設定を切り替える方法を示します。
具体的には、`company` ディレクトリ以下のリポジトリで作業しているときは、会社用の Git ユーザー名とメールアドレスを使い、`ojisancancode` ディレクトリ以下のリポジトリで作業しているときは、個人用のユーザー名とメールアドレスを使うようにします。

リポジトリごとに local 設定 (`.git/config`) をするのもよいのですが、扱うリポジトリが増えてくるといちいち設定するのが大変なので、親ディレクトリの `company`、`ojisancancode` 単位でまるっと設定を入れ替えます。

- 参考: [Git 設定のスコープ (local/global/system) を理解する](/p/af7q7n3/)


設定ファイルの自動切換え（includeIf ディレクティブ）
----

Git クライアントの設定ファイル（`~/.gitconfig` など）には、もともと別の設定ファイルをインクルードする機能 (`include` ディレクティブ）があるのですが、[Conditional includes（条件付きインクルード）](https://git-scm.com/docs/git-config#_conditional_includes) の仕組みを使うと、指定した条件に一致したときのみ設定ファイルをインクルードすることができます。
この仕組みを利用して、次のような条件付きインクルードを行えば、Git のユーザー設定をディレクトリごとに自動で切り替えることができます。

- `~/gitwork/company` 以下のリポジトリであれば、`~/.gitconfig-company` をインクルードする
- `~/gitwork/ojisancancode` 以下のリポジトリであれば、`~/.gitconfig-ojisancancode` をインクルードする

条件付きインクルードを行うには、__`includeIf`__ ディレクティブを使用します。
ここでは、ユーザー単位の global 設定でこの設定を行います。
__`~/.gitconfig`__ ファイルを開いて次のような感じで記述します（Windows では `git config --edit --global` コマンドで開いた方が安全かもしれません）。

{{< code lang="ini" title="~/.gitconfig" >}}
[includeIf "gitdir:~/gitwork/company/"]
	path = ~/.gitconfig-company

[includeIf "gitdir:~/gitwork/ojisancancode/"]
	path = ~/.gitconfig-ojisancancode
{{< /code >}}


{{% note title="コマンドで設定する方法" %}}
`~/.gitconfig` ファイルを直接編集せずに、次のようにコマンドラインから設定することもできます（たぶんファイルを直接編集した方が楽です）。

```console
$ git config --global includeIf."gitdir:~/gitwork/company/".path ".gitconfig-company"
$ git config --global includeIf."gitdir:~/gitwork/ojisancancode/".path ".gitconfig-ojisancancode"
```
{{% /note %}}

あとは、上記のファイルからインクルードしているファイルを次のような感じで作成します。

{{< code lang="ini" title="~/.gitconfig-company" >}}
[user]
	name = Rei Ayanami
	email = 会社用のメールアドレス
{{< /code >}}

{{< code lang="ini" title="~/.gitconfig-ojisancancode" >}}
[user]
	name = ojisancancode
	email = 個人用のメールアドレス
{{< /code >}}

これで設定は完了です。
対象リポジトリのディレクトリへ移動して、それぞれの設定ファイルの内容が反映されているかを確認しておきます。

```console
$ cd ~/gitwork/company/repo1
$ git config --show-origin user.name
file:C:/Users/maku/.gitconfig-company    Rei Ayanami

$ cd ~/gitwork/ojisancancode/repo4
$ git config --show-origin user.name
file:C:/Users/maku/.gitconfig-ojisancancode     ojisancancode
```

`includeIf` ディレクティブは、Git リポジトリとして初期化された（`.git` がある）ディレクトリ以下でのみ有効なことに注意してください。
今回の例でいうと、`~/gitwork/company` 直下で作業しているときは有効ではなく、`~/gitwork/company/repo1` の下で作業しているときに有効になります。


（応用）デフォルト設定とオーバーライド
----

ほとんどのリポジトリで使う設定を `~/.gitconfig` に記述しておき、特定のリポジトリ用の設定だけを `includeIf` ディレクティブでインクルードするという方法もあります。
つまり、通常はデフォルト設定 (`~/.gitconfig`) を使い、必要に応じて設定をオーバーライド (`~/.gitconfig-xxx`) する方法です。

{{< code lang="ini" title="~/.gitconfig（ほとんどのケースで使うデフォルト設定）" >}}
[user]
	name = Rei Ayanami
	email = rei.ayanami@example.com

[includeIf "gitdir:~/gitwork/ojisancancode/"]
	path = ~/.gitconfig-ojisancancode
{{< /code >}}

{{< code lang="ini" title="~/.gitconfig-ojisancancode（個人用の設定）" >}}
[user]
	name = ojisancancode
	email = ojisancancode@example.com
{{< /code >}}


参考
----

- [Git 設定のスコープ (local/global/system) を理解する](/p/af7q7n3/)
- [Git の設定値がどのファイルで設定されているか調べる (config --show-origin)](/p/msds6iv/)

