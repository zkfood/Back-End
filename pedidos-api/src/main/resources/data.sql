INSERT INTO tipo_produdo (nome) VALUES
    ('Pratos'),
    ('Porções'),
    ('Bebidas');

INSERT INTO usuario (nome, email, senha, cpf, autenticado) VALUES
    ('Ale', 'ale@email', 'senha', '02533705071', 'false');

INSERT INTO telefone (numero, usuario_id) VALUES
    ('12345678910', 1);

INSERT INTO endereco (cep, rua, bairro, numero, complemento, usuario_id) VALUES
    ('08160475', 'Rua Fred Astaire', 'Jardim Silva Teles', '123', 'complemento', 1);

INSERT INTO produto (nome, disponibilidade, tipo_produto, descricao, valor, qtd_pessoas, imagem) VALUES
    ('Produto Exemplo', true, 1, 'Descrição do produto exemplo', 20.00, '2', '');