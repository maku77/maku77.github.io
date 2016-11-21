---
title: クラスを継承する
created: 2008-05-22
---

Perl におけるクラス継承のサンプル
----

`Base` クラスを継承した `MyClass` クラスを定義するには次のようにします。

```perl
{
    package MyClass;
    our @ISA = qw(Base);
    # ...
}
```

perl 5.004_04 以降の場合

```perl
{
    package MyClass;
    use base qw(Base);
    # ...
}
```

解説
----

次のように `MyClass` パッケージの `greet` メソッドを呼び出そうとしたとき、

```perl
MyClass->greet;
```

あるいは、`MyClass` という名前に bless されたリファレンス `$ref` を使って

```
my $ref = bless {}, 'MyClass'
$ref->greet;
```

のようにメソッドを呼び出そうとしたときに、`MyClass` パッケージの中に `greet` メソッドが見つからない場合、`@MyClass::ISA` というパッケージスコープの配列に格納されたパッケージ名に対して、`greet` メソッドが順番に検索されます。
`ISA` という名前は、IS-A 関係を示すところから来ています。

`MyClass` クラスを `Base` クラスから継承させたい場合は、次のようにします。

```perl
{
    package Base;
    sub greet { print "Hello"; }
}

{
    package MyClass;
    our @ISA = ('Base');
}

MyClass->greet;  # Base パッケージの greet メソッドが呼び出される。
```

多重継承したい場合は、`ISA` 配列に複数のパッケージ名を指定するだけです。

```perl
{
    package MyClass;
    our @ISA = qw(Base1 Base2 Base3);
}
```

上記のようにすると、`MyClass` クラスは `Base1`、`Base2`、`Base3` クラスを多重継承していることになります。

```perl
MyClass->greet;
```

としたときに `MyClass` パッケージに `greet` メソッドが定義されていない場合、`ISA` 配列に格納された順でパッケージ内のメソッドが検索されます。
つまり、`Base1`、`Base2`、`Base3` パッケージにそれぞれ `greet` メソッドが定義されている場合、`Base1::greet` が優先的に呼び出されます。

perl 5.004_04 からは、`@ISA` 配列を直接操作する代わりに `use base` ディレクティブを使用することができます。
`use base` ディレクティブを使用して継承関係を構築した場合は、コンパイル時に関係がチェックされます。

```perl
{
    package MyClass;
    use base qw(Base1 Base2);
}
```

これは、あたかも次のように記述したかのように振舞います。

```perl
package MyClass;
BEGIN {
    require Base1;
    require Base2;
    push @ISA, qw(Base1 Base2);
}
```

