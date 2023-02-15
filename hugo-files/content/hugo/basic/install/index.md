---
title: "Hugo とは／Hugo をインストールする"
url: "p/r8ufyk5/"
date: "2017-08-23"
tags: ["Hugo"]
aliases: /hugo/basic/install.html
---

Hugo とは
----

{{< image src="img-001.png" >}}

- [Hugo (https://gohugo.io/)](https://gohugo.io/)

Hugo は Jekyll や [Middleman](/middleman/) と同様の静的サイトジェネレータです。
Markdown 形式などで記述したコンテンツから、HTML ファイルを作成してくれます。

Hugo は Google の __Go 言語__ で作成されており、他のサイトジェネレータに比べて __動作が非常に速い__ という特徴を持っています（それに比べて Jekyll は特に遅いですね ^^;）。
公式サイトでも動作の高速さをアピールしており、今後もその方針は変わらなさそうなので安心です。

また、インストールに関しても、他の（Ruby 製や Python 製の）サイトジェネレータは、さまざまな外部モジュールをインストールしなければいけないので時間がかかったりすることがありますが、Hugo であれば一瞬で終わります。
例えば、Windows の実行環境を構築する場合も、`hugo.exe` という１ファイルを置くだけで終わります。
LiveReload などの機能もデフォルトで組み込まれており、立ち上げまでの導入が非常にシンプルです。


Hugo のインストール
----

### Mac OS の場合

Mac の場合は、Homebrew (`brew`) で簡単に __Hugo をインストール__ できます。

```console
$ brew install hugo
```

すでに Hugo がインストールされている状態で、__Hugo を最新バージョンに更新__ したいときは次のようにします。

```console
$ brew upgrade hugo
```

現在インストールされている __Hugo のバージョン__ は次のように確認できます。

```console
$ hugo version
```

### Windows の場合

Windows の場合は、[Hugo の Releases ページ](https://github.com/gohugoio/hugo/releases)から zip アーカイブ（`hugo_extended_0.110.0_windows-amd64.zip` など）をダウンロードしてしまうのが手っ取り早いでしょう。
Sass などの機能を使う場合は、`hugo_extended` で始まるファイルを使わないといけないことに注意してください。

展開した `hugo.exe` を適当なディレクトリに置いて、パスを通せばインストール完了です。
簡単！

現在インストールされている __Hugo のバージョン__ は次のように確認できます。

```console
$ hugo version
```

