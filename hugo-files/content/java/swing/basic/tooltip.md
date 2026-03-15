---
title: "Javaメモ: Swing - コンポーネントにツールティップを表示する"
url: "p/wfrdpzt/"
date: "2011-01-06"
tags: ["java"]
aliases: ["/java/swing/basic/tooltip.html"]
---

Swing のコンポーネントにツールティップを付けるには、各種コンポーネントの親クラスである JComponent の以下のメソッドを使用します。

~~~
void javax.swing.JComponent#setToolTipText(String text)
~~~

{{< code lang="java" title="使用例（ボタンにツールティップを設定する）" >}}
JButton button = new JButton("Send");
button.setToolTipText("Send the message");
{{< /code >}}
