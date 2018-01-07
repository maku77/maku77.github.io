---
title: Ruby の対話型プログラムを起動する
date: "2015-10-15"
---

irb を使用する
====
Ruby には、標準の対話型プログラムとして `irb` コマンドが搭載されています。

```ruby
$ irb
irb(main):001:0> 10 + 20
=> 30
irb(main):002:0> 'ABC' * 3
=> "ABCABCABC"
irb(main):003:0> exit
$
```


pry を使用する
====

`irb` よりもカラフルな出力を行ってくれる `pry` というコマンドが提供されています。
`pry` は、RubyGems で簡単にインストールすることができます。

#### pry のインストール

```
$ gem install pry
```

起動するときは、単純に `pry` と実行するだけです。

```ruby
$ pry
[1] pry(main)> puts 'ABC'
ABC
=> nil
[2] pry(main)> p 'ABC'
"ABC"
=> "ABC"
[3] pry(main)> exit
$
```

