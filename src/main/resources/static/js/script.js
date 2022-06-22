window.onload = () => {

};

function AddUser() {
    $.post("/rest/add/user", {
        username:
            "user" +
            document.querySelector("input[type=number]").value,
        password: "user",
        roles: [0, 1],
        _csrf: document.querySelector("input[name=_csrf]").value,
    });
}
function GetAllUsers() {
    $.get("/rest/get/users", (data) => console.log(data));
}
function GetUser(id) {
    if (id < 1) {
        id = document.querySelector("input[type=number]").value;
    }

    $.get("/rest/get/user/" + id, (data) => console.log(data));
}