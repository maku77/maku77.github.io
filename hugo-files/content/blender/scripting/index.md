---
title: "Blenderメモ: Python で Blender を操作する"
url: "p/2qa2g9m/"
date: "2018-02-27"
tags: ["blender"]
aliases: [/blender/scripting/]
draft: true
---

オブジェクトの一覧を取得する
----

```python
>>> for i in bpy.data.objects:
...     print("type={:10}name={}".format(i.type, i.name))
...
type=CAMERA    name=Camera
type=MESH      name=Cube
type=LAMP      name=Lamp
```

