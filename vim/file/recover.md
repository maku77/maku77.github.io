---
title: "スワップファイルからファイルを復旧する (:recover)"
date: "2008-04-02"
---

スワップファイルの扱い方
----

Vim でファイルを開くと、一時的に編集内容を保持するためのスワップファイルが作成されます。
デフォルトでは、`sample.txt` のスワップファイル名は `.sample.txt.swp` になります。
Vim を終了すると、スワップファイルは自動的に削除されます。

以下のようなケースの場合、Vim でファイルを開いたときに前回作成されたスワップファイルが残っている可能性があります。

- 別の Vim で同じファイルを開いている。
- 前回の編集中に Vim がクラッシュした。

このような場合は、編集開始時に、「読み込み専用で開く」、「復活させる」、といった選択肢が表示されるので、適切な処理を選択してください。
例えば、別の Vim で同時編集しているような場合は、読み込み専用で開くのがよいでしょう。

スワップファイルからのファイルの復旧を明示的に実行するには下記のようにします。

~~~
:recover sample.txt
~~~

あるいは、起動時に `-r` オプションを指定します。

~~~
$ vim -r sample.txt
~~~


スワップファイルの名前を確認する
----

現在編集中のファイルに対応するスワップファイルの名前は、下記のようにして確認することができます。

~~~
:swapname
~~~


スワップファイルに直ちに書き込む
----

スワップファイルへの書き込みは、時間、あるいはタイプ数によって自動的に実行されますが、明示的に書き込みを実行することもできます。

~~~
:preserve
~~~


参考
----

* [バックアップファイル／スワップファイルの設定 (backup, swapfile)](../settings/backup.html)

