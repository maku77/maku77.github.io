---
title: "Gitメモ: Git でパッチファイルを作成する (git diff/apply/format-patch/am)"
url: "p/3x2viow/"
date: "2010-09-17"
tags: ["git"]
aliases: [/git/git-patch.html]
---

`git diff` で変更内容をファイルに保存しておく方法
----

### `git diff` → `git apply` の基本

インデックスに追加していない変更をパッチファイルとして残しておきたいときは、以下のように **`git diff`** の出力を保存しておきます。

```console
$ git diff file.txt > file.patch
```

これで、`file.txt` に関する変更が `file.patch` というファイルに保存されるので、次のように `file.txt` の内容を破棄しても大丈夫です。

```console
$ git checkout file.txt
```

パッチファイルの内容を適用するには、**`git apply`** コマンドを使います。

```console
$ git apply file.patch
```

このコマンドは、単純にワーキングツリー上のファイルにパッチの内容を反映するだけなので、コミットが作成されることはありません。

### Linux 標準の patch コマンドで適用可能なパッチファイルを作成する

Linux に標準で付属している `patch` コマンドで適用できるパッチファイルを作成するには、`git diff` を実行するときに **`--no-prefix`** オプションを指定します。
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

{{< code lang="console" title="例: patch ファイルの作成" >}}
$ git diff --no-prefix file.cpp > file.patch
{{< /code >}}

{{< code lang="console" title="例: patch ファイルの適用" >}}
$ （.git ディレクトリのあるルートディレクトリへ移動）
$ patch -p0 < file.patch
{{< /code >}}

{{% note %}}
仮にパッチファイルを作るときに `--no-prefix` を付けないで作成してしまった場合でも、`patch` コマンドの引数で **`-p1`** オプションを付けてパスの階層を調整すれば、そのまま適用することができます。
```console
$ patch -p1 < file.patch
```
{{% /note %}}

### パッチファイルの基準ディレクトリを調整する

`git diff` の出力からパッチファイルを作成すると、そのパッチファイルは `.git` ディレクトリのあるディレクトリから適用できる形のパッチファイルとして作成されます。
例えば、`.git` ディレクトリと同じ階層に `aaa` ディレクトリがあるとして、その中の `file.txt` のパッチファイルを作成するとします。

```console
$ cd aaa
$ git diff --no-prefix file.cpp > file.prefix
```

と実行すると、パッチファイル内のパスの表現は、

```diff
--- aaa/file.cpp
+++ aaa/file.cpp
```

となります。
つまり、このパッチファイルは `.git` ディレクトリを基準として `patch` コマンドを適用する前提で作成されます。
このパッチファイルを、`aaa` ディレクトリをカレントディレクトリにして適用できるようにするには、`--relative` オプションで、基準となるディレクトリを指定します。

```console
$ git diff --no-prefix --relative=aaa/ file.cpp > file.prefix
```

これで、`aaa` ディレクトリからパッチファイルを当てることができます。

```console
$ cd aaa
$ patch -p0 < file.patch
```


`git format-patch` でコミット内容をもとにパッチファイルを作成する方法
----

**`git format-patch`** コマンドを使うと、既存のコミット履歴をもとにパッチファイルを作成することができます。
このパッチファイルを受け取った人は、**`git am`** コマンドを使ってコミット履歴ごと取り込むことができます。

例えば、以下のように実行すると、コミット履歴からパッチファイルを `patches` ディレクトリに作成します。
パッチファイルはコミット 1 つごとに 1 つずつ作られます。

```console
$ git format-patch -o patches fec09ab        # fec09ab からの HEAD までのコミット
$ git format-patch -o patches fec09ab..      # 同上
$ git format-patch -o patches fec09ab..HEAD  # 同上
```

作成されたパッチファイルは `git am` コマンドで適用することができます（am は apply mailbox の略）。

```console
$ git am patches/0001-HogeHoge.patch
```

すでにパッチ対象のファイルが更新されていると、`git am` でエラーが発生します。
`git am` を取り消したい場合は、以下のようにすると、パッチを適用しようとする前の状態に戻ります。

```console
$ git am --abort
```

`git am` に `-3` オプションをつけると、修正されたファイルに対してパッチを適用できる可能性があがります。

```console
$ git am -3 patches/0001-HogeHoge.patch
```

上記のようにしてコンフリクトが発生した場合は、まずは `git status` で対象のファイルを確認し、ファイルを修正してコンフリクトを解決します。
その後、以下のようにしてパッチファイルによるコミットを確定させます。

```console
$ git add conflicted_file.cpp
$ git am --resolved
```

