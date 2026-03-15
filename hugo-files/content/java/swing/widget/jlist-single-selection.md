---
title: "Javaメモ: Swing - JList で単一の項目のみ選択できるようにする"
url: "p/ot34aii/"
date: "2011-01-23"
tags: ["java"]
aliases: ["/java/swing/widget/jlist-single-selection.html"]
---

デフォルトでは、JList で表示されている項目は、Control キーを押しながらクリックすることで複数選択することができます。
`setSelectionMode()` メソッドを使用することで、この振る舞いを変更して、項目を１つだけしか選択できないようにすることができます。

選択モードを表す定数は `javax.swing.ListSelectionModel` で定義されています。

- `ListSelectionModel.SINGLE_SELECTION` -- 1 つの項目のみ選択可能。
- `ListSelectionModel.SINGLE_INTERVAL_SELECTION` -- 複数の項目を選択可能（ただし連続した範囲のみ）。
- `ListSelectionModel.MULTIPLE_INTERVAL_SELECTION` -- 複数の項目を選択可能。

{{< code lang="java" title="使用例" >}}
// JList list = new JList(new MyListModel());
list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
{{< /code >}}
