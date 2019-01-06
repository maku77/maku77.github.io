---
title: "カスタムヘルパを作成する"
date: "2016-01-16"
---

ヘルパーメソッドを作成しておくと、テンプレートの中から自由に呼び出せるようになります。
ヘルパーメソッドを定義するには、`config.rb` ファイルの中の `helpers` ブロックの中で任意のメソッドを定義します。

#### config.rb

```ruby
helpers do
  def greet(name)
    'Hello, ' + name
  end
end
```

すると、テンプレート（erb ファイルなど）の中から、下記のように呼び出せるようになります。

#### sample.html.erb

```html
<p>
  <%= greet 'Maku' %>
</p>
```

下記のように展開されます。

```html
<p>
  Hello, Maku
</p>
```

ヘルパーメソッドのモジュール化
---

複雑なヘルパーメソッドを定義する場合や、他の人に共有したいような場合は、ヘルパーメソッドの定義をモジュール化しておくことができます。
ここでは、下記のようなディレクトリ構成で、`lib/my_helpers.rb` としてモジュール化することにします。

```
+-- config.rb
+-- lib/
     +-- my_helpers.rb
```

MyHelpers モジュールは下記のように、1 つのメソッドだけを含んでいます。

#### lib/my_helpers.rb

```ruby
module MyHelpers
  def greet(name)
    'Hello, ' + name
  end
end
```

`config.rb` の中から上記のモジュールをロードして、`helpers` メソッドに渡すことで、テンプレートの中からヘルパーメソッドを呼び出せるようになります。

#### config.rb

```ruby
require_relative 'lib/my_helpers'
helpers MyHelpers
```

