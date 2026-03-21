---
title: "Gitメモ: ファイルの変更を取り消してコミットやインデックスの内容に戻す (git checkout, git checkout HEAD)"
url: "p/xjixs9v/"
date: "2010-09-17"
tags: ["git"]
aliases:
  - /git/file/checkout-from-commit.html
  - /git/file/checkout-from-index.html
---

Git には、ファイルの状態が存在する場所として、大きく次の 3 つがあります。

- **ワーキングツリー（作業ディレクトリ）** … 実際に編集しているファイル
- **インデックス（ステージングエリア）** … `git add` でコミット対象として登録された状態
- **コミット（リポジトリ）** … `git commit` で確定された状態

`git checkout` コマンドを使うと、インデックスやコミットの内容でワーキングツリーのファイルを上書きし、変更を取り消すことができます。


ファイルの内容を最新コミットの内容に戻す
----

ファイルの変更をすべて破棄して、最後にコミットした状態に戻したい場合は、次のように `HEAD`（最新コミット）を指定して実行します。

```console
$ git checkout HEAD file.txt
```

この方法では、`git add` 済みのインデックスの内容も最新コミットの状態にリセットされます。
ファイル名を複数指定したり、ディレクトリ名を指定したりすることも可能です。


ファイルの内容をインデックスの内容に戻す
----

`git add` した後にさらにファイルを編集してしまったけれど、`git add` した時点の内容に戻したい場合は、次のように `git checkout` でファイル名だけを指定します。

```console
$ git checkout file.txt
```

`HEAD` を付けた場合との違いに注意してください。

- `git checkout HEAD file.txt` … **最新コミット**の内容でワーキングツリーを上書きする
- `git checkout file.txt` … **インデックス（`git add` した時点の内容）** でワーキングツリーを上書きする

まだ一度も `git add` していないファイルに対して実行した場合は、インデックスの内容＝最新コミットの内容になるため、結果的にどちらも同じ動作になります。

{{% note title="git rm した後のチェックアウト" %}}
`git rm` を実行すると、ワーキングツリーからファイルが削除されるだけでなく、インデックスからも削除されます。
そのため、次のような操作は失敗します。

```console
$ git rm file.txt  # ワーキングツリーとインデックスから file.txt を削除
$ git checkout file.txt  # これは失敗する！（インデックスに file.txt がないため）
```

この場合は、コミットから復元する必要があります。

```console
$ git checkout HEAD file.txt  # 最新コミットから file.txt を復元する
```
{{% /note %}}

