---
title: dbus-glib で method call を実装する（クライアント側の実装）
created: 2012-05-10
---

`dbus-binding-tool` を使用すると、クライアント側の実装に使うヘッダーファイルとして、
以下のようなコードが自動生成されます。

#### client.h（抜粋）

```cpp
gboolean com_example_DBus_Test_Calc_add(DBusGProxy *proxy,
        const gint IN_val1, const gint IN_val2,
        gint* OUT_return_val, GError **error) {
    return dbus_g_proxy_call(proxy, "add", error,
            G_TYPE_INT, IN_val1, G_TYPE_INT, IN_val2, G_TYPE_INVALID,
            G_TYPE_INT, OUT_return_val, G_TYPE_INVALID);
}

gboolean com_example_DBus_Test_Calc_subtract(DBusGProxy *proxy,
        const gint IN_val1, const gint IN_val2,
        gint* OUT_return_val, GError **error) {
    return dbus_g_proxy_call(proxy, "subtract", error,
            G_TYPE_INT, IN_val1, G_TYPE_INT, IN_val2, G_TYPE_INVALID,
            G_TYPE_INT, OUT_return_val, G_TYPE_INVALID);
}
```

Async （非同期）版のコードも生成されていることが分かります。

```cpp
DBusGProxyCall* com_example_DBus_Test_Calc_add_async(DBusGProxy *proxy,
        const gint IN_val1, const gint IN_val2,
        com_example_DBus_Test_Calc_add_reply callback, gpointer userdata) {
    // ...
    return dbus_g_proxy_begin_call(...);
}

DBusGProxyCall* com_example_DBus_Test_Calc_subtract_async(DBusGProxy *proxy,
        const gint IN_val1, const gint IN_val2,
        com_example_DBus_Test_Calc_subtract_reply callback, gpointer userdata) {
    // ...
    return dbus_g_proxy_begin_call(...);
}
```

