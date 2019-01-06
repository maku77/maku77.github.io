---
title: "インポート可能なモジュールを作成する"
date: "2008-06-29"
---

独自モジュールの基本
----

use でサブルーチンを main パッケージにインポート可能なモジュールを作成するには、下記のようなコードを記述します。

#### MyLib.pm（独自モジュール）

```perl
package MyLib;

use base qw(Exporter);
our @EXPORT = qw(func1);
our @EXPORT_OK = qw(func3 func4);

sub func1 { print "func1\n"; }
sub func2 { print "func2\n"; }
sub func3 { print "func3\n"; }

1;
```

#### main.pl（使用例）

```perl
use MyLib;
func1;
func2;
```

解説
----

```perl
use MyLib qw(func1 func2 func3);
```

とした場合、以下のようなコードを記述したのと同様の意味になります。

```perl
BEGIN {
    require MyModule;
    MyLib->import(qw(func1 func2 func3));
}
```

つまり、`MyLib` パッケージの中で適切に `import` サブルーチンを実装することで、main モジュールにサブルーチンをインポートできるようになります。

#### main.pl

```perl
use MyLib qw(func1 func2 func3);
func1;
func2;
func3;
```

#### MyLib.pm

```perl
package MyLib;

sub import {
    my $class = shift;
    for (@_) {
        *{"main::$_"} = \&$_;
    }
}

sub func1 { print "func1\n"; }
sub func2 { print "func2\n"; }
sub func3 { print "func3\n"; }

1;
```

ただ、上記のような単純な `import` では、インポートリストを省略した場合のデフォルトのインポートに対応できないし、モジュールを作成するごとにそのような `import` メソッドを記述していたのでは手間がかかります。そこで、**Exporter** というモジュールを使用してこれらの作業を単純化します。

Exporter モジュールは `import` ルーチンを提供しているので、これを継承するようにして独自のモジュールを作成します。`Export->import` には次のような特徴があります。

- インポートリストが省略されたら `@EXPORT` 配列の内容が使用される。
- インポートリストに明示的に指定するサブルーチンは `@EXPORT` あるいは `@EXPORT_OK` 配列に登録されていなければならない。

たとえば、インポートリストを省略した場合に `func1` だけがデフォルトで main パッケージにロードされるようにするには次のように定義します。

#### MyLib.pm

```perl
package MyLib;

use base qw(Exporter);
our @EXPORT = qw(func1);  # デフォルトのインポートリスト
our @EXPORT_OK = qw(func2 func3);

sub func1 { print "func1\n"; }
sub func2 { print "func2\n"; }
sub func3 { print "func3\n"; }

1;
```

これで、上記のモジュールは次のように使用できます。

```perl
use MyLib;  # デフォルトで func1 がインポートされる
func1;
```

`@EXPORT_OK` に `func2` `func3` が登録されているので、必要に応じて `func2` `func3` をインポートすることができます。

```perl
use MyLib qw(func2 func3);
func2;
func3;
```

デフォルトのインポートリストに加え、さらに明示的にインポートするサブルーチンを指定する場合は、`:DEFAULT` というショートカットを使用すると便利です。`:DEFAULT` は、デフォルトのインポートリストを示すショートカットです。

```perl
use MyLib qw(:DEFAULT func3);
func1;
func3;
```

