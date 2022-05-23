# SeriesHankbookCompose

Projeto desenvolvido utilizando kotlin compose!

Aplicativo foi desenvolvido para ajudar o usuario a acompanhar as series e filmes que estão sendo lançados no mundo!

Utiliza api do site https://www.themoviedb.org/ 

Modo de configuração:
Necesario adicionar um projeto firebase ao projeto.
Baixe o "google-service.json" do seu projeto e cole na pasta "app" e antes de compilar o projeto.

No Cloud Firestore é necessario criar uma coleção chamada "AppSettings" com o documento "ApiKey" e o campo "Key" contendo como valor a chave da api do  https://www.themoviedb.org/ 

