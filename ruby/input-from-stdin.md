---
title: 標準入力から読み込む
created: 2011-10-01
layout: ruby
---

下記のように、リダイレクトやパイプで渡されたテキスト（標準入力）を読み込みたい場合のお話です。

```
$ ./main.rb < input.txt
$ cat input.txt | ./main.rb
```


標準入力から全ての行を一行ずつ読み込む
----

標準入力から次の一行を読み込むには、`ARGF.gets` を使用します。
`ARGF.gets` は `gets` と省略できます。

#### 方法 (1) ARGF.gets

```ruby
while line = gets
    print '> ' + line
end
```

`ARGF.each` でループ処理させることもできます。

#### 方法 (2) ARGF.each

```ruby
ARGF.each do |line|
    print '> ' + line
end
```

インデックスを付けながら一行ずつ読み込む
----

`ARGF.each` の代わりに、`ARGF.each_with_index` を使用すると、インデックスを取得しながらループ処理を行うことができます。

```ruby
ARGF.each_with_index do |line, i|
  puts "#{i}: #{line}"
end
```
