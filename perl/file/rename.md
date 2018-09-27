---
title: "ファイル名を変更する (rename)"
date: "2008-03-24"
---

Perl で既存ファイルの名前を変更するには、`rename` を使用します。

#### old.txt を new.txt にリネーム

~~~ perl
rename 'old.txt', 'new.txt'
    or die "Cannot rename files: $!";
~~~

リネーム後のファイル名を持つファイルがすでに存在する場合は、そのファイルが上書きされてしまうので、以下のようにファイルの存在を先にチェックするべきです。

#### リネームでの既存ファイルの上書きを防ぐ

~~~ perl
my $old = 'old.txt';
my $new = 'new.txt';
if (-e $new) {
    warn "$new already exists.\n";
} else {
    rename $old, $new or warn "Cannot rename files: $!";
}
~~~

`rmname` 関数は、ファイルの移動にも使用することができます。

- [参考: ファイルを移動する (rename)](move.html)

