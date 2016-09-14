---
title: "Windows で Linux 系のコマンドを使用できるようにする (Gow)"
created: 2012-11-19
---

Gow のインストール
----

[Gow (Gnu On Windows)](https://github.com/bmatzelle/gow/wiki)

Gow をインストールすると、Windows で Linux の主なコマンド群をサクッと使うことができるようになります。
インストーラを実行するだけで数秒でインストールできるので、簡単なコマンドを使うために Cygwin のような大げさな環境をインストールしなくても済みます。

Gow で使えるようなる Linux 系コマンドの一覧
---

[Gow - executables_list](https://github.com/bmatzelle/gow/wiki/executables_list)

Gow がインストールされている環境であれば、以下のように確認することもできます。

#### Gow 0.7 で確認

```
C:\> gow -l
Available executables:

  awk, basename, bash, bc, bison, bunzip2, bzip2, bzip2recover, cat,
  chgrp, chmod, chown, chroot, cksum, clear, cp, csplit, curl, cut, dc,
  dd, df, diff, diff3, dirname, dos2unix, du, egrep, env, expand, expr,
  factor, fgrep, flex, fmt, fold, gawk, gfind, gow, grep, gsar, gzip,
  head, hostid, hostname, id, indent, install, join, jwhois, less,
  lesskey, ln, ls, m4, make, md5sum, mkdir, mkfifo, mknod, mv, ncftp, nl,
  od, pageant, paste, patch, pathchk, plink, pr, printenv, printf, pscp,
  psftp, putty, puttygen, pwd, rm, rmdir, scp, sdiff, sed, seq, sftp,
  sha1sum, shar, sleep, sort, split, ssh, su, sum, sync, tac, tail, tar,
  tee, test, touch, tr, uname, unexpand, uniq, unix2dos, unlink, unrar,
  unshar, uudecode, uuencode, vim, wc, wget, whereis, which, whoami,
  xargs, yes, zip
```

