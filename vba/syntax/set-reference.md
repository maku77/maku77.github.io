---
title: "オブジェクトへの参照を代入する"
date: "2018-04-17"
---

~~~ vb
Private m_obj As MyObject

Public Sub SetObj(obj As MyObject)
    Set m_obj = obj
End Sub
~~~

オブジェクトへの参照を代入するには、`Set` キーワードを使用します。

