---
title: GitHub でソフトウェア配布用にプロジェクトの Web サイトを作成する
created: 2014-07-14
layout: git
---

GitHub Pages という機能を使用すると、GitHub 上で Web サイトをホスティングできます。
GitHub を開発用にだけ使うのであれば、リポジトリだけ公開しておけばよいのですが、作成したソフトウェアを配布したり、マニュアルを公開するのであれば、GitHub Pages 上で通常の Web サイトとして見えるようにしておくのがよいでしょう。

プロジェクトの Web サイトを作る
====

GitHub Pages は非常に簡単に作成できます。
各リポジトリに *gh-pages* というブランチを作り、そこに Web サイトのコンテンツ （index.html など）を commit すれば、そのプロジェクト用の Web サイトとして見えるようになります。

```
https://<username>.github.io/<repository>/
```

Organization を作成している場合は、上記の ```<username>``` のところを、```<organization>``` と読み替えてください。

プライベートな GitHub Pages は作れない
----

現状では、プライベートリポジトリ上の GitHub Pages (gh-pages) を作ったとしても、Web サイトの方は必ず公開されてしまうようです。
プライベートな情報提供サイトを作る場合は、リポジトリの README.md や Wiki を活用するか、独自で Web サーバを立ち上げる必要があります。

ユーザ用、組織用の Web サイトを作る
====

GitHub Pages では、リポジトリごとの Web サイトだけではなく、ユーザや組織用のトップサイトを作成することも可能です。その場合は、```<username>.github.io``` という名前のリポジトリを作成し、その *master* ブランチにコンテンツを格納してください（この場合はリポジトリが Web サイト専用になるので、gh-pages ブランチを作る必要がないのです）。
下記のようなアドレスで参照できるようになります。

```
https://<username>.github.io/
```

GitHub Pages のコンテンツ生成の仕組み -- Jekyll
====

GitHub Pages は内部では [Jekyll](http://jekyllrb.com/) が動作しており、Markdown 形式で記事を記述したり、ブログのようなものも簡単に作成することができるようになっています。
Jekyll 用の形式（先頭に Yaml 形式のヘッダを付ける）で記述したファイルを作成するだけで、自動的に GitHub Pages 側で変換処理が行われ、Web サイトとして表示できるようになります。Jekyll によってどんなことができるかは、Jekyll 本家のサイトを参照しましょう。

参考
====

* [GitHub Pages](https://pages.github.com)
* [Jekyll](http://jekyllrb.com/)
* [Run Jekyll on Windows](http://jekyll-windows.juthilo.com/)

