---
title: "ファイルを移動する (rename)"
date: "2008-03-24"
---

Perl で既存ファイルの移動を行いたいときは、ファイル名の変更にも使える `rename` 関数を使用します。

#### dir1/sample.txt を dir2/sample.txt へ移動

~~~ perl
rename 'dir1/sample.txt', 'dir2/sample.txt'
    or warn "Cannot move sample.txt: $!";
~~~

移動先のファイルがすでに存在する場合は、上書きされてしまうので注意が必要です。
`File::Copy` モジュールの `move` 関数を使ってファイルを移動させることもできます。

- [参考: ファイル名を変更する (rename)](rename.html)

