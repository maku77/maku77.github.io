---
title: JFace - JFace のウィンドウにウィジェットを配置する
date: "2010-08-21"
---

![jface-create-contents1.png](./jface-create-contents1.png)

JFace アプリケーションのウィンドウにウィジェットを配置するには、ApplicationWindow のスーパークラスである `org.eclipse.jface.window.Window` クラスの `createContents` メソッドをオーバーライドし、引数で渡された Composite オブジェクトにウィジェットを配置していきます。

~~~ java
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class MainWindow extends ApplicationWindow {
    public MainWindow() {
        super(null);
    }

    @Override
    protected Control createContents(Composite parent) {
        Button btn = new Button(parent, SWT.NONE);
        btn.setText("OK");
        return parent;
    }

    public static void main(String[] args) {
        MainWindow w = new MainWindow();
        w.setBlockOnOpen(true);  // Window を閉じるまで open メソッドをブロック
        w.open();
        Display.getCurrent().dispose();
    }
}
~~~

デフォルトで、上記のように配置したボタンは、ウィンドウサイズを変えると自動的にそのサイズも引き延ばされます。

![jface-create-contents2.png](./jface-create-contents2.png)

