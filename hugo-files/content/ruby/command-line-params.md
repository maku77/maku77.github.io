---
title: "Rubyメモ: コマンドライン引数を扱う"
url: "p/btwa5fv/"
date: "2006-06-16"
tags: ["ruby"]
aliases: ["/ruby/command-line-params.html"]
---

コマンドライン引数を取得する
====
`ARGV` を使用して、コマンドライン引数の数や、値を取得することができます。

{{< code lang="ruby" title="sample.rb" >}}
puts ARGV.size
puts ARGV[0], ARGV[1], ARGV[2]
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ruby sample.rb AAA BBB
2
AAA
BBB
nil
{{< /code >}}

指定したインデックスに対応するコマンドライン引数が存在しない場合は、`nil` を返します。


コマンドライン引数をループで処理する
====

```ruby
while arg = ARGV.shift
  puts arg
end
```

`ARGV` は配列としてアクセスできるので、`Array#shift` メソッドを使って、コマンドライン引数を一つずつ取得することができます。
`shift` メソッドは要素が空 (`[]`) になると最後に `nil` を返すので、上記のように `while` ループで列挙できます。


コマンドライン引数が 0 個の場合に Usage を表示して終了
====

{{< code lang="ruby" title="sample.rb" >}}
def show_usage()
  puts "Usage: #{$0} <InputFile>"
  exit(1)
end

show_usage() if ARGV.empty?
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ruby sample.rb
Usage: sample.rb <InputFile>
{{< /code >}}

