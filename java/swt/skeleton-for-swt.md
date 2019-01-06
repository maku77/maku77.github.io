---
title: "SWT - SWT アプリケーションの雛形コード"
date: "2010-08-14"
---

#### Main.java

~~~ java
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
     private void createComponents(Composite parent) {
         parent.setLayout(new FillLayout());
         // この中で Widget を追加していく
     }

     public Main() {
        Display display = new Display();
        Shell shell = new Shell(display);
        createComponents(shell);
        shell.setText("Title");
        shell.setSize(300, 200);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    public static void main(String[] args) {
        new Main();
    }
}
~~~

