INSERT INTO tb_usuario(id,email,name,password,username)
values(default,'rafa@gmall.com', 'rafael silva', '$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6', 'rafasilva');

INSERT INTO tb_role(role_id,role_name)
values(1,'ROLE_ADMIN'),(2,'ROLE_USER');

INSERT INTO tb_users_roles(user_id,role_id)
values(1,1),(1,2);

INSERT INTO tb_livro (ano_publicacao,titulo,usuario_id)
values('2022-11-05T01:09:11','the lord of kings',1);

INSERT INTO tb_emprestimo(data_devolucao,data_emprestimo,usuario_id)
values('2022-11-05T01:12:09.477Z','2025-12-06T01:12:09.477Z',1);

INSERT INTO tb_emprestimo_livro(emprestimo_id,livro_id)
values(1,1);

INSERT INTO tb_usuario(id,email,name,password,username)
values(default,'marcos@gmall.com', 'marcos pereira souza', '$2a$10$2n9REGGbEqSHj7fcEEg2heGAzkwcwTnnyKIlQaW21P5QVpwiQOrk6', 'marcospereira');


INSERT INTO tb_users_roles(user_id,role_id)
values(2,2);

INSERT INTO tb_livro (ano_publicacao,titulo,usuario_id)
values('2020-01-05T00:00:00','o poder da acao',2);

INSERT INTO tb_emprestimo(data_devolucao,data_emprestimo,usuario_id)
values('2025-12-05T00:00:00.477Z','2030-12-06T00:00:00.477Z',2);

INSERT INTO tb_emprestimo_livro(emprestimo_id,livro_id)
values(2,2);


