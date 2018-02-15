var tasks_col = document.getElementsByClassName("server-status-link");

var tasks = Array.from(tasks_col);

tasks.forEach(function(task) {
    var goodImage = document.createElement("IMG");
    var badImage = document.createElement("IMG");
    var xhr = new XMLHttpRequest();
    var displayName = task.firstChild.textContent;

    xhr.open("GET", task.href, true);
    xhr.onreadystatechange = function (e) {

    if (xhr.status === 200) {
        goodImage.src = `https://img.shields.io/badge/${displayName}-online-brightgreen.svg`;
        goodImage.style.visibility = "visible";
        task.appendChild(goodImage);

    } else {
        badImage.src = `https://img.shields.io/badge/${displayName}-offline-red.svg`;
        task.appendChild(badImage);
        badImage.style.visibility = "visible";
    }};
    xhr.send(null);
 });