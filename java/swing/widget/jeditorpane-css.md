---
title: Swing - JEditorPane にスタイルシート (CSS) を設定する
date: "2011-03-19"
---

`JEditorPane` に CSS（スタイルシート）を設定するには、`HTMLEditorKit`、`StyleSheet` オブジェクトを使用する必要があります。

`JEditorPane#setText()` で設定する HTML テキスト内で、`style` 要素を埋め込んでも反映されません。

~~~ java
//import javax.swing.JEditorPane;
//import javax.swing.text.html.HTMLEditorKit;
//import javax.swing.text.html.StyleSheet;

// JEditorPane editorPane = ...;
HTMLEditorKit editorKit = new HTMLEditorKit();
StyleSheet css = editorKit.getStyleSheet();
css.addRule("BODY { margin:5px; }");
css.addRule("H1 { color:blue; }");
css.addRule("PRE { background:ffeeee; margin:10px; padding:3px; }");
editorKit.setStyleSheet(css);
editorPane.setEditorKit(editorKit);

// editorPane.setText(HTML_TEXT);
~~~

