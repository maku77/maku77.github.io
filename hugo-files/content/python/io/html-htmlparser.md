---
title: "Python で HTML をパースする (HTMLParser)"
url: "p/nqz8fnu/"
date: "2015-05-18"
tags: ["Python", "HTML"]
aliases: /python/parse-html-by-html-parser.html
---

HTMLParser で HTML を処理する
----

Python に標準搭載されている HTML パーサである [html.parser.HTMLParser](https://docs.python.org/3/library/html.parser.html) は、イベントドリブンな HTML パーサです。

__`HTMLParser`__ クラスを継承して独自のパーサを作成し、__`feed`__ メソッドに HTML テキストを渡すことによってパースを開始します。
下記のサンプルコードでは、開始タグ、終了タグ、テキストデータ、コメントが見つかったときに、それぞれ内容を出力しています。

{{< code lang="python" title="sample.py" >}}
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
{{< /code >}}

{{< code title="実行結果" >}}
START  : html []
START  : body []
START  : div [('class', 'class1'), ('id', 'id1')]
DATA   : Hello World
END    : div
COMMENT:  I am a comment 
END    : body
END    : html
{{< /code >}}

`HTMLParser` は、単純に HTML 要素を前から順番に処理していくだけなので、現在の要素がどのようなコンテキストで記述されているかは、パーサクラス内で判断していくように実装する必要があります。


ローカルの HTML ファイル、Web 上の HTML ファイルを扱う場合
----

ローカルファイルの内容は、Python 標準の `open` 関数で読み込むことができます。

{{< code lang="python" title="ローカルの HTML ファイル" >}}
text = open('input.html').read()
{{< /code >}}

Web 上の HTML ファイルの内容も、`requests` モジュールなどで簡単に読み込めます。

{{< code lang="python" title="Web 上の HTML ファイル" >}}
import requests
text = requests.get('http://example.com/').text
{{< /code >}}

あとは、取得した HTML テキストを __`HTMLParser#feed()`__ に渡してやれば OK です。

