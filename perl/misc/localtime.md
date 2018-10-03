---
title: "現在の日時を文字列で取得する (localtime, gmtime)"
date: "2008-03-24"
---

`localtime` 関数や、`gmtime` 関数をスカラーコンテキストで呼び出すと、現在の日時を文字列表現で取得することができます。

~~~ perl
my $tm_local = localtime;  # ローカル時間を文字列で取得
my $tm_gmt = gmtime;       # 国際標準時間 (GMT) を文字列で取得
print "$tm_local\n";       # 'Mon May  5 14:32:46 2008'
print "$tm_gmt\n";         # 'Mon May  5 05:32:46 2008'
~~~

`localtime` と `gmtime` は引数としてタイムスタンプを受け取ることができますが、これを省略すると、デフォルトで `time` 関数が返す値を使用するようになっています。
`time` 関数は現在の日時を表すタイムスタンプを返します。

#### 下記 2 つは同じ意味

~~~ perl
my $str = localtime;
my $str = localtime time;
~~~

