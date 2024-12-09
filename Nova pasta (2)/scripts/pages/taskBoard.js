import { API_BASE_URL } from "../../config/apiConfig.js";
import { getFromLocalStorage } from "../utils/storage.js";

const boardsList = document.getElementById("boardsList");
const userNameSpan = document.getElementById("userName");
const logoutButton = document.getElementById("logoutButton");
const boardTitle = document.getElementById("boardTitle");
const boardLayout = document.getElementById("board");

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
            // console.log(board.Id)
            boardTitle.innerHTML = event.target.innerHTML;
            loadBoard(board.Id);
        })
        boardsList.appendChild(listItem);
    });
}

async function loadBoard(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/ColumnByBoardId?BoardId=${id}`)
        if(!response.ok) {
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

        const columnHeader = document.createElement("header");
        columnHeader.className = "column-header";
        columnHeader.innerHTML = `<h5>${column.Name}</h5>`;

        const columnBody = document.createElement("div");
        columnBody.className = "column-body";
        columnBody.id = `tasks-${column.Id}`;


        columnItem.appendChild(columnHeader);
        columnItem.appendChild(columnBody);


        boardLayout.appendChild(columnItem);

        fetchTasksByColumn(column.Id).then((res)=>{
            addTasksToColumn(column.Id, res)
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

function addTasksToColumn(columnId, tasks) {
    const columnBody = document.getElementById(`tasks-${columnId}`);

    tasks.forEach((task) => {
        const taskItem = document.createElement("div");
        taskItem.className = "task-item";
        taskItem.innerHTML = `
            <h6>${task.Title || "Sem título"}</h6>
            <p>${task.Description || "Sem descrição"}</p>
        `;
        columnBody.appendChild(taskItem);
    });
}

function loadUserName() {
    const userName = getFromLocalStorage("user");
    console.log(userName)
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

// Função para aplicar o tema
function applyTheme(theme) {
    if (theme === 'dark') {
        document.body.classList.add('dark-theme');
        document.body.classList.remove('light-theme');
    } else if (theme === 'light') {
        document.body.classList.add('light-theme');
        document.body.classList.remove('dark-theme');
    }
    // Opcional: Salvar no localStorage
    localStorage.setItem('theme', theme);
}

// Função para carregar o tema salvo ou da API
async function loadTheme() {
    try {
        // Recuperar tema salvo no localStorage
        const savedTheme = localStorage.getItem('theme');
        
        if (savedTheme) {
            applyTheme(savedTheme);
            document.getElementById('darkmode-toggle').checked = savedTheme === 'dark';
        } else {
            // Buscar tema pela API
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

// Adicionar evento ao checkbox
document.getElementById('darkmode-toggle').addEventListener('change', toggleTheme);

// Carregar tema ao iniciar a página
loadTheme();


const taskBoardContainer = document.getElementById('task-board-container');
const createColumnBtn = document.getElementById('create-column');

// Função para criar um bloco de tarefas
function createTaskBlock() {
    const blockTitle = prompt('Digite o nome do bloco de tarefas:');
    if (!blockTitle) return;

    const block = document.createElement('div');
    block.classList.add('task-block');

    block.innerHTML = `
        <h3>${blockTitle}</h3>
        <ul class="task-list"></ul>
        <button class="add-task-btn">Adicionar Tarefa</button>
        <button class="delete-block-btn">Excluir Bloco</button>
    `;

    // Eventos para os botões
    const addTaskBtn = block.querySelector('.add-task-btn');
    const deleteBlockBtn = block.querySelector('.delete-block-btn');

    addTaskBtn.addEventListener('click', () => addTask(block));
    deleteBlockBtn.addEventListener('click', () => deleteBlock(block));

    taskBoardContainer.appendChild(block);

    // Salvar no Local Storage e na API
    saveBlocksToLocalStorage();
    sendToAPI(getBlocksData());
}

// Função para adicionar uma tarefa
function addTask(block) {
    const taskName = prompt('Digite o nome da tarefa:');
    if (!taskName) return;

    const taskList = block.querySelector('.task-list');
    const task = document.createElement('li');
    task.textContent = taskName;

    // Permitir remover tarefa ao clicar nela
    task.addEventListener('click', () => {
        task.remove();
        saveBlocksToLocalStorage();
        sendToAPI(getBlocksData());
    });

    taskList.appendChild(task);

    // Salvar no Local Storage e na API
    saveBlocksToLocalStorage();
    sendToAPI(getBlocksData());
}

// Função para excluir um bloco de tarefas
function deleteBlock(block) {
    if (confirm('Tem certeza de que deseja excluir este bloco?')) {
        block.remove();
        saveBlocksToLocalStorage();
        sendToAPI(getBlocksData());
    }
}

// Função para salvar blocos no Local Storage
function saveBlocksToLocalStorage() {
    const blocks = getBlocksData();
    localStorage.setItem('taskBlocks', JSON.stringify(blocks));
}

// Função para carregar blocos do Local Storage
function loadBlocksFromLocalStorage() {
    const savedBlocks = JSON.parse(localStorage.getItem('taskBlocks')) || [];
    savedBlocks.forEach((blockData) => {
        const block = document.createElement('div');
        block.classList.add('task-block');

        block.innerHTML = `
            <h3>${blockData.title}</h3>
            <ul class="task-list">${blockData.tasks.map(task => `<li>${task}</li>`).join('')}</ul>
            <button class="add-task-btn">Adicionar Tarefa</button>
            <button class="delete-block-btn">Excluir Bloco</button>
        `;

        // Eventos para os botões
        const addTaskBtn = block.querySelector('.add-task-btn');
        const deleteBlockBtn = block.querySelector('.delete-block-btn');

        addTaskBtn.addEventListener('click', () => addTask(block));
        deleteBlockBtn.addEventListener('click', () => deleteBlock(block));

        const taskList = block.querySelector('.task-list');
        taskList.querySelectorAll('li').forEach((task) => {
            task.addEventListener('click', () => {
                task.remove();
                saveBlocksToLocalStorage();
                sendToAPI(getBlocksData());
            });
        });

        taskBoardContainer.appendChild(block);
    });
}

// Função para obter os dados dos blocos
function getBlocksData() {
    const blocks = [];
    const taskBlocks = document.querySelectorAll('.task-block');

    taskBlocks.forEach((block) => {
        const title = block.querySelector('h3').textContent;
        const tasks = Array.from(block.querySelectorAll('.task-list li')).map(task => task.textContent);
        blocks.push({ title, tasks });
    });

    return blocks;
}

// Função para enviar dados para a API
async function sendToAPI(data) {
    try {
        const response = await fetch('https://personal-ga2xwx9j.outsystemscloud.com/TaskBoard_CS/rest/TaskBoard', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ blocks: data }),
        });
        const result = await response.json();
        console.log('Dados enviados para a API:', result);
    } catch (error) {
        console.error('Erro ao enviar dados para a API:', error);
    }
}

// Evento para criar nova coluna
createColumnBtn.addEventListener('click', createTaskBlock);

// Carregar blocos ao iniciar
loadBlocksFromLocalStorage();






// Elementos DOM
const createBoardButton = document.getElementById('button');
const boardContainer = document.getElementById('board-container');

// URL da API
const apiUrl = 'https://personal-ga2xwx9j.outsystemscloud.com/TaskBoard_CS/rest/TaskBoard';

// Função para criar uma nova board
async function createBoard() {
    const boardName = prompt('Digite o nome da nova board:');
    if (!boardName) {
        alert('O nome da board não pode estar vazio.');
        return;
    }

    // Dados da board
    const boardData = {
        name: boardName,
    };

    try {
        // Enviar solicitação POST para a API
        const response = await fetch(`${apiUrl}/boards`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(boardData),
        });

        // Verificar a resposta
        if (response.ok) {
            const newBoard = await response.json();
            alert(`Board criada com sucesso: ${newBoard.name}`);
            displayBoard(newBoard); // Mostrar na interface
        } else {
            const error = await response.json();
            alert(`Erro ao criar a board: ${error.message}`);
        }
    } catch (error) {
        console.error('Erro ao conectar à API:', error);
        alert('Não foi possível criar a board. Verifique sua conexão.');
    }
}

// Função para exibir uma board criada na interface
function displayBoard(board) {
    const boardElement = document.createElement('div');
    boardElement.textContent = board.name;
    boardElement.style.border = '1px solid #ccc';
    boardElement.style.padding = '10px';
    boardElement.style.margin = '10px 0';
    boardContainer.appendChild(boardElement);
}

// Evento para criar uma nova board ao clicar no botão
createBoardButton.addEventListener('click', createBoard);
