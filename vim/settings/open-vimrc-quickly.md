---
title: ショートカットキーで .vimrc ファイルを開く
date: "2014-01-27"
---

ショートカットキーで .vimrc を開く
----

`~/.vimrc` などの設定ファイル名は、`$MYVIMRC` 変数で参照することができます。
下記のキーマッピング設定をしておくと、`F1` キーを押すだけで簡単に `.vimrc` ファイルを開けるようになります。

#### ~/.vimrc

```vim
nmap <F1> :tabnew $MYVIMRC<CR>
```

ここでは、`:tabnew` コマンドを使って、新しいタブで `.vimrc` ファイルを開くようにしています。


参考
----

* [設定ファイル (.vimrc) を開く、リロードする](../settings/reload-vimrc.html)

