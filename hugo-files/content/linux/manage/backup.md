---
title: "Linuxメモ: ディレクトリ内のファイルを zip ファイルにバックアップする"
url: "p/3qnenzf/"
date: "2017-08-19"
tags: ["linux"]
aliases: ["/linux/manage/backup.html"]
---

zip コマンドの基本
----
下記のようにすると、`sample` ディレクトリ内のファイルをすべて `sample.zip` アーカイブとして保存することができます。

```console
$ zip -r sample.zip sample
```

より上位のディレクトリからアーカイブを作成する場合は、同様に、

```console
$ zip -r sample.zip aaa/bbb/ccc/sample
```

とすることができますが、このようにすると、アーカイブ内に `aaa/bbb/ccc` ディレクトリが含まれてしまいます。
アーカイブ内のトップのディレクトリを `sample` ディレクトリにしたい場合は、次のようにカレントディレクトリを変更してから `zip` コマンドを実行するようにします。

```console
$ (cd aaa/bbb/ccc && zip -r sample.zip sample)
```

コマンド全体を括弧で囲んでいるのは、コマンド実行後にシェルのカレントディレクトリを元のディレクトリに復帰させるためです。


アーカイブファイル名に日付を入れる
----

`date` コマンドと組み合わせて、下記のように実行すれば、アーカイブ名に自動的に日付を入れることができます（例: `sample-20170819.zip`）。

```console
$ zip -r sample-`date +%Y%m%d`.zip sample
```


指定したディレクトリを除いてアーカイブする
----

例えば、アーカイブの対象から `.git` ディレクトリを除きたいときは、下記のように `--exclude` オプションを指定します。

```console
$ zip -r sample.zip --exclude=*/.git/* sample
```

複数の `--exclude` オプションを指定することもできます。

```console
$ zip -r sample.zip --exclude=*/.git/* --exclude=*/.svn/* sample
```

