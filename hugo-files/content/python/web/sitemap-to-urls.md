---
title: "Web サイトのサイトマップ (sitemap.xml) からすべての URL を抽出する"
url: "p/bp9q8p6/"
date: "2023-05-25"
tags: ["Python"]
---

サイトマップとは？
----

Web サイトのサイトマップファイル (`sitemap.xml`) には、次のような形で全ページの URL 情報が記載されています。
このファイルは、主に Google などの検索エンジンのために提供されています。

{{< code lang="xml" title="sitemap.xml" >}}
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xhtml="http://www.w3.org/1999/xhtml">
  <url>
    <loc>http://example.com/page1/</loc>
    <lastmod>2021-01-01</lastmod>
  </url>
  <url>
    <loc>http://example.com/page2/</loc>
    <lastmod>2022-02-02</lastmod>
  </url>
  <url>
    <loc>http://example.com/page3/</loc>
    <lastmod>2023-03-03</lastmod>
  </url>
</urlset>
{{< /code >}}


sitemap.xml から URL の一覧を取得する
----

次の Python スクリプトは、上記のような `sitemap.xml` ファイルから、すべての URL 情報を抽出します。
`sitemap.xml` ファイル自体のダウンロードには [urlopen](/p/o2e43ct/) を使用し、XML のパースには [ElementTree](/p/cp9q7n5/) を使用しています。

{{< code lang="python" title="extract-urls.py" >}}
from urllib.request import urlopen
from xml.etree import ElementTree

url = "https://example.com/sitemap.xml"


def fetch(url: str) -> str:
    """指定した URL のリソースを文字列で取得します。"""
    with urlopen(url) as res:
        return res.read().decode("utf-8")


def extract_urls(sitemap_xml: str) -> list[str]:
    """sitemap.xml の内容から URL を抽出します。"""
    urls = []
    root = ElementTree.fromstring(sitemap_xml)
    # <loc> タグの中身を取得する
    for loc in root.iter("{http://www.sitemaps.org/schemas/sitemap/0.9}loc"):
        urls.append(loc.text)
    return urls


if __name__ == "__main__":
    sitemap_xml = fetch(url)
    urls = extract_urls(sitemap_xml)
    for url in urls:
        print(url)
{{< /code >}}

{{< code title="実行結果" >}}
http://example.com/page1/
http://example.com/page2/
http://example.com/page3/
{{< /code >}}


（おまけ）URL が示すリソースをまとめてダウンロードする
----

上記のように作成した URL リストを使って、まとめて HTML ファイルとしてダウンロードするサンプルコードです。
ダウンロードするときのファイル名を自動で生成するのは意外と面倒なので、ここでは単純に `1.html`、`2.html`、`3.html` といった連番のファイル名で保存しています。

{{< code lang="python" title="download-htmls.py" >}}
import os
import time
from urllib.request import urlretrieve


def download_all(urls: list[str], outdir="out") -> None:
    os.makedirs(outdir, exist_ok=True)
    count = 1
    for url in urls:
        save_path = f"{outdir}/{count}.html"
        print(f"Downloading {url} => {save_path}")
        urlretrieve(url, save_path)
        count += 1
        time.sleep(0.5)  # sleep を入れてサーバー負荷を下げる


if __name__ == "__main__":
  # 本来は sitemap.xml から抽出した URL リストを使う
  urls = [
      "http://example.com/page1/",
      "http://example.com/page2/",
      "http://example.com/page3/",
  ]
  download_all(urls)
{{< /code >}}

{{< code title="実行結果" >}}
Downloading http://example.com/page1/ => out/1.html
Downloading http://example.com/page2/ => out/2.html
Downloading http://example.com/page3/ => out/3.html
{{< /code >}}

