window.onload = () => {
    GetAllUsers();
};

class Button {
    #ColumnName;
    #Object;
    #OnClick;

    get ColumnName() {
        return this.#ColumnName;
    }
    get Object() {
        return this.#Object;
    }

    constructor(col, getObj, objText, onClick) {
        this.#ColumnName = col;
        this.#Object = getObj();
        this.#Object.innerHTML = objText;
        this.#OnClick = onClick;
    }

    SetClickParams(params) {
        let str = "";

        for (let i = 0; i < params.length; i++) {
            if (i > 0) {
                str += ",";
            }
            str += params[i];
        }

        this.#Object.setAttribute(
            "onclick",
            this.#OnClick + "(" + str + ")"
        );
    }
}

function InitTable(data, table_obj, addRows) {
    const table_head = document.createElement("thead");
    const table_body = document.createElement("tbody");

    let elements;

    ClearChildren(table_obj);

    table_obj.appendChild(table_head);
    table_obj.appendChild(table_body);

    ClearChildren(table_head);
    elements = Object.keys(Object.values(data)[0]);
    if (addRows != null) {
        addRows.forEach((r) => elements.push(r.ColumnName));
    }
    table_head.appendChild(GetTableRow(elements));

    ClearChildren(table_body);
    for (
        let i = 0;
        i < Object.values(Object.values(data)).length;
        i++
    ) {
        elements = Object.values(Object.values(data)[i]);
        if (addRows != null) {
            addRows.forEach((r) => {
                r.SetClickParams([
                    Object.values(Object.values(data)[i])[0],
                ]);
                elements.push(r.Object.outerHTML);
            });
        }
        table_body.appendChild(GetTableRow(elements));
    }
}

function GetTableRow(res) {
    const length = res.length;

    let tr = document.createElement("tr");
    let td = document.createElement("td");

    for (let i = 0; i < length; i++) {
        if (Array.isArray(res[i])) {
            td.innerHTML = "";
            for (let j = 0; j < res[i].length; j++) {
                td.innerHTML +=
                    "<span>" +
                    Object.values(Object.values(res[i])[j])[1] +
                    "</span> ";
            }
        } else {
            td.innerHTML = res[i];
        }

        tr.appendChild(td);
        td = document.createElement("td");
    }

    return tr;
}

function ClearChildren(element) {
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

function Edit(param) {
    console.log("Edit with id: " + param);
}
function GetAllUsers() {
    $.get("/rest/get/users", (data) => {
        InitTable(data, document.querySelector("#table1"), [
            new Button(
                "edit",
                () => document.createElement("button"),
                "edit",
                "Edit"
            ),
            new Button(
                "delete",
                () => document.createElement("button"),
                "delete",
                "DeleteUser"
            ),
        ]);
        InitTable(data, document.querySelector("#table2"));
    });
}
function GetUser(id) {
    $.get("/rest/get/user/" + id, (data) => console.log(data));
}
function SaveUser(num) {
    $.post("/rest/add/user", {
        username: "user" + num,
        password: "user",
        roles: [1],
        _csrf: GetToken(),
    });
}
function DeleteUser(id) {
    $.post("/rest/delete/user", {
        id: id,
        _csrf: GetToken(),
    }).done(GetAllUsers);
}

function GetToken() {
    return document.querySelector("meta[name=_csrf]").getAttribute("content");
}