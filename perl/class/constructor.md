---
title: "コンストラクタを定義する"
date: "2008-05-21"
---

コンストラクタ定義の例
----

下記はクラス `MyClass` のコンストラクタを定義するサンプルです。

```perl
{
    package MyClass;
    sub new {
        my $class = shift;
        my $self = {};
        bless $self, $class;
    }
}
```

実際にこのクラスのインスタンスを作成するときは下記のように実行します。

```perl
my $obj = MyClass->new;
```


解説
----

Perl では、あるリファレンス変数をクラス名に対して **bless （祝福）**すると、そのリファレンス変数はそのクラスのインスタンスになります。
リファレンス変数はどんな変数のリファレンスでも構いませんが、多くの場合 はハッシュ変数のリファレンスを用いて、インスタンス変数などをハッシュで管理できるようにします。

```perl
my $ref = {};
bless $ref, 'MyClass';    # $ref は MyClass クラスのインスタンスになる
```

上記のように `MyClass` という名前で bless されたリファレンス変数 `$ref` を使って、

```perl
$ref->greet(@params);
```

とすると、`MyClass` パッケージの `greet` メソッドが呼び出されるようになります。
上記のように矢印を使った呼び出しは、あたかも以下のように `MyClass` パッケージのメソッドを呼び出したのと同じように振舞います。

```perl
MyClass::greet($ref, @params);
```

あるパッケージ内のサブルーチンで、そのパッケージ名に対して bless したリファレンスを返すようにすれば、そのサブルーチンはコンストラクタとして使用できるようになります。

```perl
MyClass->hoge();
```

のようにクラスメソッドを呼び出した場合に、暗黙的に第 1 引数でクラス名 `MyClass` が渡されることを利用して、パッケージ名をクラス名とみなして bless することができます。
C++ や Java と違い、コンストラクタの名前は `new` でなくても構いません。

```perl
{
    package MyClass;
    sub new {
        my $class = shift;  # MyClass->new; と呼び出した場合 $class は 'MyClass' となる
        my $self = {};        # 何のリファレンスでも構わない
        bless $self, $class;  # $ref はインスタンスとなる
        $self;
    }
}
```

`bless` 演算子は、便宜的に bless したリファレンスを返すようになっているので、上記のような単純なコンストラクタは次のように簡略化して記述できます。

```perl
sub new {
    bless {}, shift;
}
```

bless した後にいろいろ初期化処理を行う場合は、最後の行に `$self` とだけ記述しておくのを忘れないようにしてください。
