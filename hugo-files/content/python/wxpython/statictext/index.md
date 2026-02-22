---
title: "Pythonメモ: wxPython - StaticText（変更不可のラベル）"
url: "p/m5kkuhu/"
date: "2007-04-06"
tags: ["python"]
aliases: /python/wxpython/statictext.html
---

wx.StaticText の基本
----

{{< image src="img-001.png" >}}

`wx.StaticText` は、選択不可のテキストラベルを表示するために使用します。
サンプルでは、配置位置を分かりやすくするため一つ目のラベル以外は背景色を変更していますが、デフォルトの背景色は透明です。

{{< code lang="python" title="サンプルコード" >}}
import wx

class MyFrame(wx.Frame):
    def __init__(self):
        wx.Frame.__init__(self, None, -1, 'Title', size=(300,250))

        # Create widgets.
        panel = wx.Panel(self)

        # Basic label.
        label1 = wx.StaticText(panel, -1, 'basic label')

        # Left aligned label.
        label2 = wx.StaticText(panel, -1, 'left aligned')
        label2.SetBackgroundColour('green')

        # Center aligned label.
        label3 = wx.StaticText(panel, -1, 'center aligned', style=wx.ALIGN_CENTER)
        label3.SetBackgroundColour('green')

        # Right aligned label.
        label4 = wx.StaticText(panel, -1, 'right aligned', style=wx.ALIGN_RIGHT)
        label4.SetBackgroundColour('green')

        # Left aligned multiple lines label.
        label5 = wx.StaticText(panel, -1, 'Left aligned\nmultiple\nlines text')
        label5.SetBackgroundColour('cyan')

        # Center aligned multiple lines label.
        label6 = wx.StaticText(panel, -1, 'Center aligned\n'
            'multiple\nlines text', style=wx.ALIGN_CENTER)
        label6.SetBackgroundColour('cyan')

        # Center aligned multiple lines label.
        label7 = wx.StaticText(panel, -1, 'Right aligned\n'
            'multiple\nlines text', style=wx.ALIGN_RIGHT)
        label7.SetBackgroundColour('cyan')

        # Set sizer.
        sizer = wx.BoxSizer(wx.VERTICAL)
        sizer.Add(label1, 0, wx.EXPAND|wx.LEFT|wx.RIGHT|wx.TOP, 5)
        sizer.Add(label2, 0, wx.EXPAND|wx.LEFT|wx.RIGHT|wx.TOP, 5)
        sizer.Add(label3, 0, wx.EXPAND|wx.LEFT|wx.RIGHT|wx.TOP, 5)
        sizer.Add(label4, 0, wx.EXPAND|wx.LEFT|wx.RIGHT|wx.TOP, 5)
        sizer.Add(label5, 0, wx.EXPAND|wx.LEFT|wx.RIGHT|wx.TOP, 5)
        sizer.Add(label6, 0, wx.EXPAND|wx.LEFT|wx.RIGHT|wx.TOP, 5)
        sizer.Add(label7, 0, wx.EXPAND|wx.LEFT|wx.RIGHT|wx.TOP, 5)
        panel.SetSizer(sizer)

if __name__ == '__main__':
    app = wx.PySimpleApp()
    MyFrame().Show(True)
    app.MainLoop()
{{< /code >}}

