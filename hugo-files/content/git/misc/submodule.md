---
title: "Git サブモジュールで別リポジトリの内容を組み込む (git submodule)"
url: "p/dsctaq7/"
date: "2022-11-13"
tags: ["Git"]
---

Git サブモジュールとは
----

Git サブモジュールは、既存の別リポジトリの内容を、サブディレクトリの形で参照できるようにする仕組みです。
例えば、次のようなディレクトリ構成のプロジェクトがあったとします。

```
my-project/
  +-- src/
  +-- my-libs/  ★別リポジトリをサブモジュールとして組み込む
```

ここでは、別リポジトリで管理している共有ライブラリを `my-libs` サブディレクトリの形で参照できるようにしています。
NPM や Maven などのパッケージレジストリから共有ライブラリを取り込む方法もありますが、Git サブモジュールの仕組みを使うと、__メインプロジェクトでの開発と共有ライブラリの開発を並行して進められる__ ようになります。

Git サブモジュールで特徴的なのは、メインプロジェクトからはサブモジュールの内容を __コミットハッシュのみで追跡する__ ということです。
この振る舞いを理解してしまえば、Git サブモジュールを使いこなすのは難しくありません。
サブモジュール側の変更履歴は、あくまでサブモジュール側の Git リポジトリで管理されます。
つまり、サブモジュール側のリポジトリで大量のコミットが行われていたとしても、メインプロジェクト側のリポジトリサイズが増加していくということはありません。
メインプロジェクト側では、どの時点でのスナップショット（のコミットハッシュ）を参照するかを指定するだけです。
サブモジュールとして取り込む Git リポジトリの URL は柔軟に切り替えることができます。

### Git サブモジュールの利用例

- 共有ライブラリ用のリポジトリがあるけれど、NPM や Maven などのパッケージリポジトリにはリリースしていないとき、サブモジュールとして共有ライブラリを取り込む。メインプロジェクト側の開発中に、並行して共有ライブラリのコードを修正したい場合も同様。
- 頻繁に更新されるファイルがあるけれど、メインプロジェクト側のコミット履歴には残したくないとき、別リポジトリでそのファイルを管理し、サブモジュールとして取り込む。


別リポジトリをサブモジュールとして追加する (git submodule add)
----

既存の別リポジトリの内容（前述の例では共有ライブラリ）を、カレントプロジェクトにサブモジュールとして組み込みたいときは、__`git submodule add`__ コマンドを使用します。

```console
$ git submodule add <別リポジトリのURL> [ローカルディレクトリ]
```

例えば次のように実行すると、

```console
$ git submodule add https://github.com/maku77/my-libs
```

ローカルに `my-libs` というディレクトリが作成されて、サブモジュールとして参照できるようになります。
別のディレクトリ名で取り込みたい場合は、末尾にディレクトリ名を追加で指定します。

初めてサブモジュールが追加されると、__`.gitmodules`__ というメタ情報ファイルが作成されます。
ここには、サブモジュールごとのリポジトリ URL とローカルディレクトリのパスが記録されています。
このファイルをコミットすることで、他の開発者がサブモジュールとして管理されているファイルを取得できるようになります。

{{< code lang="ini" title=".gitmodules" >}}
[submodule "my-libs"]
	path = my-libs
	url = https://github.com/maku77/my-libs
{{< /code >}}

最初に説明した通り、サブモジュールの内容はコミットハッシュでのみ追跡されています。
各サブモジュールのディレクトリに、どのコミットハッシュの内容が取得されているかは、__`git submodule status`__ コマンドで確認することができます。

```console
$ git submodule status
 ffb0ef23b9cc39d05b860d2379977268b2f44194 my-libs (heads/main)
```

あとは、今回作成された `.gitmodules` ファイルと `my-libs` ディレクトリを `git commit` すれば作業完了です。
ちなみに、サブモジュールとして追加された `my-libs` ディレクトリは、次のような特殊モード (`160000`) のファイルとして登録され、コミットハッシュのみが記録されています。

```console
$ git diff --staged my-libs
diff --git a/my-libs b/my-libs
new file mode 160000
index 0000000..ffb0ef2
--- /dev/null
+++ b/my-libs
@@ -0,0 +1 @@
+Subproject commit ffb0ef23b9cc39d05b860d2379977268b2f44194
```


