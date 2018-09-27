---
title: "ファイル・ディレクトリを削除する (unlink, rmdir)"
date: "2008-03-25"
---

ファイルの削除 (unlink)
----

`unlink` にファイル名のリストを渡すと、指定したファイルを削除することができます。

~~~ perl
$cnt = unlink 'a', 'b', 'c';
unlink @goners;
unlink glob '*.bak';
~~~

`unlink` は削除に成功したファイルの数を返します。
各々のファイルが削除できたかを調べるには、次のようにループでひとつずつ削除する必要があります。

~~~ perl
for (glob '*.tmp') {
    unlink or warn "Cannot delete $_: $!";
}
~~~

より詳しい内容は `perldoc -f unlink` を参照してください。


ディレクトリの削除 (rmdir)
----

ディレクトリを削除する場合は、`rmdir` を使用することが推奨されています。

~~~ perl
rmdir 'tempdir' or warn "Cannot delete 'tempdir': $!";
~~~

