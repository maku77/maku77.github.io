---
title: "doskey を使ってコマンドプロンプト用のマクロを登録する"
date: "2014-10-04"
lastmod: "2020-10-22"
---

doskey コマンドによるマクロ登録の基本
---

__`doskey`__ コマンドを使用すると、コマンドプロンプトから使用するマクロ（コマンドエイリアス）を設定することができます。

```
C:\> doskey cat=type $*
C:\> doskey ls=dir /b $*
C:\> doskey u=cd ..
C:\> doskey uu=cd ..\..
C:\> doskey uuu=cd ..\..\..
C:\> doskey cd-dir1=cd D:\my\favorite\dir1
C:\> doskey cd-dir2=cd D:\my\favorite\dir2
```

現状登録されているマクロの一覧を表示するには __`doskey /macros`__ コマンドを使用します。

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

`doskey` コマンドで設定したマクロは、コマンドプロンプトを閉じると消えてしまいます。
ここでは、コマンドプロンプトを起動したときに、自動的に `doskey` でマクロ設定を復帰させるようにする方法を説明します。

### マクロ設定の保存と復帰

現在のマクロ設定をファイルに保存するには、`doskey /macros` コマンドの出力をファイルにリダイレクトします。

```
C:\> doskey /macros > D:\x\myconf\doskey_macros.txt
```

逆に、上記のように保存したマクロ設定ファイルを読み込むには、__`doskey /macrofile`__ コマンドを使用します。

```
C:\> doskey /macrofile=D:\x\myconf\doskey_macros.txt
```

### コマンドプロント起動時に自動読み込み

コマンドプロンプト (`cmd.exe`) の起動時に、__`/k`__ オプションを使用すると、任意のコマンドを実行した上で起動することができます。
このオプションで上記の `doskey /macrofile` コマンドを実行するようにすれば、コマンドプロンプトの起動と同時にマクロ設定を復帰させることができます。

この `cmd.exe /k` コマンドの実行を自動化するには、`cmd.exe` のショートカットファイルを作成するのが簡単です。
`cmd.exe` のショートカットを作成し、そのプロパティの __リンク先__ を下記のように変更します（`/k` オプション以降を追加）。

```
%windir%\system32\cmd.exe /k "doskey /macrofile=D:\x\myconf\doskey_macros.txt"
```

あとは、このショートカットファイルを使ってコマンドプロンプトを起動すれば、自動的に `doskey` によるマクロ設定が有効になります。

