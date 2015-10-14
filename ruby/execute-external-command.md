---
title: 外部プログラムを呼び出す
created: 2011-07-29
layout: ruby
---

バッククォートを使用する方法
====

下記は Ruby プログラムの中から `/bin/ls` を実行して、その出力結果を取得するサンプルです。

```ruby
#!/usr/bin/ruby
result = `/bin/ls`
puts result
```

外部プログラムに対してパラメータを渡すこともできます。

```ruby
#!/usr/bin/ruby
result = `/bin/ls #{ARGV[0]}`
puts result
```

外部プログラムの出力結果を 1 行ずつ処理することもできます。

```ruby
#!/usr/bin/ruby
result = `/bin/ls`
result.each do |line|
  puts '===' + line
end
```

