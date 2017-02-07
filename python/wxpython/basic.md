---
title: wxPython によるウィンドウ表示の基本
created: 2007-02-06
---

wxPython の Hello World（シンプルなウィンドウを表示）
----

下記は、シンプルなウィンドウを表示するだけの wxPython アプリケーションのサンプルコードです。

~~~ python
import wx

class MyApp(wx.App):
    def OnInit(self):
        frame = wx.Frame(None, -1, "Hello wxPython")
        frame.SetSizeWH(300, 200)
        frame.Show(True)
        self.SetTopWindow(frame)  # 省略可
        return True

if __name__ == '__main__':
    app = MyApp(0)
    app.MainLoop()
~~~

wxPython プログラムでは、まず、`wx.App` を継承してアプリケーションクラスを作成します。
`wx.App` インスタンスが生成された後で呼び出される `wx.App#OnInit()` をオーバライドして、メインウィンドウ (`wx.Frame`) を作成します。
`wx.Frame` インスタンスは `wx.App` によって管理されるため、`wx.App` インスタンスを生成するまで `wx.Frame` は生成することができません。
`wx.App#OnInit()` は `wx.App` インスタンスが生成されてから呼び出されることが保証されているので、ここでメインウィンドウとなる `wx.Frame` をインスタンス化するのが一般的です。

`wx.App#SetTopWindow()` でトップレベルウィンドウに設定したウィンドウ (`wx.Frame`) が、すべて閉じられたときにアプリケーションは終了します。
ここでは 1 つのウィンドウをトップレベルに設定しているので、そのウィンドウを閉じたときにアプリケーションが終了します。
`SetTopWindow()` を呼び出さなかった場合は、`wx.App` によって管理されている `wx.Frame` のうち少なくとも 1 つがトップレベルウィンドウとして自動的に設定されるので、上記のようにウィンドウがひとつしか存在しないアプリケーションの場合は、`SetTopWindow()` を省略することができます。

`wx.App#OnInit()` の戻り値では通常 `True` を返しますが、リソースの初期化失敗などでアプリケーションの継続できない場合は、`False` を返すことでアプリケーションを終了させることができます。


独自の Frame クラスを作る
----

~~~ python
import wx

class MyFrame(wx.Frame):
    def __init__(self):
        wx.Frame.__init__(self, None, -1, "Hello wxPython",
            wx.DefaultPosition, wx.Size(300, 200))

class MyApp(wx.App):
    def OnInit(self):
        frame = MyFrame()
        frame.Show(True)
        self.SetTopWindow(frame)
        return True

if __name__ == '__main__':
    app = MyApp(0)
    app.MainLoop()
~~~

`wx.Frame` クラスを継承すると、独自の `Frame` クラスを作成することができます。
`Frame` の表示サイズは、コンストラクタのパラメータで指定しなくても、後から `wx.Frame#SetSizeWH()` メソッドを使って変更することができます。

~~~ python
class MyFrame(wx.Frame):
    def __init__(self):
        wx.Frame.__init__(self, None, -1, "Hello wxPython")
        self.SetSizeWH(300, 200)
~~~


wx.html.HtmlWindow
----

HtmlWindow クラスを使用すると、HTML を用いて UI 表示を行うことができます。

~~~ python
import wx
import wx.html

class MyHtmlFrame(wx.Frame):
    def __init__(self, parent, title):
        wx.Frame.__init__(self, parent, -1, title)
        html = wx.html.HtmlWindow(self)
        if "gtk2" in wx.PlatformInfo:
            html.SetStandardFonts()
        html.SetPage(
            "Here is some <b>formatted</b> <i><u>text</u></i> "
            "loaded from a <font color=\"red\">string</font>.")

if __name__ == '__main__':
    app = wx.PySimpleApp()
    frm = MyHtmlFrame(None, "Simple HTML")
    frm.Show()
    app.MainLoop()
~~~


ウィジェットのウィンドウ ID について
----

