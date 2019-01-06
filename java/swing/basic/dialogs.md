---
title: "Swing - 標準的なダイアログいろいろ"
date: "2011-02-12"
---

単純なメッセージを表示するダイアログ
----

~~~ java
JOptionPane.showMessageDialog(parent, "メッセージ");
~~~


エラーダイアログ
----

~~~ java
JOptionPane.showMessageDialog(parent, "This is an error!", "Error", JOptionPane.ERROR_MESSAGE);
~~~


確認ダイアログ (YES / NO)
----

~~~ java
int ret = JOptionPane.showConfirmDialog(parent, "Are you sure to delete?",
        "Delete a memo", JOptionPane.YES_NO_OPTION);
if (ret == JOptionPane.OK_OPTION) {
    System.out.println("OK");
}
~~~


入力ダイアログ
----

~~~ java
String inputValue = JOptionPane.showInputDialog("Please input a value");
if (inputValue == null || inputValue.isEmpty()) {
    return;
}
System.out.println(inputValue);
~~~

入力ダイアログのウィンドウを閉じたり、キャンセルボタンを押すと、`null` が返されます。

`showInputDialog()` の第２引数で、入力ダイアログに初期値を設定することができます。

~~~ java
String inputValue = JOptionPane.showInputDialog("Please input a value", "default value");
if (inputValue == null || inputValue.isEmpty()) {
    return;
}
System.out.println(inputValue);
~~~

