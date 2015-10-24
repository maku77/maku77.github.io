---
title: Python で HTML をパースする (Beautiful Soup)
created: 2015-05-17
layout: python
---

Beautiful Soup とは
===================

Python 標準の html.parser モジュールは、SAX 形式のイベントドリブンなパーサなため、若干扱いにくいところがあります。Beautiful Soup モジュールを使用することで、HTML の要素に簡単にアクセスすることができるようになります。

* [Beautiful Soap 4](http://www.crummy.com/software/BeautifulSoup/bs4/doc/)


Beautiful Soup パッケージのインストール
=======================================

Beautiful Soup は下記のようにインストールできます。

```sh
$ pip install beautifulsoup4
```

HTML をパースする
=================

最初に、HTML ファイルや、HTML 形式の文字列から ```bs4.BeautifulSoup``` オブジェクトを生成します。

#### HTML ファイルから soup を作成
```python
import bs4

soup = bs4.BeautifulSoup(open('index.html'))
```

#### HTML 文字列から soup を作成
```python
import bs4

soup = bs4.BeautifulSoup('<html>data</html>')
```

#### Web 上の HTML リソースから soup を作成（requests モジュールを使用）
```python
import bs4
import requests
URL = 'http://yahoo.co.jp/'

res = requests.get(URL)
if res.status_code != requests.codes.ok:
    print('Error')
    exit(1)
soup = bs4.BeautifulSoup(res.text)
```


最初に登場する要素を見つける
----------------------------
```bs4.BeautifulSoup``` オブジェクトを作成したら、各要素の検索を行えるようになります。
```bs4.BeautifulSoup``` オブジェクトのプロパティとして、タグ名を指定すると、最初に見つかった要素を参照することができます。
HTML 要素は ```bs4.element.Tag``` オブジェクトとして表現されます。
下記の例では、最初に見つかった p 要素の内容を出力しています。

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

```soup.p``` は、```soup.find('p')``` と書くこともできます。

いろいろな条件で要素を見つける
------------------------------
```find()``` メソッドを使用すると、いろいろな条件で HTML 要素を検索することができます。

```python
elem = soup.find('p')  # 最初に見つかった p 要素
elem = soup.find(id='id100')  # id 属性が content である要素
elem = soup.find(class_=re.compile('comment'))  # class 属性に comment を含む要素
elem = soup.find('a', href=re.compile('^http://example.com/'))  # href に特定のドメイン名を含むリンク
```

```class``` キーワードは Python の予約語のため、HTML 要素の class 属性値を検索するには、```class_``` というパラメータを使用することに注意してください。

すでに ```find()``` メソッドによって見つけた要素がある場合は、その子要素を検索することができます。

```python
title = soup.find('head').find('title')
````

要素が見つからない場合、```find()``` メソッドは ```None``` を返します。


特定の条件に一致する要素をすべて見つける
----------------------------------------
特定のタグの要素を検索するには、```find_all()``` メソッドを使用します。
戻り値は、```bs4.element.ResultSet``` オブジェクトで、for ループによりイテレート可能です。
下記の例では、すべての a 要素を取得しています。

```python
import bs4

html = '''<html><body>
<h1>Title</h1>
<a href="http://google.com/">Google</a>
<a href="http://yahoo.com/">Yahoo</a>
</body></html>'''

soup = bs4.BeautifulSoup(html)
links = soup.find_all('a')
for link in links:
    print(link.text)
```

#### 実行結果
```
Google
Yahoo
```

見つかった要素の属性は、```attrs``` プロパティによって参照できます。
下記の例では、HTML 内のすべてのリンク（a 要素）を見つけ、href 属性を出力しています。

```python
links = soup.find_all('a')
for link in links:
    if 'href' in link.attrs:
        print(link.text, ':', link.attrs['href'])
```

#### 実行結果
```
Google : http://google.com/
Yahoo : http://yahoo.com/
```

CSS セレクタによる要素の検索
----------------------------
```find_all``` の代わりに、```select``` メソッドを使用すると、CSS セレクタによる要素の検索を行えます。
JavaScript でよく使われるライブラリである jQuery に慣れた人には、こちらの方が親しみやすいでしょう。

```python
elems = soup.select('a')  # すべてのリンク
elems = soup.select('.class1')  # class 属性に class1 を含む要素
elems = soup.select('table.class1')  # class 属性に class1 を含む table 要素
elems = soup.select('#nav')  # id 属性が nav である要素
elems = soup.select('#nav a')  # id 属性が nav である要素以下のリンク
elems = soup.select('table tr')  # table 要素以下の tr 要素
```

