---
title: "Perlメモ: ファイル名を変更する (rename)"
url: "p/immphre/"
date: "2008-03-24"
tags: ["perl"]
aliases: ["/perl/file/rename.html"]
---

Perl で既存ファイルの名前を変更するには、`rename` を使用します。

{{< code lang="perl" title="old.txt を new.txt にリネーム" >}}
rename 'old.txt', 'new.txt'
    or die "Cannot rename files: $!";
{{< /code >}}

リネーム後のファイル名を持つファイルがすでに存在する場合は、そのファイルが上書きされてしまうので、以下のようにファイルの存在を先にチェックするべきです。

{{< code lang="perl" title="リネームでの既存ファイルの上書きを防ぐ" >}}
my $old = 'old.txt';
my $new = 'new.txt';
if (-e $new) {
    warn "$new already exists.\n";
} else {
    rename $old, $new or warn "Cannot rename files: $!";
}
{{< /code >}}

`rmname` 関数は、ファイルの移動にも使用することができます。

- [参考: ファイルを移動する (`rename`)](/p/o6t5j9k/)

