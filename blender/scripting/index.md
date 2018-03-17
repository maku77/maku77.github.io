---
title: "Python で Blender を操作する"
date: "2018-02-27"
---

オブジェクトの一覧を取得する
----

~~~
>>> for i in bpy.data.objects:
...     print("type={:10}name={}".format(i.type, i.name))
...
type=CAMERA    name=Camera
type=MESH      name=Cube
type=LAMP      name=Lamp
~~~

