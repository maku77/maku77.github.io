---
title: "Resource (3) ページのツリー構造を取得する"
date: "2016-01-17"
---


Resource オブジェクトの `children` 属性を参照すると、子ページの Resource 情報を取得できるため、これを利用してページのツリー構成を調べることができます。
下記の `dump_tree` メソッドは、指定したページの Resource オブジェクト以下のページ構成を出力します。

```ruby
# インデント付きでページのパスとタイトルを表示
def dump_page(res, indent)
  puts ('  ' * indent) + "#{res.path} : #{res.data.title}"
end

# 'index.html' を示す Resource か判別する
def is_index(res)
  res.url.end_with?('/')
end

# 指定したページから再帰的にツリー構造を表示
def dump_tree(res, indent=0)
  dump_page(res, indent)

  res.children.each do |child|
    # 同じ階層の HTML
    dump_page(child, indent) unless is_index(child)

    # 下位ディレクトリの HTML
    dump_tree(child, indent+1) if is_index(child)
  end
end
```

`index.html` という名前のページは、親ページとして判別されるため、`index.html` を示す Resource オブジェクトに対しては例外的な処理が必要なことに注意してください。
上記では、`Resource#url` が `/` で終わっていることを確認して `index.html` であるかどうかを判断しています。

下記のようにして、最上位の `index.html` からのツリー構造を出力できます。

```ruby
res = sitemap.find_resource_by_path('/index.html')
dump_tree(res)
```

#### 出力例

```
index.html
a.html
b.html
  sub1/index.html
  sub1/a.html
  sub1/b.html
    sub1/sub1-1/index.html
    sub1/sub1-1/a.html
    sub1/sub1-1/b.html
  sub2/index.html
  sub2/a.html
  sub2/b.html
    sub2/sub2-1/index.html
    sub2/sub2-1/a.html
    sub2/sub2-1/b.html
```

