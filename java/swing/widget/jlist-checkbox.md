---
title: Swing - JList でリスト項目としてチェックボックスを表示する
created: 2011-03-12
---


CheckBoxList クラス
----

下記の `CheckBoxList` クラスは、`JList` を継承したコンポーネントで、モデル要素として `JCheckBox` のリストを格納することを意図して実装しています。

#### CheckBoxList.java

~~~ java
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class CheckBoxList extends JList {
     public CheckBoxList() {
          super();
          setCellRenderer(cellRenderer);
          addMouseListener(mouseListener);
     }

     private MouseListener mouseListener = new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
               int index = locationToIndex(e.getPoint());
               if (index != -1) {
                    JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
                    checkbox.setSelected(!checkbox.isSelected());
                    repaint();
               }
          }
     };

    private ListCellRenderer cellRenderer = new ListCellRenderer() {
          @Override
          public Component getListCellRendererComponent(JList list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
               JCheckBox checkbox = (JCheckBox) value;
               //checkbox.setBackground(isSelected ? getSelectionBackground() : getBackground());
               //checkbox.setForeground(isSelected ? getSelectionForeground() : getForeground());
               //checkbox.setEnabled(isEnabled());
               //checkbox.setFont(getFont());
               //checkbox.setFocusPainted(false);
               //checkbox.setBorderPainted(true);
               return checkbox;
          }
     };
}
~~~


CheckBoxList の使い方
----

`CheckBoxList` のモデルとして、`JCheckBox` を格納することで、チェックボックスのリストを表示することができます。

~~~ java
DefaultListModel listModel = new DefaultListModel();
listModel.addElement(new JCheckBox("AAA"));
listModel.addElement(new JCheckBox("BBB"));
listModel.addElement(new JCheckBox("CCC"));

CheckBoxList cbl = new CheckBoxList();
cbl.setModel(listModel);
~~~

各チェックボックスの状態は、以下のように `ListModel` から `JCheckBox` オブジェクトを取得することで確認することができます。

~~~ java
// DefaultListModel listModel = ...;

int size = listModel.getSize();
for (int i = 0; i < size; ++i) {
    JCheckBox cb = (JCheckBox) listModel.getElementAt(i);
    boolean isSelected = cb.isSelected();
    // ...
}
~~~

