---
title: "Perlメモ: Config ファイル（key=value という行形式のファイル）を読み込むサンプル"
url: "p/otkf7pj/"
date: "2008-05-22"
tags: ["perl"]
aliases: ["/perl/file/config.html"]
---

ここでは、下記のようなフォーマットのキー＆バリューの形で記述された config ファイルを処理するサンプルコードを示します。

{{< code title="読み込むファイルの例 (sample.config)" >}}
# 行コメント
key1 = value1
key2 = value2  # 行末コメント
key3 = value3
{{< /code >}}

{{< code lang="perl" title="パース例: 参考 「Perl クックブック」" >}}
while (<>) {
    chomp;    # 改行を削除
    s/#.*//;    # コメント部分を削除
    s/^\s+//;    # 先頭の空白を削除
    s/\s+$//;    # 行末の空白を削除
    next unless length;    # 実質的に空っぽの行なら次の行へ

    # キーと値を分割（'=' の前後の空白をついでに削除）
    my ($key, $val) = split(/\s*=\s*/, $_, 2);

    # ここで $key, $val を使用する
    $hash{$key} = $val;
}
{{< /code >}}

行ごとの処理をサブルーチンにした方がよいかもしれません。

```perl
sub get_key_and_value {
    my ($line) = @_;

    chomp $line;    # 改行を削除
    $line =~ s/#.*//;    # コメント部分を削除
    $line =~ s/^\s+//;    # 先頭の空白を削除
    $line =~ s/\s+$//;    # 行末の空白を削除
    return () unless length($line);    # 実質的に空っぽの行なら空リストを返す

    # キーと値を分割してリストで返す （'=' の前後の空白をついでに削除）
    return split(/\s*=\s*/, $line, 2);
}

while (<>) {
    my ($key, $val) = get_key_and_value($_);
    next unless defined $key;

    # Do something here with $key and $val...
}
```

