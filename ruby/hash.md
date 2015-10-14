---
title: ハッシュを作成する
created: 2011-10-07
layout: ruby
---


空のハッシュを作成する
====

```ruby
myHash = {}
myHash = Hash.new
```


初期値を与えてハッシュを作成する
===

```ruby
personInfo = {
  'name' => 'makkuma',
  'age'  => 100,
  'mail' => 'makkuma@example.com'
}
```

一行で記述する事もできます。

```ruby
myHash = {'key1' => 'val1', 'key2' => 'val2'}
```

