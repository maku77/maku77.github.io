---
title: "Perlメモ: パッケージによる名前空間の設定 (package, our)"
url: "p/wehh6tg/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/syntax/namespace.html"]
---

名前空間の基本
----

`package` ディレクティブでパッケージ名を宣言すると、その後に定義される要素はそのパッケージの名前空間に配置されるようになります。
例えば、

```perl
package Hoge;
```

とすると、その後に定義した変数やサブルーチンなどは、`Hoge::` プレフィックスを付けてアクセスしなければいけなくなります（名前の衝突を防ぐことができるようになる）。
もちろん、同じパッケージ内からアクセスする場合は、プレフィックスは必要ありません。

下記の `hoge.pl` モジュールは、`Hoge` パッケージ内に複数の関数を定義しています。

{{< code lang="perl" title="hoge.pl" >}}
package Hoge;

sub greet {
    print "Hello\n";
}

sub greet_twice {
    greet();
    greet();
}

1;  # use や require による読み込み結果を真にするためのお決まり
{{< /code >}}

これを使用する側のスクリプトは下記のようになります。

{{< code lang="perl" title="main.pl" >}}
require 'hoge.pl';
Hoge::greet_twice();
{{< /code >}}

**パッケージ名は大文字で始めなければいけません**。
次のように複数の単語を `::` で繋いだパッケージ名を指定することもできます。

```perl
package They::Their::Them;
```


パッケージスコープの変数を定義する (our)
----

パッケージスコープで共有する変数を定義するには **`our`** キーワードを使用します。

```perl
package Foo;     # カレントパッケージを Foo に
our $bar = 100;  # $Foo::bar を定義する
```

詳しい説明は `perldoc -f our` で参照することができます。

