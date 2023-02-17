---
title: "Hugo テンプレート内にコメントを記述する"
url: "p/5zwytgx/"
date: "2017-12-15"
tags: ["Hugo"]
description: "Hugo のテンプレート内では、C/C++ や Java に似た形式でコメントを記述することができます。"
aliases: /hugo/template/comment.html
---

Hugo のテンプレートファイル内では、C/C++ や Java に似た形式（__`/*`__ と __`*/`__）でコメントを記述することができます。
コメントとして記述した部分は、HTML ファイルに出力するときに削除されます。

```cpp
{{/* a comment */}}
```

複数行にまたがるコメントを記述することもできます。

```cpp
{{/*
    comment
    comment
    comment
*/}}
```

