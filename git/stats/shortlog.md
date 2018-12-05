---
title: "ユーザーごとのコミット統計を出力する (git shortlog)"
date: "2018-12-04"
---

git shortlog の使い方
----

**`git shortlog`** コマンドを使用すると、ユーザごとのコミットを一覧で表示することができます。

何もオプションを付けないで実行すると、ユーザ名のアルファベット順に、コミット数とそれぞれのコミットのタイトルが列挙されます。

~~~
$ git shortlog
Rei Ayanami (3):
      Fix xxx yyy zzz
      Encapsulate FileCache in ProgramFacade
      Remove unnecessary comments in utility classes
Shinji Ikari (4):
      Remove currentTimeMillis() calls from FocusPosition
      Package information for com.example.foo
      Apply LRU caching for event information
      Emphasize FPS display
...
~~~

デフォルトではアルファベット順にユーザが表示されますが、**`-n`** オプションを付けるとコミット数の多いユーザ順に表示されます。
さらに、**`-s`** オプションを付けることで、コミット数だけが表示されるようになります。
**`-e`** オプションは、名前に加えてメールアドレスも表示します。

~~~
$ git shortlog -nse
  6183 Doppo Orochi <doppo-orochi@example.com>
  1323 Kaoru Hanayama <kaoru-hanayama@example.com>
  1201 Kaiou Retsu <kaiou-retsu@example.com>
  1043 Katsumi Orochi <katsumi-orochi@example.com>
   832 Izou Motobe <izou-motobe@example.com>
...
~~~

これを見れば、誰がたくさんコミットしているかを調べることができます。

[revision range](https://git-scm.com/docs/gitrevisions) の指定によって、コミット時期を絞り込んで集計することもできます。

#### 例: 1年前から 2018-12-01 の間でコミットの多い人を調べる

~~~
$ git shortlog -nse --since="1 year ago" --until="2018-12-01"
~~~


.mailmap ファイルによるユーザの集約
----

`git shortlog` による統計情報は、異なるユーザ名やメールアドレスを使ってコミットされたものは別々に集計されてしまいます。
このような場合は、**`.mailmap`** ファイルを用意することで、同一のユーザによるコミットとして集計することができます。
詳しくは [git help shortlog で表示されるヘルプ](https://git-scm.com/docs/git-shortlog)を参照してください。

