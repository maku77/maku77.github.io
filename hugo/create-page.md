---
title: Hugo で記事を作成する
created: 2017-08-25
---

記事ファイルを作成する
----

Hugo で公開する Web サイトの記事は、`content` ディレクトリに Markdown ファイルの形式で作成していきます（例: `sample.md`）。
記事ファイルは、空っぽのテキストファイルから作成していくこともできますが、`hugo new` コマンドを使用することで、記事のひな形ファイル (`archetypes/default.md`) をベースにして Markdown ファイルを自動生成することができます。


#### 記事ファイル（Markdown ファイル）を作成

~~~
$ hugo new sample.md
/Users/maku/my_site/content/sample.md created
~~~

上記のように実行すると、`content` ディレクトリ内に `sample.md` ファイルが作成されます。
作成されたファイルの先頭部分には、下記のような Front matter ヘッダが記述されています。
このヘッダが、`archetypes/default.md` をもとにして自動生成されたものです。

#### content/sample.md

~~~
---
title: "Sample"
date: 2017-08-25T22:55:55+09:00
draft: true
---
~~~

あとは、タイトル (`title`) を適切なものに書き換え、Front matter ヘッダ以降に記事の本文を記述していきます。
公開できるレベルまで記述できたら、ヘッダ部分の `draft: true` の行を削除します。


Archetypes
----

ここで、ベースとなっている `archetypes/default.md` を覗いてみましょう。

#### archetypes/default.md

~~~
---
title: "{{"{{"}} replace .TranslationBaseName "-" " " | title }}"
date: {{"{{"}} .Date }}
draft: true
---
~~~

`title` フィールドには `hugo new` コマンドで指定した名前が自動で入り、`date` フィールドには現在の時刻が自動で入るようになっています。
このファイルの内容をもとに、記事ファイルが作成されていることが分かりますね。


記事作成と同時にエディタで編集を始める
----

`hugo new` コマンドで記事ファイルを作成するときに、`--editor` オプションを指定すると、記事ファイルの生成と同時に指定したエディタでファイルを開くことができます。

#### 例: 記事ファイルを作成して vim で開く

~~~
$ hugo new sample.md --editor vim
~~~

