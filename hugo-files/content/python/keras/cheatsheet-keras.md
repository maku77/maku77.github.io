---
title: "Keras チートシート"
url: "p/niwn3x5/"
date: "2024-01-08"
tags: ["Keras"]
draft: true
---

one-hot ベクトル化
----

```python
y_train = keras.utils.np_utils.to_categorical(y_train.astype("int32"), 10)
```

0〜9 の数値データ（一次元ベクトル）を [0, 0, 1, 0, ...] のような one-hot ベクトルに変換する。

