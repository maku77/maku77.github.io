---
title: SWT - SWT を使用するための設定
date: "2010-08-08"
---

swt.jar のダウンロード
----

SWT を使ったアプリケーションをビルドするとき、あるいは、SWT を使用して作成されたアプリケーションを実行するときは、各プラットフォーム用の swt.jar が必要です。
swt.jar は下記のサイトからダウンロードできます（2010-02-26 の最新は 3.5.2）。

- [http://www.eclipse.org/swt/](http://www.eclipse.org/swt/)

swt.jar の中には、SWT の class ファイルだけでなく、各プラットフォーム用の共有ライブラリ （Windows の場合は swt-win32-3557.dll など）も含まれているので、swt.jar さえあれば、SWT を利用して作ったアプリケーションを実行することができます。


Eclipse から swt.jar を使用する
----

Eclipse で SWT を使ったプログラムを行うには、Java プロジェクトに swt.jar へのクラスパスを追加します。
swt.jar ファイル自体は、プロジェクトのディレクトリにコピーして使うようにすれば、別の PC で開発の続きをするときに、swt.jar を再度ダウンロードしなくて済みます。

1. Package Explorer のプロジェクトに、swt.jar をドラッグ＆ドロップ。これで、プロジェクトのディレクトリに swt.jar がコピーされます。
2. Package Explorer でプロジェクトを右クリック => Property
3. Java Build Path の Libraries タブで Add JARs
4. プロジェクト内の swt.jar を選択


SWT の HelloWorld
----

SWT のクラスパスの設定が済んだら、以下のようなプログラムを作成して、Ctrl + F11 で実行してみましょう。

~~~ java
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
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
}
~~~

