---
title: コマンドライン引数を扱う
created: 2006-06-16
layout: ruby
---

コマンドライン引数を取得する
====
`ARGV` を使用して、コマンドライン引数の数や、値を取得することができます。

#### sample.rb
```ruby
puts ARGV.size
puts ARGV[0], ARGV[1], ARGV[2]
```

#### 実行結果
```
$ ruby sample.rb AAA BBB
2
AAA
BBB
nil
```

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

#### sample.rb
```ruby
def ShowUsage()
    puts "Usage: #{$0} <InputFile>\n"
end

if ARGV.empty?
    ShowUsage()
    exit(1)
end
```

#### 実行結果

```
$ ruby sample.rb
Usage: sample.rb <InputFile>
```

