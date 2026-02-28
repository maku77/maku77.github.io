---
title: "Rubyメモ: 関数定義の基本"
url: "p/3sgsrtu/"
date: "2003-10-13"
tags: ["ruby"]
aliases: ["/ruby/method.html"]
---

Ruby の関数は、下記のように `def` キーワードを使用して定義します。

```ruby
# メソッドの定義
def add(a, b)
  a + b
end

# メソッドの呼び出し
puts add(1, 2)
```

戻り値は `return` を使って明示できますが、最後に評価した値が関数の戻り値として扱われるので、多くの場合は `return` を省略できます。
むしろ `return` を省略した方が少しだけ処理が速いらしいです（『Ruby ソースコード完全解説』より）。

