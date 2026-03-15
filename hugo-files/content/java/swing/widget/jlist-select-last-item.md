---
title: "Javaメモ: Swing - JList で最後の項目を選択する"
url: "p/grodjd9/"
date: "2011-03-17"
tags: ["java"]
aliases: ["/java/swing/widget/jlist-select-last-item.html"]
---

JList に格納されている項目数を調べ、それを `JList#setSelectedIndex` でのインデックス指定に利用することで、JList の末尾の要素を選択することができます。

```java
// JList list;
int size = list.getModel().getSize();
if (size > 0) {
    list.setSelectedIndex(size - 1);
}
```
