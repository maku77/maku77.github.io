---
title: "Python でモジュールを import する方法いろいろ"
date: "2009-11-19"
---

モジュール名でインポート
====
まずは基本から。
モジュール名でインポートする方法です。
この方法でモジュールをインポートした場合は、内部の関数などを参照するときは、モジュール名をプレフィックスとして付けます。

#### math モジュールをインポート
```python
import math
math.ceil(1.3)  # => 2
```


特定の関数のみをインポート
====
モジュールから指定した関数だけインポートすれば、モジュール名を指定せずにその関数を直接呼び出せるようになります。

#### math モジュール内の ceil 関数のみをインポート
```python
from math import ceil
ceil(1.3)  # => 2
```


別名を付けてインポート
====
モジュール名が長すぎると感じた場合は、`as` キーワードを使用して別名を付けて参照できるようにすることもできます。

#### ElementTree モジュールを ET という別名でインポート
```
import xml.etree.ElementTree as ET
tree = ET.parse('country_data.xml')
```
