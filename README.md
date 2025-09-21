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



## 1.3. Hello Java アプリを開発



1. **以下のフォルダ階層を作る**
     ```
    ├── bin
    └── src
        └── mypkg
            └── App.java
    ```
    ```sh
    $ mkdir -p bin
    $ mkdir -p src/mypkg
    ```
    
2. **Javaコードを書く**
   
    `src/mypkg/App.java`を編集する
    
    ```java
    package mypkg;
    
    public class App {
    	public static void main(String[] args) {
    		System.out.println("Hello world");
    	}
    }
    ```
    
3. **コンパイルする**
    クラスファイルの出力先として、`bin`フォルダを指定する。
    ```sh
    $ javac src/**/*.java -d bin 
    ```

    結果はこうなる

    ```sh
    $ tree .
    ├── bin
    │   └── mypkg
    │       └── App.class
    └── src
        └── mypkg
            └── App.java
    ```
    
4. **実行する**

    ```sh
    $ java -classpath bin mypkg.App
    Hello world
    ```

5. **クラスパスを環境変数に指定して実行する**
   
    ```sh
    $ export CLASSPATH=bin
    $ java mypkg.App
    Hello world
    ```

6. **シェルスクリプトから実行する**

    1. シェルスクリプトフォルダ`sh`を作る
        ```sh
        $ mkdir sh
        ```
        
    1. テキストエディタで `sh/app` を作成する
        
        ```sh
        #!/usr/bin/env bash
        THIS_DIR="$(cd $(dirname $0);pwd)" #=> このシェルスクリプトが存在しているフォルダの絶対パス
        export CLASSPATH="${THIS_DIR}/../bin" #=> 一つ上を経由したbinフォルダのパスをクラスパスに追加
        java mypkg.App "$@" # このシェルスクリプトの入力引数をJavaアプリに渡す
        ```
    
    2. 実行可能ファイルにする
        ```sh
        $ chmod +x sh/app
        ```
    
    3. 実行する
    
        ```sh
        $ ./sh/app
        Hello world
        ```
    
        

- サンプルコード

    - [java_hello ブランチ](https://github.com/nogayama/building_cli_in_java/tree/java_hello)

        ```sh
        $ git clone git@github.com:nogayama/building_cli_in_java.git -b java_hello cli_java_hello
        ```

        
