---
title: "Javaメモ: Swing - JTree で同時に1つのノードしか選択できないようにする"
url: "p/yqoe4b6/"
date: "2011-02-12"
tags: ["java"]
aliases: ["/java/swing/widget/jtree-single-selection.html"]
---

JTree のノードは、デフォルトで複数のノードを選択できるようになっています。
一度にひとつのノードしか選択できないようにするには、下記のように選択モードを設定します。

```java
tree.getSelectionModel().setSelctionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
```
