---
title: "n 文字のランダム文字列を生成する"
date: "2021-07-05"
---

rand_str 関数を実装する
----

### rand-str.rb

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

### 実行例

```
$ ruby rand-str.rb
rspgyta5h8
```

### 解説

`rand(n)` 関数は「0 〜 n より小さい数」のランダムな整数を返します。
`chars[rand(chars.length)]` とすることで、`chars` 文字列（や配列）からランダムに 1 つの要素を取得できます。
あとは、これを指定された文字数 `len` だけ繰り返し取得して、全て結合して文字列にすれば完成です。


参考
----

- [Python でランダムな文字列を生成する (random.choice)](/python/numstr/random-string.html)
- [ランダムな文字列を生成する Web サイト](https://maku.blog/p/3nx9is3/)

