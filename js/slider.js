$(function(e,n,t,r){Slider=function(n,r){"use strict";function l(){var n=y.children(),l="";null!=t.getElementById("lang-select")?l=t.getElementById("lang-select").value:null!=t.getElementById("mobile-lang-select")&&(l=t.getElementById("mobile-lang-select").value),"zh"===l?(null!=t.getElementById("banner1")&&(t.getElementById("banner1").style="background: url(/SuperMap-iDesktop-Cross/img/banner1.png) no-repeat center"),null!=t.getElementById("banner2")&&(t.getElementById("banner2").style="background: url(/SuperMap-iDesktop-Cross/img/banner2.png) no-repeat center"),null!=t.getElementById("banner3")&&(t.getElementById("banner3").style="background: url(/SuperMap-iDesktop-Cross/img/banner3.png) no-repeat center")):(null!=t.getElementById("banner1")&&(t.getElementById("banner1").style="background: url(/SuperMap-iDesktop-Cross/img/banner1_en.png) no-repeat center"),null!=t.getElementById("banner2")&&(t.getElementById("banner2").style="background: url(/SuperMap-iDesktop-Cross/img/banner2_en.png) no-repeat center"),null!=t.getElementById("banner3")&&(t.getElementById("banner3").style="background: url(/SuperMap-iDesktop-Cross/img/banner3_en.png) no-repeat center")),a(),"hover"==h?n.mouseover(function(){s();var n=e(this).index();c(n,r.mode)}).mouseout(function(){m&&i()}):n.click(function(){s();var n=e(this).index();c(n,r.mode),m&&i()}),m&&i()}function a(){var e=n.children().first();"slide"==r.mode?e.width(k):e.children().css({position:"absolute",left:0,top:0}).first().siblings().hide()}function i(){g=setInterval(function(){o(p)},r.time)}function o(e){var n;n=e==I-1?0:e+1,c(n,r.mode)}function c(e,n){v.stop(!0,!0),E.stop(!0,!0),"slide"==n?function(){if(e>p)v.animate({left:"-="+Math.abs(e-p)*B+"px"},f);else{if(!(e<p))return;v.animate({left:"+="+Math.abs(e-p)*B+"px"},f)}}():function(){v.children(":visible").index()!=e&&v.children().fadeOut(f).eq(e).fadeIn(f)}();try{y.children("."+b).removeClass(b),y.children().eq(e).addClass(b)}catch(t){}p=e,r.exchangeEnd&&"function"==typeof r.exchangeEnd&&r.exchangeEnd.call(this,p)}function s(){clearInterval(g)}function u(){s(),o(0==p?I-2:p-2),m&&i()}function d(){s(),o(p==I-1?-1:p),m&&i()}if(n){var g,r=r||{},p=0,b=r.activeControllerCls,f=r.delay,m=r.auto,y=r.controller,h=r.event,v=n.children().first(),E=v.children(),I=E.length,B=n.width(),k=B*E.length;return l(),{prev:function(){u()},next:function(){d()}}}}}(jQuery,window,document)),$(function(){var e=new Slider($("#banner_tabs"),{time:5e3,delay:400,event:"hover",auto:!0,mode:"fade",controller:$("#bannerCtrl"),activeControllerCls:"active"});$("#banner_tabs .flex-prev").click(function(){e.prev()}),$("#banner_tabs .flex-next").click(function(){e.next()})});