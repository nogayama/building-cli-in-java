# Introduction to Building Command-Line Tools in Java



Java 言語でコマンドラインツールを作りながら、実践的な Java について学ぶチュートリアルです。



[TOC]

# 1. Java



## 1.1. Setup for system



1. **Brewをインストール**

    1. 管理者権限があるユーザーとしてログイン
    2. brew をインストール
        ```sh
        $ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
        ```

2. **Javaをインストール**

    1. 管理者権限があるユーザーとしてログイン
    1. `openjdk`をインストール
    ```sh
    $ brew install openjdk
    ```



## 1.2. Setup for user



1. **環境変数JAVA_HOMEにjavaのフォルダを追加**

    1. 普段利用するユーザーとしてログイン
    2. 環境変数 JAVA_HOME を確認
        ```sh
        $ echo $JAVA_HOME
        ```
    3. なければ、起動スクリプトに環境変数の宣言を追加する
        ```sh
        $ echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk"' >> ~/.zshrc
        ```

2. **環境変数PATHにjavaコマンドが存在するフォルダを追加**

    1. 普段利用するユーザーとしてログイン
    2. 環境変数 JAVA_HOME を確認
        ```sh
        $ echo $PATH | grep openjdk
        ```
    3. なければ、起動スクリプトに環境変数の宣言を追加する
        ```sh
        $ echo 'export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"' >> ~/.zshrc
        ```
3. **動作確認**
    1. 普段利用するユーザーとしてログイン
    2. バージョンを表示
        ```sh
        $ java --version
        openjdk 21.0.2 2024-01-16
        ```

