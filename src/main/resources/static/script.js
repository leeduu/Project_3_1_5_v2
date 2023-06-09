// Вывод всех юзеров в таблицу
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

// Вывод ролей в form-select
fetch("http://localhost:8080/api/roles")
    .then(response => response.json())
    .then(roles => {
        roles.forEach(role => {
            $('.form-select').append($('<option/>').attr("value", role.id).text(role.name.replace("ROLE_", "")));
        })
    })

// Модальное окно Edit user
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
        if (editForm.roles === undefined){
            editRolesArray.push({
                id: 1,
                name: "ROLE_USER"
            })
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

// Модальное окно Delete user
$(document).ready(function() {
    const db = document.getElementById('deleteUserButton');
    if(db) {
        db.addEventListener('click', deleteUser);
    }
});

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
$(document).ready(function() {
    const nb = document.getElementById('submitNewUserButton');
    if(nb) {
        nb.addEventListener('click', newUser);
    }
});

async function newUser() {
    let newForm = document.querySelector("#newUserForm");

    newForm.addEventListener('submit', (e) => {
        e.preventDefault();
        let newRolesArray = [];
        if (newForm.newRoles !== undefined) {
            for (let i = 0; i < newForm.newRoles.length; i++) {
                if (newForm.newRoles[i].selected) newRolesArray.push({
                    id: newForm.newRoles[i].value,
                    name: "ROLE_" + newForm.newRoles[i].text
                })
            }
        }
        if (newForm.roles === undefined){
            newRolesArray.push({
                id: 1,
                name: "ROLE_USER"
            })
        }
        console.log("newRolesArray")
        fetch("http://localhost:8080/api/users/new", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: newForm.newUsername.value,
                email: newForm.newEmail.value,
                password: newForm.newPassword.value,
                roles: newRolesArray
            })
        }).then(res => res.json())
            .then(() => {
                allUsers();
                $('#users-table').click();
                newForm.reset();
            });

    })
}
