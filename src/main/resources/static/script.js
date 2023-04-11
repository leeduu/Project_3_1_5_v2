// Вывод всех юзеров в таблицу                                                  // DONE
$(async function() {
    await allUsers();
});
async function allUsers() {
    fetch("http://localhost:8080/api/users")
        .then(function (response) {  // to do sth with server response
            return response.json();
        })
        .then(function (users) {
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
                    data-target="#edit" data-action="edit" data-id="${user.id}" onclick="editUser(${user.id})">
                    Edit</button>
                </td>
                <td><button type="button" id="deleteUserButton" class="btn btn-danger" data-bs-toggle="modal"
                    data-target="#delete" data-action="delete" data-id="${user.id}" onclick="deleteUser(${user.id})">
                    Delete</button>
                </td>
            </tr>
        `;
            }
            placeholder.innerHTML = out;
        })
}

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

// Модальное окно Edit user                                                          // DONE
const editUserButton = document.querySelector('#editUserButton');
editUserButton.addEventListener('click', editUser);

async function editUser(id) {
    let form = document.querySelector("#editUserForm");
    fetch("http://localhost:8080/api/users/" + id)
        .then(function (response) {  // to do sth with server response
            return response.json();
        })
        .then(user => {

            form.editUserId.value = user.id;
            form.editUserUsername.value = user.username;
            form.editUserPassword.value = user.password;
            form.editUserEmail.value = user.email;
            fetch("http://localhost:8080/api/roles")
                .then(res => res.json())
                .then(roles => {
                    roles.forEach(role => {
                        $('#editUserRoles').append($('<option/>').attr("value", role.id).text(role.name.replace("ROLE_", "")));
                    })
                })
            $('#editUserModal').modal('show');
        });
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        let editRoles = [];
        if (form.roles !== undefined) {
            for (let i = 0; i < form.roles.length; i++) {
                if (form.roles[i].selected) editRoles.push({
                    id: form.roles[i].value,
                    name: "ROLE_" + form.roles[i].text
                })
            }
        }
        fetch("http://localhost:8080/api/users/" + id, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: form.editUserId.value,
                username: form.editUserUsername.value,
                email: form.editUserEmail.value,
                password: form.editUserPassword.value,
                roles: editRoles
            })
        }).then(res => res.json())
            .then(() => {
            $('#closeEditUserFormButton').click();
            allUsers();
        })
    })
}

// Модальное окно Delete user                                                         // DONE
// $(async function() {
//     await deleteUser();
// });
const deleteUserButton = document.querySelector('#deleteUserButton');
deleteUserButton.addEventListener('click', deleteUser);

async function deleteUser(id) {
    let editForm = document.querySelector("#deleteUserForm");
    fetch("http://localhost:8080/api/users/" + id)
        .then(function (response) {  // to do sth with server response
            return response.json();
        })
        .then(user => {

            editForm.deleteUserId.value = user.id;
            editForm.deleteUseUsername.value = user.username;
            editForm.deleteUserPassword.value = user.password;
            editForm.deleteUserEmail.value = user.email;
            fetch("http://localhost:8080/api/roles")
                .then(res => res.json())
                .then(roles => {
                    roles.forEach(role => {
                        $('#deleteUserRoles').append($('<option/>').attr("value", role.id).text(role.name.replace("ROLE_", "")));
                    })
                })
            $('#deleteUserModal').modal('show');
        });
    editForm.addEventListener('submit', (e) => {
        e.preventDefault()
        fetch("http://localhost:8080/api/users/" + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(() => {
                allUsers();
            })
    })
}

// New User                                              // ID увеличивается на 3
$(async function() {                                     // в базе создается лишняя таблица
    await newUser();                                     // ПЕРЕСТАЛ ПРЕДЛАГАТЬ РОЛИ
});
// // const newUserButton = document.querySelector('#new-user');
// // newUserButton.addEventListener('click', newUser);
async function newUser() {
    let newForm = document.querySelector("#newUserForm");
    fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                $('#newRoles').append($('<option/>').attr("value", role.id).text(role.name.replace("ROLE_", "")));
            })
        });
    // $('#newUserForm').show();


    newForm.addEventListener("submit", event => {
        event.preventDefault();

        let roles = [];
        if (newForm.roles !== undefined) {
            for (let i = 0; i < newForm.roles.length; i++) {
                if (newForm.roles[i].selected) roles.push({
                    id: newForm.roles[i].value,
                    name: "ROLE_" + newForm.roles[i].text
                })
            }
        }
        fetch("http://localhost:8080/api/users/new", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: newForm.newUsername.value,
                email: newForm.newEmail.value,
                password: newForm.newPassword.value,
                roles: roles
            })
        }).then(res => res.json())
          .then(() => {
              newForm.reset();
              allUsers();
              $('#users-table').click();
          })
    });
}

