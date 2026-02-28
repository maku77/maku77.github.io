---
title: "Rubyメモ: 文字列から正規表現にマッチする部分を取り出す (String#slice, Regexp#match, String#scan)"
url: "p/has6rjr/"
date: "2011-07-29"
tags: ["ruby"]
aliases: ["/ruby/string/extract.html"]
---

String#slice で文字列の一部を簡単に抽出する
----

ある文字列リテラル、あるいは文字列変数の中から、正規表現に一致する部分文字列を抽出するには、`String#slice(Regexp)` を使用できます。

```ruby
s = 'ABCDEFG'
m = s.slice(/C.+/)
if m
  puts m  #=> "CDEFG"
else
  puts 'Not found'
end
```

正規表現に一致する文字列が見つからない場合は、`slice` メソッドは `nil` を返します。


Regexp クラスを使う方法
----

`Regexp` クラスを使用すると、もう少し柔軟に部分文字列を抽出することができます。
下記の例では、最初に見つかった数値と、2 番目に見つかった数値を抽出しています。

```ruby
re = Regexp.new('(\d+)[^\d]+(\d+)')
s = 'AAA123BBB456CCC'
m = re.match(s)
if m
  puts m[0]  # => "123BBB456"   # パターン全体と一致した部分文字列
  puts m[1]  # => "123"         # 1番目のカッコ内と一致した部分文字列
  puts m[2]  # => "456"         # 2番目のカッコ内と一致した部分文字列
  puts m[3]  # => nil
else
  puts 'Not found'
end
```

パターンに一致する部分文字列が見つからない場合は、`Regexp#match` の戻り値は `nil` になるので、上記のように必ず `if` で分岐して処理しましょう。


String#scan で同じパターンに一致する部分文字列をすべて配列で取得する
----

同じパターンで繰り返し出現する文字列を、配列の形ですべて抽出したいときは `String#scan` を使用するのが簡単です。

{{% private note %}}
例: スペースで区切られた単語をすべて抽出する
{{% /private %}}
```ruby
s = "AAA BBB CCC"
arr = s.scan(/\w+/)
arr.each { |x| puts x }
```

{{% private note %}}
実行結果
{{% /private %}}
```
AAA
BBB
CCC
```

正規表現パターン内を括弧（`(` と `)`）でグルーピングすれば、その単位で一致部分を抽出することができます。
パターン内に括弧を含む場合は、`String#scan` の戻り値は 2 次元配列になることに注意してください。
2 次元目の各要素が、グループごとに一致した部分文字列になります。

{{% private note %}}
例: コロン区切りのキーとバリューのペアをすべて抽出する
{{% /private %}}
```ruby
s = 'AAA:100 BBB:200 CCC:300'
arr = s.scan(/(\w+):(\w+)/)
arr.each { |x| puts "#{x[0]} -- #{x[1]}" }
```

{{% private note %}}
実行結果
{{% /private %}}
```
AAA -- 100
BBB -- 200
CCC -- 300
```
