---
title: "Pythonメモ: wxPython のコンポーネントをドラッグで動かせるようにする"
url: "p/tkxvf77/"
date: "2007-04-23"
tags: ["python"]
aliases: /python/wxpython/drag-component.html
---

{{< image src="img-001.png" >}}

このサンプルでは、配置した赤色の `wx.Panel` オブジェクトをドラッグ＆ドロップで移動できるようにしています。

{{< code lang="python" title="windowDragger.py" >}}
import wx

class WindowDragger:
    def __init__(self, window):
        self.window = window
        self.isDragStarted = False
        self.window.Bind(wx.EVT_LEFT_DOWN, self.OnLeftDown)
        self.window.Bind(wx.EVT_LEFT_UP, self.OnLeftUp)
        self.window.Bind(wx.EVT_MOTION, self.OnMouseMove)

    def OnLeftDown(self, evt):
        self.isDragStarted = True
        self.window.CaptureMouse()
        self.prevMousePos = evt.GetPosition()
        evt.Skip()

    def OnLeftUp(self, evt):
        self.isDragStarted = False
        if self.window.HasCapture():
            self.window.ReleaseMouse()
        evt.Skip()

    def OnMouseMove(self, evt):
        if evt.Dragging() and evt.LeftIsDown() and self.isDragStarted:
            mousePos = evt.GetPosition()
            wndPos = self.window.GetPosition()
            self.window.Move(wndPos - (self.prevMousePos - mousePos))
        evt.Skip()
{{< /code >}}

{{< code lang="python" title="main.py" >}}
import wx
import windowDragger

class MainFrame(wx.Frame):
    def __init__(self):
        wx.Frame.__init__(self, None, -1, 'Title', size=(200,150))
        panel = wx.Panel(self)
        childPanel = wx.Panel(panel, size=(30,30), pos=(10,10))
        childPanel.SetBackgroundColour('red')
        # Enable windows to drag.
        dragger = windowDragger.WindowDragger(childPanel)

if __name__ == '__main__':
    app = wx.PySimpleApp()
    MainFrame().Show()
    app.MainLoop()
{{< /code >}}

