📚 Sistema de Biblioteca - Gestão de Metas de Leitura

Este projeto é uma aplicação mobile para gerir metas de leitura com sincronização em tempo real num servidor backend. Finalmente tudo a funcionar! 🚀
🛠️ Tecnologias Utilizadas
Mobile (Android)	Backend (Spring Boot)
Kotlin + Jetpack Compose	Kotlin + Spring Web
Retrofit + OkHttp (API)	Spring Data JPA
Room Database (Local)	H2 Database (Em Memória)
ViewModel + Coroutines	Gradle
🚀 Como Rodar o Projeto
1. Configuração do Backend (Servidor)

O servidor tem de estar a rodar para o app conseguir sincronizar os dados.

    Abrir o projeto: Abre a pasta do backend no IntelliJ IDEA ou VS Code.

    Verificar a porta: O servidor roda por padrão na porta 8080. Se der erro de porta ocupada, faz o kill -9 no PID que estiver a usar a porta.

    Executar: Roda a classe BibliotecaApplication.kt.

    Validar: Abre o navegador e acesse http://localhost:8080/api/goals.

        Se aparecer [], o servidor está OK.

2. Configuração do Mobile (Android)

⚠️ Atenção ao IP do Servidor:

  Descobrir o teu IP: * No Mac/Linux: No terminal, digita ifconfig | grep inet (procura o endereço que começa por 192.168...).

    No Windows: Abre o Prompt de Comando (cmd) ou PowerShell, digita ipconfig e procura por IPv4 Address.

    Configurar Retrofit: No arquivo RetrofitClient.kt, altera a BASE_URL:

        Telefone Físico: Usa o IP da tua máquina (http://192.168.X.X:8080/).

        Emulador: Usa o IP especial do Android http://10.0.2.2:8080/.

    Permissões: O arquivo AndroidManifest.xml já está configurado com android:usesCleartextTraffic="true" para permitir conexões HTTP sem stress.

    Executar: Dá o Run no Android Studio e pronto.
