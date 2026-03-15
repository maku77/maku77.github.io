---
title: "Javaメモ: Swing - JTextArea で複数行のテキストを表示する"
url: "p/vvtvhiy/"
date: "2011-01-23"
tags: ["java"]
aliases: ["/java/swing/widget/jtextarea-multiline.html"]
---

複数行のテキストを表示したり編集したりする場合は、JTextArea を使用します（１行だけの表示でよい場合は、JTextField を使用します）。

{{< image src="img-001.png" >}}

JTextArea 単体では、スクロール機能を備えていないので、通常は JScrollPane の上に JTextArea を配置して使用します。

{{< code lang="java" title="サンプルコード" >}}
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyPanel extends JPanel {
    public MyPanel() {
        JTextArea textArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
{{< /code >}}
