---
title: "Perforceメモ: p4-backup：オープンされているファイルをバックアップする"
url: "p/e6wxjws/"
date: "2008-05-09"
tags: ["perforce"]
aliases: ["/p4/p4-backup.html"]
---

## 概要

末尾の `p4-backup` スクリプトは、Perforce クライアントで編集中の（オープンしている）ファイルをバックアップするスクリプトです。


## 使い方

p4-backup コマンドは、バックアップしたい Perforce ディレクトリのパスと、保存先のディレクトリをパラメータとして受け取ります。

{{< code lang="console" title="//dir/name 以下のファイルを backup ディレクトリ以下にバックアップ" >}}
$ p4-backup //dir/name... backup
{{< /code >}}

`p4-backup` のパラメータを省略すると、下記のように実行したのと同様に処理されます。

{{< code lang="console" title="すべてのオープン中のファイルを backup ディレクトリにバックアップ" >}}
$ p4-backup //... backup
{{< /code >}}

カレントディレクトリ以下のファイルを backup ディレクトリにバックアップしたいのであれば、下記のように実行します。

```console
$ p4-backup ...
```


## スクリプト

{{< code lang="perl" title="p4-backup (Perl スクリプト)" >}}
#!/usr/bin/perl
#
# Perforce backup utility.
#
# p4-backup copies files opened by a p4 client
# into a backup directory.
#
# Usage:
#   ./p4-backup <P4Path> <BackupDirectory>
#
# ChangeLog:
#   2008-05-09 Created.
#
use strict;
use File::Copy qw/ copy /;

my $path = $ARGV[0] || '//...';
my $backup_dir = $ARGV[1] || 'backup';

print "P4Path = $path\n";
print "BackupDir = $backup_dir\n\n";

if (!-e $backup_dir) {
    mkdir $backup_dir, 0755 or die "Cannot create '$backup_dir': $!";
}

print "Copying files...\n";
for (`p4 fstat -Ro $path`) {
    next unless /^... clientFile /;
    s/^... clientFile //;
    chomp;
    print "$_\n" if copy $_, $backup_dir;
}
{{< /code >}}
