---
title: ディレクトリ内のファイルを列挙する
created: 2010-12-14
---

ディレクトリ内のファイルやディレクトリを列挙するには、dirent.h で定義されている、

- opendir
- readdir
- closedir

を使用します。
`readdir` の返すエントリには、カレントディレクトリを示す `.` や、親ディレクトリを示す `..` も含まれます。

~~~ cpp
#include <stdio.h>
#include <dirent.h>

int main() {
    DIR *pDir = opendir("/home");
    if (pDir == NULL) {
        perror("opendir");
        return -1;
    }

    dirent *pDirEnt = NULL;
    while ((pDirEnt = readdir(pDir)) != NULL) {
        printf("%s\n", pDirEnt->d_name);
    }

    if (closedir(pDir) != 0) {
        perror("closedir");
        return -1;
    }
}
~~~

