class Modal {
    static Modals = [];
    static Duration = 200;
    static Container;

    #Id;
    get Id() {
        return this.#Id;
    }
    #Object;
    get Object() {
        return this.#Object;
    }

    constructor(data) {
        this.#Id = data.id;

        const obj = document.createElement("div");
        obj.className = "my-modal appear";
        obj.setAttribute(
            "style",
            "animation-duration: " + Modal.Duration + "ms"
        );

        let content = document.createElement("div");
        content.className = "modal-content";

        let header = document.createElement("div");
        header.className = "modal-header  gap-2";

        let body = document.createElement("div");
        body.className = "modal-body";

        let username = document.createElement("input");
        username.setAttribute("id", "username");
        username.setAttribute("placeholder", "Enter username...");
        username.className = "form-control";
        username.value = data.username;

        let password = document.createElement("input");
        password.setAttribute("id", "password");
        password.setAttribute("type", "password");
        password.setAttribute("placeholder", "Enter password...");
        password.className = "form-control";
        password.value = data.password;

        let roles = document.createElement("div");
        roles.className =
            "form-group d-flex align-items-center gap-3";

        for (let i = 0; i < data.rolesTotal.length; i++) {
            let role = document.createElement("div");
            role.className = "d-flex align-items-center gap-1";

            let role_check = document.createElement("input");
            role_check.className = "form-check-input";
            role_check.setAttribute(
                "id",
                data.rolesTotal[i].role + data.rolesTotal[i].id
            );
            role_check.setAttribute("type", "checkbox");
            role_check.value = data.rolesTotal[i].role;
            role_check.checked =
                data.roles.find((obj) => {
                    return obj.role == data.rolesTotal[i].role;
                }) != null;

            let role_lbl = document.createElement("label");
            role_lbl.setAttribute(
                "for",
                data.rolesTotal[i].role + data.rolesTotal[i].id
            );
            role_lbl.innerHTML = data.rolesTotal[i].role;

            role.appendChild(role_check);
            role.appendChild(role_lbl);

            roles.appendChild(role);
        }

        let footer = document.createElement("div");
        footer.className = "modal-footer gap-2";

        let title = document.createElement("h5");
        title.className = "modal-title";
        title.innerHTML = "Edit user";

        let close1 = document.createElement("button");
        close1.className = "btn-close";

        let close2 = document.createElement("button");
        close2.className = "btn btn-secondary";
        close2.innerHTML = "Close";

        let save = document.createElement("button");
        save.className = "btn btn-primary";
        save.innerHTML = "Save Changes";

        obj.appendChild(content);

        content.appendChild(header);
        content.appendChild(body);
        content.appendChild(footer);

        header.appendChild(title);
        header.appendChild(close1);

        body.appendChild(username);
        body.appendChild(password);
        body.appendChild(roles);

        footer.appendChild(close2);
        footer.appendChild(save);

        close1.onclick = () => Modal.Close(this);
        close2.onclick = () => Modal.Close(this);
        save.onclick = () => Modal.Save(this);

        this.#Object = obj;
        $(this.#Object)
            .draggable({
                containment: "parent",
                handle: title,
                stack: ".my-modal",
                snap: true,
            })
            .css("position", "absolute");
        Modal.Container.append(this.#Object);
        setTimeout(
            () => this.#Object.classList.remove("appear"),
            Modal.Duration
        );
    }

    static Open(id) {
        if (Modal.Container == null) {
            Modal.Container = document.createElement("div");
            Modal.Container.className = "my-modal-container";
            document.body.appendChild(Modal.Container);
        }

        var result = Modal.Modals.find((obj) => {
            return obj.Id == id;
        });

        if (result == null) {
            GetUserForEdit(id, (data) => Modal.Modals.push(new Modal(data)));
        }
    }

    static Close(obj) {
        obj.Object.classList.add("disappear");
        setTimeout(() => {
            Modal.Container.removeChild(obj.Object);
            const index = Modal.Modals.indexOf(obj);
            if (index > -1) {
                Modal.Modals.splice(index, 1);
            }
        }, Modal.Duration);
    }

    static Save(obj) {
        let roles = [];

        obj.Object.querySelectorAll(".form-check-input").forEach(
            (element) => {
                if (element.checked) {
                    roles.push(element.value);
                }
            }
        );

        $.post("/rest/save/user", {
            id: obj.Id,
            username: obj.Object.querySelector("#username").value,
            password: obj.Object.querySelector("#password").value,
            roles: roles,
            _csrf: GetToken(),
        }).done(() => Modal.Close(obj));
    }
}