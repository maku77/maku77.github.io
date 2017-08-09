---
title: Swing - JTextFiled で Esc キーを押したときにテキストをクリアする
created: 2011-05-05
---

`JTextField` 上でのキー入力を検出するには、`JTextField` インスタンスに `KeyListener` を登録します。
例えば、Esc キー (`KeyEvent.VK_ESCAPE`) の入力を検出するには下記のようにします。

~~~ java
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import javax.swing.JTextField;

textField.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        // Esc キーでテキストフィールドをクリア
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            textField.setText("");
        }
    }
});
~~~

Linux の bash シェルなどでは、`Ctrl + U` といったキーコンビネーションでテキストをクリアすることができます。
こういったキーコンビネーションをハンドルするには、下記のように `KeyEvent#isControlDown()` などを利用して実装します。

~~~ java
@Override
public void keyPressed(KeyEvent e) {
    // Ctrl + U でテキストフィールドをクリア
    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_U) {
        textInput.setText("");
    }
}
~~~

