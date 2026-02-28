---
title: "Rubyメモ: 重複順列 (repeated permutation) を作成する"
url: "p/dh49opn/"
date: "2012-01-28"
tags: ["ruby"]
aliases: ["/ruby/number/repeated-permutation.html"]
---

## Array#repeated_permutation() で重複順列を作成する

`Array#repeated_permutation()` は、与えられた配列要素から重複を許す順列を作成します (Ruby 1.9.2〜)。
例えば、`[1, 2]` という配列から、長さ 3 の重複順列を作成すると、以下のような組み合わせが得られます。

```
[1, 1, 1], [1, 1, 2], [1, 2, 1], [1, 2, 2]
[2, 1, 1], [2, 1, 2], [2, 2, 1], [2, 2, 2]
```

{{% code lang="ruby" title="例: 順番に配列として処理する" %}}
[1, 2].repeated_permutation(3) do |perm|
  p perm
end
{{% /code %}}

{{% code lang="ruby" title="例: 順番に各値を取得しながら処理する" %}}
[1, 2].repeated_permutation(3) do |a, b, c|
  puts "#{a}, #{b}, #{c}"
end
{{% /code %}}


## 自力で重複順列を作成する

以下のような多重ループを使えば、`repeated_permutation` と同じ結果を簡単に得られます。
ただし、Ruby 1.9.2 以上を使用できるのなら、素直に `repeated_permutation` を使った方がシンプルに記述できます。

```ruby
arr = [1, 2]
arr.each do |a|
  arr.each do |b|
    arr.each do |c|
      puts "#{a}, #{b}, #{c}"
    end
  end
end
```


## 関連記事

* [順列 (permutation) を作成する (C++)](/cpp/number/permutation.html)
* [順列 (permutation) を作成する (Ruby)](/p/wk75ra7/)
* [順列 (permutation) を作成する (Java)](/java/numstr/permutation.html)
* [組み合わせ (combination) を作成する (Ruby)](/p/ef785b3/)
* [組み合わせ (combination) を作成する (C++)](/cpp/number/combination.html)
