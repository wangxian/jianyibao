define(function(require, exports, module){
  var $ = require("jquery");
  var _ = require("underscore");
  var Banner = require("./banner");

  // Button of Back to top
  $("#back-top a").click(function(){
    $(document.body).animate({"scrollTop": 0}, 600);
  });

  // 换行转换为html p
  function nl2p(str) {
    if(typeof str !== "string") return;
    return "<p>"+ str.replace(/\n/g, "</p><p>") +"</p>";
  }


  // 通栏导航
  var b1 = new Banner(".banner .banner.content", require("/read/get/news_xinwen") );
  b1.run();

  // 热点公告
  var b2 = new Banner(".box-2 .left", require("/read/get/news_huodong"));
  b2.onscroll(function(item){
    // console.log(item);
    var el = $(".box-2 .right");
    el.find("h5").text(item.title);
    el.find(".box").html( nl2p(item.intro_zh) );
    el.find(".readmore").attr("href", "/read-"+ item.id +".html");
  });
  b2.run();

  // 视频鼠标经过效果
  $("#box-video").delegate("li", "mouseenter", function(e){
    var _e = $(e.currentTarget);
    _e.addClass("on");
  }).delegate("li", "mouseleave", function(e){
    var _e = $(e.currentTarget);
    _e.removeClass("on");
  });

  // 播放视频
  require("/assets/js/bootstrap.min.js");
  $("#box-video").delegate("li", "click", function(e) {
    var _e = $(e.currentTarget);

    $("#embed-box").attr("src", _e.attr("data-url"));
    $('#myModal').modal()
  });

  // 精彩图集 ------------------------------------------------------------
  var totalWidth = 0;
  var moving = false;
  $(".box-3 .left li").click(function(){

    $('#galleryModal').modal();
    require.async("/read/get/gallery_list", function(gallery_list){
      totalWidth = gallery_list.length * 80;
      var ih = _.map(gallery_list, function(v){
        return "<img src='"+ v.img +"_thumb.jpg' />";
      });
      $("#thumb-box .items").width( totalWidth ).html(ih.join("")).find("img:first").click();
    });

  });

  // 隐藏的时候触发
  $('#galleryModal').on('hidden', function () {
    $("#thumb-box .items").empty();
  });

  // 精彩图集，切换
  $('#thumb-box .items').delegate("img", "click", function(e){
    var _e = $(e.currentTarget);
    if(_e.hasClass("on")) return false;
    $('#thumb-box .items img.on').removeClass("on");
    _e.addClass("on");

    // 加载图片
    var imgurl = _e.attr("src").replace("_thumb.jpg", "");
    $("#imgbox").fadeOut("slow", function(){
      $("#imgbox").css("background-image", "url("+ imgurl +")");
      $("#imgbox").fadeIn("slow");
    });
  });

  // 左翻页
  $('#move-left').click(function(e){
    var _e = $(e.currentTarget);
    if(!_e.hasClass("on")) return false;
    if(moving) return false;
    moving = true;

    $('#thumb-box .items').animate({"left": "+=1000"}, "fast", function(){
      moving = false;
      if( Math.abs($('#thumb-box .items').position().left) < 1000 ) {
        $('#move-left').removeClass("on");
      }
      $('#move-right').addClass("on");
    });
  });

  // 右翻页
  $('#move-right').click(function(e){
    var _e = $(e.currentTarget);
    if(!_e.hasClass("on")) return false;
    if(moving) return false;
    moving = true;

    $('#thumb-box .items').animate({"left": "-=1000"}, "fast", function(){
      moving = false;
      // console.log(Math.abs($('#thumb-box .items').position().left) , totalWidth-1000)
      if( Math.abs($('#thumb-box .items').position().left) > totalWidth-1000 ) {
        $('#move-right').removeClass("on");
      }
      $('#move-left').addClass("on");
    });

  });


});