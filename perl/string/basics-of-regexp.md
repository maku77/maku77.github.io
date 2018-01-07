---
title: 正規表現による文字列マッチングの基本
date: "2016-03-30"
---

ある文字列が正規表現パターンにマッチするかを調べるには `m//` 演算子を使用します。
`m//` 演算子は、デフォルトで `$_` に格納されたテキストの内容とマッチング処理を行います。

```perl
$_ = 'You can do it.';
if (m/can/) {
    print "Found!\n";
}
```

パターンの前後を囲むデリミタは、一般的にはスラッシュ (`/`) が使用されますが、下記のようにスラッシュ以外の記号を使用することができます（このあたりの柔軟性は `qw//` 演算子と同様です）。

```perl
m!pattern!
m?pattern?
m#pattern#
m<pattern>
m{pattern}
```

スラッシュをデリミタに使用する場合は、プレフィックスの `m` を省略することができます。
このショートカット記法を使うため、多くの人はスラッシュをデリミタとして使います。

```perl
$_ = 'hogehoge';
if (/pattern/) {
    ...
}
```

`$_` 以外の任意の文字列変数とマッチングさせるには、**結合演算子 (binding operator)** と呼ばれる `=~` を使います。

```perl
my $line = 'hogehoge';
if ($line =~ /pattern/) {
    ...
}
```

マッチングの結果の真偽値を、変数に保存しておくこともできます。

```perl
my $is_matched = /pattern/;
my $is_matched = $line =~ /pattern/;
my $is_matched = <STDIN> =~ /pattern/;
```

