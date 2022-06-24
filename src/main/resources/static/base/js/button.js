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

    constructor(col, getObj, objText, onClick, classes) {
        this.#ColumnName = col;
        this.#Object = getObj();
        this.#Object.innerHTML = objText;
        this.#Object.className = classes;
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