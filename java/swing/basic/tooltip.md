---
title: Swing - コンポーネントにツールティップを表示する
date: "2011-01-06"
---

Swing のコンポーネントにツールティップを付けるには、各種コンポーネントの親クラスである JComponent の以下のメソッドを使用します。

~~~
void javax.swing.JComponent#setToolTipText(String text)
~~~

#### 使用例（ボタンにツールティップを設定する）

~~~ java
JButton button = new JButton("Send");
button.setToolTipText("Send the message");
~~~

