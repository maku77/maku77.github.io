---
title: "use によるモジュールのインポート"
date: "2008-06-29"
---

a) デフォルトのサブルーチンをインポートする
----

```perl
use File::Basename;
```

のようにすると、`File::Basename` パッケージのデフォルトのサブルーチンがカレントパッケージにインポートされます。インポートされたサブルーチンは次のように使用できます。

```perl
my $dirname = dirname('/usr/bin/perl');
my $basename = basename('/usr/bin/perl');
```

b) インポートリストでインポートするサブルーチンを指定する
----

```perl
use File::Basename qw( basename fileparse );
```

指定したサブルーチンをだけをインポートすることができます。


c) インポートリストに空リストを指定し、フルネームでサブルーチンを呼び出す
----

```perl
use File::Basename ();
my $dirname = File::Basename::dirname('/usr/bin/perl');
```

フルネームでサブルーチンを呼び出すようにすると、タイプ数は多くなりますが、名前の衝突を防ぐことができます。


d) オブジェクト指向モジュールのインポート
----

```perl
use Math::BigInt;
my $obj = Math::BigInt->new(1234567890);
```

オブジェクト指向モジュールは、インスタンス化したオブジェクトを使ってメソッド呼び出しを行うため、通常は何もインポートしません。

ただし、中には関数指向モジュールとしても、オブジェクト指向モジュールとしても使用できるモジュールがあります。

```perl
# オブジェクト指向モジュールとして使用
use CGI;
my $cgi = CGI->new;
my $name = $cgi->param("name");

# 関数指向モジュールとして使用
use CGI qw(param);
my $name = param("name");
```

