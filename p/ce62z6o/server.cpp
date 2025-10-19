#include "calc.h"
#include <gio/gio.h>
#include <stdlib.h>

#define SERVER_ADDR "unix:abstract=sample"
#define OBJECT_PATH "/com/example/MyApp/Calc"

// Implementation of "Add" method.
gboolean handleAdd(ComExampleMyAppCalc *object, GDBusMethodInvocation *invocation,
        gint val1, gint val2) {
    gint ret = val1 + val2;
    com_example_my_app_calc_complete_add(object, invocation, ret);
    return TRUE;
}

// Implementation of "Subtract" method.
gboolean handleSubtract(ComExampleMyAppCalc *object, GDBusMethodInvocation *invocation,
        gint val1, gint val2) {
    gint ret = val1 - val2;
    com_example_my_app_calc_complete_subtract(object, invocation, ret);
    return TRUE;
}

// Called when the client connects to the server.
void handleConnect(GDBusServer *server, GDBusConnection *conn, gpointer data) {
    ComExampleMyAppCalc *skel = com_example_my_app_calc_skeleton_new();
    ComExampleMyAppCalcIface *iface = COM_EXAMPLE_MY_APP_CALC_GET_IFACE(skel);
    iface->handle_add = handleAdd;
    iface->handle_subtract = handleSubtract;

    GError *error = NULL;
    gboolean ret = g_dbus_interface_skeleton_export(
            G_DBUS_INTERFACE_SKELETON(skel), conn, OBJECT_PATH, &error);
    if (!ret) {
        g_error("g_dbus_interface_skeleton_export() failed: %s", error->message);
        exit(-1);
    }
}

static GDBusServer* createServer() {
    GError *error = NULL;
    gchar *guid = g_dbus_generate_guid();
    GDBusServerFlags flags = G_DBUS_SERVER_FLAGS_NONE;
    GDBusServer *server = g_dbus_server_new_sync(
            SERVER_ADDR, flags, guid, NULL, NULL, &error);
    if (server == NULL) {
        g_error("g_dbus_server_new_sync() failed: %s", error->message);
        exit(-1);
    }
    g_free(guid);
    return server;
}


int main() {
    g_type_init();

    GDBusServer *server = createServer();
    g_signal_connect(server, "new-connection", G_CALLBACK(handleConnect), NULL);
    g_dbus_server_start(server);
    g_print("Server is listening at: %s\n", g_dbus_server_get_client_address(server));

    // Start main loop
    GMainLoop *loop = g_main_loop_new(NULL, FALSE);
    g_main_loop_run(loop);

    // Main loop finished
    g_main_loop_unref(loop);
    g_object_unref(server);
}

