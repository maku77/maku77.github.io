---
title: p4-backup：オープンされているファイルをバックアップする
created: 2008-05-09
---

概要
====
末尾の `p4-backup` スクリプトは、Perforce クライアントで編集中の（オープンしている）ファイルをバックアップするスクリプトです。


使い方
====

p4-backup コマンドは、バックアップしたい Perforce ディレクトリのパスと、保存先のディレクトリをパラメータとして受け取ります。

#### //dir/name 以下のファイルを backup ディレクトリ以下にバックアップ
```
$ p4-backup //dir/name... backup
```

`p4-backup` のパラメータを省略すると、下記のように実行したのと同様に処理されます。

#### 全てのオープン中のファイルを backup ディレクトリにバックアップ
```
$ p4-backup //... backup
```

カレントディレクトリ以下のファイルを backup ディレクトリにバックアップしたいのであれば、下記のように実行します。

```
$ p4-backup ...
```


スクリプト
====

#### p4-backup (Perl スクリプト)
```perl
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
```

