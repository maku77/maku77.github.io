---
title: "ステータスラインの表示内容を設定する (statusline, laststatus)"
date: "2009-02-06"
---

![statusline.png](./statusline.png){: .center }

画面下部のステータスラインには、編集中のファイル名や、カーソル位置の情報などを表示することができます。


ステータスラインのヘルプを表示する
----

~~~
:help 'statusline'
~~~

<div class="note">
オプションに関するヘルプを参照するときは、正式には上記のようにパラメータをシングルクォートで囲んで指定します。ただし、<code>statusline</code> に関してはシングルクォートで囲まなくても同じ項目が表示されます。
</div>


ステータスラインに表示する内容を設定する
----

~~~
:set statusline {format}
:set laststatus=2  "常に Status Line を表示する
~~~

ステータスラインはデフォルトではウィンドウを分割したときにしか表示されないので、上記では `laststatus` の値を 2 に設定して常に表示するようにしています。
逆に、ステータスラインを常に非表示にしたいときは次のようにします。

~~~
:set laststatus=0
~~~

#### 設定例 (vimrc)

~~~
set statusline=%F%m%h%w\ %<[ENC=%{&fenc!=''?&fenc:&enc}]\ [FMT=%{&ff}]\ [TYPE=%Y]\ %=[CODE=0x%02B]\ [POS=%l/%L(%02v)]
~~~

上記で設定しているパラメータは次のように具体的な値に置換されて表示されます。

* `%F` -- ファイルのフルパス。
* `%m` -- 編集されているなら [+]。リードオンリーなら [-]。
* `%h` -- Help buffer なら [HELP] と表示。
* `%w` -- Preview window なら [PREVIEW] と表示。
* `%<` -- ウィンドウの横幅が縮まってもここまでは表示することを保証。
* `%{&fenc!=''?&fecn:&enc}` -- fileencoding が設定されていればその値、設定されていなければ encoding を表示。
* `%{&ff}` -- fileformat の値を表示。%{&fileformat} の省略形。(dos, unix, mac)
* `%Y` -- filetype の値を表示。通常はこれに対応する syntax file が読み込まれているはず。
* `%02B` -- カーソル位置の文字コードを16進数で表示。
* `%l` -- カーソル位置の行番号。
* `%L` -- ファイルの行数。
* `%02v` -- カーソル位置の桁番号。

