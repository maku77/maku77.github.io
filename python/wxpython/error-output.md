---
title: "wxPython アプリケーションのエラー出力方法を理解する"
date: "2007-02-13"
---

stdout/stderr への出力をウィンドウ／コンソールへ出力する
----

wxPython アプリケーションにおいて、`wx.App` インスタンスが作成された後に `print` 関数などを使用してメッセージを出力しようとすると、新しい空のウィンドウが開いて、そこにメッセージが出力されます。
これは、`wx.App` のコンストラクタのデフォルトパラメータで、出力が wxPython の提供する **stdout/stderr ウィンドウ** にリダイレクトされるように設定 (`redirect=True`) されているからです。

この状態でアプリケーションを実行すると、ユーザの作成したメインウィンドウとは別に空のウィンドウが表示され、そこにリダイレクトされたメッセージが表示されるようになります。
このウィンドウもトップレベルウィンドウとして扱われるため、メインウィンドウとこのウィンドウの両方を閉じるまではアプリケーションの `MainLoop()` は終了しません。
新しい stdout/stderr ウィンドウを開かないようにするには、`wx.App` のコンストラクタで `redirect=False` に設定します。
この設定により、標準出力、標準エラー出力への出力をコンソールへ表示するように変更できます。

~~~ python
class MyApp(wx.App):
    def __init__(self):
        wx.App.__init__(self, redirect=False)  # 出力はコンソールへ
        ...
~~~

別の方法として、`wx.App` の代わりに `wx.PySimpleApp` をアプリケーションクラスとして使用する方法があります。
`wx.PySimpleApp` はデフォルトで `redirect=False` に設定されているので、標準出力、標準エラー出力への出力はコンソールに表示されます。

~~~ python
class MyApp(wx.PySimpleApp):
    ...
~~~

リダイレクトの設定が有効なのは、`wx.App` インスタンスの生存中だけであることに注意してください。
例えば、`wx.App.__init__()` でリダイレクト設定が行われる前に標準出力へ出力したメッセージや、`MainLoop()` が終了して `wx.App` オブジェクトが破棄された後のメッセージは、リダイレクト設定にかかわらずコンソールへ出力されます。

#### サンプルコード

~~~ python
import wx

class MyApp(wx.App):
    def __init__(self):
        wx.App.__init__(self, redirect=True)

    def OnInit(self):
        frame = wx.Frame(None, -1, "Title")
        frame.Show(True)
        print "Print to stdout/stderr window"
        return True

if __name__ == '__main__':
    app = MyApp()
    app.MainLoop()
    print 'Print to console'
~~~


stdout/stderr への出力をファイルにリダイレクトする
----

`wx.App` のコンストラクタで `redirect=True` に設定し、さらに `filename` パラメータを指定すると、指定したファイルに標準出力、標準エラーへの出力をリダイレクトすることができます。

~~~ python
class MyApp(wx.App):
    def __init__(self):
        wx.App.__init__(self, redirect=True, filename="log.txt")
~~~

