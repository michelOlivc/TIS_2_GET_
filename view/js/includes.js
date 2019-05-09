
$(document).ready(function() {
    includeImports();
    includeHeader();
})

function includeImports() {
    var content = `
    <link href="../bootstrap-4.3.1/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="../css/estilo.css" rel="stylesheet" type="text/css">
    `;

    $("head").append(content);
}

function includeHeader() {
    var content = `
    <div id="logo"></div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-custom">
    <div class="collapse navbar-collapse" id="conteudoNavbarSuportado">
        <ul class="nav nav-pills nav-fill">
            <li class="nav-item">
                <a class="nav-link" href="medico.html">Situação médica</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="cartoes.html">Cartões</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="estatisticas.html">Estatísticas</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="escalacao.html">Escalação</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Cadastros
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="jogadores.html">Jogadores</a>
                    <a class="dropdown-item" href="time.html">Time</a>
                </div>
            </li>
        </ul>
    </div>
    </nav>
    `;

    $("header").html(content);
}