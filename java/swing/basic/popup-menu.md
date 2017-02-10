---
title: Swing - 右クリックでポップアップメニューを表示する
created: 2011-02-20
---

マウスの右クリックをしたときにポップアップメニューを表示するには、JPopupMenu に JMenuItem を登録しておき、`JPopupMenu.show()` を実行します。

~~~ java
JPopupMenu popup = new JPopupMenu();
JMenuItem item = JMenuItem("menu item");
item.addActionListener(this);
popup.add(item);
popup.show(this, posX, poxY);
~~~

下記の MousePopupMenu クラスは、JPopupMenu を継承したクラスで、コンストラクタで渡された Component 上で右クリックしたときにカーソル位置にポップアップメニューを表示します。
このクラスはマウスによるポップアップメニューを表示したい場合に、汎用的に使用することができます。

コンストラクタの引数の `java.awt.Component` には、ポップアップを可能にする最上位のコンポーネントを指定しなければいけません。
例えば、JList 上でポップアップさせたいのであれば、その JList を含んでいる JPanel ではなく、JList そのものを指定する必要があります。

~~~ java
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/**
 * Popup menu that handles a right-button click.
 */
@SuppressWarnings("serial")
public class MousePopupMenu extends JPopupMenu {
    public MousePopupMenu(Component component) {
        component.addMouseListener(new PopupListener());
    }

    private class PopupListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                MousePopupMenu.this.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
~~~

上記の MousePopupMenu クラスを使ったサンプルコードです。
以下の例では、2 つのメニュー項目を表示しています。

~~~ java
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
    private MousePopupMenu popup = new MousePopupMenu(this);

    public MyPanel() {
        setupPopupMenu();
        setPreferredSize(new Dimension(200, 100));
    }

    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, e.getActionCommand());
        }
    };

    private void setupPopupMenu() {
        JMenuItem item1 = new JMenuItem("popup menu item 1");
        JMenuItem item2 = new JMenuItem("popup menu item 2");
        item1.addActionListener(listener);
        item2.addActionListener(listener);
        popup.add(item1);
        popup.add(item2);
    }
}
~~~

