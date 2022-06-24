function GetAllUsers(onComplete) {
    $.get("/rest/get/users", (data) => onComplete(data));
}
function GetUser(id, onComplete) {
    $.get("/rest/get/user/" + id, (data) => onComplete(data));
}
function GetUserForEdit(id, onComplete) {
    $.get("/rest/edit/user/" + id, (data) => onComplete(data));
}
function GetMe(onComplete) {
    $.get("/rest/get/user/me", (data) => onComplete(data));
}
function DeleteUser(id, onComplete) {
    $.post("/rest/delete/user", {
        id: id,
        _csrf: GetToken(),
    }).done(onComplete);
}