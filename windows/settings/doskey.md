---
title: doskey を使ってコマンドプロンプト用のマクロを登録する
date: "2014-10-04"
---

doskey コマンドによるマクロ登録の基本
---

`doskey` コマンドを使用すると、コマンドプロンプトから使用するマクロ（コマンドエイリアス）を設定することができます。

```
C:\> doskey cat=type $*
C:\> doskey ls=dir /b $*
C:\> doskey u=cd ..
C:\> doskey uu=cd ..\..
C:\> doskey uuu=cd ..\..\..
C:\> doskey cd-dir1=cd D:\my\favorite\dir1
C:\> doskey cd-dir2=cd D:\my\favorite\dir2
```

現状登録されているマクロの一覧を表示するには以下のようにします。

```
C:\> doskey /macros
cat=type $*
ls=dir /b $*
u=cd ..
uu=cd ..\..
uuu=cd ..\..\..
cd-dir1=cd D:\my\favorite\dir1
cd-dir2=cd D:\my\favorite\dir2
```


コマンドプロンプト起動時に自動的にマクロ設定するようにする
----

コマンドプロンプトを起動したときに、自動的に `doskey` によるマクロ設定が有効になってくれると便利です。
ここでは、自動的にマクロを有効にする `cmd.exe` のショートカットを作成します。

まずは現状のマクロ設定をファイルに保存しておきます。

```
C:\> doskey /macros > D:\x\myconf\doskey_macros.txt
```

次に、`cmd.exe` のショートカットファイルのプロパティを開き、**リンク先** を下記のように変更します（`/k` オプションの以降を追加）。

```
%windir%\system32\cmd.exe /k "doskey /macrofile=D:\x\myconf\doskey_macros.txt"
```

あとは、このショートカットファイルからコマンドプロンプトを起動すれば、自動的に doskey によるマクロ設定が有効になります。

