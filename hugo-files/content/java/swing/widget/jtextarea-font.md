---
title: "Javaメモ: Swing - JTextArea に表示するフォントを設定する"
url: "p/95grovt/"
date: "2011-03-21"
tags: ["java"]
aliases: ["/java/swing/widget/jtextarea-font.html"]
---

`JTextArea#setFont` メソッドにより、表示するフォントを変更することができます。
例えば、下記のように `Font.MONOSPACED` を指定した `Font` インスタンスを作成することで、等幅のフォントで描画できるようになります。

{{< code lang="java" title="例: 等幅フォントを表示する" >}}
// JTextArea textArea = ...;
textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
{{< /code >}}
