---
title: "C 言語 → アセンブラ言語 ─ PIC めもめも"
created: 2002-08-01
---

代入
----

#### VAR = 100

~~~
    MOVLW  D'100'
    MOVF   VAR, F
~~~

#### VAR1 = VAR2

~~~
    MOVF    VAR2, W
    MOVWF   VAR1
~~~

#### VAR++

~~~
    INCF    VAR, F
~~~

#### VAR += 100

~~~
    MOVLW   D'100'
    ADDWF   VAR, F
~~~

#### VAR1 += VAR2

~~~
    MOVF    VAR2, W
    ADDWF   VAR1, F
~~~


ループ
----

#### for (i = 100; i > 0; i--) { ... }

~~~
    MOVLW   D'100'              ;100 回ループ
    MOVWF   i
LOOP
    ...
    DECFSZ  i, F
    GOTO    LOOP
~~~


IF 分岐
----

#### if (w == 100) { ... }

~~~
    SUBLW   100                 ; 100 - w -> w
    BTFSC   STATUS, Z           ; w == 100 でなかったらスキップ
    GOTO    W_IS_100
    GOTO    W_IS_NOT_100

W_IS_100
    ...
    goto ...

W_IS_NOT_100
    ...
~~~

#### if (VAR == 100) { ... }

~~~
    MOVLW   100                 ;VAR - 100 → W
    SUBWF   VAR, W              ;
    BTFSC   STATUS, Z           ;VAR == 100 でなかったらスキップ
    GOTO    VAR_IS_100
    GOTO    VAR_IS_NOT_100

VAR_IS_100
    ...

VAR_IS_NOT_100
    ...
~~~

#### if (A >= B) { ... }

~~~
    MOVF    B, W                ; B → W
    SUBWF   A, W                ; A - W → W
    BTFSC   STATUS, C           ; A >= B なら負でない
    GOTO    A_LARGER_EQUAL
    GOTO    B_LARGER
~~~


SWITCH 分岐
----

#### CASE 1, 2, 3 への分岐（1 からの連番の場合）

~~~
==== C ====

    switch (FLAG) {
        case 1:
            ...
            break;
        case 2:
            ...
            break;
        case 3:
            ...
            break;
        default:
            ...
            break;
    }

==== ASSEMBLER ===

    DECF   FLAG, F
    BTFSC  STATUS, Z
    GOTO   CASE_1

    DECF   FLAG, F
    BTFSC  STATUS, Z
    GOTO   CASE_2

    DECF   FLAG, F
    BTFSC  STATUS, Z
    GOTO   CASE_3

DEFAULT
    ...
    GOTO xxx
CASE_1
    ...
    GOTO xxx
CASE_2
    ...
    GOTO xxx
CASE_3
    ...
    GOTO xxx

（FLAG の値を壊さないようにする場合は次のように TEMP_FLAG を用意する）

    MOVF   FLAG, W
    MOVWF  TEMP_FLAG  ; FLAG の値を代入

    DECF   TEMP_FLAG, F
    BTFSC  STATUS, Z
    GOTO   CASE_1

    DECF   TEMP_FLAG, F
    BTFSC  STATUS, Z
    GOTO   CASE_2

    DECF   TEMP_FLAG, F
    BTFSC  STATUS, Z
    GOTO   CASE_3

DEFAULT
    ...
    GOTO xxx
CASE_1
    ...
    GOTO xxx
CASE_2
    ...
    GOTO xxx
CASE_3
    ...
    GOTO xxx
~~~

#### CASE 2, 4, 6 への分岐（とびとびの値の場合）

~~~
==== C ====

    switch (FLAG) {
        case 2:
            ...
            break;
        case 4:
            ...
            break;
        case 6:
            ...
            break;
        default:
            ...
            break;
    }

==== ASSEMBLER ====

    MOVLW   2                   ; 2 の場合の分岐処理
    SUBWF   FLAG, W             ;
    BTFSC   STATUS, Z           ;
    GOTO    CASE_2              ;

    MOVLW   4                   ; 4 の場合の分岐処理
    SUBWF   FLAG, W             ;
    BTFSC   STATUS, Z           ;
    GOTO    CASE_4              ;

    MOVLW   6                   ; 6 の場合の分岐処理
    SUBWF   FLAG, W             ;
    BTFSC   STATUS, Z           ;
    GOTO    CASE_6              ;

DEFAULT
    ...
    GOTO xxx
CASE_2
    ...
    GOTO xxx
CASE_4
    ...
    GOTO xxx
CASE_6
    ...
    GOTO xxx
~~~


演算
----

#### 2 バイトデータの足し算 (A += B)

~~~
A_LOW       EQU ...
B_LOW       EQU ...
A_HIGH      EQU ...
B_HIGH      EQU ...

    MOVF    B_LOW, W
    ADDWF   A_LOW, F            ; 下位が A_LOW へ
    BTFSC   STATUS, C           ; あふれたら上位へ +1
    INCF    B_HIGH, F
    MOVF    B_HIGH, W
    ADDWF   A_HIGH, F           ; 上位が A_HEGH へ
~~~

#### 2 倍 (VAR *= 2)

~~~
    BCF     STATUS, C
    RLF     VAR, F
~~~

キャリーフラグをクリアしてからシフトするのがポイントです。

#### 1/2 倍 (VAR /= 2)

~~~
    BCF     STATUS, C
    RRF     VAR, F
~~~

キャリーフラグをクリアしてからシフトするのがポイントです。

