---
title: Git でパッチファイルを作成する
date: "2010-09-17"
---

パッチファイルの作成と適用
====

git patch コマンドで適用可能なパッチファイルを作成する
----
ここでは、git apply コマンドで適用可能な Git 用の patch ファイルを作成する方法を示します。
インデックスに追加していない変更を、とりあえずパッチファイルとして残しておきたいような場合は、以下のように作成します。

```bash
$ git diff file.txt > file.patch
```

例えば、インデックスに追加していない変更を以下のように破棄してしまったとしても、

```bash
$ git checkout file.txt
```

パッチファイルが残っていれば、後からその変更分を復活させることができます。

```
$ git apply file.patch
```


Linux 標準の patch コマンドで適用可能なパッチファイルを作成する
----
Linux に標準で付属している `patch` コマンドで適用できるパッチファイルを作成するには、`git diff` を実行するときに `--no-prefix` オプションを指定します。
これにより、パッチファイル内のパス表記が、

```diff
--- a/aaa/bbb/ccc/file.cpp
+++ b/aaa/bbb/ccc/file.cpp
```

のように `a` や `b` のプレフィックスがついていたものを

```diff
--- aaa/bbb/ccc/file.cpp
+++ aaa/bbb/ccc/file.cpp
```

のように `patch` コマンドで解釈できるものに変更できます。

#### 例: patch ファイルの作成

```bash
$ git diff --no-prefix file.cpp > file.patch
```

#### 例: patch ファイルを適用

```bash
$ （.git ディレクトリのあるルートディレクトリへ移動）
$ patch -p0 < file.patch
```

### おまけ

仮にパッチファイルを作るときに `--no-prefix` を付けないで作成してしまった場合でも、`patch` コマンドの引数で `-p1` オプションを付けてパスの階層を調整すれば、そのまま適用することができます。

```bash
$ patch -p1 < file.patch
```


指定したディレクトリから適用するためのパッチファイルを作成する
====

`git diff` の出力からパッチファイルを作成すると、そのパッチファイルは `.git` ディレクトリのあるディレクトリから適用できる形のパッチファイルとして作成されます。
例えば、`.git` ディレクトリと同じ階層に `aaa` ディレクトリがあるとして、その中の `file.txt` のパッチファイルを作成するとします。

```bash
$ cd aaa
$ git diff --no-prefix file.cpp > file.prefix
```

と実行すると、パッチファイル内のパスの表現は、

```diff
--- aaa/file.cpp
+++ aaa/file.cpp
```

となります。
つまり、このパッチファイルは `.git` ディレクトリから `patch` コマンドで当てる形で作成されます。
このパッチファイルを、`aaa` ディレクトリをカレントディレクトリにして適用できるようにするには、`--relative` オプションで、基準となるディレクトリを指定します。

```bash
$ git diff --no-prefix --relative=aaa/ file.cpp > file.prefix
```

これで、`aaa` ディレクトリからパッチファイルを当てることができます。

```bash
$ cd aaa
$ patch -p0 < file.patch
```


メールで送るためのパッチファイルを作成する
====

`git format-patch` でパッチファイルを作成すると、そのパッチファイルを受け取った人は、`git am` コマンドを使ってコミット履歴をキープして自分のリポジトリにコミットできます。

例えば、以下のように実行すると、コミット履歴からパッチファイルを `patches` ディレクトリ作成します。
パッチファイルはコミット 1 つごとに 1 つずつ作られます。

```bash
$ git format-patch -o patches fec09ab        # fec09ab からの HEAD までのコミット
$ git format-patch -o patches fec09ab..      # 同上
$ git format-patch -o patches fec09ab..HEAD  # 同上
```

作成されたパッチファイルは `git am` コマンドで適用することができます（am は apply mailbox の略）。

```bash
$ git am  patches/0001-HogeHoge.patch
```

すでにパッチ対象のファイルが更新されていると、`git am` でエラーが発生します。
`git am` を取り消したい場合は、以下のようにすると、パッチを適用しようとする前の状態に戻ります。

```bash
$ git am --abort
```

`git am` に `-3` オプションをつけると、修正されたファイルに対してパッチを適用できる可能性があがります。

```bash
$ git am -3 patches/0001-HogeHoge.patch
```

上記のようにしてコンフリクトが発生した場合は、まずは `git status` で対象のファイルを確認し、ファイルを修正してコンフリクトを解決します。
その後、以下のようにしてパッチファイルによるコミットを確定させます。

```bash
$ git add conflicted_file.cpp
$ git am --resolved
```

