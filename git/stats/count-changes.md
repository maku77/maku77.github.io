---
title: "あるバージョン間の変更行数（追加＆削除）を集計する (git diff --stat, git log --numstat)"
date: "2018-12-04"
---

下記の説明では、バージョン 1、バージョン 2 のリリースコミットとして、タグ `v1`、`v2` が設定されているとします。
バージョン間の変更行数を調べるには、主に **`git diff --stat`** を使った方法と、**`git log --numstat`** を使った方法があります。

- <b>`git diff --stat v1 v2`</b><br>v1 と v2 のスナップショット間で差分を取って変更行数を集計したいとき
- <b>`git log --numstat v1..v2`</b><br>v1 から v2 までの各コミットの修正行数を集計したいとき


バージョン間の変更行数を調べる (1) git diff を使う方法
----

リビジョン間（`v1` と `v2`）の変更行数を調べるには、下記のように `git diff` に **`--stat`** オプションを付けて実行します。

~~~
$ git diff --stat v1 v2
 .../path/to/filename/Sample1.java   |   5 +
 .../path/to/filename/Sample2.java   |   5 +
 .../path/to/filename/Sample3.java   |   4 +-
 .../path/to/filename/Sample4.java   |  26 -
 ...
 22 files changed, 327 insertions(+), 655 deletions(-)
~~~

ファイル単位の増減に加え、出力の最終行に、追加された行数 (insertions) と、削除された行数 (deletions) が表示されます。

### リビジョンの指定方法いろいろ

~~~
$ git diff --stat v1 master # v1 と master ブランチを比較
$ git diff --stat v1 HEAD   # v1 とカレントブランチの最新を比較
$ git diff --stat v1        # 同上（後ろの HEAD は省略可能）
~~~

### 対象ファイルを絞り込む

#### 例: カレントディレクトリ以下のみ

~~~
$ git diff --stat v1 v2 -- .
~~~

ディレクトリパスなどを指定して絞り込みを行う場合は、タグ名などの指定とあいまいになるのを防ぐために、パスの指定の前に `--` に入れます（上記の例では省略可能です）。

#### 例: カレントディレクトリ以下の java ファイルのみ

~~~
$ git diff --stat v1 v2 -- "./*.java"
~~~

#### 例: カレントディレクトリ以下のみ、ただし xml ファイルを除く

~~~
$ git diff --stat v1 v2 -- . ":(exclude)*.xml"
~~~


バージョン間の変更行数を調べる (2) git log を使う方法
----

`git log` はコミットログを列挙するコマンドですが、**`--numstat`** オプションを付けて実行すると、各ファイルの変更行数（追加行数と削除行数）も一緒に表示されます。
バイナリファイルに関しては、変更行数をカウントできないため、追加行数と削除行数はハイフン表示 (`-`) になります。

#### v2 にだけ入っている変更の一覧と、それぞれのファイルの変更行数を出力

~~~
$ git log --numstat v1..v2
commit 37a670b16bf5b6aec76b13aee87ff52e69a01c83
Author: Katsumi Orochi <katsumi-orochi@example.com>
Date:   Wed Oct 10 14:22:18 2018 +0900

    Replace digital clock display with analog one

5	2	common/clock/build.gradle
21	5	common/clock/ClockView.java

commit f4078c857a5a3f9e132d5baef5d52d7785d1897c
Author: Doppo Orochi <doppo-orochi@example.com>
Date:   Tue Oct 9 10:54:27 2018 +0900

    Fix typos and comments

5	1	common/clock/build.gradle
-	-	libs/querystring.jar

...
~~~

この出力を集計することでも、バージョン間の修正行数を調べることができます。
ただし、`git log` コマンドはコミットごとの変更内容を出力するため、同じファイルに対して何度も修正を繰り返している場合は、`git diff` を使った集計方法よりも変更行数は多く計算されます。
つまり、**純粋なバージョン間の差分を取りたいのであれば `git diff --stat` を、日々の修正量の合計を求めたいのであれば `git log --numstat` を使用します**。

実際に変更行数（追加行数と削除行数）を集計したい場合は、上記のような出力だとやりにくいので、出力フォーマット指定を指定する **`--pretty`** オプションを追加することで、ファイルの変更行数だけをリスト出力するとよいでしょう。
次の例では、さらに、マージコミットを対象外とする **`--no-merges`** オプションを指定して出力しています。

~~~
$ git log --numstat --no-merges --pretty="" v1..v2
5	2	common/clock/build.gradle
21	5	common/clock/ClockView.java
5	1	common/clock/build.gradle
-	-	libs/querystring.jar
...
~~~

この出力をテキストファイルなどにリダイレクトしておいて、Excel などにその内容を張り付けてやれば、追加行数と削除行数の合計は簡単に求めることができます。
あるいは、下記のように簡単なスクリプトにパイプして合計してしまうこともできるでしょう。

~~~
$ git log --numstat --no-merges --pretty="" v1..v2 | ruby -ane "BEGIN{$i=0;$d=0;}; $i+=$F[0].to_i; $d+=$F[1].to_i; END{print $i,' ',$d}"
33850 16243
~~~

- 参考: [Ruby でワンライナープログラミング](/ruby/io/oneliner.html)

### リビジョンの指定方法いろいろ

~~~
$ git log --numstat v1..master  # master にだけ入っているもの
$ git log --numstat v1..HEAD    # カレントブランチ (HEAD) にだけ入っているもの
$ git log --numstat v1..        # 同上
$ git log --numstat --since="1 year ago" --until="2018-01-15"  # 日付範囲
~~~

### 対象ファイルを絞り込む

#### 例: カレントディレクトリ以下のみ

~~~
$ git log --numstat v1..v2 -- .
~~~

#### 例: カレントディレクトリ以下のみ、ただし xml ファイルを除く

~~~
$ git log --numstat v1..v2 -- . ":(exclude)*.xml"
~~~

#### 例: カレントディレクトリ以下の java ファイルのみ

~~~
$ git log --numstat v1..v2 -- "./*.java"
~~~

対象とするファイルの拡張子を絞り込みたい場合、例えばカレントディレクトリ以下の Java ファイルのみを対象とする場合は、`-- "./*.java"` というように指定します。
`-- . "*.java"` という指定はうまくいきません。

