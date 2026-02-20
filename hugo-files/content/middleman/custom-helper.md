---
title: "Middlemanメモ: カスタムヘルパーを作成する"
url: "p/8q9efxn/"
date: "2016-01-16"
tags: ["middleman"]
aliases: /middleman/custom-helper.html
---

ヘルパーメソッドを作成しておくと、テンプレートの中から自由に呼び出せるようになります。
ヘルパーメソッドを定義するには、`config.rb` ファイルの中の `helpers` ブロックの中で任意のメソッドを定義します。

{{< code lang="ruby" title="config.rb" >}}
helpers do
  def greet(name)
    'Hello, ' + name
  end
end
{{< /code >}}

すると、テンプレート（erb ファイルなど）の中から、下記のように呼び出せるようになります。

{{< code lang="html" title="sample.html.erb" >}}
<p>
  <%= greet 'Maku' %>
</p>
{{< /code >}}

下記のように展開されます。

```html
<p>
  Hello, Maku
</p>
```

ヘルパーメソッドのモジュール化
---

複雑なヘルパーメソッドを定義する場合や、他の人と共有したい場合は、ヘルパーメソッドの定義をモジュール化しておくことができます。
ここでは、下記のようなディレクトリ構成で、`lib/my_helpers.rb` としてモジュール化することにします。

```
+-- config.rb
+-- lib/
     +-- my_helpers.rb
```

MyHelpers モジュールは下記のように、1 つのメソッドだけを含んでいます。

{{< code lang="ruby" title="lib/my_helpers.rb" >}}
module MyHelpers
  def greet(name)
    'Hello, ' + name
  end
end
{{< /code >}}

`config.rb` の中から上記のモジュールをロードして、`helpers` メソッドに渡すことで、テンプレートの中からヘルパーメソッドを呼び出せるようになります。

{{< code lang="ruby" title="config.rb" >}}
require_relative 'lib/my_helpers'
helpers MyHelpers
{{< /code >}}
