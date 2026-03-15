---
title: "Javaメモ: JDBC で最初のレコードだけを取得する"
url: "p/nj5v9bp/"
date: "2011-02-03"
tags: ["java"]
aliases: ["/java/jdbc-get-first-record.html"]
---

`Statement#setMaxRows()` を使用して、SELECT クエリで検索されたレコードのうちの最初のレコードのみを取得することができます。

```java
String sql = "SELECT * FROM tbl";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setMaxRows(1);
ResultSet rs = stmt.executeQuery();
```
