---
title: "GitHub でソフトウェア配布用にプロジェクトの Web サイトを作成する (GitHub Pages)"
url: "p/yujway4/"
date: "2014-07-14"
lastmod: "2023-11-15"
tags: ["GitHub"]
description: "GitHub Pages という機能を使用すると、GitHub 上で Web サイトをホスティングできます。GitHub を開発用にだけ使うのであれば、リポジトリだけ公開しておけばよいのですが、作成したソフトウェアを配布したり、マニュアルを公開するのであれば、GitHub Pages 上で通常の Web サイトとして見えるようにしておくのがよいでしょう。"
changes:
  - 2023-11-15: master を main に改名
aliases: /git/github/project-portal.html
---

GitHub Pages という機能を使用すると、GitHub 上で Web サイトをホスティングできます。GitHub を開発用にだけ使うのであれば、リポジトリだけ公開しておけばよいのですが、作成したソフトウェアを配布したり、マニュアルを公開するのであれば、GitHub Pages 上で通常の Web サイトとして見えるようにしておくのがよいでしょう。

プロジェクトの Web サイトを作る
----

各プロジェクト用の GitHub Pages は非常に簡単に作成できます。
各リポジトリに __`gh-pages`__ という名前のブランチを作り、そこに Web サイトのコンテンツ（`index.html` など）をコミットすれば、そのプロジェクト用の Web サイトとして見えるようになります。

```
https://<username>.github.io/<repository>/
```

Organization を作成している場合は、上記の `<username>` のところを、`<organization>` と読み替えてください。

{{% note title="GitHub Pages の非公開化" %}}
プライベートリポジトリで GitHub Pages (gh-pages) を作ったとしても、Web サイトはインターネット上に公開されてしまうことに注意してください。
プライベートな情報提供サイトを作る場合は、リポジトリの `README.md` や Wiki を活用するか、独自で Web サーバを立ち上げる必要があります。

あるいは、GitHub Enterprise Cloud という高額なプランを導入すれば、非公開の GitHub Pages を作成することができます。

- 参考: [Changing the visibility of your GitHub Pages site - GitHub Enterprise Cloud Docs](https://docs.github.com/en/enterprise-cloud@latest/pages/getting-started-with-github-pages/changing-the-visibility-of-your-github-pages-site)
{{% /note %}}


ユーザ用、組織用の Web サイトを作る
----

GitHub Pages では、リポジトリごとの Web サイトだけではなく、ユーザや組織用のトップサイトを作成することも可能です。
その場合は、`<username>.github.io` という名前のリポジトリを作成し、その __`main`__ ブランチにコンテンツを格納してください（この場合はリポジトリが Web サイト専用になるので、`gh-pages` ブランチを作る必要がありません）。
下記のようなアドレスで参照できるようになります。

```
https://<username>.github.io/
```

GitHub Pages のコンテンツ生成の仕組み -- Jekyll
----

GitHub Pages は内部では [Jekyll](http://jekyllrb.com/) が動作しており、Markdown 形式で記事を記述したり、ブログのようなものも簡単に作成することができるようになっています。
Jekyll 用のフォーマット（先頭に YAML 形式のヘッダを付ける）で記述したファイルを作成するだけで、自動的に GitHub Pages 側で変換処理が行われ、Web サイトとして表示できるようになります。
Jekyll でどんなことができるかは、Jekyll 本家のサイトを参照しましょう。

