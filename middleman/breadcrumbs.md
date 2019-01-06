---
title: "パンくずリストを表示する"
date: "2016-01-17"
---

パンくずリストとは
----

パンくずリストというのは、トップページから現在のページにたどり着くまでの一連のリンクです。
例えば下記のような構造のサイトを考えてみます。

```
source/
  +-- index.html
  +-- foo/
      +-- index.html
      +-- bar/
          +-- index.html
          +-- a.html
          +-- b.html
```

`foo/bar/a.html` というページにたどり着くには、トップページから次のように辿ってくることが想定されます。

+ index.html
+ foo/index.html
+ foo/bar/index.html
+ foo/bar/a.html

表示上のリンクは、おそらく下記のような感じになります。

```
Home > Foo > Bar
```

Middleman でパンくずリスト情報を取得する
----

Middleman ではカレントページの情報を `current_page` というキーワードで参照できます。
実際にはカレントページの Resource オブジェクトであり、このオブジェクトの `parent` 属性を使って上位のページを辿っていくことができます。

下記の `get_breadcrumbs` ヘルパーメソッドは、現在のページに対するパンくずリスト情報を、Resource オブジェクトの配列の形で取得します。

#### config.rb に追加

```ruby
helpers do
  # カレントページに対するパンくずリストを取得する。
  # 戻り値は Resources オブジェクトの配列。最上位の index ページで
  # 呼び出した場合や、親ページが存在しない場合は空の配列を返す。
  def get_breadcrumbs
    result = []
    r = current_page.parent
    while r
      result.unshift(r)
      r = r.parent
    end
    return result
  end
end
```

あとは、任意のテンプレート内で下記のようにパンくずリストを表示できます。

```html
<div class="breadcrambs">
  <% get_breadcrumbs.each_with_index do |res, index| %>
    <% if index > 0 %>
      &gt;
    <% end %>
    <% link_to res.url do %>
      <span class="breadcrambs_item"><%= res.data.title %></span>
    <% end %>
  <% end %>
</div>
```

上記では、各 index ページの Frontmatter に記述されている `title` 情報を表示するようにしています（`res.data.title` の部分）。
パンくずリストに表示するラベルはもう少しシンプルにしたいという場合は、パンくずリストのラベルを Frontmatter で新しく定義することで実現できます。

ちなみに、前述の `get_breadcrumbs` は、自分自身のページの `Resource` を含まないリストを返しますが、自分自身のページも含めてしまいたい場合は、下記の様に変更すれば OK です（１行変わっただけ）。

~~~ ruby
def get_breadcrumbs
  result = []
  r = current_page
  while r
    result.unshift(r)
    r = r.parent
  end
  return result
end
~~~

