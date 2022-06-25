window.onload = () => {
    Init();

    let add_new = document.querySelector(".add-new .btn");
    if(add_new != null) {
        add_new.onclick = () => {
            const usernameObj = document.querySelector(".add-new #username");
            const passwordObj = document.querySelector(".add-new #password");
            const rolesObj = document.querySelectorAll(".add-new .form-check-input");

            let roles = [];

            rolesObj.forEach(
                (element) => {
                    if (element.checked) {
                        roles.push(element.value);
                    }
                }
            );

            SaveUser({
                id: 0,
                username: usernameObj.value,
                password: passwordObj.value,
                roles: roles
            }, () => {
                Init();
                usernameObj.value = "";
                passwordObj.value = "";
                rolesObj.forEach(element => element.checked = false);
                alert("User added successfully!");
            });
        }
    }
};

function Init() {
    GetAllUsers((data) => {
        InitTable(data, document.querySelector("#table1"), [
            new Button(
                "edit",
                () => document.createElement("button"),
                "edit",
                "Edit",
                "btn btn-info"
            ),
            new Button(
                "delete",
                () => document.createElement("button"),
                "delete",
                "Delete",
                "btn btn-danger"
            ),
        ]);
    });
    GetMe((data) =>
        InitTable(data, document.querySelector("#table2"))
    );
}

function Edit(id) {
    Modal.Open(id);
}

function Delete(id) {
    DeleteUser(id, Init);
}

function ClearChildren(element) {
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

function InitTable(data, table_obj, addRows) {
    if(table_obj == null) {
        return;
    }

    const table_head = document.createElement("thead");
    const table_body = document.createElement("tbody");

    let elements;

    ClearChildren(table_obj);
    ClearChildren(table_head);
    ClearChildren(table_body);

    table_obj.appendChild(table_head);
    table_obj.appendChild(table_body);

    elements = Array.isArray(data)
        ? Object.keys(Object.values(data)[0])
        : Object.keys(data);

    if (addRows != null) {
        addRows.forEach((r) => elements.push(r.ColumnName));
    }
    table_head.appendChild(GetTableRow(elements));

    if (Array.isArray(data)) {
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
    } else {
        elements = Object.values(data);
        if (addRows != null) {
            addRows.forEach((r) => {
                r.SetClickParams([Object.values(data)[0]]);
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
                let str;
                for (
                    let k = 0;
                    k < Object.values(res[i][j]).length;
                    k++
                ) {
                    if (
                        typeof Object.values(res[i][j])[k] ==
                        "string"
                    ) {
                        str = Object.values(res[i][j])[k];
                        break;
                    }
                }
                td.innerHTML += "<span>" + str + "</span> ";
            }
        } else {
            td.innerHTML = res[i];
        }

        tr.appendChild(td);
        td = document.createElement("td");
    }

    return tr;
}