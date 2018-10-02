---
title: "変数の内容をファイルに保存する／復元する"
date: "2008-05-18"
---

`Storable` モジュールの `store` 関数を使うと、指定した変数の値をバイナリファイルとして保存することができます。
保存するデータはリファレンスで指定します。

~~~ perl
use Storable;

my %data = (
 aaa => [100, 101, 102],
 bbb => [200, 201, 202],
);
store \%data, 'sample.dat';
~~~

複数の配列データも、次のように無名配列リファレンスでまとめることで保存できます。

~~~ perl
store [\@array1, \@array2], 'sample.dat';
~~~

保存されたファイルの内容は、`retrieve` 関数で復元できます。
保存時と同様にリファレンスで内容を参照します。

~~~ perl
use Storable;
my $data = retrieve 'sample.dat';
~~~

より詳しい内容は、`perldoc Storable` で調べることができます。

