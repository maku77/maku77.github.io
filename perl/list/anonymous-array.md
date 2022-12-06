---
title: "無名配列コンストラクタ／無名ハッシュコンストラクタ"
date: "2008-05-18"
---

無名配列と無名ハッシュ
----

無名配列コンストラクタ `[ ]` (anonymous array constructor) で配列を作成すると、配列変数名を持たない配列データがメモリ上に作成され、そのリファレンスだけを取得できます。
無名配列は配列要素を `[ ]` で囲んで作成できます。
戻り値は配列ではなく、リファレンスであることに注意してください。

~~~ perl
my $reference = ['AAA', 'BBB', 'CCC];  # 3要素の無名配列へのリファレンスを取得
my $reference = [qw(AAA BBB CCC)];     # 上記と同様
my $reference = [];                    # 空の無名配列へのリファレンスを取得
~~~

ハッシュの場合も同様に、無名ハッシュコンストラクタ `{ }` (anonymous hash constructor) を使って、ハッシュ変数名を持たないハッシュデータをメモリ上に作成できます。

~~~ perl
my $reference = {AAA => 100, BBB => 200, CCC => 300};
my $reference = {};    # 空の無名ハッシュへのリファレンスを取得
~~~


コラム: 使用する括弧に関して
----

通常の配列とハッシュを作成する場合は、どちらの場合も要素を `( )` で囲んで作成します。
配列とハッシュ、どちらが生成されるかは、変数名のプレフィックスに `@` と `%` のどちらの記号が使われているかで判断されます。

~~~ perl
@arr = (100, 200, 300);
%hash = ('a', 100, 'b', 200);
~~~

一方、無名配列と無名ハッシュを作成する場合は、取得できる値がどちらも同じ形のリファレンス（スカラー値）なので、異なる括弧を使ってどちらを生成するか区別しないといけません。

~~~ perl
$ref = [100, 200, 300];
$ref = {'a', 100, 'b', 200};
~~~
