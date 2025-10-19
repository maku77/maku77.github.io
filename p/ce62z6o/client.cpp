#include "calc.h"
#include <gio/gio.h>
#include <stdlib.h>

#define SERVER_ADDR "unix:abstract=sample"
#define OBJECT_PATH "/com/example/MyApp/Calc"

class MyCalc {
public:
    MyCalc() : m_conn(0), m_proxy(0) {
        connect();
        createProxy();
    }

    virtual ~MyCalc() {
        if (m_proxy) g_object_unref(m_proxy);
        if (m_conn) g_object_unref(m_conn);
    }

    gint add(gint val1, gint val2) const {
        gint buf;
        com_example_my_app_calc_call_add_sync(m_proxy, val1, val2, &buf, NULL, NULL);
        return buf;
    }

    gint subtract(gint val1, gint val2) const {
        gint buf;
        com_example_my_app_calc_call_subtract_sync(m_proxy, val1, val2, &buf, NULL, NULL);
        return buf;
    }

private:
    GDBusConnection* m_conn;
    ComExampleMyAppCalc* m_proxy;

    void connect() {
        GError *error = NULL;
        m_conn = g_dbus_connection_new_for_address_sync(
                SERVER_ADDR,
                G_DBUS_CONNECTION_FLAGS_AUTHENTICATION_CLIENT,
                NULL, NULL, &error);
        if (m_conn == NULL) {
            g_error("g_dbus_connection_new_for_address_sync() failed: %s", error->message);
            exit(-1);
        }
    }

    void createProxy() {
        GError *error = NULL;
        m_proxy = com_example_my_app_calc_proxy_new_sync(
                m_conn, G_DBUS_PROXY_FLAGS_NONE, NULL, OBJECT_PATH, NULL, &error);
        if (m_proxy == NULL) {
            g_error("com_example_my_app_calc_proxy_new_sync() failed: %s", error->message);
            exit(-1);
        }
    }
};

int main() {
    g_type_init();

    MyCalc calc;
    g_print("add = %d\n", calc.add(10, 20));
    g_print("sub = %d\n", calc.subtract(10, 20));
}
