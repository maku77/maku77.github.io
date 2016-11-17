#!/usr/bin/env python

import dbus
import dbus.mainloop.glib
import gobject

ADDR = 'tcp:host=127.0.0.1,port=12300'
OBJECT_PATH = '/com/example/CounterObject'
INTERFACE = 'com.example.Counter'


# Get an interface to manipulate a remote object.
def get_counter_interface():
    con = dbus.connection.Connection(ADDR)
    counter_object = con.get_object(object_path=OBJECT_PATH)
    return dbus.Interface(counter_object, INTERFACE)


def main():
    counter = get_counter_interface()
    counter.SetCount(100)
    val = counter.GetCount()


if __name__ == '__main__':
    main()
