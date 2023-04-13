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

// Вывод ролей в form-select
fetch("http://localhost:8080/api/roles")
    .then(response => response.json())
    .then(roles => {
        roles.forEach(role => {
            $('.form-select').append($('<option/>').attr("value", role.id).text(role.name.replace("ROLE_", "")));
        })
    })

// Модальное окно Edit user                                                          // DONE
// const eb = document.getElementById("editUserButton");
// eb.addEventListener("click", async function() {
//     await editUser();
// });

// $(async function() {
//     await editUser();
// });

$(document).ready(function() {
    const eb = document.getElementById('editUserButton');
    if (eb) {
        eb.addEventListener('click', editUser);
    }
});

async function editUser(id) {
    let editForm = document.getElementById("editUserForm");
    fetch("http://localhost:8080/api/users/" + id)
        .then(function (response) {  // to do sth with server response
            return response.json();
        })
        .then(user => {

            editForm.editUserId.value = user.id;
            editForm.editUserUsername.value = user.username;
            editForm.editUserPassword.value = user.password;
            editForm.editUserEmail.value = user.email;

            $('#editUserModal').modal('show');
        });
    editForm.addEventListener('submit', (e) => {
        e.preventDefault();
        let editRolesArray = [];
        if (editForm.roles !== undefined) {
            for (let i = 0; i < editForm.roles.length; i++) {
                if (editForm.roles[i].selected) editRolesArray.push({
                    id: editForm.roles[i].value,
                    name: "ROLE_" + editForm.roles[i].text
                })
            }
        }
        fetch("http://localhost:8080/api/users/" + id, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.editUserId.value,
                username: editForm.editUserUsername.value,
                email: editForm.editUserEmail.value,
                password: editForm.editUserPassword.value,
                roles: editRolesArray
            })
        }).then(res => res.json())
            .then(() => {
            $('#closeEditUserFormButton').click();
            allUsers();
        })
    })
}

// Модальное окно Delete user                                                         // DONE
// const db = document.getElementById("deleteUserButton");
// db.addEventListener("click", async function() {
//     await deleteUser();
// });

$(document).ready(function() {
    const db = document.getElementById('deleteUserButton');
    if(db) {
        db.addEventListener('click', deleteUser);
    }
});

// $(async function() {
//     await deleteUser();
// });

// const deleteUserButton = document.getElementById('deleteUserButton');
// deleteUserButton.addEventListener('click', deleteUser);

async function deleteUser(id) {
    let deleteForm = document.getElementById("deleteUserForm");
    fetch("http://localhost:8080/api/users/" + id)
        .then(function (response) {  // to do sth with server response
            return response.json();
        })
        .then(user => {

            deleteForm.deleteUserId.value = user.id;
            deleteForm.deleteUseUsername.value = user.username;
            deleteForm.deleteUserPassword.value = user.password;
            deleteForm.deleteUserEmail.value = user.email;

            $('#deleteUserModal').modal('show');
        });
    deleteForm.addEventListener('submit', (e) => {
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

// New User
//                                                                         Не записывается
//                                                                         ID увеличивается на 3
//                                                                         в базе создается лишняя таблица
// const newUserButton = document.querySelector('#new-user');
// newUserButton.addEventListener('click', newUser);
//
// async function newUser() {
//     let newForm = document.querySelector("#newUserForm");
//
//     newForm.addEventListener('submit', (e) => {
//         e.preventDefault();
//         console.log("hiii1")
//         let newRolesArray = [];
//         if (newForm.newRoles !== undefined) {
//             for (let i = 0; i < newForm.newRoles.length; i++) {
//                 if (newForm.newRoles[i].selected) newRolesArray.push({
//                     id: newForm.newRoles[i].value,
//                     name: "ROLE_" + newForm.newRoles[i].text
//                 })
//             }
//         }
//         fetch("http://localhost:8080/api/users/new", {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({
//                 username: newForm.newUsername.value,
//                 email: newForm.newEmail.value,
//                 password: newForm.newPassword.value,
//                 roles: newRolesArray
//             })
//         }).then(res => res.json())
//             .then(() => {
//                 $('#users-table').click();
//                 newForm.reset();
//             });
//
//     })
// }


// const newForm = document.getElementById("#newUserForm");
// newForm.addEventListener("submit", event => {
//     event.preventDefault(); // prevent the form from submitting normally
//     newUser();
//     console.log("test")
// });

// const nb = document.getElementById("submitNewUserButton");
// nb.addEventListener("click", async function() {
//     await newUser();
// });

// $(async function() {
//     await newUser();
// });

$(document).ready(function() {
    const nb = document.getElementById('submitNewUserButton');
    nb.addEventListener('click', newUser);
});

async function newUser() {
    const username = document.getElementById("#newUsername");
    const email = document.getElementById("#newEmail");
    const password = document.getElementById("#newPassword");
    const roles = document.getElementById("#newRoles");
    const newForm = document.getElementById("#newUserForm");

    newForm.addEventListener('submit', (e) => {
        e.preventDefault();

        let newRolesArray = [];
        if (newForm.newRoles !== undefined) {
            for (let i = 0; i < newForm.newRoles.options.length; i++) {
                if (newForm.newRoles.options[i].selected) newRolesArray.push({
                    id: newForm.newRoles.options[i].value,
                    name: "ROLE_" + newForm.newRoles.options[i].text
                })
            }
        }

        const user = {
            username: username.value,
            email: email.value,
            password: password.value,
            roles: roles
        };

        fetch("http://localhost:8080/api/users/new", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
            .then(res => res.json())
            .then(() => {
                $('#users-table').click();
                newForm.reset();
            });

        // $.ajax({
        //     type: "POST",
        //     url: "http://localhost:8080/api/users/new",
        //     data: user,
        //     dataType: "json",
        //     encode: true,
        // }).done(function (data) {
        //     console.log(data);
        // });
    });
}

