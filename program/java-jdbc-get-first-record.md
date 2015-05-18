---
title: 最初のレコードだけを取得する
created: 2011-02-03
---

```java
String sql = "SELECT * FROM tbl";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setMaxRows(1);
ResultSet rs = stmt.executeQuery();
```

