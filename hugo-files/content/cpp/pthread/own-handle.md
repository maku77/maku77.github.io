---
title: "C++メモ: pthread で自身のスレッドハンドルを取得する"
url: "p/zwhk6qf/"
date: "2010-11-08"
tags: ["cpp"]
aliases: /cpp/pthread/own-handle.html
---

pthread によって作成されたスレッドの中から、自分自身のスレッドハンドルを取得するには `pthread_self()` を使用します。

```cpp
pthread_t pthread_self(void);
```

