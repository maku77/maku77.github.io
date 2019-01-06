---
title: "Python コードの実行時間を計測する"
date: "2014-05-01"
---


下記は `datetime` モジュールを使って、Python コードの一部の実行時間を計測する例です。

```python
from datetime import datetime
start = datetime.now()

# ここに実行時間を計測したい処理

print(datetime.now() - start)
```

#### 実行結果

```
00:00:01.547393
```

