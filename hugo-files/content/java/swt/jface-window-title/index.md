---
title: "Javaメモ: JFace のウィンドウのタイトルを設定する"
url: "p/4yp5qci/"
date: "2010-08-21"
tags: ["java"]
aliases: ["/java/swt/jface-window-title.html"]
---

{{< image src="img-001.png" >}}

ウィンドウのタイトルを設定するには、ApplicationWindow クラスのスーパークラスである
Window クラスの `getShell` メソッドを使って Shell オブジェクトを取得し、`setText` を実行します。

```
Shell org.eclipse.jface.window.Window#getShell()
void org.eclipse.swt.widgets.Decorations#setText(String arg0)
```

{{< code lang="java" title="サンプルコード" >}}
@Override
protected Control createContents(Composite parent) {
    getShell().setText("Hello JFace");
    parent.setSize(300, 200);
    return parent;
}
{{< /code >}}