サブモジュールを含むリポジトリをクローンする (git submodule init, git submodule update)
----

サブモジュールを含むリポジトリ（`.gitmodules` を含むリポジトリ）をクローンした直後は、サブモジュール用のディレクトリは空っぽになっています。

```console
$ git clone https://github.com/maku77/my-project
$ cd my-project
$ ls my-libs
（空っぽ）
```

`.gitmodules` ファイルの内容に基づいてサブモジュールを利用し始めるには、__`git submodule init`__ コマンドを実行します。

```console
$ git submodule init
Submodule 'my-libs' (https://github.com/maku77/my-libs) registered for path 'my-libs'
```

これにより、ワーキングディレクトリ内の各サブモジュールディレクトリを、どのリポジトリ URL にマッピングすべきかが `.git/config` ファイルに保存されます。
この時点では、まだ `my-libs` ディレクトリは空っぽの状態で、実際にサブモジュールのファイル群を取得するには、__`git submodule update`__ コマンドを実行する必要があります。
サブモジュールがさらに別のサブモジュールを含んでいる場合は、__`--recursive`__ オプションを付けるとまとめて取得できます。
基本的には、このオプションは常に付けておけばよいでしょう。

{{< code lang="console" title="サブモジュールのファイルを取得" >}}
$ git submodule update --recursive
Cloning into '/Users/maku/y/gitwork/maku77/my-project/my-libs'...
Submodule path 'my-libs': checked out 'ffb0ef23b9cc39d05b860d2379977268b2f44194'
{{< /code >}}

これで、メインプロジェクト (`my-project`) からサブモジュール (`my-libs`) のファイルを参照できるようになります。
クローン直後に `git submodule init` と `git submodule update` を実行するのは、ほとんど定型作業になっているので、これらをまとめて実行する __`git submodule update --init`__ コマンドが用意されています。

