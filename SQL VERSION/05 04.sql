drop database boleto_faj;
create database boleto_faj;
use boleto_faj;

-- Administrador (TUDO), Gerente(ADICIONA ITENS), 
create table usuario(
	codigo integer primary key auto_increment,
    usuario varchar(64) not null unique , -- Utiliza para fazer o login, não pode ser igual
	nome varchar(64) not null,
    sobrenome varchar(64) default "",
    email varchar(64) not null unique , -- não pode ser igual
    senha varchar(32) not null -- Utiliza para fazer o login
);

create table boleto(
	codigo integer primary key auto_increment,
    nome_item varchar(64),
    valor varchar(64),
    vencimento long,
    statu int default 0,
    emissao long,
    id_usuario int
    -- --------------------------------------
    -- Explicações do STATUS ----------------
	-- 0 = Boleto pendente                  -
    -- 1 = Falta 3 dias para paga o boleto  -
    -- 2 = atrasado                         -
    -- --------------------------------------
);
-- SELECT * FROM boleto WHERE nome_item = 'Netell' AND valor = 0.0 AND vencimento = 1556593200000 AND emissao = 1555642800000 AND id_usuario = 0;
create table upload_boleto(
	codigo integer primary key auto_increment,
    id_usuario integer,
    id_boleto integer,
    caminho varchar(120),
    
    foreign key (id_usuario) references usuario(codigo),
    foreign key (id_boleto) references boleto(codigo)
);

create table permissao(
    -- --------------------------------------
    -- Explicações do CODIGO ----------------
    -- o codigo do usuário deve ser inserido-
    -- após a criação da conta no sistema   -
    -- --------------------------------------
	codigo integer primary key unique ,
    statu int default 0, 
    -- --------------------------------------
    -- Explicações do STATUS ----------------
	-- 0 = Aguardando liberação de acesso   -
    -- 1 = Habilitado a utilizar o sistema  -
    -- 2 = Desabilitado a utilizar o sistema-
    -- --------------------------------------
    -- Explicações das Permissões -----------
    -- 0 = Não habilitado                   -
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

-- INSERT INTO usuario(usuario,nome,sobrenome,email,senha) VALUE('adm','adm','adm','123','123');
-- UPDATE permissao set statu = 1 where codigo = 1;
-- select codigo as c from usuario where usuario = "Pedro" and email = "159159159";
SELECT * FROM boleto WHERE nome_item = "asdasds" AND valor = 0.0 
AND vencimento = 1555383600000 AND emissao = 1555642800000 AND id_usuario = 0;
-- SELECT usuario as u, email as e FROM usuario;
SELECT * FROM usuario;
SELECT * FROM boleto;
SELECT * FROM permissao;
UPDATE statu 
SELECT * FROM upload_boleto;

-- select * from permissao;
-- drop table boleto;

-- ALTER TABLE boleto DROP FOREIGN KEY id_usuario;
SELECT * FROM boleto WHERE statu = 0 OR statu = 1 OR statu = 2;
INSERT INTO upload_boleto VALUE(0,0,3,"c:/teste")

insert into permissao(codigo) values (1);
-- delete from permissao where codigo = 1;
