---
title: p4 update と p4 integrate の違いを理解する
date: "2005-07-14"
---

~~~
$ p4 update //depot/A/...
~~~

とすると、ディポ上の `//depot/A/...` が、 クライアント仕様の `//depot/A/...` ビューを通してローカルにマッピングされます。

~~~
$ p4 integrate //depot/A/... //depot/B/...
~~~

とすると、ディポ上の `//depot/A/...` が、クライアント仕様の `//depot/B/...` ビューを通してローカルにマッピングされます。

