---
title: クラスメソッドとしてもインスタンスメソッドとしても使えるメソッドを定義する
date: "2008-05-22"
---

ここでは、あるメソッドがクラスメソッドの形 (`MyClass->hoge()`) で呼び出されたのか、インスタンスメソッドの形 (`$obj->hoge()`) で呼び出されたのかによって、処理を切り替える方法を示します。

あるパッケージに bless されたリファレンスを `ref` 演算子の引数として渡すと、そのパッケージ名を取得することができます。
それ以外の引数を渡すと、`ref` 演算子は空文字列を返すようになっています。

`ref` の戻り値を真偽値として利用すれば、渡した変数がリファレンスであるかどうかを判断できます。
パッケージ内のメソッドの第 1 引数を `ref` 演算子でチェックすれば、そのメソッドがクラスメソッドとして呼び出されているか、インスタンスメソッドとして呼び出されているかを判断できます。

```perl
{
    package MyClass;
    sub greet {
        my $either = shift;
        ref $either ?
            print "Instance method.\n" :
            print "Class method.\n";
    }
}

MyClass->greet;  # "Class method"

my $ref = bless {}, 'MyClass';
$ref->greet;  # "Instance method"
```

逆に、クラスメソッドとしてだけ、あるいは、インスタンスメソッドとしてだけ呼び出せるメソッドを定義することは Perl ではできません。
その代わりに、間違った呼び出し方をした場合に例外を発生させることは可能です。

```perl
sub instance_method {
    ref(my $self = shift) or croak "instance variable needed";
    # ...
}

sub class_method {
    ref(my $class = shift) and croak "class name needed";
    # ...
}
```

`ref` の詳細に関しては、`perldoc -f ref` を参照してください。

