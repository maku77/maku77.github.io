---
title: "Javaメモ: JFace アプリケーションの雛形コード"
url: "p/6g22sgg/"
date: "2010-10-11"
tags: ["java"]
aliases: ["/java/swt/skeleton-for-jface.html"]
---

{{< code lang="java" title="MainWindow.java （このクラスはあまり変更しない）" >}}
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
        new MyComposite(parent);
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
{{< /code >}}


{{< code lang="java" title="MyComposite.java （このクラスを変更していく）" >}}
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

        // Widget は this を親にして配置していく
        Button btn = new Button(this, SWT.NONE);
        btn.setText("OK");
    }
}
{{< /code >}}
