---
title: "JDBCドライバの一覧を取得する"
date: "2010-08-15"
---

```java.sql.DriverManager#getDrivers()``` メソッドを使用して、```Drivermanager``` に登録済みの JDBC ドライバをすべて取得することができます。
下記の例では、SQLite の JDBC ドライバを登録した後で、JDBC ドライバの一覧を取得しています。

```java
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver d = drivers.nextElement();
            System.out.println(d.getClass() + " Ver:" + d.getMajorVersion());
        }
   }
}
```

#### 実行結果
```
class sun.jdbc.odbc.JdbcOdbcDriver Ver:2
class org.sqlite.JDBC Ver:3
```
