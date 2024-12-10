import { API_BASE_URL } from "../../config/apiConfig.js";
import { getFromLocalStorage } from "../utils/storage.js";

const boardsList = document.getElementById("boardsList");
const userNameSpan = document.getElementById("userName");
const logoutButton = document.getElementById("logoutButton");
const boardTitle = document.getElementById("boardTitle");
const boardLayout = document.getElementById("board");

let currentBoardId = null; // Variável para armazenar o ID do board atual

async function loadBoards() {
    try {
        const response = await fetch(`${API_BASE_URL}/Boards`);
        if (!response.ok) {
            throw new Error("Erro ao carregar boards");
        }
        const boards = await response.json();
        populateBoardsDropdown(boards);
    } catch (error) {
        console.error("Erro ao carregar boards:", error);
    }
}

function populateBoardsDropdown(boards) {
    boards.forEach((board) => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `<a class="dropdown-item" id="dropdown-item" value="${board.Id}">${board.Name}</a>`;
        listItem.addEventListener("click", (event) => {
            boardTitle.innerHTML = event.target.innerHTML;
            currentBoardId = board.Id; // Armazena o ID do board selecionado
            loadBoard(board.Id);
        });
        boardsList.appendChild(listItem);
    });
}

async function loadBoard(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/ColumnByBoardId?BoardId=${id}`);
        if (!response.ok) {
            throw new Error("Erro ao carregar colunas");
        }
        const columns = await response.json();
        populateColumns(columns);
    } catch (error) {
        console.error("Erro ao carregar colunas:", error);
    }
}

function populateColumns(columns) {
    boardLayout.innerHTML = "";

    columns.forEach((column) => {
        const columnItem = document.createElement("article");
        columnItem.className = "column-item";
        columnItem.dataset.columnId = column.Id;

        const columnHeader = document.createElement("header");
        columnHeader.className = "column-header";
        columnHeader.innerHTML = `
            <h5>${column.Name}</h5>
            <button class="delete-column-btn">Excluir Coluna</button>
            <button class="add-task-btn">Adicionar Tarefa</button>
        `;

        const columnBody = document.createElement("div");
        columnBody.className = "column-body";
        columnBody.id = `tasks-${column.Id}`;

        columnItem.appendChild(columnHeader);
        columnItem.appendChild(columnBody);
        boardLayout.appendChild(columnItem);

        fetchTasksByColumn(column.Id).then((res) => {
            addTasksToColumn(column.Id, res);
        });

        // Evento para excluir coluna
        columnHeader.querySelector(".delete-column-btn").addEventListener("click", async () => {
            const confirmDelete = confirm(`Tem certeza de que deseja excluir a coluna "${column.Name}"?`);
            if (confirmDelete) {
                try {
                    await deleteColumn(column.Id);
                    // Após excluir a coluna da API, remove a coluna da interface
                    columnItem.remove();
                } catch (error) {
                    console.error("Erro ao excluir a coluna:", error);
                }
            }
        });

        // Evento para adicionar tarefa
        columnHeader.querySelector(".add-task-btn").addEventListener("click", () => {
            addTask(column.Id);
        });
    });
}


function fetchTasksByColumn(columnId) {
    const endpoint = `${API_BASE_URL}/TaskBoard_CS/rest/TaskBoard/TasksByColumnId?ColumnId=${columnId}`;
    return fetch(endpoint)
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Erro ao buscar tasks para ColumnId ${columnId}: ${response.status}`);
            }
            return response.json();
        })
        .catch((error) => {
            console.error(error);
            return [];
        });
}

async function addTask(columnId) {
    const taskTitle = prompt("Digite o título da nova tarefa:");
    if (!taskTitle) return; // Se não houver título, retorna sem fazer nada

    // 1. Cria a tarefa localmente (na interface)
    const columnBody = document.getElementById(`tasks-${columnId}`);
    const taskItem = document.createElement("div");
    taskItem.className = "task-item";
    taskItem.innerHTML = `
        <h6>${taskTitle}</h6>
        <button class="delete-task-btn">Excluir Tarefa</button>
    `;
    taskItem.querySelector(".delete-task-btn").addEventListener("click", () => {
        taskItem.remove();
        deleteTaskFromBackend(columnId, taskItem); // Excluir tarefa do backend quando removida da interface
    });

    columnBody.appendChild(taskItem);

    // 2. Envia a tarefa para o backend (API) para salvar
    addTaskToColumn(columnId, taskTitle);
}

async function addTaskToColumn(columnId, taskTitle) {
    try {
        const response = await fetch(`${API_BASE_URL}/AddTask`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ columnId, taskTitle }),
        });
        if (response.ok) {
            const newTask = await response.json();
            console.log("Tarefa adicionada:", newTask);
        } else {
            throw new Error("Erro ao adicionar tarefa");
        }
    } catch (error) {
        console.error("Erro ao adicionar tarefa:", error);
    }
}

