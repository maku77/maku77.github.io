---
title: "Rubyメモ: n 文字のランダム文字列を生成する"
url: "p/vt7ws7t/"
date: "2021-07-05"
tags: ["ruby"]
aliases: ["/ruby/string/rand-str.html"]
---

rand_str 関数を実装する
----

{{% private note %}}
rand-str.rb
{{% /private %}}

次の `rand_str` 関数は、指定した長さのランダム文字列を生成します。
使用する文字の種類は、第 2 引数 (`chars`) で指定することができます。

```ruby
# chars の中からランダムに len 文字を選んだランダム文字列を生成します
def rand_str(len=7, chars='23456789abcdefghijkmnopqrstuvwxzy')
  arr = Array.new(len) do
    chars[rand(chars.length)]
  end
  return arr.join
end

# テスト
if $0 == __FILE__
  puts rand_str(10)
end
```

{{% private note %}}
実行例
{{% /private %}}
```
$ ruby rand-str.rb
rspgyta5h8
```

{{% private note %}}
解説
{{% /private %}}

`rand(n)` 関数は、0 以上 n 未満のランダムな整数を返します。
`chars[rand(chars.length)]` とすることで、`chars` 文字列からランダムに 1 つの文字を取得できます。
あとは、これを指定された文字数 `len` だけ繰り返し取得して、すべて結合して文字列にすれば完成です。


参考
----

- [Python でランダムな文字列を生成する (`random.choice`)](/p/vfp5zx9/)
- [ランダムな文字列を生成する Web サイト](https://maku.blog/p/3nx9is3/)
