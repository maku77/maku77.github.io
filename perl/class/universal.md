---
title: すべてのクラスの親クラスとして振舞う UNIVERSAL パッケージを拡張する
date: "2008-06-27"
---

Perl では継承関係を `@ISA` 配列のツリー構造によって表します。
メソッドを呼び出そうとすると、メソッドが最初に見つかるまで `@ISA` ツリーをさかのぼりながら検索します。
最後までメソッドが見つからなかった場合は、必ず最後に `UNIVERSAL` パッケージに存在するメソッドを検索します。
つまり、`UNIVERSAL` パッケージはすべてのクラスの基底クラスの役割を果たします。

```perl
sub UNIVERSAL::hoge {
    warn ref(shift), " can say hoge!\n";
}

my $obj = bless {}, "MyClass";
$obj->hoge();  # MyClass::hoge はないので、UNIVERSAL::hoge が呼び出される。
```

`UNIVERSAL` パッケージには `isa()`、`can()` といったユーティリティメソッドがあらかじめ定義されています。

