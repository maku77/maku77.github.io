---
title: "VBA マクロ全体の実行を終了する (End)"
date: "2018-04-27"
---

マクロ全体の実行を中断するには、`End` を単独で呼び出します。

~~~ vb
Sub Test()
    If MsgBox("マクロの実行を終了しますか？", vbYesNo) = vbYes Then
        MsgBox "終了します"
        End
    End If
    MsgBox "マクロを最後まで実行しました"
End Sub
~~~

ちなみに、プロシージャを抜けるときは `Exit Sub` です。

