---
title: "NeoVim を manpager として使用する（man ページを NeoVim で表示する）"
url: "p/hfwenni/"
date: "2025-09-23"
tags: ["man", "neovim"]
---

NeoVim で man ページで開く
----

NeoVim は標準でファイルタイププラグインとして **`:Man`** を提供しており、man ページを NeoVim 内で表示することができます。

{{< code lang="vim" title="NeoVim 上で man ページを開く" >}}
:Man {キーワード}
{{< /code >}}

次のように **`MANPAGER`** 環境変数を設定しておくと、ターミナルから **`man`** コマンドを実行したときに、NeoVim で開いてくれるようになります。

{{< code lang="zsh" title="~/.zshrc など" >}}
export MANPAGER="nvim +Man!"
{{< /code >}}


:Man の操作方法
----

man ページを NeoVim で開いた後の操作方法は、less ベースの操作方法と同様ですが、`:Man` ならではの操作方法もあります。
下記あたりを覚えておくと便利です。

- **`gO`** ... 目次を開く（目次から `Enter` でジャンプできます）
- **`Shift-K`** / **`Ctrl-]`** ... カーソル下のキーワードの man ページへジャンプ
- **`Ctrl-O`** ... ジャンプ前の位置に戻る

