//logica pra abrir o menu e fechar 
const hamburguerOpen = document.getElementById('hamburguer');
const hamburguerClose = document.getElementById('hamburguer-close');
const afterClick = document.getElementById('afterClick');

// Função para abrir o menu
function openMenu() {
    afterClick.classList.add('active');
}

// Função para fechar o menu
function closeMenu() {
    afterClick.classList.remove('active');
}

// Adiciona o evento para abrir o menu
hamburguerOpen.addEventListener('click', openMenu);

// Adiciona o evento para fechar o menu
hamburguerClose.addEventListener('click', closeMenu);