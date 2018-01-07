---
title: Hugo とは／Hugo をインストールする
date: "2017-08-23"
---

Hugo とは
----

[![install.png](./install.png){: .center}](https://gohugo.io/)

- [Hugo (https://gohugo.io/)](https://gohugo.io/)

Hugo は Jekyll や [Middleman](/middleman/) と同様の静的サイトジェネレータです。
Markdown 形式などで記述したコンテンツから、HTML ファイルを作成してくれます。

Hugo は Google の **Go 言語** で作成されており、他のサイトジェネレータに比べて動作が非常に速いという特徴を持っています（それに比べて Jekyll は特に遅いですね ^^;）。
公式サイトでも動作の高速さをアピールしており、今後もその方針は変わらなさそうなので安心です。

また、インストールに関しても、他の（Ruby 製や Python 製の）サイトジェネレータは、さまざまな外部モジュールをインストールしなければいけないので時間がかかったりすることがありますが、Hugo であれば一瞬で終わります。
例えば、Windows の実行環境を構築する場合も、`hugo.exe` という１ファイルを置くだけで終わります。
LiveReload などの機能もデフォルトで組み込まれており、立ち上げまでの導入が非常にシンプルです。


Hugo のインストール
----

### Mac OS の場合

Mac の場合は、Homebrew で簡単にインストールできます。

~~~
$ brew install hugo
~~~

### Windows の場合

Windows の場合は、[Hugo の Releases ページ](https://github.com/gohugoio/hugo/releases)から zip アーカイブ（`hugo_0.26_Windows-64bit.zip` など）をダウンロードしてしまうのが手っ取り早いでしょう。
展開した `hugo.exe` を適当なディレクトリに置いて、パスを通せばインストール完了です。
簡単！

### 動作確認

インストールが終わったら、動作確認のためにバージョン情報を表示してみましょう。

~~~
C:\> hugo version
Hugo Static Site Generator v0.26 windows/amd64 BuildDate: 2017-08-23T10:14:00+09:00
~~~

