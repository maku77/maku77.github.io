---
title: "Python で HTML をパースする (Beautiful Soup)"
url: "p/r4m4k2i/"
date: "2015-05-17"
tags: ["Python", "HTML"]
aliases: /python/parse-html-by-beautiful-soup.html
---

Beautiful Soup とは
----

Python 標準の `html.parser` モジュールは、SAX 形式のイベントドリブンなパーサなため、若干扱いにくいところがあります。
Beautiful Soup ライブラリを使用することで、HTML の要素に簡単にアクセスすることができるようになります。

- [Beautiful Soap Documentation](http://www.crummy.com/software/BeautifulSoup/bs4/doc/)


Beautiful Soup パッケージのインストール
----

Beautiful Soup は次のようにインストールできます。

```console
$ pip install beautifulsoup4
```


HTML をパースする
----

最初に、HTML ファイルや、HTML 形式の文字列から __`bs4.BeautifulSoup`__ オブジェクトを生成する必要があります。

{{< code lang="python" title="HTML ファイルから soup を作成" >}}
from bs4 import BeautifulSoup

soup = BeautifulSoup(open("index.html"))
{{< /code >}}

{{< code lang="python" title="HTML 文字列から soup を作成" >}}
from bs4 import BeautifulSoup

soup = BeautifulSoup("<html>data</html>")
{{< /code >}}

{{< code lang="python" title="Web 上の HTML リソースから soup を作成（requests モジュールを使用）" >}}
from bs4 import BeautifulSoup
import requests

res = requests.get("https://example.com/")
if res.status_code != requests.codes.ok:
    print("Error")
    exit(1)
soup = BeautifulSoup(res.text)
{{< /code >}}


最初に登場する要素を見つける
----------------------------

`BeautifulSoup` オブジェクトを生成したら、各要素の検索を行えるようになります。
`BeautifulSoup` オブジェクトのプロパティで HTML 要素のタグ名を指定すると、最初に見つかった要素（__`bs4.element.Tag`__ オブジェクト）を参照することができます。
下記の例では、最初に見つかった `p` 要素の内容を出力しています。

```python
import bs4

html = '''<html><body>
<h1>Title</h1>
<p>Hello, I'm John.</p>
</body></html>'''

soup = bs4.BeautifulSoup(html)
print(type(soup.p))  #=> <class 'bs4.element.Tag'>
print(soup.p.name)   #=> 'p'
print(soup.p.text)   #=> 'Hello, I'm John.'
```

`soup.p` の部分は、`soup.find('p')` と書くこともできます。


いろいろな条件で要素を見つける
------------------------------

__`find`__ メソッドを使用すると、いろいろな条件で HTML 要素を検索することができます。

```python
elem = soup.find("p")  # 最初に見つかった p 要素
elem = soup.find(id="id100")  # id 属性が content である要素
elem = soup.find(class_=re.compile("comment"))  # class 属性に comment を含む要素
elem = soup.find("a", href=re.compile("^https://example.com/"))  # href に特定のドメイン名を含むリンク
```

`class` キーワードは Python の予約語のため、HTML 要素の class 属性値を検索するには、末尾に `_` の付いた __`class_`__ というパラメータ名を使用することに注意してください。

すでに `find` メソッドによって見つけた要素がある場合は、その要素を基準にして子要素を検索することができます。

```python
title = soup.find("head").find("title")
```

要素が見つからない場合、`find` メソッドは __`None`__ を返します。


特定の条件に一致する要素をすべて見つける
----------------------------------------

指定したタグ名の要素をすべて取得するには、__`find_all`__ メソッドを使用します。
戻り値は、__`bs4.element.ResultSet`__ オブジェクトで、`for` ループを使って見つかった要素を順番に処理できます。
次の例では、すべての `a` 要素を取得しています。

{{< code lang="python" title="a 要素をすべて取得する" hl_lines="10" >}}
from bs4 import BeautifulSoup

html = '''<html><body>
<h1>Title</h1>
<a href="https://google.com/">Google</a>
<a href="https://yahoo.com/">Yahoo</a>
</body></html>'''

soup = BeautifulSoup(html)
links = soup.find_all("a")
for link in links:
    print(link.text)
{{< /code >}}

{{< code title="実行結果" >}}
Google
Yahoo
{{< /code >}}

見つかった要素の属性は、__`attrs`__ プロパティで参照できます。
次の例では、HTML 内のすべての `a` 要素を検索し、その `href` 属性を出力しています。

```python
links = soup.find_all("a")
for link in links:
    if "href" in link.attrs:
        print(link.text, ":", link.attrs["href"])
```

{{< code title="実行結果" >}}
Google : http://google.com/
Yahoo : http://yahoo.com/
{{< /code >}}


CSS セレクタによる要素の検索
----------------------------

`find_all` の代わりに、__`select`__ メソッドを使用すると、CSS セレクタによる要素の検索を行えます。

```python
elems = soup.select("a")           # すべての a 要素
elems = soup.select("table tr")    # table 要素以下の tr 要素
elems = soup.select(".hoge")       # class 属性に hoge を含む要素
elems = soup.select("table.hoge")  # class 属性に hoge を含む table 要素
elems = soup.select("#nav")        # id 属性が nav である要素
elems = soup.select("#nav a")      # id 属性が nav である要素以下の a 要素
```

