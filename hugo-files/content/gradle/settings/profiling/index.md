---
title: "Gradle によるビルドのボトルネックを探す（プロファイリング） (--profile)"
url: "p/ofoencn/"
date: "2016-09-09"
tags: ["gradle"]
aliases: ["/gradle/settings/profiling.html"]
---

`gradle` コマンドを実行するときに、**`--profile`** オプションを追加して実行すると、ビルド中の各処理でどの程度時間がかかっているかのプロファイリング結果を出力してくれます。
プロファイリング結果は、**`build/reports/profile`** ディレクトリ以下に次のような HTML ファイルとして出力されます。

{{< image src="img-001.png" title="build/reports/profile/profile-2016-09-09-11-44-54.html" >}}

Gradle によるビルドがあまりにも遅いと感じたら、このプロファイリング機能を使ってボトルネックを探るとよいです。

