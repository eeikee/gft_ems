insert into gft(id,cep, cidade, endereco, estado, nome, telefone)
values
(1,"18095-450","Sorocaba","Av. São Francisco, 98","SP", "GFT Sorocaba", "11 2176-3253"),
(2,"06454-000","Barueri","Alameda Rio Negro, 585","SP", "GFT Alphaville", "11 2176-3253"),
(3,"80250-210","Rebouças","Torre Trinity Corporate, Av. Sete de Setembro, 2451 - 6º andar","PR", "GFT Curitiba", "41 4009-5700");

insert into tecnologia(id,nome)
values(1,"JAVA"),(2,"Thymeleaf"),(3,"Spring"),(4,".NET"),(5,"C#"),(6,"JavaScript"),
(7,"Node.js"),(8,"Kotlin"),(9,"CSS"),(10,"HTML"),(11,"Python");

insert into vaga(id,codigo,abertura_vaga,descricao,projeto,qtd_vaga)
values(1,"","2020-11-13","", "",-1);

insert into vaga(id,codigo,abertura_vaga,descricao,projeto,qtd_vaga)
values(2,"GFT2011131","2020-11-13","Analista Desenvolvedor de sitemas Senior", "Itaú",1),
(3,"GFT2011132","2020-11-13","Especialista Salesforce", "Salesforce",3),
(4,"GFT2011133","2020-11-13","Analista de Testes - QA", "VIVO",2);

insert into vaga_tecnologia(vaga_id,tecnologias_id)
values(2,1),(3,11),(4,5);

insert into funcionario(id,nome,cargo,inicio_wa,matricula,gft_id) 
values(1,"Marcio Funes","L4","2020-01-26","GFT200623", 2),
(3,"Evandro Verones","L5","2020-05-02","200712",3);

insert into funcionario(id,nome,cargo,inicio_wa,matricula,termino_wa,gft_id)
values(2,"Eliney Sabino","L6","2020-03-24","200815","2020-11-16",1);

insert into funcionario_tecnologia(vaga_id,tecnologias_id)
values(1,1),(2,11),(3,5);

insert into historico(id,nome_funcionario,nome_cliente,codigo_vaga,iniciowa,inicio_alocacao)
values(1,"Clécio Gomes","GFT","GFT200523", "2020-06-16","2020-10-14"),
(2,"Henrique Monteiro","GFT","GFT200524", "2020-04-15","2020-10-14"),
(3,"Kenji Kitano","GFT","GFT200525", "2020-07-30","2020-10-07");

