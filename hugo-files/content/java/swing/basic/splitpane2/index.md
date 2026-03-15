---
title: "Javaメモ: Swing - スプリッターを折り畳み可能にする"
url: "p/5sd7u5a/"
date: "2011-01-23"
tags: ["java"]
aliases: ["/java/swing/basic/splitpane2.html"]
---

JSplitPane の以下のメソッドで `true` を指定すると、スプリッター上のボタンなどを押して、左側のペーン、あるいは右側のペーンをワンタッチで折り畳めるようになります。

```java
public void JSplitPane.setOneTouchExpandable(boolean newValue)
```

{{< image title="初期状態" src="img-001.png" >}}
{{< image title="左側のペーンを折り畳んだ状態" src="img-002.png" >}}
{{< image title="右側のペーンを折り畳んだ状態" src="img-003.png" >}}

この折り畳み機能がデフォルトで有効になっているかは、現在使用しているルック＆フィールによります。
Java ルック＆フィールを使用している場合は、デフォルトで無効になっています。

{{< code lang="java" title="使用例" >}}
// JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);

splitPane.setOneTouchExpandable(true);
{{< /code >}}
