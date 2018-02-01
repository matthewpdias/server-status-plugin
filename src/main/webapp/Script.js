var goodImage = "${rootURL}/plugin/server-status-plugin/images/greenball.png"
var badImage = "${rootURL}/plugin/server-status-plugin/images/greenball.png"

var newImage = document.createElement("IMG")

var tasks = document.getElementsByClassName("task-link")

var xhr = new XMLHttpRequest();


for (var i = 0; i < tasks.length; i++) {
    if (tasks[i].textContent.match("SIDEBAR_SERVER_STATUS")) {
        tasks[i].textContent = tasks[i].textContent.replace("SIDEBAR_SERVER_STATUS", " ");
        xhr.open('GET', tasks[i].href, false);
        xhr.send;
        if (xhr.readyState == 4 && xhr.status == 200) {
            newImage.src = goodImage;
        } else {
            newImage.src = badImage;
        }
        tasks[i].appendChild(newImage);
    }
}