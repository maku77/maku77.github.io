---
title: "Perlメモ: 画像ファイルの幅、高さを調べる (`Image::Size`)"
url: "p/mr7u9nh/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/misc/imgsize.html"]
---

CPAN で配布されている `Image::Size` モジュールを使用すると、簡単に画像ファイルの幅や高さを取得できます。

```perl
use Image::Size;

my ($width, $height) = imgsize('sample.png');
die 'Cannot get the size of the image.' unless defined $height;
```
