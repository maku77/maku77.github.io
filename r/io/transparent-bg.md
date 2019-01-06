---
title: "画像ファイル (2) 画像の背景を透過させる"
date: "2014-12-09"
---

作図デバイスを切り替えるときに、`bg` オプションに `"transparent"` を指定すると、背景を透過した画像を出力することができます。

```r
> png("sample.png", width=400, height=300, bg="transparent")
> plot(x, y)
> dev.off()
```

