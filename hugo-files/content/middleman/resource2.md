---
title: "Middlemanメモ: Resource オブジェクト (2) ページの親子関係を取得する"
url: "p/25v7sun/"
date: "2016-01-17"
tags: ["middleman"]
aliases: /middleman/resource2.html
---

`Resource` オブジェクトには、`parent`、`children`、`siblings` という属性があり、親、子、兄弟のページをそれぞれ取得できるのですが、具体的にどのようなページが取得されるのかをまとめておきます。
これをちゃんと理解すると、サイトマップや、ナビゲーション、パンくずリストなどの作成に役立ちます。

下記は、渡された `Resource` オブジェクトの親、子、兄弟を調べるためのユーティリティメソッドです。
ここでは、このメソッドを使用して調査を進めます。

```ruby
# 渡された Resource オブジェクトの親、子、兄弟のパスを表示する
def dump_relationship(res)
  puts "path = #{res.path}"

  # 親のパスを表示
  puts "parent = #{res.parent.path}" if res.parent

  # すべての子のパスを表示
  res.children.each_with_index do |child, i|
    puts "children[#{i}] = #{child.path}"
  end

  # すべての兄弟のパスを表示
  res.siblings.each_with_index do |sib, i|
    puts "siblings[#{i}] = #{sib.path}"
  end
end
```

サイトのディレクトリ構成は、下記のようになっているとします。

```
+-- source/
    +-- index.html.erb
    +-- a.html.erb
    +-- b.html.erb
    +-- c.html.erb
    +-- sub1/
        +-- index.html.erb
        +-- a.html.erb
        +-- b.html.erb
        +-- c.html.erb
    +-- sub2/
        +-- index.html.erb
        +-- a.html.erb
        +-- b.html.erb
        +-- c.html.erb
```

まず、最上位の `index.html` に対する、親、子、兄弟の情報を出力してみます。

```ruby
res = sitemap.find_resource_by_path('/index.html')
dump_relationship(res)
```

```
path = index.html
children[0] = a.html
children[1] = b.html
children[2] = c.html
children[3] = sub1/index.html
children[4] = sub2/index.html
```

最上位の index.html なので、`parent` はありません。
`children` には同じ階層のページ、及び、下位のディレクトリの `index.html` が含まれ、`siblings` は空っぽです。
最初は混乱すると思いますが、下記でなぜこのようになるか種明かしがあります。

次に、最上位にある `a.html` について見てみます。

```ruby
res = sitemap.find_resource_by_path('/a.html')
dump_relationship(res)
```

```
path = a.html
parent = index.html
siblings[0] = b.html
siblings[1] = c.html
siblings[2] = sub1/index.html
siblings[3] = sub2/index.html
```

`parent` は同じ階層にある `index.html` になりました。
そして、`children` は空っぽです。
代わりに `siblings` に `index.html` 以外の同じ階層のページ、及び、下位のディレクトリの `index.html` が含まれています。

つまり、`index.html` というファイルは、ひとつ上の階層に存在しているものと考えればよいようです。
そして、`children` が存在するのは、`index.html` に対する Resource のみです（正確には、ディレクトリ名と同じベース名を持つ HTML ファイルもそのディレクトリの `index.html` と同じ振る舞いをするのですが、わかりにくくなるので、そのようなファイル名は付けないこととします）。

ここまで理解できれば、以下のケースもすべて理解できるはずです。
`sub1/index.html` について見てみましょう。

```ruby
res = sitemap.find_resource_by_path('/sub1/index.html')
dump_relationship(res)
```

```
path = sub1/index.html
parent = index.html
children[0] = sub1/a.html
children[1] = sub1/b.html
children[2] = sub1/c.html
siblings[0] = a.html
siblings[1] = b.html
siblings[2] = c.html
siblings[3] = sub2/index.html
```

最後に、`sub1/a.html` について見てみましょう。

```ruby
res = sitemap.find_resource_by_path('/sub1/a.html')
dump_relationship(res)
```

```
path = sub1/a.html
parent = sub1/index.html
siblings[0] = sub1/b.html
siblings[1] = sub1/c.html
```
