#!/usr/bin/env python

import dbus
import dbus.mainloop.glib
import gobject

BUS_NAME = 'com.example.CounterService'
OBJECT_PATH = '/com/example/CounterObject'
INTERFACE = 'com.example.Counter'


# Get an interface to manipulate a remote object.
def get_counter_interface():
    session_bus = dbus.SessionBus()
    try:
        counter_object = session_bus.get_object(BUS_NAME, OBJECT_PATH)
    except dbus.DBusException as e:
        print(str(e))
        sys.exit(1)

    return dbus.Interface(counter_object, INTERFACE)


def on_count_changed(count):
    print('CountChanged: ' + str(count))


def on_timeout(counter_iface):
    val = counter_iface.GetCount()
    counter_iface.SetCount(val + 1)
    return True  # continued

def main():
    dbus.mainloop.glib.DBusGMainLoop(set_as_default=True)

    counter = get_counter_interface()
    counter.connect_to_signal('CountChanged', on_count_changed)

    # Call the SetCount method repeatedly
    gobject.timeout_add(1000, on_timeout, counter)

    # Start the mainloop to receive signals
    loop = gobject.MainLoop()
    loop.run()

if __name__ == '__main__':
    main()
