---
title: "Javaメモ: SWT - FillLayout でウィジェットを縦／横に等間隔に並べる"
url: "p/65pnvmd/"
date: "2010-08-21"
tags: ["java"]
aliases: ["/java/swt/fill-layout.html"]
---

ウィジェットを縦／横に等間隔に並べる
----

Composite に FillLayout を設定すると、その Composite に対して追加されるウィジェットが縦一列 (`SWT.VERTICAL`)、あるいは横一列 (`SWT.HORIZONTAL`) に等間隔で配置されます。

#### SWT.VERTICAL

{{< image src="img-001.png" >}}
{{< image src="img-002.png" >}}

#### SWT.HORIZONTAL

{{< image src="img-003.png" >}}
{{< image src="img-004.png" >}}

配置したウィジェットは親 Composite 全体を占めるように引き延ばされます。
ウィンドウサイズ（親 Composite のサイズ）を変更すると、それぞれのウィジェットのサイズが自動的に等間隔になるよう伸縮されます。

- FillLayout のコンストラクタ: `org.eclipse.swt.layout.FillLayout.FillLayout(int type)`
- Composite の Layout 設定用メソッド: `void org.eclipse.swt.widgets.Composite.setLayout(Layout layout)`

{{< code lang="java" title="サンプルコード" >}}
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class MyComposite extends Composite {
    MyComposite(Composite parent) {
        super(parent, SWT.NONE);

        setLayout(new FillLayout(SWT.VERTICAL));
        for (int i = 0; i < 5; ++i) {
            Button btn = new Button(this, SWT.None);
            btn.setText("Button " + i);
        }
    }
}
{{< /code >}}

FillLayout の各種マージンを設定する
----

{{< image src="img-005.png" >}}

FillLayout オブジェクトの下記フィールドを設定することで、各種マージンを設定することができます。

- `marginHeight` -- Composite の上端、下端からのマージン
- `marginWidth` -- Composite の左端、右端からのマージン
- `spacing` -- 配置したウィジェット同士の間隔

{{< code lang="java" title="サンプルコード" >}}
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class MyComposite extends Composite {
    MyComposite(Composite parent) {
        super(parent, SWT.NONE);

        FillLayout layout = new FillLayout(SWT.HORIZONTAL);
        layout.marginHeight = 10;  // Composite の上端、下端からのマージン
        layout.marginWidth = 50;   // Composite の左端、右端からのマージン
        layout.spacing = 5;        // 配置したウィジェット同士の間隔
        setLayout(layout);

        for (int i = 0; i < 3; ++i) {
            Button btn = new Button(this, SWT.None);
            btn.setText("Button " + (i + 1));
        }
    }
}
{{< /code >}}

