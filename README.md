# SDU Helper Bot

## Overview
**SDU Helper Bot** is a Java-based Telegram bot named **@sdu_helperappbot**. The bot assists users by providing links to essential SDU resources and facilitating question management through a variety of commands.

## Features
The bot supports the following commands:

- **`/start`**: Initializes and starts the bot.
- **`/help`**: Displays a list of all available commands.
- **`/ask`**: Activates "ask mode," allowing users to ask questions. Ask mode remains active until "Cancel" is pressed or written.
- **`/moodle_sdu`**: Provides a link to the SDU Moodle page.
- **`/my_sdu`**: Provides a link to the SDU portal.
- **`/Show_All_Questions`**: Displays a list of all questions.

---

## Developer Guide

### Requirements
To run or contribute to the SDU Helper Bot, you need:
- **Java Development Kit (JDK)** version 21 or higher.
- **Telegram Bot API Token**: Register a bot with [BotFather](https://core.telegram.org/bots#botfather) on Telegram to receive an API token.
- Internet connection to interact with Telegram servers.

### Setting Up and Running the Bot
1. Clone the repository:
   ```bash
   git clone https://github.com/DMen6L/SDU_HelperBot.git
   cd SDU_HelperBot
   ```

2. Import the project into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

### Troubleshooting
- **Problem**: Bot doesn’t respond to commands.
    - **Solution**: Verify the API token and check if the bot is correctly registered with Telegram.

- **Problem**: Dependency issues or missing libraries.
    - **Solution**: Ensure that all required dependencies are added to the project. If using Maven, ensure `pom.xml` is correctly configured.

- **Problem**: Cannot connect to Telegram servers.
    - **Solution**: Verify internet connection and check if Telegram’s servers are reachable from your network.

---

### Coding Guidelines
To ensure code consistency and maintainability, follow these conventions:

#### Naming Conventions
- **Classes**: Use `UpperCamelCase` (e.g., `BotInitializer`, `SHA_MainConfig`).
- **Variables** and **Methods**: Use `lowerCamelCase` (e.g., `sendMessage`, `config`).

#### Comments
- Include descriptive comments for classes and methods.
- Use inline comments for complex or non-obvious logic.

#### Commits
- Write clear and concise commit messages.
- Format: `[Feature/Refactor/Fix]: Short description`.
  Example: `Rework, meaning reworking the whole code for some reason(for me it is deployment)`

