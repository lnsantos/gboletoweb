
create database boleto_faj;
use boleto_faj;

-- Administrador (TUDO), Gerente(ADICIONA ITENS), 
create table usuario(
	codigo integer primary key auto_increment,
    usuario varchar(64) not null unique , -- Utiliza para fazer o login, n�o pode ser igual
	nome varchar(64) not null,
    sobrenome varchar(64) default "",
    email varchar(64) not null unique , -- n�o pode ser igual
    senha varchar(32) not null -- Utiliza para fazer o login
);
-- drop table boleto;

create table boleto(
	codigo integer primary key auto_increment,
    nome_item varchar(64),
    valor varchar(64),
    vencimento long,
    statu int default 0,
    emissao long,
    id_usuario int,
    caminho text
    -- --------------------------------------
    -- Explica��es do STATUS ----------------
	-- 0 = Boleto pendente ( VERDE )                  -
    -- 1 = Falta 3 dias para paga o boleto ( AMARELO )  -
    -- 2 = atrasado ( VERMELHO )                         -
    -- --------------------------------------
);
select * from boleto;
-- UPDATE permissao SET statu = 1 WHERE codigo =  1;
-- SELECT  * FROM boleto as b WHERE b.id_usuario = '1' AND b.statu = 0 OR b.statu = 1 OR b.statu = 2 ORDER BY b.vencimento

create table permissao(
    -- --------------------------------------
    -- Explica��es do CODIGO ----------------
    -- o codigo do usu�rio deve ser inserido-
    -- ap�s a cria��o da conta no sistema   -
    -- --------------------------------------
	codigo integer primary key unique ,
    statu int default 0, 
    -- --------------------------------------
    -- Explica��es do STATUS ----------------
	-- 0 = Aguardando libera��o de acesso   -
    -- 1 = Habilitado a utilizar o sistema  -
    -- 2 = Desabilitado a utilizar o sistema-
    -- --------------------------------------
    -- Explica��es das Permiss�es -----------
    -- 0 = N�o habilitado                   -
    -- 1 = Habilitado ao usuario            -
	-- --------------------------------------
    cria_usuario int default 0,
    cria_boleto int default 0,
    deleta_usuario int default 0,
    deleta_boleto int default 0,
    edita_usuario int default 0,
    edita_boleto int default 0,
    foreign key (codigo) references usuario(codigo)
);