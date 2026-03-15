---
title: "Javaメモ: JDBCドライバの一覧を取得する"
url: "p/c96fmw7/"
date: "2010-08-15"
tags: ["java"]
aliases: ["/java/jdbc-list-drivers.html"]
---

```java.sql.DriverManager#getDrivers()``` メソッドを使用して、```DriverManager``` に登録済みの JDBC ドライバをすべて取得することができます。
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

{{< code lang="console" title="実行結果" >}}
class sun.jdbc.odbc.JdbcOdbcDriver Ver:2
class org.sqlite.JDBC Ver:3
{{< /code >}}
