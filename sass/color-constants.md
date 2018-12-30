---
title: "色（カラー値）を表す定数の一覧"
date: "2018-12-30"
---

<style>
.local-cells {
  text-align: center;
}
.local-cells > div {
  display: inline-block;
  text-align: left;
  width: 170px;
  height: 30px;
  margin-bottom: 10px;
}
.local-cells > div div:first-child {
  display: inline-block;
  width: 30px;
  height: 30px;
}
.local-cells > div div:nth-child(2) {
  display: inline-block;
  vertical-align: top;
  margin: 0;
  padding-left: 5px;
  width: 140px;
  line-height: 1.3;
}
</style>

Sass スクリプトの中で色を表現する方法としては、`#ff00ff` のような16進数での指定方法以外にも、下記のように色を表す定数を使用する方法があります。

~~~ scss
$my-color: magenta;  // Color型の変数を定義
$my-color: #ff00ff;  // 同上
~~~

色を表す定数には下記のようなものが用意されています。

<div class="local-cells">
<div><div style="background: #F0F8FF"></div><div>aliceblue<br>(0xF0F8FFFF)</div></div>
<div><div style="background: #FAEBD7"></div><div>antiquewhite<br>(0xFAEBD7FF)</div></div>
<div><div style="background: #00FFFF"></div><div>aqua<br>(0x00FFFFFF)</div></div>
<div><div style="background: #7FFFD4"></div><div>aquamarine<br>(0x7FFFD4FF)</div></div>
<div><div style="background: #F0FFFF"></div><div>azure<br>(0xF0FFFFFF)</div></div>
<div><div style="background: #F5F5DC"></div><div>beige<br>(0xF5F5DCFF)</div></div>
<div><div style="background: #FFE4C4"></div><div>bisque<br>(0xFFE4C4FF)</div></div>
<div><div style="background: #000000"></div><div>black<br>(0x000000FF)</div></div>
<div><div style="background: #FFEBCD"></div><div>blanchedalmond<br>(0xFFEBCDFF)</div></div>
<div><div style="background: #0000FF"></div><div>blue<br>(0x0000FFFF)</div></div>
<div><div style="background: #8A2BE2"></div><div>blueviolet<br>(0x8A2BE2FF)</div></div>
<div><div style="background: #A52A2A"></div><div>brown<br>(0xA52A2AFF)</div></div>
<div><div style="background: #DEB887"></div><div>burlywood<br>(0xDEB887FF)</div></div>
<div><div style="background: #5F9EA0"></div><div>cadetblue<br>(0x5F9EA0FF)</div></div>
<div><div style="background: #7FFF00"></div><div>chartreuse<br>(0x7FFF00FF)</div></div>
<div><div style="background: #D2691E"></div><div>chocolate<br>(0xD2691EFF)</div></div>
<div><div style="background: #FF7F50"></div><div>coral<br>(0xFF7F50FF)</div></div>
<div><div style="background: #6495ED"></div><div>cornflowerblue<br>(0x6495EDFF)</div></div>
<div><div style="background: #FFF8DC"></div><div>cornsilk<br>(0xFFF8DCFF)</div></div>
<div><div style="background: #DC143C"></div><div>crimson<br>(0xDC143CFF)</div></div>
<div><div style="background: #00FFFF"></div><div>cyan<br>(0x00FFFFFF)</div></div>
<div><div style="background: #00008B"></div><div>darkblue<br>(0x00008BFF)</div></div>
<div><div style="background: #008B8B"></div><div>darkcyan<br>(0x008B8BFF)</div></div>
<div><div style="background: #B8860B"></div><div>darkgoldenrod<br>(0xB8860BFF)</div></div>
<div><div style="background: #A9A9A9"></div><div>darkgray<br>(0xA9A9A9FF)</div></div>
<div><div style="background: #006400"></div><div>darkgreen<br>(0x006400FF)</div></div>
<div><div style="background: #A9A9A9"></div><div>darkgrey<br>(0xA9A9A9FF)</div></div>
<div><div style="background: #BDB76B"></div><div>darkkhaki<br>(0xBDB76BFF)</div></div>
<div><div style="background: #8B008B"></div><div>darkmagenta<br>(0x8B008BFF)</div></div>
<div><div style="background: #556B2F"></div><div>darkolivegreen<br>(0x556B2FFF)</div></div>
<div><div style="background: #FF8C00"></div><div>darkorange<br>(0xFF8C00FF)</div></div>
<div><div style="background: #9932CC"></div><div>darkorchid<br>(0x9932CCFF)</div></div>
<div><div style="background: #8B0000"></div><div>darkred<br>(0x8B0000FF)</div></div>
<div><div style="background: #E9967A"></div><div>darksalmon<br>(0xE9967AFF)</div></div>
<div><div style="background: #8FBC8F"></div><div>darkseagreen<br>(0x8FBC8FFF)</div></div>
<div><div style="background: #483D8B"></div><div>darkslateblue<br>(0x483D8BFF)</div></div>
<div><div style="background: #2F4F4F"></div><div>darkslategray<br>(0x2F4F4FFF)</div></div>
<div><div style="background: #2F4F4F"></div><div>darkslategrey<br>(0x2F4F4FFF)</div></div>
<div><div style="background: #00CED1"></div><div>darkturquoise<br>(0x00CED1FF)</div></div>
<div><div style="background: #9400D3"></div><div>darkviolet<br>(0x9400D3FF)</div></div>
<div><div style="background: #FF1493"></div><div>deeppink<br>(0xFF1493FF)</div></div>
<div><div style="background: #00BFFF"></div><div>deepskyblue<br>(0x00BFFFFF)</div></div>
<div><div style="background: #696969"></div><div>dimgray<br>(0x696969FF)</div></div>
<div><div style="background: #696969"></div><div>dimgrey<br>(0x696969FF)</div></div>
<div><div style="background: #1E90FF"></div><div>dodgerblue<br>(0x1E90FFFF)</div></div>
<div><div style="background: #B22222"></div><div>firebrick<br>(0xB22222FF)</div></div>
<div><div style="background: #FFFAF0"></div><div>floralwhite<br>(0xFFFAF0FF)</div></div>
<div><div style="background: #228B22"></div><div>forestgreen<br>(0x228B22FF)</div></div>
<div><div style="background: #FF00FF"></div><div>fuchsia<br>(0xFF00FFFF)</div></div>
<div><div style="background: #DCDCDC"></div><div>gainsboro<br>(0xDCDCDCFF)</div></div>
<div><div style="background: #F8F8FF"></div><div>ghostwhite<br>(0xF8F8FFFF)</div></div>
<div><div style="background: #FFD700"></div><div>gold<br>(0xFFD700FF)</div></div>
<div><div style="background: #DAA520"></div><div>goldenrod<br>(0xDAA520FF)</div></div>
<div><div style="background: #808080"></div><div>gray<br>(0x808080FF)</div></div>
<div><div style="background: #008000"></div><div>green<br>(0x008000FF)</div></div>
<div><div style="background: #ADFF2F"></div><div>greenyellow<br>(0xADFF2FFF)</div></div>
<div><div style="background: #808080"></div><div>grey<br>(0x808080FF)</div></div>
<div><div style="background: #F0FFF0"></div><div>honeydew<br>(0xF0FFF0FF)</div></div>
<div><div style="background: #FF69B4"></div><div>hotpink<br>(0xFF69B4FF)</div></div>
<div><div style="background: #CD5C5C"></div><div>indianred<br>(0xCD5C5CFF)</div></div>
<div><div style="background: #4B0082"></div><div>indigo<br>(0x4B0082FF)</div></div>
<div><div style="background: #FFFFF0"></div><div>ivory<br>(0xFFFFF0FF)</div></div>
<div><div style="background: #F0E68C"></div><div>khaki<br>(0xF0E68CFF)</div></div>
<div><div style="background: #E6E6FA"></div><div>lavender<br>(0xE6E6FAFF)</div></div>
<div><div style="background: #FFF0F5"></div><div>lavenderblush<br>(0xFFF0F5FF)</div></div>
<div><div style="background: #7CFC00"></div><div>lawngreen<br>(0x7CFC00FF)</div></div>
<div><div style="background: #FFFACD"></div><div>lemonchiffon<br>(0xFFFACDFF)</div></div>
<div><div style="background: #ADD8E6"></div><div>lightblue<br>(0xADD8E6FF)</div></div>
<div><div style="background: #F08080"></div><div>lightcoral<br>(0xF08080FF)</div></div>
<div><div style="background: #E0FFFF"></div><div>lightcyan<br>(0xE0FFFFFF)</div></div>
<div><div style="background: #FAFAD2"></div><div>lightgoldenrodyellow<br>(0xFAFAD2FF)</div></div>
<div><div style="background: #D3D3D3"></div><div>lightgray<br>(0xD3D3D3FF)</div></div>
<div><div style="background: #90EE90"></div><div>lightgreen<br>(0x90EE90FF)</div></div>
<div><div style="background: #D3D3D3"></div><div>lightgrey<br>(0xD3D3D3FF)</div></div>
<div><div style="background: #FFB6C1"></div><div>lightpink<br>(0xFFB6C1FF)</div></div>
<div><div style="background: #FFA07A"></div><div>lightsalmon<br>(0xFFA07AFF)</div></div>
<div><div style="background: #20B2AA"></div><div>lightseagreen<br>(0x20B2AAFF)</div></div>
<div><div style="background: #87CEFA"></div><div>lightskyblue<br>(0x87CEFAFF)</div></div>
<div><div style="background: #778899"></div><div>lightslategray<br>(0x778899FF)</div></div>
<div><div style="background: #778899"></div><div>lightslategrey<br>(0x778899FF)</div></div>
<div><div style="background: #B0C4DE"></div><div>lightsteelblue<br>(0xB0C4DEFF)</div></div>
<div><div style="background: #FFFFE0"></div><div>lightyellow<br>(0xFFFFE0FF)</div></div>
<div><div style="background: #00FF00"></div><div>lime<br>(0x00FF00FF)</div></div>
<div><div style="background: #32CD32"></div><div>limegreen<br>(0x32CD32FF)</div></div>
<div><div style="background: #FAF0E6"></div><div>linen<br>(0xFAF0E6FF)</div></div>
<div><div style="background: #FF00FF"></div><div>magenta<br>(0xFF00FFFF)</div></div>
<div><div style="background: #800000"></div><div>maroon<br>(0x800000FF)</div></div>
<div><div style="background: #66CDAA"></div><div>mediumaquamarine<br>(0x66CDAAFF)</div></div>
<div><div style="background: #0000CD"></div><div>mediumblue<br>(0x0000CDFF)</div></div>
<div><div style="background: #BA55D3"></div><div>mediumorchid<br>(0xBA55D3FF)</div></div>
<div><div style="background: #9370DB"></div><div>mediumpurple<br>(0x9370DBFF)</div></div>
<div><div style="background: #3CB371"></div><div>mediumseagreen<br>(0x3CB371FF)</div></div>
<div><div style="background: #7B68EE"></div><div>mediumslateblue<br>(0x7B68EEFF)</div></div>
<div><div style="background: #00FA9A"></div><div>mediumspringgreen<br>(0x00FA9AFF)</div></div>
<div><div style="background: #48D1CC"></div><div>mediumturquoise<br>(0x48D1CCFF)</div></div>
<div><div style="background: #C71585"></div><div>mediumvioletred<br>(0xC71585FF)</div></div>
<div><div style="background: #191970"></div><div>midnightblue<br>(0x191970FF)</div></div>
<div><div style="background: #F5FFFA"></div><div>mintcream<br>(0xF5FFFAFF)</div></div>
<div><div style="background: #FFE4E1"></div><div>mistyrose<br>(0xFFE4E1FF)</div></div>
<div><div style="background: #FFE4B5"></div><div>moccasin<br>(0xFFE4B5FF)</div></div>
<div><div style="background: #FFDEAD"></div><div>navajowhite<br>(0xFFDEADFF)</div></div>
<div><div style="background: #000080"></div><div>navy<br>(0x000080FF)</div></div>
<div><div style="background: #FDF5E6"></div><div>oldlace<br>(0xFDF5E6FF)</div></div>
<div><div style="background: #808000"></div><div>olive<br>(0x808000FF)</div></div>
<div><div style="background: #6B8E23"></div><div>olivedrab<br>(0x6B8E23FF)</div></div>
<div><div style="background: #FFA500"></div><div>orange<br>(0xFFA500FF)</div></div>
<div><div style="background: #FF4500"></div><div>orangered<br>(0xFF4500FF)</div></div>
<div><div style="background: #DA70D6"></div><div>orchid<br>(0xDA70D6FF)</div></div>
<div><div style="background: #EEE8AA"></div><div>palegoldenrod<br>(0xEEE8AAFF)</div></div>
<div><div style="background: #98FB98"></div><div>palegreen<br>(0x98FB98FF)</div></div>
<div><div style="background: #AFEEEE"></div><div>paleturquoise<br>(0xAFEEEEFF)</div></div>
<div><div style="background: #DB7093"></div><div>palevioletred<br>(0xDB7093FF)</div></div>
<div><div style="background: #FFEFD5"></div><div>papayawhip<br>(0xFFEFD5FF)</div></div>
<div><div style="background: #FFDAB9"></div><div>peachpuff<br>(0xFFDAB9FF)</div></div>
<div><div style="background: #CD853F"></div><div>peru<br>(0xCD853FFF)</div></div>
<div><div style="background: #FFC0CB"></div><div>pink<br>(0xFFC0CBFF)</div></div>
<div><div style="background: #DDA0DD"></div><div>plum<br>(0xDDA0DDFF)</div></div>
<div><div style="background: #B0E0E6"></div><div>powderblue<br>(0xB0E0E6FF)</div></div>
<div><div style="background: #800080"></div><div>purple<br>(0x800080FF)</div></div>
<div><div style="background: #663399"></div><div>rebeccapurple<br>(0x663399FF)</div></div>
<div><div style="background: #FF0000"></div><div>red<br>(0xFF0000FF)</div></div>
<div><div style="background: #BC8F8F"></div><div>rosybrown<br>(0xBC8F8FFF)</div></div>
<div><div style="background: #4169E1"></div><div>royalblue<br>(0x4169E1FF)</div></div>
<div><div style="background: #8B4513"></div><div>saddlebrown<br>(0x8B4513FF)</div></div>
<div><div style="background: #FA8072"></div><div>salmon<br>(0xFA8072FF)</div></div>
<div><div style="background: #F4A460"></div><div>sandybrown<br>(0xF4A460FF)</div></div>
<div><div style="background: #2E8B57"></div><div>seagreen<br>(0x2E8B57FF)</div></div>
<div><div style="background: #FFF5EE"></div><div>seashell<br>(0xFFF5EEFF)</div></div>
<div><div style="background: #A0522D"></div><div>sienna<br>(0xA0522DFF)</div></div>
<div><div style="background: #C0C0C0"></div><div>silver<br>(0xC0C0C0FF)</div></div>
<div><div style="background: #87CEEB"></div><div>skyblue<br>(0x87CEEBFF)</div></div>
<div><div style="background: #6A5ACD"></div><div>slateblue<br>(0x6A5ACDFF)</div></div>
<div><div style="background: #708090"></div><div>slategray<br>(0x708090FF)</div></div>
<div><div style="background: #708090"></div><div>slategrey<br>(0x708090FF)</div></div>
<div><div style="background: #FFFAFA"></div><div>snow<br>(0xFFFAFAFF)</div></div>
<div><div style="background: #00FF7F"></div><div>springgreen<br>(0x00FF7FFF)</div></div>
<div><div style="background: #4682B4"></div><div>steelblue<br>(0x4682B4FF)</div></div>
<div><div style="background: #D2B48C"></div><div>tan<br>(0xD2B48CFF)</div></div>
<div><div style="background: #008080"></div><div>teal<br>(0x008080FF)</div></div>
<div><div style="background: #D8BFD8"></div><div>thistle<br>(0xD8BFD8FF)</div></div>
<div><div style="background: #FF6347"></div><div>tomato<br>(0xFF6347FF)</div></div>
<div><div style="background: #000000"></div><div>transparent<br>(0x00000000)</div></div>
<div><div style="background: #40E0D0"></div><div>turquoise<br>(0x40E0D0FF)</div></div>
<div><div style="background: #EE82EE"></div><div>violet<br>(0xEE82EEFF)</div></div>
<div><div style="background: #F5DEB3"></div><div>wheat<br>(0xF5DEB3FF)</div></div>
<div><div style="background: #FFFFFF"></div><div>white<br>(0xFFFFFFFF)</div></div>
<div><div style="background: #F5F5F5"></div><div>whitesmoke<br>(0xF5F5F5FF)</div></div>
<div><div style="background: #FFFF00"></div><div>yellow<br>(0xFFFF00FF)</div></div>
<div><div style="background: #9ACD32"></div><div>yellowgreen<br>(0x9ACD32FF)</div></div>
</div>

アルファ値に関しては、上記のようにすべて 0xff (255) の不透過になっています。
アルファ値部分だけを変更したい場合は、下記のように `rgba` 関数で指定することができます。

~~~ scss
$my-color: rgba(red, 0.3);  // アルファ30%
~~~

