---
title: "Swing - JList で単一の項目のみ選択できるようにする"
date: "2011-01-23"
---

デフォルトでは、JList で表示されている項目は、Control キーを押しながらクリックすることで複数選択することができます。
`setSelectionMode()` メソッドを使用することで、この振る舞いを変更して、項目を１つだけしか選択できないようにすることができます。

選択モードを表す定数は `java.swing.ListSelectionMode` で定義されています。

- `ListSelectionMode.SINGLE_SELECTION` -- １つの項目のみ選択可能。
- `ListSelectionMode.SINGLE_INTERVAL_SELECTION` -- 複数の項目を選択可能（ただし連続した範囲のみ）。
- `ListSelectionMode.MULTIPLE_INTERVAL_SELECTION` -- 複数の項目を選択可能。

#### 使用例

~~~ java
// JList list = new JList(new MyListModel());
list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
~~~

