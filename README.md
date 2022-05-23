# SeriesHankbookCompose

Projeto desenvolvido utilizando kotlin compose!

Aplicativo foi desenvolvido para ajudar o usuario a acompanhar as series e filmes que estão sendo lançados no mundo!

Utiliza api do site https://www.themoviedb.org/ 

Modo de configuração
Necesario adicionar um projeto firebase ao projeto.
Baixe o "google-service.json" do seu projeto e cole na pasta "app" e antes de compilar o projeto.

No Cloud Firestore é necessario criar uma coleção chamada "AppSettings" com o documento "ApiKey" e o campo "Key" contendo como valor a chave da api do  https://www.themoviedb.org/ 

Estrutura do projeto
o projeto foi separado por pastas de acordo com suas funcionalidades.
Data - Classes que serão utilizadas no sistema para ORM
DI - Configurações relacionadas as injeções de dependencias do programa
Network - Configurações relacionadas ao get da API
Repository - Camada onde são configuradas a logica do sistema (validações de set e get,etc) 
UI - Classes que estão diretamente ligadas a interação com o usuario (interface grafica)
Util - Classes para auxiliar as demais com funções comuns e até mesmo strings estaticas
