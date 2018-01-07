---
title: Swing - JTextArea に表示するフォントを設定する
date: "2011-03-21"
---

`JTextArea#setFont` メソッドにより、表示するフォントを変更することができます。
例えば、下記のように `Font.MONOSPACED` を指定した `Font` インスタンスを指定することで、等幅のフォントで描画できるようになります。

#### 例: 等幅フォントを表示する

~~~ java
// JTextArea textArea = ...;
textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
~~~

