---
title: バックアップファイル／スワップファイルの設定 (backup, swapfile)
created: 2008-04-02
---

バックアップファイルの設定
----

### バックアップの ON/OFF (backup, nobackup)

Vim には、編集中のファイルを自動でバックアップするための機能が付いています（デフォルトではバックアップ機能 Off になっています）。
バックアップの On/Off を切り替えるには以下のようにします。

~~~
:set backup    "Backup On
:set nobackup  "Backup Off（デフォルト）
~~~

現状の設定は、`:set backup?` で確認できます。

バックアップを有効に設定した状態で、例えば、`sample.txt` というファイルを保存すると、同じディレクトリに `sample.txt~` というバックアップファイルが生成されます。


### バックアップディレクトリの設定 (backupdir)

バックアップファイルは、デフォルトでは編集中のファイルと同じディレクトリに作成されますが、このディレクトリは自由に変更することができます。
バックアップファイルを保存するディレクトリの設定は、`backupdir` オプションで行います。
ディレクトリ候補をカンマ (`,`) で区切って複数指定することができます。

#### バックアップディレクトリの設定例

~~~
:set backupdir=.,~/tmp,~/    "デフォルトの設定（カレントディレクトリに作られる）
:set backupdir=~/temp/backup/
:set backupdir=D:/y/backup/vim,C:/tmp,C:/temp
~~~

`backup` オプションを設定してあっても、この `backupdir` が指定されていないとバックアップファイルは生成されません。

`backupdir` には、存在するディレクトリを指定しておく必要があります。
存在しないディレクトリを指定すると、ファイルを保存したときに、**「E510: バックアップファイルを作れません」**というエラーが発生します。

`backupdir` をカレントディレクトリ以外の特定のディレクトリに設定した場合は、同じファイル名のファイルを編集して保存すると、バックアップディレクトリにあるバックアップファイルは上書きされます。


### バックアップファイル名のプレフィックスを変更する (backupext)

~~~
:set backupext=.back
~~~

上記のように設定しておくと、`sample.txt` のバックアップファイル名は `sample.txt~` ではなく、`sample.txt.back` になります。


スワップファイルの設定
----

Vim はデフォルトで、ファイルをオープンしたときに、`＜ファイル名＞.swp` というスワップファイルを同じディレクトリに作成します。
これは、万が一 Vim がクラッシュしたときに備えるためのものです。

### スワップファイルの ON/OFF (swapfile, noswapfile)

スワップファイルの機能を On/Off するには以下のようにします。

~~~
:set swapfile    "スワップファイルを生成する（デフォルト）
:set noswapfile  "スワップファイルを生成しない
~~~


### スワップファイルを保存するディレクトリの設定 (directory)

スワップファイルの生成場所を、任意のディレクトリに変更することができます。

~~~
:set directory=.,c:\tmp,c:\temp    "デフォルトの設定（カレントディレクトに作成）
:set directory=~/temp/backup/
:set directory=D:/y/backup/vim,C:/tmp,C:temp
~~~

デフォルトの設定では、まずカレントディレクトリ (`.`) にスワップファイルを作成しようとします。
既に同じ名前のファイルがあったりして、スワップファイルを作成できない場合は、カンマで区切られた次の候補（上の場合は `c:\tmp`）のディレクトリへ保存されます。


### スワップファイルの保存タイミングを変更する (updatetime, updatecount)

デフォルトでは、4 秒おき、あるいは 200 文字タイプするごとにスワップファイルに保存されます。
これらのタイミングを変更するには以下のように設定します。

~~~
:set updatetime=30000  "30秒ごとに保存
:set updatecount=500   "500文字タイプするごとに保存
~~~



まとめ
----

バックアップファイルやスワップファイルは、デフォルトでは編集中のファイルと同じディレクトリに作成されます。
カレントディレクトリに勝手にファイルが作成されるのが気になる場合は、下記のように保存先のディレクトリを変更しておくとよいでしょう。

#### ~/.vimrc

~~~ vim
""" Backup file settings
set backup
set backupdir=~/.vim/vim_backup

""" Swap file settings
set swapfile
set directory=~/.vim/vim_swap
~~~

保存先のディレクトリは、あらかじめ作成しておく必要があることに注意してください。


参考
----

* [スワップファイルからファイルを復旧する (:recover)](../file/recover.html)
