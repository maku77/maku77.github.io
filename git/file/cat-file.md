---
title: "特定のバージョンのファイルの内容を確認する (cat-file)"
date: "2014-07-17"
---

`git cat-file` や、`git show` コマンドを使用すると、特定のコミット時点における、指定したファイルの内容を確認することができます。

~~~
$ git cat-file -p <CommitID>:<FileName>
$ git show <CommitID>:<FileName>
~~~

`git show` コマンドの結果は、`less` などのページャ上で表示されます。


#### 例: コミットID 8de2fcd0 における Main.java の内容を表示する

~~~
$ git cat-file -p 8de2fcd0:Main.java
~~~

