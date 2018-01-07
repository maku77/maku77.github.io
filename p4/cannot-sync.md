---
title: "p4 sync しようとすると opened for edit and can't be deleted というエラーになる"
date: "2006-06-16"
---

`p4 sync` を実行したときに下記のように失敗することがあります。

~~~
$ p4 sync
...
//depot/hoge.txt#1 - is opend for edit and can't be deleted
~~~

p4 コマンドリファレンスによると、

~~~
p4 sync copies a particular depot file only if
it meets all of the following criteria:

  - The file must be visible through the client view;
  - It must not already be opened by p4 edit, p4 delete,
    p4 add, or p4 integrate;
  - It must not already exist in the client workspace at
    its latest revision (the head revision).
~~~

という仕様になっており、オープン状態になっているファイルは `p4 sync` をしてもディポからファイルがコピーされないようになっています。
このようになっているおかげで、編集中のファイルを誤ってディポ上のファイルで上書きしてしまう心配がなくなり、自分が編集中でないファイルだけを `p4 sync` により常に最新に保つことができます。

もし、編集中のファイルであっても、問答無用でディポ上のファイルで上書きしたい場合は、`p4 sync` コマンドを `-f` オプションを付けて実行します。
`-f` オプションを付けると、対象のファイルが `p4 edit`、`p4 delete` でオープンされていようが、指定したリビジョンと同じリビジョンのファイルであろうが、ディポ上のファイルで強制的に上書き保存するようになります。
要するに、ローカルにファイルがない状態から `p4 sync` するのとほぼ同じ結果が得られます。

~~~
$ p4 sync -f @<LabelName>
~~~

