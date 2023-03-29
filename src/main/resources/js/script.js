const URL = 'http://localhost:8080/api/users';
let result = '';

const show = (allUsers) => {
    const container = document.getElementById("allUsersTableBody");
    const users = Array.from(allUsers);
    users.forEach(user => {
        result += `<tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.email}</td>
        <td>${user.role.toString()}</td>
        <td>
            <button type="button" class="btn btn-info" data-bs-toggle="modal" th:data-bs-target="${'#editModal'+user.id}">
            Edit</button>
        </td>
        <td>
            <button type="button" class="btn btn-danger" data-bs-toggle="modal"th:data-bs-target="${'#deleteModal'+user.id}">-->
            Delete</button>
        </td>      
 </tr>`
    });
    container.innerHTML = result;
};

fetch(URL, {headers: {'Content-type': 'application/json'},}
)
    .then(response => response.json())
    .then(data => show(data));