---
title: "VBAメモ: オブジェクトへの参照を代入する (Set)"
url: "p/w3u2or7/"
date: "2018-04-17"
tags: ["vba"]
aliases: /vba/syntax/set-reference.html
---

```vb
Private m_obj As MyObject

Public Sub SetObj(obj As MyObject)
    Set m_obj = obj
End Sub
```

オブジェクトへの参照を代入するには、`Set` キーワードを使用します。
