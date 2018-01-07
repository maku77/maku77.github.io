---
title: D-Bus Java で P2P D-Bus サーバに接続してメソッドを呼び出すサンプル
date: "2012-06-22"
---

#### Main.java

```java
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DirectConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import com.example.MyApp.Calc;

public class Main {
    private static final String SERVER_ADDR = "unix:abstract=sample";
    private static final String OBJECT_PATH = "/com/example/MyApp";

    public static void main(String[] args) {
        try {
            DirectConnection dc =new DirectConnection(SERVER_ADDR);
            Calc calc = conn.getRemoteObject(OBJECT_PATH, Calc.class);
            // ...

            int result = calc.Add(100, 200);
            // ...

            dc.disconnect();
        } catch (DBusException e) {
            e.printStackTrace();
        }
        ...
    }
}
```

