---
title: Perl でヒアドキュメント
created: 2008-07-15
---

Perl では下記の構文でヒアドキュメント (here doc) を定義します。

```perl
print <<EOT;
Dear $name,

My order arrived yesterday.
EOT
```

識別子を囲むクォート文字によって、here doc 文字列内の変数が展開されるかどうかが決まります。
クォート文字を省略した場合は、ダブルクォートで囲んだものとみなされます（変数が展開される）。

```python
# here doc 文字列内の変数が展開される
my $s = <<"EOT";
...
EOT

# here doc 文字列内の変数は展開されない
my $s = <<'XYZ';
...
XYZ

# here doc 文字列内のコマンドの実行結果が格納される
my $s = <<`COMMAND`;
grep 'use' *.pl
grep 'hoge' *.pl
COMMAND
```
