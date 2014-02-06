/**
 * Initialization Application Loader
 * @Author WangXian
 * @Company wangxian.me
 */

// APP Global variables
var APP = {};

// App config
APP.config = {
  "debug" : true
};

// Current portal page name
APP.portalname = window.location.pathname === "/" ? "index" : window.location.pathname.slice(window.location.pathname.lastIndexOf("/")+1, -5);

// Portal js file name
APP.initjs = "/assets/js/"+ APP.portalname + ".js";

// seajs config
seajs.config({
  "debug": APP.config.debug,
  "alias": {
    "jquery"     : "jquery/jquery/1.10.1/jquery.js",
    "underscore" : "gallery/underscore/1.4.4/underscore.js",
    "backbone"   : "gallery/backbone/1.0.0/backbone.js",
    "es5-safe"   : "gallery/es5-safe/0.9.2/es5-safe.js",
    "json"       : "gallery/json/1.0.0/json.js",
    "moment"     : "gallery/moment/2.3.1/moment.js",
    "store"      : "gallery/store/1.3.14/store.js",
    "md5"        : "gallery/md5/1.0.0/md5.js",
    "sha1"       : "gallery/sha1/1.0.0/sha1.js"
  },
  "preload": ["jquery", window.JSON?"":"json"]
});

// Dispatcher App
seajs.use(APP.initjs);