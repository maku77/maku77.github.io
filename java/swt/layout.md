---
title: SWT - SWT の Layout は Composite の入れ子で構成する
date: "2010-08-28"
---

SWT を使ったレイアウトは、主に Composite を入れ子構造にして配置していきます。
例えば、ボタンを配置する Composite は以下のように実装できます。

#### MyComposite.java

~~~ java
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class MyComposite extends Composite {
    public MyComposite(Composite parent) {
        super(parent, SWT.NONE);

        setLayout(new FillLayout(SWT.VERTICAL));
        for (int i = 0; i < 5; ++i) {
            Button btn = new Button(this, SWT.None);
            btn.setText("Button " + i);
        }
    }
}
~~~

ここで配置するウィジェットとして、さらに Composite を指定することにより、入れ子構造ができあがっていきます。
後は、メインウィンドウに最上位の Composite として配置するだけです。

#### MainWindow.java

~~~ java
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class MainWindow extends ApplicationWindow {
    public MainWindow() {
        super(null);
    }

    @Override
    protected Control createContents(Composite parent) {
        new MyComposite(parent);  // ★ここを独自の Composite クラスに入れ替えれば OK
        return parent;
    }

    private void run() {
        setBlockOnOpen(true);
        open();
        Display.getCurrent().dispose();
    }

    public static void main(String[] args) {
        new MainWindow().run();
    }
}
~~~

