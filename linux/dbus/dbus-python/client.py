#!/usr/bin/env python

import dbus

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


def main():
    counter = get_counter_interface()
    counter.SetCount(100)
    val = counter.GetCount()
    print("count: " + str(val))


if __name__ == '__main__':
    main()
