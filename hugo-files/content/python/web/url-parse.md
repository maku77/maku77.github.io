---
title: "Python で URL 文字列を各パートに分割する (urllib.parse.urlparse)"
url: "p/rr2ahpx/"
date: "2023-05-26"
tags: ["Python", "URL"]
---

Python の標準ライブラリの [urllib.parse.urlparse](https://docs.python.org/3/library/urllib.parse.html#urllib.parse.urlparse) 関数を使用すると、URL 形式の文字列から __`ParseResult`__ オブジェクトを生成することができます。
`ParseResult` オブジェクトの各プロパティを参照することで、URL の各パート（スキーム、ドメイン、パス、クエリ文字列など）を取り出すことができます。

{{< code lang="python" title="sample.py（URL 文字列を解析する）" >}}
from urllib.parse import urlparse

url = "https://example.com/p/abcdefg/?foo=1&bar=2#top"

parsed_url = urlparse(url)  # ParseResult オブジェクトを生成する
print(parsed_url.scheme)    # => https
print(parsed_url.netloc)    # => example.com
print(parsed_url.path)      # => /p/abcdefg/
print(parsed_url.query)     # => foo=1&bar=2
print(parsed_url.fragment)  # => top
{{< /code >}}

