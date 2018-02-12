var tasks = document.getElementsByClassName("server-status-link");
var goodImage = document.createElement("IMG")
var badImage = document.createElement("IMG")
var taskList = Array.from(tasks);


taskList.forEach(function(task, index) {

  var xhr = new XMLHttpRequest();
  var parent = task;
  xhr.open("GET", task.href, true);
  xhr.onreadystatechange = function (e) {

  if (xhr.status === 200) {
    goodImage.src = `https://img.shields.io/badge/${parent.href}-online-brightgreen.svg`
    parent.appendChild(goodImage);
    console.log("SUCCESSFUL CONNECTION");
  } else {
    badImage.src = `https://img.shields.io/badge/${parent.href}-offline-red.svg`
    parent.appendChild(badImage);
    console.log("COULD NOT CONNECT");
  }};
  xhr.send(null);

});