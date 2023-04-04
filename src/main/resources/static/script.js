
// Вывод всех юзеров в таблицу
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
$(async function() {
    await authUser();
});
async function authUser() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(authentication => {
            $('#authUserEmail').append(authentication.email);
            let roles = authentication.authorities.map(auth => auth.authority).toString().replaceAll("ROLE_", "").replaceAll(",", ", ");
            $('#authUserRoles').append(roles);

            let authUser = `$(
            <tr>
                <td>${authentication.id}</td>
                <td>${authentication.username}</td>
                <td>${authentication.email}</td>
                <td>${roles}</td>)`;
            $('#userProfileTableBody').append(authUser);
        })
}

// Модальное окно Edit user



