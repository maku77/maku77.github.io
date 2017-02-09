---
title: Swing - JButton（ボタン）にショートカットキーを割り当てる
created: 2011-02-12
---

JButton の以下のメソッドを使用して、ボタンにショートカットキーを設定できます。

~~~ java
void JButton.setMnemonic(int mnemonic)
~~~

`mnemonic` パラメータには、`java.awt.event.KeyEvent.VK_A` のようなキーボード上の一文字にあたるキーコードを指定します。
例えば、`mnemonic` として `KeyEvent.VK_N` を設定すると、ボタンに表示されているテキストの N という文字にアンダースコアが引かれ、Alt + N がショートカットキーとして割り当てられます（Mac の場合は Option + N）。

![jbutton-shortcut.png](./jbutton-shortcut.png)

#### サンプルコード

~~~ java
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
    public MyPanel() {
        JButton newButton = new JButton("New");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        // Set shortcut keys.
        newButton.setMnemonic(KeyEvent.VK_N);
        editButton.setMnemonic(KeyEvent.VK_E);
        deleteButton.setMnemonic(KeyEvent.VK_D);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = e.getActionCommand();
                JOptionPane.showMessageDialog(MyPanel.this, message);
            }
        };

        newButton.addActionListener(listener);
        editButton.addActionListener(listener);
        deleteButton.addActionListener(listener);

        setLayout(new FlowLayout());
        add(newButton);
        add(editButton);
        add(deleteButton);
    }
}
~~~

