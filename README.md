# Gerenciador de formulários de anamnese

O aplicativo foi desenvolvido como material do projeto extensivo da Faculdade Estácio de Sá, com
foco na gestão de formulários de anamnese voltado para profissionais de extensão de cílios. Ele
permite criar, visualizar, atualizar e deletar formulários dos clientes, facilitando a administração
das informações de saúde e preferências dos usuários.

## Funcionalidades

- **Criar Formulários**: Adicione novos formulários de anamnese com informações detalhadas dos
  clientes.
- **Visualizar Formulários**: Consulte os formulários já existentes de maneira prática.
- **Atualizar Formulários**: Edite os formulários conforme necessário para manter as informações
  atualizadas.
- **Deletar Formulários**: Remova os formulários desatualizados ou que não são mais necessários.

## Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **Injeção de Dependência**: Koin
- **Banco de Dados Local**: Room
- **Navegação**: Jetpack Navigation Component
- **Binding**: ViewBinding para ligar as Views ao código de forma eficiente
- **Gerenciamento de Estados**: LiveData e ViewModel

## Configuração e Instalação

### Pré-requisitos

- Android Studio (versão mais recente recomendada)
- Android SDK
- JDK 17 ou superior

### Passos para rodar o projeto

1. Clone o repositório:

```bash
git clone https://github.com/usuario/anamnesis-form-manager.git
```

2. Abra o projeto no **Android Studio**.

3. Sincronize o projeto com o **Gradle**.

4. Execute o aplicativo em um dispositivo ou emulador Android.