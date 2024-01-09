---
title: "Vim のアンドゥ操作とリドゥ操作"
url: "p/rihibef/"
date: "2008-11-13"
tags: ["vim"]
aliases: /vim/basic/undo-and-redo.html
---

アンドゥ／リドゥの操作方法
----

Vim でアンドゥ (Undo) 操作、リドゥ (Redo) 操作を行うには、下記のコマンドを使用します。

- __`u`__ ... アンドゥ
- __`Ctrl-r`__ ... リドゥ（アンドゥによって取り消された操作を復旧）


アンドゥ回数の設定
----

アンドゥ操作のレベル（制限回数）を変更するには __`undolevels`__ オプションを設定します。

```vim
:set undolevels=300
```

Linux や Windows では、`undolevels` の規定値は 1000 なので、通常は変更する必要はないでしょう。

