---
title: "SWT - 矢印ボタンを作る"
date: "2010-10-17"
---

![arrow-button.png](./arrow-button.png)

Button インスタンスを生成するときのフラグとして、`SWT.ARROW` と以下のフラグを合わせて指定すると、矢印を表すボタンを作成できます。

- `SWT.UP`
- `SWT.DOWN`
- `SWT.LEFT`
- `SWT.RIGHT`

#### サンプルコード

~~~ java
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class MyComposite extends Composite {
    MyComposite(Composite parent) {
        super(parent, SWT.NONE);
        addComponents();
    }

    private void addComponents() {
        setLayout(new FillLayout(SWT.HORIZONTAL));
        new Button(this, SWT.ARROW | SWT.UP);
        new Button(this, SWT.ARROW | SWT.DOWN);
        new Button(this, SWT.ARROW | SWT.LEFT);
        new Button(this, SWT.ARROW | SWT.RIGHT);
    }
}
~~~

