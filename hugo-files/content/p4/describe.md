---
title: "Perforceメモ: チェンジリストの内容を確認する (p4 describe)"
url: "p/ee5sdww/"
date: "2006-08-22"
tags: ["perforce"]
aliases: ["/p4/describe.html"]
---

指定したチェンジリストで変更された内容 (diff) の一覧を表示する
----

Perforce では、一連のファイルの変更を、チェンジリストという編集中ファイルのリストとして管理しています。
あるチェンジリストで管理しているファイルの修正内容を確認するには以下のようにします。

{{< code lang="console" title="例: チェンジリスト #12345 で変更された内容 (diff) をまとめて表示する" >}}
$ p4 describe 12345
{{< /code >}}

出力する diff の形式をカスタマイズするには、`p4 describe` コマンドに以下のオプションを指定します。

* `-dc`: context 形式
* `-du`: unified 形式
* `-dn`: RCS 形式


指定したチェンジリストで変更したファイルの一覧を表示する
----

特定のチェンジリスト内で修正されているファイルの一覧情報だけを確認したいときは、`p4 describe` コマンドに `-s` を付けて実行します。

{{< code lang="console" title="例: チェンジリスト #12345 で変更したファイルの一覧を表示する" >}}
$ p4 describe -s 12345
Change 12345 by maku@maku.projectx on 2007/02/23 14:12:24

        Modified hogehoge.

Affected files ...

... //ProjectX/src/Hoge.cpp#8 edit
... //ProjectX/src/Hoge.h#5 edit
{{< /code >}}


チェンジリストの内容を標準出力へ出力する
----

`p4 change` コマンドに `-o` オプションを指定すると、チェンジリストの内容を標準出力に出力することができます。

```console
$ p4 change -o                     # デフォルトのチェンジリストの内容
$ p4 change -o <ChangeListNumber>  # 指定したチェンジリストの内容
```
