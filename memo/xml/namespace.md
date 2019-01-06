---
title: "XML の名前空間"
date: "2013-03-31"
---

名前空間を宣言する
----

XML の名前空間を宣言するには、任意の要素の開始タグで、

```
xmlns:<NamespacePrefix>="<NamespaceName(URI)>"
```

のような属性を指定します。この属性を指定した要素以下の要素でその名前空間を使用できるようになります。属性を宣言した要素自体でも使えるので、XML の root 要素で名前空間を宣言すれば、全ての要素でその名前空間が使えることになります。

```xml
<?xml version="1.0" ?>
<b:books xmlns:b="http://example.com/dtd/books">
  <b:book>
    <b:title>Title 1</b:title>
    <b:author>Author 1</b:author>
  </b:book>
</b:books>
```

上記の XML 文書の例では、Namespace prefix（名前空間接頭辞）は `b` で、Namespace Name (URI) は `http://example.com/dtd/books` が指定されています。
名前空間を使用する時は、例を見れば明らかですが、`<NamespacePrefix>:<LocalName>` のように要素名を指定します。


### 名前空間名の URI が指すリソースは存在しなくてよい

名前空間名として指定する URI は、あくまで一意な名前を指定するものであって、その URI が示す先に、何らかのリソースファイルが存在している必要はありません。

### 名前空間の接頭辞に使えない名前

要素名と同様に、名前空間の接頭辞 (Namespace prefix) にも、`xml` で始まる名前は付けられません。大文字にしてもダメです。例えば、以下のような Namespace prefix はすべて NG です。

#### 名前空間プレフィックスの NG 例

* xml_prefix
* xmlPrefix
* XmlPrefix
* XML_Prefix


名前空間の接頭辞の上書き
----

親要素で既に宣言されている Namespace prefix を子要素で宣言すると、その prefix は上書きされます。ただし、その子要素より上位の要素では、親要素の Namespace prefix の宣言が使用されます。


デフォルトの名前空間
----

名前空間を宣言するときに、接頭辞 (prefix) を省略して宣言すると、デフォルトの名前空間 (default namespace) の宣言になります。デフォルトの名前空間が宣言された要素以下の要素は、prefix を特に指定しなくても、その名前空間に属するとみなされます。

```xml
<?xml version="1.0" ?>
<note xmlns="http://example.com/dtd/note">
  <message>Hello</message>
</note>
```

上記の XML は、note 要素でデフォルトの名前空間が宣言されています。
以下のように記述したものと同じ意味を持ちます。

```xml
<?xml version="1.0" ?>
<n:note xmlns:n="http://example.com/dtd/note">
  <n:message>Hello</n:message>
</n:note>
```


属性の名前空間
----

名前空間接頭辞のない属性は、その要素に属するローカルな属性とみなされます。

#### ローカル属性

```xml
<?xml version="1.0" ?>
<n1:aaa xmlns:n1="http://example.com/n1">
  <n1:bbb attr="val" />
</n1:aaa>
```

上記のように記述した場合、属性 `attr` は、`n1:bbb` 要素に属するローカルな属性とみなされます。特定の要素に属さない属性（グローバル属性）を指定したい場合は、属性名に Prefix を付ける必要があります。

#### グローバル属性

```xml
<?xml version="1.0" ?>
<n1:aaa xmlns:n1="http://example.com/n1"
        xmlns:n2="http://example.com/n2">
  <n1:bbb n2:attr="val" />
</n1:aaa>
```

