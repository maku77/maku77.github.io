---
title: "別の Git リポジトリの内容を強引にマージする"
date: "2020-02-18"
---

何をしたいか？
----

本質的に内容の異なるリポジトリ `App1` と `App2` があったとして、`App1` に `App2` 上のファイル群を強引にマージしてしまう方法です。
ここでは、`App2` の `master` ブランチの内容を、`App1` の `master` ブランチ上に取り込んでみます。

ユースケースとしては、`App2` に新しいアプリや機能が実装されていて、それらを `App1` にも徐々に採用していきたいので、とりあえずソースコードだけ `App1` 側に取り込んでしまおう、というケースが考えられます。

残念ながらコミットログはうまくマージできないっぽいです（一括でコードをコミットしたというログになってしまう）。
これだと、ファイル群を丸ごとコピペするのとあまり変わらないような気もしますが、ファイルのコピペだと、同じ名前のファイルがあった場合に、上書き保存することになってしまいます。
下記の手順のようにちゃんと `git merge` を使うことで、同一ファイル内のコンフリクトを解消しながらマージ作業を進めることができます。


2 つの Git リポジトリをマージする
----

### Step 1: 作業用のリポジトリをクローンする

まず、マージ作業を行う側（マージ先）の `App1` リポジトリをクローンして、`master` ブランチをチェックアウトしておきます。

```
$ git clone https://github.com/yourname/App1.git
$ cd App1
$ git checkout master （クローン直後は必要なし）
```

### Step 2: 取り込むリポジトリを参照できるようにする

`App1` リポジトリから、**リモートリポジトリ**として `App2` を参照できるようにします。
次のように、**`git remote add`** コマンドを使って、リモートリポジトリ名（ここでは `App2`）を作成しておくと、その後は長い URL を入力しなくても、`App2` という名前でリモートリポジトリを参照できるようになります。

```
$ cd App1
$ git remote add App2 https://github.com/yourname/repo2.git
```

リモートリポジトリが追加されているか確認しておきましょう。

```
$ git remote
App2    https://github.com/yourname/App2.git (fetch)
App2    https://github.com/yourname/App2.git (push)
origin  https://github.com/yourname/App1.git (fetch)
origin  https://github.com/yourname/App1.git (push)
```

正しく、`App2` という名前でリモートリポジトリ名が登録されていることが分かります。
ちなみに、`origin` というのはクローン元のリポジトリを示すもので、`git clone` 時に自動的に作成されます。

### Step 3: リモートリポジトリの内容をフェッチする

リモートリポジトリ名を追加しただけでは、`App2` の内容はローカルには存在しないので、**`git fetch`** コマンドでリモートリポジトリの内容をフェッチしておきます。

```
$ git fetch App2
```

フェッチが完了すると、`App2` リモートリポジトリのブランチの一覧（リモートトラッキングブランチ）を確認できます。

```
$ git branch -r
App2/master
App2/topic1
App2/topic2
origin/HEAD -> origin/master
origin/topic1
origin/topic2
...
```

リモートリポジトリの、各ブランチのログを確認することもできます。
このとき、必ず `<リポジトリ名>/<ブランチ名>` のようなペアで名前を指定する必要があります（ローカルブランチ名の指定と区別するため）。

```
$ git log App2/master
...
```

### Step 4: リポジトリの内容をマージする

あとは、**`git merge`** コマンドを使って、`App1` リポジトリの `master` ブランチへ、`App2` リポジトリの `master` ブランチの内容をマージするだけです。

```
$ git merge --allow-unrelated-histories App2/master
```

Git 2.9 以降は、関連のないリポジトリの内容をマージするときに、**`--allow-unrelated-histories`** オプションの指定が必要です。
このオプションを付けないと、以下のようなエラーになって失敗します。

```
$ git merge App2/master
fatal: refusing to merge unrelated histories
```

マージコマンドの実行に成功すると、次のようにたくさんコンフリクトすると思います。

```
$ git merge --allow-unrelated-histories App2/master
CONFLICT (add/add): Merge conflict in settings.gradle
Auto-merging settings.gradle
CONFLICT (add/add): Merge conflict in gradle/wrapper/gradle-wrapper.properties
Auto-merging gradle/wrapper/gradle-wrapper.properties
...
```

これはある意味しょうがないので、`git state` や `git diff` でコンフリクトしているファイルを確認しながら修正し、コンフリクトをすべて取り除いたらコミット＆プッシュしてマージ完了です。

```
$ git commit
```

#### トラブルシューティング (overwritten by merge)

マージ時に、次のようなエラーが出て失敗することがあります。

```
$ git merge --allow-unrelated-histories App2/master
error: The following untracked working tree files would be overwritten by merge:
        .idea/codeStyles/Project.xml
        .idea/codeStyles/codeStyleConfig.xml
        .idea/gradle.xml
        ...
Please move or remove them before you merge.
Aborting
```

これは、マージしようとしている `App2` リポジトリに含まれているファイル群が、マージ先の `App1` リポジトリの `.gitignore` で Git 管理対象外になっていたりすると発生します。
Git 管理されていないローカルファイルが上書きされてしまうから危ないよという警告ですね。
次のいずれかの方法で対応すればよいと思います。

- `App1` 側から上記ファイルを削除して、`App2` 側のファイルを受け入れる。
- `App1` 側の `.gitignore` を修正して、上記ファイルを Git 管理対象にしてコミットする。
- `App2` 側から上記ファイルを削除してコミットする。


### Step 5: リモートリポジトリ名を削除

これは必須ではないですけど、必要なくなったリモートリポジトリ名は邪魔なので削除しておきます。

```
$ git remote remove App2
$ git remote
origin
```

