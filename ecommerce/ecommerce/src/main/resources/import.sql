insert into categoria (nome, descricao) values ('Tecnologia', 'Produtos tecnológicos e eletrônicos');
insert into categoria (nome, descricao) values ('Games', 'Produtos para jogos e entretenimento');
insert into categoria (nome, descricao) values ('Casa', 'Produtos para casa e decoração');
insert into categoria (nome, descricao) values ('Esportes', 'Produtos esportivos e acessórios');
insert into categoria (nome, descricao) values ('Áudio', 'Equipamentos de áudio e som');


insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Notebook Lenovo', 'Notebook com processador Intel e 8GB de memória RAM', 3499.90, 12, 1);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Monitor Gamer', 'Monitor gamer de 24 polegadas com alta resolução', 899.90, 20, 2);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Cadeira Gamer', 'Cadeira gamer confortável com ajuste de altura', 1199.90, 8, 2);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Luminária LED', 'Luminária LED para mesa com diferentes níveis de iluminação', 89.90, 35, 3);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Halteres 10kg', 'Par de halteres de 10kg para exercícios físicos', 249.90, 15, 4);


insert into cliente (nome, email, telefone) values ('Lucas Almeida', 'lucas.almeida@email.com', '14988881111');
insert into cliente (nome, email, telefone) values ('Beatriz Costa', 'beatriz.costa@email.com', '14988882222');
insert into cliente (nome, email, telefone) values ('Rafael Martins', 'rafael.martins@email.com', '14988883333');
insert into cliente (nome, email, telefone) values ('Juliana Ferreira', 'juliana.ferreira@email.com', '14988884444');
insert into cliente (nome, email, telefone) values ('Marcos Pereira', 'marcos.pereira@email.com', '14988885555');


insert into pedido (data, status, valor_total, cliente_id) values ('2026-09-10 09:30:00', 'PAGO', 3499.90, 1);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-09-11 13:45:00', 'PENDENTE', 899.90, 2);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-09-12 15:20:00', 'PAGO', 1199.90, 3);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-09-13 10:10:00', 'ENVIADO', 179.80, 4);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-09-14 17:30:00', 'PENDENTE', 249.90, 5);


insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (1, 3499.90, 1, 1);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (1, 899.90, 2, 2);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (1, 1199.90, 3, 3);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (2, 89.90, 4, 4);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (1, 249.90, 5, 5);


insert into pagamento (valor, data, status, tipo, pedido_id) values (3499.90, '2026-09-10 09:35:00', 'APROVADO', 'PIX', 1);
insert into pagamento (valor, data, status, tipo, pedido_id) values (899.90, '2026-09-11 13:50:00', 'PENDENTE', 'CARTAO', 2);
insert into pagamento (valor, data, status, tipo, pedido_id) values (1199.90, '2026-09-12 15:25:00', 'APROVADO', 'CARTAO', 3);
insert into pagamento (valor, data, status, tipo, pedido_id) values (179.80, '2026-09-13 10:15:00', 'APROVADO', 'PIX', 4);
insert into pagamento (valor, data, status, tipo, pedido_id) values (249.90, '2026-09-14 17:35:00', 'PENDENTE', 'PIX', 5);