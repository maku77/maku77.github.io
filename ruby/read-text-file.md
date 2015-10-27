---
title: テキストファイルを読み込む
created: 2010-05-07
---

テキストファイルを一行ずつ読み込む
====

C 言語的な書き方
----

下記のように `open` 関数を単独で使用する場合は、明示的な `close` 処理が必要です。

### IO#each を使う方法

```ruby
file = open('input.txt')
file.each do |line|
  puts line
end
file.close
```

### IO#gets を使う方法

```ruby
file = open('input.txt')
while line = file.gets
  puts line
end
file.close
```


ブロックパラメータを使用する書き方
----

ファイルの `open` 時にブロックを指定すると、自動で `close` 処理を行ってくれます。

```ruby
open('input.txt') do |file|
  file.each do |line|
    puts line
  end
end
```

上記のように、ブロックパラメータである `file` 変数を一度しか参照しないのであれば、下記のように `open` と `each` をチェーンすることで、より簡潔に記述することができます。

```ruby
open('input.txt').each do |line|
  puts line
end
```

File.foreach を使う方法
----

テキストファイルを 1 行ずつ処理する場合は、`File.foreach` を使用すると、とてもシンプルに記述できます。

```ruby
File.foreach('input.txt') do |line|
  puts line
end
```


テキストファイルから最初の一行だけ読み込む
====

```ruby
line = open('input.txt').gets
```

