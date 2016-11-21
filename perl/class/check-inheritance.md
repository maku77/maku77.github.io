---
title: あるクラスを継承しているかどうか調べる
created: 2008-06-27
---

すべてのクラスの基底クラスである `UNIVERSAL` パッケージに用意されている `isa()` メソッドを使用すると、あるクラスがあるクラスのサブクラス（あるいはそれ自身）であるかを調べることができます（IS-A 関係のチェック）。

#### クラスの継承関係を調べる

```perl
if (Rectangle->isa("Shape")) {
    print "Rectangle inherits Shape.";
}
```

#### オブジェクトの継承関係を調べる

```perl
my $obj = Rectangle->new();
if ($obj->isa("Shape")) {
    print "This obj is an instance of Shape.\n";
}
```

ちなみに、`$obj->isa()` という呼び出し形式を用いる場合は、`$obj` が bless されたオブジェクトでないとエラーになります。
`$obj` が bless されていない場合でもエラーを発生させないようにするには、次のように `UNIVERSAL` パッケージのメソッドを直接呼び出します。

```perl
if (UNIVERSAL::isa($obj, "Shape")) {
    ...
}
```

`isa()` と比べて `ref()` 関数は bless されたオブジェクト自分自身のクラス名を返すので、継承関係を考慮した IS-A 関係を調べることはできません。

```perl
my $obj = Rectangle->new();
if (ref $obj eq "Shape") {  # $obj は Rectangle であって Shape ではない…
    ...
}
```

