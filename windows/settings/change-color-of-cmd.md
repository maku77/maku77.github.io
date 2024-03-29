---
title: "コマンドプロンプトの文字色と背景色を変更する"
date: "2005-07-27"
---

（Windows XP で確認）

コマンドプロンプトのタイトルバーを右クリックして、「規定値」から色を変更できますが、バッチファイルなどから動的にコマンドプロンプトの色を変えたい場合は `color` コマンドを使用します。


構文
----

```
color MN   （M が背景色、N が文字色）
```

#### 背景を白、文字を赤に設定

```
C:\> color fc
```

見づらい色になってしまったら、パラメータなしで `color` コマンドを実行すれば、デフォルトの配色に戻すことができます。

#### デフォルトの色に戻す

```
C:\> color
```


ヘルプ
----

`color /?` でヘルプを表示することができます。

```
C:\> color /?
Sets the default console foreground and background colors.

COLOR [attr]

  attr        Specifies color attribute of console output

Color attributes are specified by TWO hex digits -- the first
corresponds to the background; the second the foreground.  Each digit
can be any of the following values:

    0 = Black       8 = Gray
    1 = Blue        9 = Light Blue
    2 = Green       A = Light Green
    3 = Aqua        B = Light Aqua
    4 = Red         C = Light Red
    5 = Purple      D = Light Purple
    6 = Yellow      E = Light Yellow
    7 = White       F = Bright White

If no argument is given, this command restores the color to what it was
when CMD.EXE started.  This value either comes from the current console
window, the /T command line switch or from the DefaultColor registry
value.

The COLOR command sets ERRORLEVEL to 1 if an attempt is made to execute
the COLOR command with a foreground and background color that are the
same.

Example: "COLOR fc" produces light red on bright white
```