function loadUserName() {
    const userName = getFromLocalStorage("user");
    console.log(userName);
    if (userName.name) {
        userNameSpan.textContent = `Olá, ${userName.name.split(' ')[0]}`;
    } else {
        userNameSpan.textContent = "Usuário não identificado";
    }
}

logoutButton.addEventListener("click", () => {
    localStorage.removeItem("user");
    window.location.href = "index.html";
});

function init() {
    loadUserName();
    loadBoards();
}

init();

function toggleTheme() {
    const isDarkMode = document.getElementById('darkmode-toggle').checked;

    if (isDarkMode) {
        applyTheme('dark');
    } else {
        applyTheme('light');
    }
}

function applyTheme(theme) {
    if (theme === 'dark') {
        document.body.classList.add('dark-theme');
        document.body.classList.remove('light-theme');
    } else if (theme === 'light') {
        document.body.classList.add('light-theme');
        document.body.classList.remove('dark-theme');
    }
    localStorage.setItem('theme', theme);
}

async function loadTheme() {
    try {
        const savedTheme = localStorage.getItem('theme');
        
        if (savedTheme) {
            applyTheme(savedTheme);
            document.getElementById('darkmode-toggle').checked = savedTheme === 'dark';
        } else {
            const response = await fetch('https://personal-ga2xwx9j.outsystemscloud.com/TaskBoard_CS/rest/TaskBoard/Themes');
            const data = await response.json();
            const activeTheme = data.find(theme => theme.Is_Active);

            if (activeTheme) {
                const themeLabel = activeTheme.Label.toLowerCase();
                applyTheme(themeLabel);
                document.getElementById('darkmode-toggle').checked = themeLabel === 'dark';
            }
        }
    } catch (error) {
        console.error('Erro ao carregar o tema:', error.message);
    }
}

document.getElementById('darkmode-toggle').addEventListener('change', toggleTheme);
loadTheme();

const taskBoardContainer = document.getElementById('task-board-container');
const createColumnBtn = document.getElementById('create-column');

// Função para adicionar coluna e salvar na API
async function addColumn(columnName) {
    if (!currentBoardId) {
        alert("Nenhum board selecionado.");
        return;
    }

    if (!columnName) {
        alert("O nome da coluna não pode estar vazio.");
        return;
    }

    try {
        const response = await fetch(`https://personal-ga2xwx9j.outsystemscloud.com/TaskBoard_CS/rest/TaskBoard/Column`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ boardId: currentBoardId, Name: columnName }), // Aqui estamos usando "Name" em vez de "columnName"
        });

        if (response.ok) {
            const newColumn = await response.json();
            console.log("Coluna adicionada:", newColumn);
            loadBoard(currentBoardId); // Recarrega as colunas após adicionar
        } else {
            throw new Error("Erro ao adicionar coluna");
        }
    } catch (error) {
        console.error("Erro ao adicionar coluna:", error);
    }
}

async function deleteColumn(columnId) {
    try {
        const response = await fetch(`https://personal-ga2xwx9j.outsystemscloud.com/TaskBoard_CS/rest/TaskBoard/Column?ColumnId=${columnId}`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        });

        if (!response.ok) {
            throw new Error("Erro ao excluir a coluna");
        }

        console.log(`Coluna com ID ${columnId} excluída com sucesso!`);
    } catch (error) {
        throw new Error(`Falha ao excluir a coluna com ID ${columnId}: ${error.message}`);
    }
}


createColumnBtn.addEventListener('click', () => {
    const columnName = prompt("Digite o nome da nova coluna:");

    if (!columnName) {
        alert("Nome da coluna inválido.");
        return;
    }

    addColumn(columnName);
});

const createBoardButton = document.getElementById('button');
const boardContainer = document.getElementById('board-container');

const apiUrl = 'https://personal-ga2xwx9j.outsystemscloud.com/TaskBoard_CS/rest/TaskBoard';

async function createBoard() {
    const boardName = prompt('Digite o nome da nova board:');
    if (!boardName) {
        alert('O nome da board não pode estar vazio.');
        return;
    }

    const boardData = {
        name: boardName,
    };

    try {
        const response = await fetch(`${apiUrl}/boards`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(boardData),
        });

        if (!response.ok) {
            throw new Error('Erro ao criar a board');
        }

        const board = await response.json();
        console.log('Nova board criada:', board);
        loadBoards();
    } catch (error) {
        console.error('Erro ao criar board:', error);
    }
}

createBoardButton.addEventListener('click', createBoard);
