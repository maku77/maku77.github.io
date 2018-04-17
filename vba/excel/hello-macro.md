---
title: "Excel の VBA マクロで Hello World"
date: "2018-04-16"
---

VBA マクロの作成
----

Excel のマクロは VBE (Visual Basic Editor) を使って作成することができます。
Visual Basic Editor を開くには、ショートカットキーの <kbd>Alt + F11</kbd> を使用するか、Excel のメニューから次のように実行します。

1. <kbd><samp>ファイル</samp></kbd> → <kbd><samp>オプション</samp></kbd> を選択。
2. <kbd><samp>リボンのユーザ設定</samp></kbd> → <samp>リボンのユーザ設定</samp> のプルダウンで <kbd><samp>メイン タブ</samp></kbd> を選択し、<kbd><samp>開発</samp></kbd> にチェックを入れる。
3. リボンに<samp>開発</samp>タブが表示されるので、その中の<kbd><samp>Visual Basic</samp></kbd> アイコンをクリック。

Visual Basic Editor を起動できたら、まずはメッセージボックスで Hello World を表示する簡単なマクロを作ってみましょう。
最初に、次のようにして標準モジュールを追加してください。

1. <kbd><samp>挿入</samp></kbd> → <kbd><samp>標準モジュール</samp></kbd>

次のようにコードを記述したら、テキストカーソルが関数内にある状態で <kbd>F5</kbd> キーを押して実行してみてください。
メッセージボックスが表示されれば成功です。
簡単ですね！

~~~ vb
'メッセージボックスを表示するマクロ
Public Sub MsgBoxTest()
    MsgBox "Hello World"
End Sub
~~~

<div class="note">
上記のマクロは、Excel ファイル（ブック）を保存しなくても実行できますが、マクロを含むブックを保存するには、xlsm 形式（マクロ有効ブック） として保存する必要があることに注意してください（xlsx 形式のファイルにはマクロを含めることができません）。
xlsx 形式のままでブックを保存しようとすると警告ダイアログが表示されるので、その指示に従って xlsm 形式を選択すれば大丈夫です。
</div>


マクロの実行
----

作成した VBA マクロは次のようなショートカットキーで実行することができます。

- Visual Basic Editor を開いているとき: <kbd>F5</kbd>
- ブックを開いているとき: <kbd>Alt + F8</kbd>

また、Excel 2007 以降では、リボンに「開発」タブを表示しておけば、その中の <kbd><samp>マクロ</samp></kbd> というボタンからマクロを起動することもできます。

