---
title: "Javaメモ: Swing - JTree でルートノードを表示する"
url: "p/xdnjok3/"
date: "2011-02-11"
tags: ["java"]
aliases: ["/java/swing/widget/jtree-show-root.html"]
---

{{< image src="img-001.png" >}}
{{< image src="img-002.png" >}}

配列 (Object[])、Vector、Hashtable から JTree を作成した場合、デフォルトではルートノードは非表示になっています。
ルートノードを表示する場合は、`JTree#setRootVisible(boolean rootVisible)` で `true` を指定します。

```java
tree.setRootVisible(true);
```

逆に、JTree のコンストラクタで TreeNode や TreeModel を指定した場合は、デフォルトでルートノードが表示されるようになっています。
その場合、ルートノードを表示しないようにするには、明示的に `false` を設定する必要があります。
