---
title: "指定したディレクトリ以下のファイルを列挙する (find)"
date: "2008-05-18"
---

`File::Find` モジュールの `find` メソッドを使用すると、指定したディレクトリ以下のファイルを走査できます。

```perl
use File::Find;

# ファイルが見つかるごとに呼び出される
sub on_find {
    print $File::Find::name find, "\n";    # ファイル名を相対パスで表示
    print $_, "\n";    # ファイル名の basename を表示
}

my @directories_to_search = qw(.);
find(\&on_find, @directories_to_search);
```

ファイルが見つかるごとに、最初の引数で指定したサブルーチンが実行されます（サブルーチンのリファレンスを指定します）。
呼び出されたサブルーチンの中では、`$File::Find::name` を使って見つかったファイル名を参照できます。
ファイル名は、検索を開始したディレクトリからの相対パスになっています。
`$_` を参照すると、ファイル名の `basename` を取得できます。

