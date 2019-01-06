---
title: "Resource オブジェクト (1) ページ情報を取得する"
date: "2016-01-16"
---

Middleman の内部では、各ページやリソースの情報を、`Resource` オブジェクトで管理しています。

- [Middleman::Sitemap::Resource クラス](http://www.rubydoc.info/gems/middleman-core/Middleman/Sitemap/Resource)


Resource オブジェクトの取得
----

`Resource` オブジェクトは下記のようにして取得することができます。

```ruby
# 現在のページの Resource オブジェクトを取得
current_page

# 指定したパスが示すファイルに対する Resource オブジェクトを取得
sitemap.find_resource_by_path('/foo/bar/index.html')

# サイト全体のファイルに対する Resource オブジェクトをリストで取得
sitemap.resources
```


Resource オブジェクトの内容
----

`Resource` オブジェクトの属性を参照することで、該当リソースに関して下記のような情報を取得することができます。

| 属性 | 値 | 例 |
| ---- | ---- |
| url | ドメイン名を除く URL 部分 | /foo/bar/ |
| path | ファイルのパス | foo/bar/index.html |
| data | Frontmatter で指定したデータ | { title: 'About this site' } |
| parent | 親ディレクトリの Resource | Resource オブジェクト |
| children | 子ディレクトリの Resource | Resource オブジェクトのリスト |
| siblings | 同じディレクトリの Resource | Resource オブジェクトのリスト |


使用例
----

### 現在のページの情報を取得する

下記の例では、`current_page` オブジェクトを参照することで、現在のページの情報を取得しています。
ここでは、URL やパスの情報、Frontmatter で定義されている情報を表示しています。

```
# Resource オブジェクトの内容を出力
def dump_resource(res)
  puts "url  = #{res.url}"
  puts "path = #{res.path}"
  puts "data = #{res.data}"
end

# 現在のページの情報を出力
dump_resource(current_page)
```

上記を任意のページ内の Ruby コードとして実行することで、Middleman サーバを走らせているターミナル上に、下記のように表示されます。

```
url  = /about.html
path = about.html
data = {"title"=>"このサイトについて"}
```


### すべてのリソースの情報を取得する

`sitemap.resources` を参照することで、サイト内のすべてのリソース（ファイル）に対する `Resource` オブジェクトをリストで取得することができます。
このリストには、画像ファイルなどに対する `Resource` オブジェクトも含まれます。
下記の例では、ファイルの拡張子をチェックすることで、HTML ページに対する `Resource` オブジェクトの情報だけを表示しています。

```ruby
# Resource オブジェクトの内容を出力
def dump_resource(res)
  puts '------------------'
  puts "url = #{res.url}"
  puts "path = #{res.path}"
  puts "data = #{res.data}"
end

# サイト内のすべての HTML ページの Resource 情報を表示
def dump_all_resources
  sitemap.resources.each do |r|
    dump_resource(r) if r.path.end_with?('.html')
  end
end

dump_all_resources
```

