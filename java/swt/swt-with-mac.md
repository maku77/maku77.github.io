---
title: SWT - Mac で SWT を使用する
created: 2010-11-23
---

ここでは SWT の zip パッケージをプロジェクトとしてインポートし、新規プロジェクトからそのプロジェクトを参照することで SWT のライブラリを使用できるようにします。

SWT ライブラリ (`swt.jar`) はプラットフォームに依存する DLL などを含んでいるので、開発対象のプロジェクトのディレクトリに直接格納してしまうよりも、この方法でプロジェクト間の参照にしておいた方が、別のプラットフォームで開発を継続するときに都合がよかったりします。

一方で、JFace のライブラリは SWT と違って Java のみで記述されているため、プラットフォームに依存しません。
JFace の jar ライブラリ群は、開発対象のプロジェクトのディレクトリに格納してしまってもよいでしょう。

SWT のページから zip パッケージをダウンロード
----

1. [https://www.eclipse.org/SWT/](https://www.eclipse.org/SWT/) から zip ファイルをダウンロード。
2. `/mylib/swt-3.5.1-cocoa-macosx.zip` などに保存する。

zip ファイルを existing project として import する
----

1. Eclipse メニューから `File` => `Import` => `General/Existing Projects into Workspace` を選択。
2. `Select archive file` から SWT の zip ファイルを指定する。

これで、ワークスペースに `org.eclipse.swt` というプロジェクトが追加されます。

新規 Java プロジェクトを作成し、SWT を使用できるようにする。
----

1. 新規プロジェクト Property を開く。
2. Eclipse メニューから `Java Build Path` => `Projects` => `Add` と辿り、`org.eclipse.swt` を追加。

これで新規プロジェクトから SWT のライブラリを参照できるようになります。

