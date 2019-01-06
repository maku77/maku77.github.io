---
title: "ハッシュをループで処理する"
date: "2016-09-10"
---


ハッシュのキーと値をループで処理する
----

`Hash#each`（別名 `Hash#each_pair`）を使用して、ハッシュに格納されたキーと値をループ処理することができます。

```ruby
my_hash = { 'key1' => 100, 'key2' => 200, 'key3' => 300 }
my_hash.each do |key, val|
  puts "#{key} is #{val}"
end
```

#### 実行結果

```
key1 is 100
key2 is 200
key3 is 300
```


ハッシュのキーのみ、値のみをループで処理する
----

`Hash#each_key` や `Hash#each_value` を使用すると、ハッシュ要素のキーのみ、値のみをループ処理することができます。

```ruby
my_hash = { 'key1' => 100, 'key2' => 200, 'key3' => 300 }
my_hash.each_key {|key| puts key }
my_hash.each_value {|value| puts value }
```

