---
title: Git の local 設定と global 設定と system 設定の違い
created: 2014-05-25
---

`git config` による設定のスコープは 3 種類あり、スコープが狭くなるほど参照時の優先度は高くなります。
下記はそれぞれのスコープでの設定方法を、優先度の高い順に示しています。

~~~
git config --local ...   # 各リポジトリごとの設定 (.git/config)（デフォルト）
git config --global ...  # 現在のユーザの共通設定 (~/.gitconfig)
git config --system ...  # システム内の共通設定 (/etc/gitconfig など)
~~~

例えば、global 設定で `user.name` が `Ichiro` になっていても、local 設定が `Jiro` になっていれば、`Jiro` の方が優先的に使用されます。

プロジェクトごとに固有の設定をする場合は、local なスコープで設定を行うとよいでしょう。
この場合、プロジェクトの作業ツリーのトップにある `.git/config` に設定が保存されます。

