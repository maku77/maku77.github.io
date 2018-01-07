---
title: SWT - ブラウザウィジェット (Browser) で HTML を表示する
date: "2010-11-24"
---

SWT の Browser ウィジェットのプラットフォーム対応状況
----

[SWT FAQ のページ](http://www.eclipse.org/swt/faq.php#browserplatforms)に以下のように書いてあります。

~~~
Q: Which platforms support the SWT Browser?
A: The SWT Browser is currently available on the following platforms:
    * Windows (Internet Explorer 5 and above)
    * Mac (OS X 10.4 and above, WebKit-based)
    * Linux GTK and Linux Motif (details)
    * Solaris-x86 and Solaris 10 SPARC (details)
    * Photon
~~~

Windows ではもちろん使えるし、Mac OS X でも最近デフォルトで使用できるようになったみたいです（Snow Lepard でも使用できることを確認済み）。


Browser ウィジェットを使ってみる
----

以下は、ウィンドウの中で Google のサイト (`http://google.com/`) を表示するサンプルです。

~~~ java
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        Browser browser = new Browser(shell, SWT.NONE);
        browser.setBounds(0, 0, 600, 400);
        browser.setUrl("http://google.com/");

        shell.setText("Title");
        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
~~~


任意の HTML テキストを表示する
----

`Browser#setText()` メソッドを使用すると、任意の HTML テキストを表示することができます。

~~~ java
browser.setText("<H1>Title</H1>" +
    "This is a <B>sample</B> text." +
    "<UL>" +
    "<LI>one</LI>" +
    "<LI>two</LI>" +
    "<LI>three</LI>" +
    "</UL>");
~~~


リンクがクリックされたときのイベントをハンドルする
----

Browser オブジェクトに LocationListener を登録することで、リンクのクリックや `setUrl()` による URL 変更をハンドルすることができます。
リンクのクリック直後は `LocationListener#changing()` が呼び出され、実際に URL 移動後は `LocationListener#changed()` が呼び出されます。

以下のサンプルは、クリックしたリンクの URL に `google.com` という文字列が含まれているかどうかを調べています。

~~~ java
browser.addLocationListener(new LocationListener() {
    @Override
    public void changed(LocationEvent e) {
    }

    @Override
    public void changing(LocationEvent e) {
        if (e.location.contains("google.com")) {
            System.out.println("Jumping to google.com");
        }
    }
});
~~~


Browser ウィジェット上での右クリックを禁止する
----

~~~ java
browser.addListener(SWT.MenuDetect, new Listener() {
    pulic void handleEvent(Event event) {
        event.doit = false;
    }
});
~~~

