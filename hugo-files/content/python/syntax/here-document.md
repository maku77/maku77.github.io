---
title: "Pythonメモ: Python でヒアドキュメント"
url: "p/xet38jy/"
date: "2012-01-14"
tags: ["python"]
aliases: /python/syntax/here-document.html
---

Python では `'''` で囲んだ複数行のテキストを文字列リテラルとして定義することができます。
次の例では、ヒアドキュメントで HTML テキストを作成し、その内容を一行ずつ処理しています。

```python
HTML = '''<html><body>
<h1>Title</h1>
<p>Hello, I'm John.</p>
</body></html>'''

for line in HTML.split('\n'):
    print('*** ' + line)
```

{{< code title="実行結果" >}}
*** <html><body>
*** <h1>Title</h1>
*** <p>Hello, I'm John.</p>
*** </body></html>
{{< /code >}}
