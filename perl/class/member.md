---
title: "メンバ変数とメンバメソッドを定義する"
date: "2008-05-21"
---

コンストラクタとメソッドの記述例
----

```perl
{
    package MyCounter;
    sub new {
        my $class = shift;
        my $self = { Count => 0 };    # デフォルト値を設定
        bless $self, $class;
        $self->_init(@_);    # 残りの引数を渡す
        $self;
    }

    sub _init {
        my $self = shift;
        my ($count) = @_;
        $self->{Count} = $count if defined $count;
    }

    sub increment {
        my $self = shift;
        ++$self->{Count};
    }

    sub get_count {
        my $self = shift;
        $self->{Count};
    }
}

# 使用例
my $obj = MyCounter->new(10);  # $ref->{Count} の初期値は 10
$obj->increment;               # $ref->{Count} は 11 になる
print $obj->get_count;         # $ref->{Count} の値を取得
```

解説
----

Perl においてインスタンスとは、bless したリファレンス変数であり、インスタンス変数を設定するということは、そのリファレンス変数に対して値を設定することを意味しています。
例えば、bless したリファレンスがハッシュへのリファレンスの場合は、インスタンス変数はハッシュ要素として管理します。

上記の例では、インスタンス変数を初期化するための `_init` メソッドを定義しています。インスタンス経由で `_init` メソッドを呼び出すと、暗黙的に第 1 引数にインスタンスのリファレンス（bless されたリファレンス）が渡されるので、そのリファレンスに対して値をセットするようにします。

もちろん `_init` メソッドのような初期化メソッドを用意せずに、コンストラクタ内でインスタンス変数を初期化してしまうこともできます。

```perl
sub new {
    my $class = shift;
    my ($count) = @_;    # パラメータを受け取る
    my $self = { Count => 0 };
    $self->{Count} = $count if defined $count;
    bless $self, $class;
}
```