{{< code lang="console" title="init と update を一気に実行" >}}
$ git submodule update --init --recursive
Submodule 'my-libs' (https://github.com/maku77/my-libs) registered for path 'my-libs'
Cloning into '/Users/maku/y/gitwork/maku77/my-project/my-libs'...
Submodule path 'my-libs': checked out 'ffb0ef23b9cc39d05b860d2379977268b2f44194'
{{< /code >}}

さらに、`git clone` と `git submodule init`、`git submodule update` を同時にやってしまう、__`git clone --recurse-submodules`__ コマンドも用意されています。
サブモジュールを含むリポジトリをクローンする場合は、__このコマンドを使えば一撃でクリア__ です。

{{< code lang="console" title="クローンしてサブモジュールも取得" >}}
$ git clone --recurse-submodules https://github.com/maku77/my-project
{{< /code >}}


メインプロジェクト内でサブモジュールのファイルを修正する
----

メインプロジェクトでの作業中に、サブモジュールのファイルを修正したくなった場合は、サブモジュールのディレクトリに移動して、サブモジュール側の Git リポジトリの修正作業を行います。
メインプロジェクト側ではサブモジュールの修正内容は管理しない（コミットハッシュだけ記録している）ので、サブモジュール側の修正は、サブモジュール側のリポジトリにコミット＆プッシュする必要があります。
典型的な作業順序は次のようになります。

1. サブモジュールのディレクトリに移動する
2. サブモジュール内でブランチを切り替える
3. サブモジュール内のファイルを修正＆コミット＆プッシュ
4. メインプロジェクトに戻り、サブモジュールディレクトリをコミット（コミットハッシュの更新）

あくまで 2 つのリポジトリで別々に修正作業を行う感じですね。
初期状態では、サブモジュール側のチェックアウト状態は detached HEAD（どのブランチも選択しておらず、特定のコミットハッシュを選択している状態）になっているので、作業対象となるブランチに切り替えてから修正作業を行います。

{{< code lang="console" title="サブモジュール側のファイルを更新" >}}
$ cd my-libs
$ git branch
* (HEAD detached at bb1319a)
  main
$ git switch main
$ （何らかのファイル修正）
$ git add .
$ git commit
$ git push
{{< /code >}}

ここでサブモジュール側の更新を（GitHub などへ）プッシュしておかないと、他の開発者がメインプロジェクト側で `git submodule update` しようとしたときに、対象のコミットハッシュが見つからない、といったことになるので注意してください。
サブモジュール側の修正が完了したら、メインプロジェクト側に戻り、参照するサブモジュールのコミットハッシュを最新のものに更新します。

```console
$ cd ..            # メインプロジェクトのルートへ戻る
$ git add my-libs  # サブモジュールの最新のコミットハッシュをステージング
$ git commit
$ git push
```

メインプロジェクトの更新内容を確認してみると、コミットハッシュの更新のみになっていることが分かります。

```console
$ git show
...
-Subproject commit 540bb2831ae6478bf43ce6f8ab7aff09e23946b7
+Subproject commit fd80dfabbc154de89f12a9c617f0d76efbdb00eb
```

`git show` コマンドや `git log -p` コマンドは、サブモジュール側の変更内容 (diff) として、上記のようなコミットハッシュしか表示してくれませんが、__`--submodule`__ オプションを付けて実行すると、コミットハッシュの代わりにサブモジュールのコミットログを確認できます。

```console
$ git show --submodule
...
Submodule common 540bb28..fd80dfa:
  > Add sidebar component
```

{{% note title="プッシュしていないコミットハッシュを参照してしまうのを防ぐ" %}}
サブモジュール側の変更をプッシュする前に、メインプロジェクト側でそのコミットを参照する変更をプッシュしてしまうと、最新コードがビルドできない状態になってしまいます（サブモジュールを `git submodule update` で取得できない）。
このような事態を防ぐために、`git push` コマンドには、__`--recurse-submodules`__ というオプションが用意されています。
このオプションで `check` や `on-demand` といった値を指定すると、次のように振る舞いが変化します。

- __`git push --recurse-submodules=check`__ ... プッシュされていないサブモジュールのコミットを参照していたら、実行を中止する
- __`git push --recurse-submodules=on-demand`__ ... プッシュされていないサブモジュールのコミットを参照していたら、サブモジュール側を先にプッシュする
{{% /note %}}


サブモジュール側のリポジトリの更新内容を取り込む (git submodule update --remote)
----

サブモジュールとして参照しているリポジトリに更新があった場合、その内容を取得するには、__`git submodule update --remote`__ コマンドを使用します。

{{< code lang="console" title="サブモジュールの内容を更新" >}}
$ git submodule update --remote --recursive
{{< /code >}}

`--remote` オプションを付けずに実行した場合は、カレントプロジェクトで記録されているコミットハッシュ値でファイルを取得するという意味になります。
なので、参照している共有ライブラリ側で独立して更新された内容を取り込むには、`--remote` オプションが必要です。
デフォルトではすべてのサブモジュールを更新しようとしますが、特定のサブモジュールだけ更新することもできます。

{{< code lang="console" title="指定したサブモジュールのみ更新" >}}
$ git submodule update --remote --recursive lib1 lib2 lib3
{{< /code >}}

上記のように `git submodule update --remote` を実行すると、サブモジュール側のチェックアウト状態は、ふたたび detached HEAD になります。
つまり、完全にリモートリポジトリ側 (GitHub) の最新のコミットを参照する状態に置き換えられます。
サブモジュール側に、まだプッシュされていないローカルコミットがあり、その内容とマージしたいときは、__`--merge`__ オプションを付けて次のように実行します。

{{< code lang="console" title="サブモジュールのローカルコミットにリモートコミットをマージ" >}}
$ git submodule update --remote --recursive --merge
{{< /code >}}

`--merge` オプションを付けずに実行して、サブモジュールにローカルコミットした内容が見えなくなってしまっても慌てる必要はありません。
いかなる場合でもコミットログは残っている（`git log --all` ですべて確認できる）ので、適切なブランチに適切なコミットをマージするだけです。

```console
$ cd my-libs         # サブモジュールへ移動
$ git switch main    # マージ先のブランチに切り替え
$ git merge fd5ccb6  # リモート側の最新 (detached HEAD) をマージ
$ git add .
$ git commit
$ git push
```

とはいえ、`git submodule update --remote` を実行する前に、サブモジュール内で行った修正はコミット＆プッシュまで済ませておく、という手順にした方が混乱せずに済むでしょう。


他の開発者が行ったサブモジュールのコミットハッシュ更新を反映する (git pull --recurse-submodules)
----

メインプロジェクト内のサブモジュールを更新した場合（コミットハッシュ値を更新した場合）、他の開発者もそのコミットハッシュに対応するサブモジュールのコードを取得する必要があります。
そのためには、`git pull` でメインプロジェクトの更新内容を取り込んだ後に、`git submodule update` を実行します。

```console
$ git pull
$ git submodule update --recursive
```

この作業も定型の操作になるので、まとめて実行する __`git pull --recurse-submodules`__ というコマンドが用意されています。

```console
$ git pull --recurse-submodules
```


サブモジュールを削除する (git submodule deinit)
----

サブモジュールが必要なくなったら、次のように登録情報やローカルに残ったファイルを削除できます。

```console
# .git/config からエントリを削除（git submodule init で追加されたもの）
$ git submodule deinit <ディレクトリ名>

# .gitmodules ファイル内のセクションを削除（git submodule add で追加されたもの）
$ git config -f .gitmodules --remove-section submodule.<ディレクトリ名>

# ローカルに残ったディレクトリを削除
$ rm -rf <ディレクトリ名>
$ rm -rf .git/modules/<ディレクトリ名>

# 変更をコミット＆プッシュ
$ git add .
$ git commit
$ git push
```


複数のサブモジュールをまとめて操作する (git submodule foreach)
----

これまでに述べてきたように、Git サブモジュールはあくまで別リポジトリのリファレンスとして動作するため、メインプロジェクト上で `git` コマンドを実行しても、コミットハッシュくらいしか参照できません。
__`git submodule foreach COMMAND`__ コマンドを使うと、各サブモジュール内で任意のコマンド (`COMMAND`) を実行したかのように振る舞わせることができます。

{{< code lang="console" title="git submodule foreach の使用例" >}}
# すべてのサブモジュール内で git diff を実行
$ git submodule foreach "git diff"

# すべてのサブモジュール内で git branch を実行
$ git submodule foreach "git branch"
{{< /code >}}


Git サブモジュール用の便利なエイリアス
----

Git サブモジュールをうまく扱うには、Git コマンドに様々なオプションを付けて操作する必要があります。
次のように、よく使いそうなコマンドをエイリアスとして登録しておくと便利です。

```console
$ git config --global alias.sclone 'clone --recurse-submodules'
$ git config --global alias.supdate 'submodule update --remote --recursive --merge'
$ git config --global alias.sdiff '!'"git diff && git submodule foreach 'git diff'"
$ git config --global alias.spush 'push --recurse-submodules=on-demand'
```

- __`git sclone`__ ... `git clone` すると同時にサブモジュールの内容も取得する
- __`git supdate`__ ... 全サブモジュールの最新バージョンのファイルを取得する（ローカルコミットがあればマージ）
- __`git sdiff`__ ... 全サブモジュールの変更内容を含んだ diff を表示する
- __`git spush`__ ... プッシュ時にサブモジュール側のコミットを先にプッシュする


Git サブモジュール関連のコマンドのまとめ
----

| コマンド | 説明 |
| ---- | ---- |
| `git clone --recurse-submodules <URL>` | `git clone` すると同時にサブモジュールも取得する |
| `git submodule add <URL>` | サブモジュールを追加する |
| `git submodule status` | サブモジュールのコミットハッシュを表示する |
| `git submodule init` | ローカルのインデックスでサブモジュールを管理し始める |
| `git submodule update --recursive` | コミットハッシュに従ってサブモジュールを取得する |
| `git submodule update --init --recursive` | `init` と `update` を同時に実行する |
| `git submodule update --remote --recursive` | サブモジュールの最新バージョンを取得する |
| `git pull --recurse-submodules` | `git pull` と同時に `update` を実行する |
| `git push --recurse-submodules=on-demand` | プッシュ時にサブモジュール側のコミットを先にプッシュする |

