---
title: ディレクトリハンドルによってファイル名のリストを取得する
date: "2008-03-25"
---

ディレクトリハンドルに対して `readdir` を使用することでファイルを列挙することができます。
グロブ関数 (`glob`) を使うよりも低レベルの操作になるので、少しコード量が増えます。

```perl
opendir DIR, './'
    or die "Cannot open current directory: $!";

while (my $name = readdir DIR) {
    print "$name\n";
}

closedir DIR;
```

`glob` 関数とは違い、`readdir` 関数が返す名前には、ディレクトリ内のファイル名（ディレクトリ名）だけしか含まれません。

ディレクトリハンドルを `readdir` で読み出すと、ドットで始まるファイルなどもすべて返されるので、これらのファイル名が必要ない場合は自力でスキップするコードを書く必要があります。

```perl
while (my $name = readdir DIR) {
    next if $name =~ /^\./;    # ドットで始まるファイルをスキップ
    print "$name\n";
}

while (my $name = readdir DIR) {
    next if $name eq '.' or $name eq '..';    # '.' と '..' をスキップ
    print "$name\n";
}
```

ある拡張子を持つファイルだけを処理するには次のようにします。

```perl
while (my $name = readdir DIR) {
    next unless $name =~ /\.txt$/;    # 拡張子が '.txt' でなかったらスキップ
    print "$name\n";
}
```

