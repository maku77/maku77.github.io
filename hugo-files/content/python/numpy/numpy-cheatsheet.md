---
title: "NumPy チートシート"
url: "p/vy94u2h/"
date: "2023-12-30"
tags: ["Python", "NumPy"]
draft: true
---

| コード | 説明 |
| ---- | ---- |
| `np.linespace(start, stop, num)` | [`[start, stop]` の範囲の等間隔の NumPy 配列を生成](#linespace) |
| `np.linespace(start, stop, num, endpoint=False)` | [`[start, stop)` の範囲の等間隔の NumPy 配列を生成](#linespace) |


等間隔のサンプルからなる NumPy 配列を生成 {#linespace}
----

{{% private %}}
- [np.linspace](https://numpy.org/doc/stable/reference/generated/numpy.linspace.html)
{{% /private %}}

```python
# stop で指定した値を含む
arr = np.linspace(0, 2.0, num=5)
print(arr)  #=> [0.0 0.5 1.0 1.5 2.0]

# stop で指定した値を含まない
arr = np.linspace(0, 2.0, num=5, endpoint=False)
print(arr)  #=> [0.0 0.4 0.8 1.2 1.6]
```

