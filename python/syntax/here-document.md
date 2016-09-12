---
title: Python でヒアドキュメント
created: 2012-01-14
---

Python では ```'''``` で囲んだ複数行のテキストを文字列リテラルとして定義することができます。
次の例では、ヒアドキュメントで HTML テキストを作成し、その内容を一行ずつ処理しています。

```python
HTML = '''<html><body>
<h1>Title</h1>
<p>Hello, I'm John.</p>
</body></html>'''

for line in HTML.split('\n'):
    print('*** ' + line)
```

#### 実行結果
```
*** <html><body>
*** <h1>Title</h1>
*** <p>Hello, I'm John.</p>
*** </body></html>
```
