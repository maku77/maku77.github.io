---
title: バックアップファイル／スワップファイルの保存ディレクトリを設定する
created: 2012-07-22
---

バックアップファイルやスワップファイルは、デフォルトでは編集中のファイルと同じディレクトリに作成されます。
これらのファイルの作成場所は、次のように変更することができます。

~~~ vim
""" Backup file settings
set backup
set backupdir=~/.vim/vim_backup

""" Swap file settings
set swapfile
set directory=~/.vim/vim_swap
~~~

