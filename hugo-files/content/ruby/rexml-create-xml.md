---
title: "Rubyメモ: REXML で XML ファイルを作成する"
url: "p/sapxuyk/"
date: "2010-06-13"
tags: ["ruby"]
aliases: ["/ruby/rexml-create-xml.html"]
---

XML ファイルの出力（基本）
====

下記のプログラムは、XML の宣言要素だけを出力するサンプルです。

```ruby
require 'rexml/document'

doc = REXML::Document.new
doc << REXML::XMLDecl.new('1.0', 'UTF-8')

File.open('output.xml', 'w') do |file|
  doc.write(file, indent=2)
end
```

{{< code lang="xml" title="出力結果 (output.xml)" >}}
<?xml version='1.0' encoding='UTF-8'?>
{{< /code >}}

出力をファイルではなく、標準出力に変更したい場合は、下記のようにします。

```ruby
doc.write(STDOUT)
```


子要素を追加する
====

要素を追加していくには、`REXML::Element` オブジェクトを作成します。

```ruby
# ルートノードを作成
root = REXML::Element.new('Root')
doc.add_element(root)

# ルートノードの下に子ノードを追加
child1 = REXML::Element.new('Child')
child2 = REXML::Element.new('Child')
child1.add_text('Text1')
child2.add_text('Text2')
child1.add_attribute('attr', 'value1')
child2.add_attribute('attr', 'value2')
root.add_element(child1)
root.add_element(child2)
```

以下のような XML が生成されます。

```xml
<?xml version='1.0' encoding='UTF-8'?>
<Root>
  <Child attr='value1'>
    Text1
  </Child>
  <Child attr='value2'>
    Text2
  </Child>
</Root>
```
