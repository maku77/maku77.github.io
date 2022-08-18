#include <windows.h>
#include <stdio.h>

int GetSaverTimeout() {
    int seconds;
    if (!::SystemParametersInfo(SPI_GETSCREENSAVETIMEOUT, 0, &seconds, 0)) {
        fprintf(stderr, "Error: SystemParametersInfo failed\n");
    }
    return seconds;
}

void SetSaverTimeout(int seconds) {
    if (!::SystemParametersInfo(SPI_SETSCREENSAVETIMEOUT, seconds, NULL, SPIF_SENDCHANGE)) {
        fprintf(stderr, "Error: SystemParametersInfo failed\n");
    }
}

int main(int argc, char** argv) {
    if (argc < 2) {
        fprintf(stderr, "Usage: %s [seconds]\n", argv[0]);
        int timeout = GetSaverTimeout();
        printf("Current timeout is %d seconds\n", timeout);
        exit(-1);
    }

    int seconds = atoi(argv[1]);
    printf("Set screensaver timeout: %d\n", seconds);
    SetSaverTimeout(seconds);
}

