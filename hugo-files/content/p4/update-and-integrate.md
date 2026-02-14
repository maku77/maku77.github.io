---
title: "Perforceメモ: p4 update と p4 integrate の違いを理解する"
url: "p/9ztg7c3/"
date: "2005-07-14"
tags: ["perforce"]
aliases: ["/p4/update-and-integrate.html"]
---

```console
$ p4 update //depot/A/...
```

とすると、ディポ上の `//depot/A/...` が、 クライアント仕様の `//depot/A/...` ビューを通してローカルにマッピングされます。

```console
$ p4 integrate //depot/A/... //depot/B/...
```

とすると、ディポ上の `//depot/A/...` が、クライアント仕様の `//depot/B/...` ビューを通してローカルにマッピングされます。
