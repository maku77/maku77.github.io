---
title: SWT - Cannot load 32-bit SWT libraries エラーが発生する場合
created: 2010-11-23
---

SWT ライブラリを使用したアプリの実行時に、下記のような Exception が発生することがあります。

~~~
Cannot load 32-bit SWT libraries on 64-bit JVM
~~~

Mac OS X Snowleopard で 64bit の Java VM を使っていたりすると、このような Exception が出て実行できないことがあります。
このような場合は、Eclipse の設定で Java VM を 32bit で動かすように指定すれば実行できるようになります。

1. Eclipse のメニューから `Preferences` → `Java` → `Installed JRE` → `Edit` と辿る。
2. Default VM Arguments に `-d32` を指定する。

