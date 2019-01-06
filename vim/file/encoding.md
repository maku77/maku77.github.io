---
title: "ファイルのエンコーディング形式、改行コードを変更する (fenc, ff)"
date: "2008-02-01"
---


改行コードの変更
----

現在編集中のファイルの改行コードを変更するには、

~~~ vim
:set ff=unix    "<NL>
:set ff=dos     "<CR> <NL>
:set ff=mac     "<CR>
~~~

のように現在の `fileformat (ff)` オプションを変更し、`:w` で保存します。

現在の改行コードは以下のように確認できます。

~~~ vim
:set ff?
~~~


新規ファイルを作成したときの改行コード
----

新規ファイルを `:vi new.txt` というように作成する場合は、その改行コードは `fileformats` オプションの先頭に記述されたフォーマットになります。
例えば、以下のように `.vimrc` ファイルに記述されていると、新規ファイルの改行コードのフォーマットは UNIX 形式 (NL) になります。

#### ~/.vimrc

~~~ vim
set fileformats=unix,dos,mac
~~~

また、この設定は、既存のファイルを開くときに認識する改行コードのリストとしても使用されます。
DOS 形式 (CR+NL) の改行コードで保存されたテキストファイルを開くとき、`fileformats` に `unix` としか設定されていない場合は、`unix` 形式の改行コードで開かれてしまうので注意してください。
なので、通常は上記のように扱う可能性のある改行コードを列挙しておきます。


エンコーディング形式の変更
----

現在編集中のファイルのエンコーディング形式を変更するには、

~~~ vim
:set fenc=utf-8
:set fenc=euc-jp
:set fenc=shift_jis
~~~

のように現在の `fileencoding (fenc)` オプションを変更し、`:w` で保存します。
指定できるエンコーディング形式名の一覧は、`:help encoding-values` で確認することができます。


新規ファイルを作成したときのエンコーディング形式
----

`fileencoding` は、ファイルを保存するときに使用されるエンコーディング形式の設定ですが、新しいファイルを `:vi new.txt` のように作成する場合のエンコーディング形式としても使われます。

~~~
:set fileencoding=euc-jp
~~~

`fileencoding` の値が空の場合は、`encoding` の値が使用されます。
`utf-8` に設定しておく場合は、全ての文字を正しく処理できるように、Vim が内部で使用するエンコーディング形式を示す `encoding` の値も `utf-8` に設定しておきましょう。


Vim が内部で使用するエンコーディング形式（encoding は utf-8 に設定すべし）
----

Vim が内部で使用しているエンコーディング形式は `encoding` に設定されています。
この値は以下のように確認できます。

~~~
:set encoding?
~~~

この値は、デフォルトではシステムのロケール設定をもとに設定されます。
Windows で日本語を使用している場合は、おそらく cp932 (Shift-JIS) になっています。

この状態で、UTF-8 エンコーディングで保存されたテキストを Vim で開くと、Vim は cp932 エンコーディングとして処理しようとするので文字化けが発生することがあります（cp932 で表現できる文字だけを含んでいるファイルなどは正しく処理できたりします）。

Vim に、ちゃんと UTF-8 としてテキストを処理するように指示するには、`.vimrc` ファイルに以下のように設定しておきます。

~~~
set encoding=utf-8
~~~

つまり、Vim で UTF-8 のテキストを問題なく開くには、少なくとも `encoding` が `utf-8` に設定されていないといけません。
`.vimrc` に必ず上のように記述しておきましょう。
`encoding` が `utf-8` に設定されていても、Shitf-JIS や EUC-JP で保存されたテキストファイルはちゃんと開くことができます。

Vim のヘルプを見ると、`encoding` の設定は、システムデフォルトの値を使用するか、`.vimrc` ファイルで任意の値を設定するようにすべき、と書いてあります。
ファイルを開いている状態で、`:set encoding=utf-8` と動的に変更しようとすると、Vim のメニューなどが文字化けしたりして、正常に動かなくなるので注意してください。

`encoding` にどのようなエンコーディング形式を指定すべきかは、使用している OS などにも依存します。
ほとんどのケースでは `utf-8` に設定しておけばよいでしょう。


Windows の GVim で set encoding=utf-8 すると文字化け
----

Windows の本家 GVim で `encoding` を `utf-8` に設定すると、エラーメッセージなどが文字化けすることがあります（Kaoriya 版の Vim は大丈夫）。
この場合、`gvim.exe` ファイルがあるディレクトリ内の、以下のファイルを入れ替える必要があります。

- iconv.dll
- libintl.dll

参考 ⇒ [http://magicant.txt-nifty.com/main/2010/04/windows-gvim-1b.html](http://magicant.txt-nifty.com/main/2010/04/windows-gvim-1b.html)


ファイルを開くときに認識するエンコーディング形式を指定する
----

既存のファイルを開くときは、`fileencodings` に指定したエンコーディングが順番に試されて、ファイルを正常に開けたものが使用されます。

~~~
:set fileencodings=ucs-bom,utf-8,default,latin1
~~~

ファイルを正常に開けた時点で、そのエンコーディング形式が `fileencoding` に設定されます（`fileencodings` ではなくて、`s` のない `fileencoding` ということに注意）。

もし、`fileencodings` に指定したエンコーディング形式のいずれでも正常に開けなかった場合は、`fileencoding` の値は空に設定されます。
その場合、エンコーディング形式として、`encoding` に設定された値が使用されます。

注意点:

- Unicode BOM (Byte Order Mark) を正しく処理するために、`ucs-bom` は他の Unicode 形式 (`utf-8`) などより前に指定する必要があります。
- `latin1` （8-bit encoding) や、`cp1250` などは一番最後に指定する必要があります（`latin1` でファイルを開くと必ず成功してしまうため）。
- `default` という指定は、`encoding` に指定したエンコーディング形式を使用することを意味します。


UTF-8、EUC-JP、Shift-JIS のファイルを読み書きできるようにする
----

様々なエンコーディング形式で保存された日本語ファイルを開くには、下記のように設定しておくのがよいでしょう。

#### ~/.vimrc あるいは %HOME%/_vimrc

~~~
set encoding=utf-8  "Vim が内部で使用するエンコーディング形式
set fileencoding=utf-8  "ファイルを新規作成するとき
set fileencodings=ucs-bom,utf-8,euc-jp,cp932,latin1  "ファイルを開くとき
set fileformats=unix,dos,mac
~~~

#### 概要説明

ファイルを新規作成する場合は、`fileencoding` に指定した値が使われます。
もし、`fileencoding` を設定していなかったら、`encoding` の値が使用されます。
ファイルを開くときは、`fileencodings` に指定したエンコーディング形式が順に試されます（どれにも一致しない場合は、`encoding` の値が使われます）。
ただし、`latin1` では必ずファイルオープンに成功するので、上記のように `fileencodings` の中に `latin1` を指定した場合、`encoding` に指定した値が使われることはありません。
また、新規ファイルを生成する場合に、デフォルトで使用される改行コードは `fileformats` の先頭に指定された形式が使用されるので、上記のように `fileformats` の先頭に unix を指定しておくと、改行コードを LF で揃えられるようになります。

