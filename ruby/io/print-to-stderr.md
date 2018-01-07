---
title: 標準エラー出力へ出力する (STDERR)
date: "2017-08-07"
---

Ruby で標準エラー出力へ出力を行うには、`STDERR.puts` や `STDERR.print` を使用します。

~~~ ruby
STDERR.puts 'エラー発生！'   # 改行あり
STDERR.print 'エラー発生！'  # 改行なし
~~~

標準出力への出力でよく使用する `puts` や `print` は、通常は下記のショートカットになっています。

~~~ ruby
STDOUT.puts 'Hello'   # puts と同じ
STDOUT.print 'Hello'  # print と同じ
~~~

