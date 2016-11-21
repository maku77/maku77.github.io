---
title: デストラクタを定義する
created: 2008-06-24
---

Perl におけるデストラクタの定義例
----

```perl
{
    package MyClass;

    # コンストラクタ
    sub new {
        bless {}, shift;
    }

    # デストラクタ
    sub DESTROY {
        my $self = shift;
        print "Destruction.\n";
        $self->SUPER::DESTROY;  # 親クラスの DESTROY を呼び出す
    }
}

my $obj = MyClass->new;
```


解説
----

あるオブジェクト `$obj` のリファレンスカウンタが 0 になり、オブジェクトが破棄されるとき、

```perl
$obj->DESTROY;
```

が呼び出されます。オブジェクトが使用していた一時ファイルなどを削除する場合は、`DESTROY` メソッドの中で後始末を行うことができます。

`MyClass` クラスが `Base` クラスを継承しているようなケースでは、`Base` クラスの `DESTROY` メソッドが正しく呼び出されるようにするために、`MyClass::DESTROY` メソッド内で明示的に `Base::DESTROY` メソッドを呼んでやる必要があります。
親クラスのメソッドを呼び出すには、明示的なクラス名を指定する代わりに、`SUPER` キーワードを使用できます。

```perl
our @ISA = qw(Base);
sub DESTROY {
    $self = shift;
    # ...
    $self->SUPER::DESTROY;  # Base::DESTROY を呼び出す
}
```

基底クラスがまだ存在しない場合でも、デストラクタには必ず `$self->SUPER::DESTROY` を含めることが推奨されています（『続・初めての Perl』より）。

ただし、`SUPER::DESTORY` は、継承しているクラスの中で最初に見つかった `DESTROY` メソッドしか実行してくれないので、多重継承をしている場合は明示的にすべての親クラスの `DESTROY` メソッドを呼び出さないといけないようです。

```perl
our @ISA = qw(Base1 Base2);
sub DESTROY {
    my $self = shift;
    # ...
    $self->Base1::DESTROY;
    $self->Base2::DESTROY;
}
```

