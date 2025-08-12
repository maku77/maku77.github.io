---
title: "Windows (MinGW) で GLUT を使用する"
url: "p/9nom8yj/"
date: "2012-12-21"
tags: ["memo"]
aliases: ["/memo/tool/glut-in-windows.html"]
---

Windows の MinGW で GLUT を使用するには、**freeglut** を使用するのが簡単です。

* https://www.transmissionzero.co.uk/software/freeglut-devel/


GLUT (freeglut) のインストール
----

「Download freeglut 2.8.0-1 for MinGW」といったところからアーカイブをダウンロードして展開すると、下記のようなファイル群が展開されます。

```
bin/
  +-- freeglut.dll
include/GL/
  +-- freeglut.h
  +-- freeglut_ext.h
  +-- freeglut_std.h
  +-- glut.h
lib/
  +-- libfreeglut.a
  +-- libfreeglut_static.a
```

それぞれ、以下のような位置にコピーすれば、インストール終了です。

* DLL ファイル -- パス (%PATH%) の通ったどこか
* ヘッダファイル -- `C:\MinGW\include\GL` ディレクトリ
* lib ファイル -- `C:\MinGW\lib` ディレクトリ


GLUT (freeglut) で HelloWorld
----

`GL/glut.h` を使用したプログラムは、以下のようにビルドできます。

```
C:\> g++ main.cpp -lfreeglut -lopengl32
```

{{< code lang="cpp" title="main.cpp" >}}
#include <GL/glut.h>

void display() {
    glClear(GL_COLOR_BUFFER_BIT);
    glBegin(GL_LINE_LOOP);
    glVertex2d(-0.5, -0.5);
    glVertex2d(0.5, -0.5);
    glVertex2d(0.5, 0.5);
    glVertex2d(-0.5, 0.5);
    glEnd();
    glFlush();
}

void init() {
    glClearColor(0.0, 1.0, 0.5, 1.0);
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA);
    glutCreateWindow(argv[0]);
    glutDisplayFunc(display);
    init();
    glutMainLoop();
}
{{< /code >}}

