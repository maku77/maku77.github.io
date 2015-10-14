---
title: コマンドライン引数を扱う
created: 2006-06-16
---

コマンドライン引数を取得する
====
`ARGV` を使用して、コマンドライン引数の数や、値を取得することができます。

#### sample.rb
```ruby
puts ARGV.size
puts ARGV[0]
```

#### 実行結果
```
$ ruby sample.rb AAA BBB CCC
3
AAA
```


サンプルコード
====

コマンドライン引数をループで処理する
----

```ruby
while x = ARGV.shift
  puts x
end
```


コマンドライン引数が 0 個の場合に Usage を表示して終了
----

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