wxPython の各ウィジェットには、そのフレーム内で一意のウィンドウ ID を割り当てる必要があります。
ウィンドウ ID は次のような方法で決めます。

### 1. 自動的に割り当てる

ウィジェットのコンストラクタの `id` パラメータで -1（あるいは `wx.ID_ANY`）を指定すると、自動的に ID を割り当ててくれます。
`id` パラメータのデフォルト値は -1 です。

~~~ python
frame = wx.Frame.__init__(None, wx.ID_ANY)
frame = wx.Frame.__init__(None, -1)  # 同じ
~~~

自動的に割り当てられたウィンドウ ID は、`GetId()` メソッドで取得することができます。

### 2. wx.NewId() で ID を作成する

~~~ python
id = wx.NewId()
frame = wx.Frame.__init__(None, id)
~~~

### 3. 明示的にウィンドウ ID の数値を指定する

ウィンドウ ID を手動で設定することもできますが、wxPython が内部で使用する `wx.ID_LOWEST` ～ `wx.ID_HIGHEST` の範囲の値は使わないように注意する必要があります。

~~~ python
frame = wx.Frame.__init__(None, 100)
~~~


ウィンドウにウィジェットを配置する
----

ウィンドウにウィジェットを配置するには、各ウィジェットのコンストラクタで親ウィジェットを指定します。
一般的に、`wx.Frame` の直下には `wx.Panel` を配置し、その下に `wx.Button` などのウィジェットを配置していきます。
`wx.Panel` の下に複数の `wx.Button` を配置することで、TAB キーによりボタンのフォーカスを移動できるようになります。

~~~ python
import wx

class MyFrame(wx.Frame):
    def __init__(self):
        wx.Frame.__init__(self, None, -1, "Sample")
        panel = wx.Panel(self)
        button1 = wx.Button(panel, -1, "Button 1", pos=(10, 10))
        button2 = wx.Button(panel, -1, "Button 2", pos=(10, 40))

class MyApp(wx.App):
    def OnInit(self):
        frame = MyFrame()
        frame.Show(True)
        return True

if __name__ == '__main__':
    app = MyApp()
    app.MainLoop()
~~~

`wx.Frame` の直下に 1 つだけウィジェットを配置すると、そのウィジェットのサイズは親ウィンドウのクライアントエリアと同じサイズに自動的に調整されます。
その場合、ウィジェットのコンストラクタで指定した位置やサイズのパラメータは無視されます。
上記の例では、`wx.Frame` の直下に `wx.Panel` を 1 つだけ配置しているので、`wx.Panel` のサイズは `wx.Frame` のクライアントエリアのサイズと同じになります。
`wx.Button` は `wx.Frame` の直下ではなく、`wx.Panel` の下に配置しているので、コンストラクタで指定した位置、サイズが使用されます。
複数のウィジェットの位置やサイズを自動調整するには、wxPython の `sizer` オブジェクトを使用します。


トップレベル・ウィンドウが全て閉じられても MainLoop を終了しないようにする
----

~~~ python
wx.App#SetExitOnFrameDelete(False)
~~~

を実行しておくと、全てのトップレベル･ウィンドウが閉じられてもアプリケーションの MainLoop を抜けないようになります。
つまり、`wx.App` インスタンスが破棄されなくなるので、新しいトップレベル・ウィンドウを生成できるようになります。
このように設定されたアプリケーションを終了するには、グローバルな関数である `wx.Exit()` を呼び出します。


名前や ID でウィジェットを取得する
----

- ウィンドウ ID で検索
  - `wx.FindWindowById(long id, Window parent=None) -> Window`
- ウィンドウに付けた名前で検索
  - `wx.FindWindowByName(String name, Window parent=None) -> Window`
- 表示しているラベルで検索（ボタンやフレームのラベル）
  - `wx.FindWindowByLabel(String label, Window parent=None) -> Window`

`wx.FindWindowByName()` で検索して、ウィンドウ名が見つからない場合は、自動的に `wx.FindWindowByLabel()` による検索が行われます。

