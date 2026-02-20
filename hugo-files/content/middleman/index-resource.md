---
title: "Middlemanメモ: 同じ階層にある index.html ページの Resource オブジェクトを取得する"
url: "p/773wwm5/"
date: "2017-08-20"
tags: ["middleman"]
aliases: /middleman/index-resource.html
---

下記の `get_index_resource` メソッドを任意のページの ERB ファイル内で実行すると、そのページと同じ階層にある `index.html` ページの `Resource` オブジェクトを取得できます。

```erb
<%
# 同じ階層の index.html の Resource オブジェクトを取得します。
# カレントページが index.html である場合、自分自身の Resource を返します。
# 同じ階層に index.html ページが存在しない場合は nil を返します。
def get_index_resource
  return current_page if current_page.url.end_with?('/')
  current_page.parent
end
%>
```

例えば、テンプレートファイル内で、下記のように記述しておけば、このメソッドの動作を確認することができます。

```erb
インデックスページの URL は <%= get_index_resource.url %> です。
```

下記のようなサイト構成になっている場合、

```
(root)/
   +-- index.html
   +-- page1.html
   +-- page2.html
   +-- dir/
       +-- index.html
       +-- page1.html
       +-- page2.html
```

それぞれのページにアクセスしたときの結果は下記のようになります。

| アクセスしたページ | get_index_resource.url | get_index_resource.path |
| ----- | ----- | ----- |
| /index.html     | /     | index.html |
| /page1.html     | /     | index.html |
| /page2.html     | /     | index.html |
| /dir/index.html | /dir/ | dir/index.html |
| /dir/page1.html | /dir/ | dir/index.html |
| /dir/page2.html | /dir/ | dir/index.html |
