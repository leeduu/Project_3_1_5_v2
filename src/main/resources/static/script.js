
// Вывод всех юзеров в таблицу
// $(async function() {
//     await allUsers();
// });
fetch("http://localhost:8080/api/users")
    .then(function(response) {  // to do sth with server response
        return response.json();
    })
    .then(function(users) {
        let placeholder = document.querySelector("#allUsersTableBody"); // access to the table body element
        let out = "";
        for (let user of users) {
                out += `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.authorities.map(role => role.name).toString().replaceAll("ROLE_", "").replaceAll(",", ", ")}<td>
                <button type="button" id="editUserButton" class="btn btn-info" data-bs-toggle="modal"
                    th:data-bs-target="${'#editUserModal' + user.id}" onclick="editUserModal">
                    Edit</button>
                </td>
                <td><button type="button" id="deleteUserButton" class="btn btn-danger" data-bs-toggle="modal"
                     th:data-bs-target="${'#deleteUserModal' + user.id}">
                     Delete</button>
                </td>
            </tr>
        `;
        }
        placeholder.innerHTML = out;
    })

// Информация об авторизованном юзере
fetch("http://localhost:8080/api/user")
    .then(function(response) {  // to do sth with server response
        return response.json();
    })
    .then(function(user) {
        let placeholder = document.querySelector("#authUserRoles"); // access to the table body element
        let out = "";
        // for (let user of user) {
            out += `
            <a ${user.email}></a>
        `;
        // }
        placeholder.innerHTML = out;
    })

// Модальное окно Edit user
// const form = document.getElementById("editUserModal");
// function retrieveFormValue(event) {
//     event.preventDefault();
//     const {username, password, email, roles} = form;
//     const values = {
//         username: username.value,
//         password: password.value,
//         email: email.value,
//         roles: roles.name,
//     };
// }
// form.addEventListener("submit", retrieveFormValue);


