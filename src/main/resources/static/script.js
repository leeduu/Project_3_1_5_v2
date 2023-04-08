
// Вывод всех юзеров в таблицу                                                  // DONE
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
                <td>${user.authorities.map(role => role.name).toString().replaceAll("ROLE_", "").replaceAll(",", ", ")}
                <td>
                <button type="button" id="editUserButton" class="btn btn-info" data-bs-toggle="modal"
                    data-target="#edit" data-action="edit" data-id="${user.id}" onclick="editModal(${user.id})">
                    Edit</button>
                </td>
                <td><button type="button" id="deleteUserButton" class="btn btn-danger" data-bs-toggle="modal"
                    data-target="#delete" data-action="delete" data-id="${user.id}">
                    Delete</button>
                </td>
            </tr>
        `;
        }
        placeholder.innerHTML = out;
    })

// Информация об авторизованном юзере                                           // DONE
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
document.querySelector("#editUserButton").onclick = function() {
    editModal();
}
async function editModal(id) {
    fetch("http://localhost:8080/api/users/" + id)
        .then(function(response) {  // to do sth with server response
            return response.json();
        })
        .then(user => {
            let form = document.forms["editUserForm"];
            form.editUserId.value = user.id;
            form.editUserUsername.value = user.username;
            form.editUserPassword.value = user.password;
            form.editUserEmail.value = user.email;
            fetch("http://localhost:8080/api/roles")
                .then(res => res.json())
                .then(roles => {
                    roles.forEach(role => {
                        let selected = false;
                        let roleToSelect = document.createElement("option");
                        roleToSelect.text = role.name.replace("ROLE_");
                        roleToSelect.value = role.id;
                        // if (roleToSelect) selected.selected = true;
                        // $('#editUserRoles')[0].appendChild(selected);
                    })
                })

            // alert(user.email);

            $('#editUserModal').modal('show');
        })
}






// EXAMPLE  ================================================================
// console.log("change started")
//
// $(document).on('click', '#changeButton', function() {
//     let id = $(this).attr('data-userID');
//     console.log('Кнопка ИЗМЕНИТЬ пользователя №' + id + ' нажата!');
//     let url = 'http://localhost:8080/api/users/' + id;
//     let body = 'Loading user #' + id + 'data...'
//     fetch(url)
//         .then(response => response.json())
//         .then(data => changingData(data))
//         .catch(error => console.log(error))
//
//     document.getElementById('changing').innerHTML = body; });
//
// console.log("change ended")
// =========================================================================

// New User                                                         // юзер не добавился, в адресной строке
// fetch('http://localhost:8080/api/users/new', {
//     method: 'POST',
//     body: new FormData( document.getElementById('newUserForm') )
// });

