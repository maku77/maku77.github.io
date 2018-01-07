---
title: JSON 形式のテキストを Python のオブジェクトに変換する
date: "2013-05-22"
---

* 参考: [Python のオブジェクトを JSON 形式のテキストに変換する](./python-to-json.html)

```python
import json

# input.json ファイルから Python オブジェクトを作成
with open('input.json', encoding='utf-8') as f:
    obj = json.load(f)

# 出力して確認
print(json.dumps(obj, indent=2))
```

