
window.onload = function(){
    var footer_div = document.getElementsByClassName("jenkins_ver");

    var href = footer_div[0].firstChild.href;

    if (href == "https://jenkins.io/") {
        var script = document.createElement("script");
        script.src = "http://localhost:8080/plugin/sidebar-link/Script.js";
        footer_div[0].appendChild(script);
    }
};