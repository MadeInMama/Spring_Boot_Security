var TOKEN;

function GetToken() {
    if (TOKEN == null) {
        TOKEN = document.querySelector("meta[name=_csrf]").getAttribute("content");
    }
    return TOKEN;
}