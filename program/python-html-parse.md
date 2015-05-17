---
title: Python で HTML をパースする
created: 2015-05-18
---

Python に標準搭載されている HTML パーサである [html.parser.HTMLParser](https://docs.python.org/3/library/html.parser.html) は、イベントドリブンな HTML パーサです。

```HTMLParser``` クラスを継承して独自のパーサを作成し、```feed``` メソッドに HTML テキストを渡すことによってパースを開始します。
下記のサンプルコードでは、開始タグ、終了タグ、テキストデータ、コメントが見つかったときに、それぞれ内容を出力しています。


#### sample.py
```python
from html.parser import HTMLParser

# HTMLParser を継承してパーサクラスを作成する
class MyParser(HTMLParser):
    def handle_starttag(self, tag, attrs):
        print("START  :", tag, attrs)
    def handle_endtag(self, tag):
        print("END    :", tag)
    def handle_data(self, data):
        print("DATA   :", data)
    def handle_comment(self, comment):
        print("COMMENT:", comment)

# パーサのインスタンスを生成してパースを実行
parser = MyParser()
parser.feed('<html><body>'
            '<div class="class1" id="id1">Hello World</div>'
            '<!-- I am a comment -->'
            '</body></html>')
```

#### 実行結果
```
START  : html []
START  : body []
START  : div [('class', 'class1'), ('id', 'id1')]
DATA   : Hello World
END    : div
COMMENT:  I am a comment 
END    : body
END    : html
```

HTMLParser は、単純に HTML 要素を前から順番に処理していくだけなので、現在の要素がどのようなコンテキストで記述されているかは、パーサクラス内で判断していくように実装する必要があります。

インターネットから HTML コンテンツを取得するには、requests モジュールを使用するのが簡単です。

```python
import requests
text = requests.get('http://example.com').text
```
