---
title: "Perlメモ: カレントディレクトリを変更する (chdir)"
url: "p/xc7nysi/"
date: "2008-03-25"
tags: ["perl"]
aliases: ["/perl/file/chdir.html"]
---

カレントディレクトリ（作業ディレクトリ）を変更するには、`chdir` を使用します。

{{< code lang="perl" title="作業ディレクトリを /aaa に変更する" >}}
chdir '/aaa' or die "Cannot change directory to /home: $!";
{{< /code >}}

`chdir` は内部でシステムコールが呼び出されるので、実行に失敗すると `$!` にエラー文字列がセットされます。
作業ディレクトリは、Perl プログラム実行元のシェルの作業ディレクトリが引き継がれますが、Perl プログラムはシェルとは別のプロセスとして動くので、Perl プログラム内で作業ディレクトリを変更しても、シェルの作業ディレクトリは変更されません。

`chdir` の引数を省略すると、現在のユーザのホームディレクトリに作業ディレクトリを設定します。

{{< code lang="perl" title="作業ディレクトリをホームディレクトリに変更する" >}}
chdir or die "Cannot change directory to home directory: $!";
{{< /code >}}

