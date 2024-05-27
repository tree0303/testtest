

function registerinputCheck() {
    let username_ele = document.getElementById("name");
    let age_ele = document.getElementById("age");
    let pass_ele = document.getElementById("pass");
    let gender_ele = document.getElementById("gender");
    if (username_ele.value != "" && age_ele.value != "" && gender_ele.value != "" && age_ele.value >= 0 && age_ele.value <= 120 && pass_ele.value != "") {
        document.getElementById("registerbtn").disabled = false;
    } else {
        document.getElementById("registerbtn").disabled = true;
    }
}

function changeinputCheck() {
    let username_ele = document.getElementById("name");
    let age_ele = document.getElementById("age");
    let gender_ele = document.getElementById("gender");
    if (username_ele.value != "" && age_ele.value != "" && gender_ele.value != "" && age_ele.value >= 0 && age_ele.value <= 120) {
        document.getElementById("registerbtn").disabled = false;
    } else {
        document.getElementById("registerbtn").disabled = true;
    }
}


function logininputCheck() {
    let name_ele = document.getElementById("name");
    let pass_ele = document.getElementById("pass");
    if (name_ele.value != "" && pass_ele.value != "") {
        document.getElementById("login").disabled = false;
    } else {
        document.getElementById("login").disabled = true;
    }
}

function indexinputCheck() {
    let ele = document.getElementById("ele_num");
    if (ele.value >= 3) {
        document.getElementById("startbtn").disabled = false;
    } else {
        document.getElementById("startbtn").disabled = true;
    }
    if (ele.value < 3) {
        ele.innerText = 3;
    }
}