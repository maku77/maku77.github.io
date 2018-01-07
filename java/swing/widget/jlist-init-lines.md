---
title: Swing - JList で最初に表示される行数を指定する
date: "2011-01-23"
---

JList の `setLayoutOrientation(JList.VERTICAL)` を実行すると、１行に１項目が表示されるようになりますが、このとき、一度に何行分の項目を表示するかを指定するのが
`setVisibleRowCount()` メソッドです。

#### 例: 表示される行数を 5 行に設定する

~~~ java
JList list = new JList(new MyListModel());
JScrollPane scrollPane = new JScrollPane(list);
list.setLayoutOrientation(JList.VERTICAL);
list.setVisibleRowCount(5);
~~~

デフォルトではこの値は 8 になっています。

