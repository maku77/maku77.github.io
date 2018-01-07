---
title: REXML で XML ファイルを読み込む
date: "2011-06-22"
---

Document オブジェクトを作成する
====

XML ファイルから作成
----

```ruby
require 'rexml/document'

doc = REXML::Document.new(File.new('input.xml'))
print doc
```

XML 文字列から作成
----

```ruby
require 'rexml/document'

xml = <<EOL
<tree>
  <trunk>
    <leaf id="1">Leaf A</leaf>
    <leaf id="2">Leaf B</leaf>
    <leaf id="3">Leaf C</leaf>
  </trunk>
</tree>
EOL

doc = REXML::Document.new xml
print doc
```

特定の要素を繰り返し処理する
====

```ruby
require 'rexml/document'

xml = <<EOL
<tree>
  <trunk>
    <leaf id="1">Leaf A</leaf>
    <leaf id="2">Leaf B</leaf>
    <leaf id="3">Leaf C</leaf>
  </trunk>
</tree>
EOL

doc = REXML::Document.new(xml)
doc.elements.each('tree/trunk/leaf') { |element|
    puts element
    puts element.text
    puts element.attributes['id']
}
```

#### 出力結果

```
<leaf id='1'>Leaf A</leaf>
Leaf A
1
<leaf id='2'>Leaf B</leaf>
Leaf B
2
<leaf id='3'>Leaf C</leaf>
Leaf C
3
```

指定する要素の階層が決まっていない場合は、`REXML::XPath.each()` を使えば、位置に関係なくすべての要素を処理できます。

```ruby
doc = REXML::Document.new(xml)
REXML::XPath.each(doc, '//leaf') { |element|
    puts element
    puts element.text
    puts element.attributes['id']
}
```

