---
title: 折りたたみ機能 (folding) を使用する
created: 2009-11-17
---

Vim の折りたたみ (folding) 機能を使用すると、特定の行を折りたたんで表示する（一時的に非表示にする）ことができます。


指定した範囲を折り畳む
----

折りたたみ機能によって、どのような範囲を折りたたむかは、`foldmethod` というオプションで制御されています。

~~~
:set foldmethod=manual （デフォルト）
~~~

としておくと、任意の選択範囲を折りたたみ領域として扱うことができるようになります。

#### 例: Visual mode で選択した部分を折り畳む

~~~
1. Visual mode (Shift-v) で領域選択
2. zf
~~~

#### 例: カーソル位置から、指定した文字列が見つかるところまでを折り畳む

~~~
zf /string
~~~

一度 `zf` コマンドによって折りたたんだ領域は、`zo` で展開、`zc` で折りたたみを行えるようになります。


インデント単位で折りたたむ
----

折りたたみの単位の設定を `indent` にすると、各種折りたたみコマンドの対象がインデント単位になります。

~~~
:set foldmethod=indent
~~~


折りたたみ機能に関するコマンド一覧
----

### 折りたたみと展開（カーソル位置の要素に対して）

~~~
zc  -- 折りたたみ (Close one fold under the cursor)
zo  -- 展開（一段階）(Open one fold under the cursor)
zO  -- 展開（すべて）(Open all folds under the cursor recursively)
~~~

### 折りたたみと展開（ファイル全体の要素に対して）

~~~
zm -- 折りたたみ（一段階） (Fold more)
zM -- 折りたたみ（すべて） (Close all folds)
zr -- 展開（一段階） (Reduce folding)
zR -- 展開（すべて） (Open all folds)
~~~

# 折りたたみ単位でジャンプ

~~~
zj -- move to the next fold
zk -- move to the previous fold
~~~


折りたたみ関連の設定
----

折りたたみ機能に関する設定は、`foldXXXXX` という名前のオプションを使って行います。

#### ~/.vimrc

~~~ vim
set foldmethod=indent  "折りたたみ範囲の判断基準（デフォルト: manual）
set foldlevel=2        "ファイルを開いたときにデフォルトで折りたたむレベル
set foldcolumn=3       "左端に折りたたみ状態を表示する領域を追加する
~~~

折りたたみ領域を決める `foldmethod` オプションには、下記のような値を設定することができます。

- `manual` -- 自分で範囲選択して折りたたみ
- `indent` -- インデント範囲
- `marker` -- {{{ と }}} で囲まれた範囲
- `expr` -- foldexpr による折りたたみレベル指定
- `syntax` -- 現在の syntax に応じた折りたたみ


折りたたみ状態の保存／復帰
----

折りたたみ状態を保存しておくと、ファイルを閉じて、再度開いたときにその折りたたみ状態に復帰させることができます。

~~~
:mkview    -- Fold 状態を保存する
:loadview  -- Fold 状態を復帰する
~~~

ファイルを閉じるとき、開くときときに、自動的に折りたたみ状態を保存・復帰するには、`~/.vimrc` に以下のように設定しておきます。

~~~ vim
au BufWinLeave * mkview
au BufWinEnter * silent loadview
~~~


折りたたみ機能（floding 機能）のヘルプ
----

~~~
:help fold.txt
~~~

とすると、折りたたみ機能（Folding機能）についてのヘルプを参照することができます。

