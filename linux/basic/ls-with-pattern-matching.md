---
title: "いずれかの文字列を含むファイルだけを列挙する"
date: "2005-05-22"
---

#### 例: ３文字以上のファイルを列挙する

```
$ ls ???*
```


#### 例: 01, 02, 03 のいずれかの文字列を含むファイルを列挙する

```
$ ls *{01,02,03}*
```

あるいは

```
$ ls *0[1-3]*
```

