---
title: 配列やリストをソートする
created: 2008-02-28
---

リスト、配列をソートしたリストを得る
----

`sort` 関数は、デフォルトでは与えられたリストの要素を文字列とみなし、ASCII コード順にソートします。

```perl
my @arr = qw/ CCC AAA BBB /;
my @sorted = sort @arr;  # @arr の値は ('AAA', 'BBB', 'CCC')
```


リスト、配列を逆順にしたリストを得る
----

```perl
my @arr = reverse 1..5;  # @arr の値は (5, 4, 3, 2, 1)
```

`reverse` 関数は、スカラーコンテキストで、文字列を逆順にする用途でも使用できます。

```perl
my $val = reverse 'ABCDE';    # $val の値は 'EDCBA'
```


ソート方法を指定してソート（ソートサブルーチン）
----

`sort` 関数にソートサブルーチンを指定すると、任意のルールでリスト、配列をソートすることができます。

ソートサブルーチンは特殊変数 `$a`、`$b` を比較し、以下のような値を返すように実装します。

- 負の値: `$a` が `$b` より小さい場合
- 正の値: `$a` が `$b` より大きい場合
- `0`: `$a` と `$b` が等しい場合（比較できない場合）

例えば、数値でソートするためのソートサブルーチンは以下のように定義できます。

```perl
sub by_number {
    if ($a < $b) { -1 } elsif ($b > $a) { 1 } else { 0 }
    # これはスペースシップ演算子を使って $a <=> $b と書くことができる
}
```

ソートサブルーチンを `by_` で始まる名前で定義しておくと、`sort` の呼び出しコードが英語のように読めるようになります。

```perl
my @sorted = sort by_number @arr;  # Sort by number!
```

ソートサブルーチンが単純な場合、一般的にはインラインで指定してしまいます。

```perl
# 数値でソート（昇順）
my @sorted = sort { $a <=>$b } @arr;

# 数値でソート（降順）
my @sorted = sort { $b <=> $a } @arr;

# 文字列でソート（昇順）（ASCII コード順）
my @sorted = sort { $a cmp $b } @arr;
my @sorted = sort @arr;    # 上記と同様 （デフォルトの動作）

# 文字列でソート（降順）
my @sorted = sort { $b cmp $a } @arr;

# 大文字・小文字を区別しないで文字列でソート
my @sorted = sort { "\L$a" cmp "\L$b" } @arr;
```

ソートの応用
----

### Schwartz Transform （シュウォーツ変換）（シュワルツ変換）

```perl
my @sorted = sort by_hoge @arr;
```

のようにソートを行う場合、ソートサブルーチンは値の比較ごとに呼ばれるため、ソートサブルーチンで重い処理を行っている場合に効率が悪くなります。

このような場合は、すべての要素の比較用の値を要素に持つリストを作成し、そのリストをソートするという方法で効率を改善できます。一般的にこの手法は、Schwartz Transform （シュウォーツ変換）（シュワルツ変換）と呼ばれます。

```perl
my @sorted =
    map $_->[0],                  # 元の値をソートされた順に抽出
    sort { $a->[1] <=> $b->[1] }  # 比較用の値でソート
    map [ $_, by_hoge($_) ],      # 元の値と、比較用の値のペア（無名配列）を作成
    @arr;                         # ソートしたい配列
```

上記のソートサブルーチン部分と、比較する値を求める部分を変更すれば、別の配列データにも適用できます。

上記の処理を冗長に書くと、以下のようになります。ここでは、カレントディレクトリ内のファイル名のリストを変更日時順にソートします。

```perl
# 配列データを準備
my @names = <*>;

# 値と、比較用の値のペアを要素に持つ配列を作成
my @names_and_date = map [$_, -M], @names;

# 比較用の値でソート
my @sorted_names_and_date = sort {
    $a->[1] <=> $b->[1]
} @names_and_date;

# ソートされた配列から必要な値だけ取り出す
my @sorted_names = map $_->[0], @sorted_names_and_date;

# 結果出力
print "$_\n" for @sorted_names;
```

Schwartz Transform という名前は、Randal Schwartz にちなんで名付けられています。


### Orcish Maneuver （シャチ泳ぎ？） によるソート

Schwartz Transform に似たソート方法として、Orcish Maneuver というソート方法があります。例えば、

```perl
my @sorted = sort { hoge($a) <=> hoge($b) } @arr;
```

というソートを行う場合、hoge 関数が比較ごとに呼び出されてしまうので、これを防ぐために、比較用の値（hoge の計算結果）をキャッシュしながらソートする方法です。

```perl
my %temp = ();
@sorted = sort {
    ($temp{$a} ||= hoge($a)) <=>
    ($temp{$b} ||= hoge($b))
} @arr;
```

ハッシュ `%temp` には、hoge の計算結果が格納されており、まだ計算されていない場合のみ hoge を実行します。ただし、一般的によく使用されているのは Schwartz Transform です。

