---
title: 外部プログラムを呼び出す
created: 2011-07-29
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
result.each_line do |line|
  puts '===> ' + line
end
```

実行するコマンドを文字列変数に格納している場合は、下記のように実行時に展開できます。

```ruby
command = 'date /t'
result = `#{command}`
```


IO.popen を使用する方法
====

```ruby
IO.popen('dir').each do |line|
  puts '===> ' + line
end
```

