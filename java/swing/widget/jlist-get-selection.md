---
title: Swing - JList で選択されている項目を取得する
created: 2011-02-19
---

JList で現在選択されている項目を取得するには、以下のようなメソッドを使用します。

- `int getSelectedIndex()`       -- 選択されていない場合は -1
- `int[] getSelectedIndices()`   -- 選択されていない場合はサイズ 0 の配列
- `Object getSelectedValue()`   -- 選択されていない場合は null
- `Object[] getSelectedValues()` -- 選択されていない場合はサイズ 0 の配列

JList の複数の項目が選択された状態で、`getSlectedIndex`, `getSelectedValues` を実行すると、選択されている項目の中で最初の項目のインデックスや値が返されます。

`getSelectedValue()` で返されるオブジェクトの実際の型は、JList にセットされている ListModel の `getElementAt(int index)` の実装で返されるオブジェクトの型に依存します。

~~~ java
// JList list = ...;
MyData data = (MyData) list.getSelectedValue();
if (data != null) {
    ...
}
~~~

