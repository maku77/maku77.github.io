---
title: JDBC で最初のレコードだけを取得する
created: 2011-02-03
---

`Statement#setMaxRows()` を使用して、SELECT クエリで検索されたレコードのうちの最初のレコードのみを取得することができます。

```java
String sql = "SELECT * FROM tbl";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setMaxRows(1);
ResultSet rs = stmt.executeQuery();
```

