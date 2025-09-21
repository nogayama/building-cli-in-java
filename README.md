# Introduction to Building Command-Line Tools in Java



Java 言語でコマンドラインツールを作りながら、実践的な Java について学ぶチュートリアルです。



[TOC]

# 1. Introduction to Java 



## 1.1. セットアップ(システム)



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



## 1.2. セットアップ(ユーザー環境)



1. **環境変数PATHにBrewを追加する**

    1. 普段利用するユーザーとしてログイン

    2. 起動スクリプトに環境変数の宣言を追加する

        ```sh
        $ echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zshrc
        ```

    

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



## 1.3. Java アプリを開発



- サンプルコード [java_hello](https://github.com/nogayama/building_cli_in_java/tree/java_hello) は以下のコマンドで取得できる。

    ```sh
    $ git clone git@github.com:nogayama/building_cli_in_java.git -b java_hello cli_java_hello
    ```




1. **以下のフォルダ階層を作る**
   
    ```
    cli_java_hello
    ├── bin
    └── src
        └── mypkg
            └── App.java
    ```
    ```sh
    $ mkdir cli_java_hello
    $ cd cli_java_hello
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

7. メインクラス指定無しのjarファイルを作成して実行

    1. **Jarをビルドする**

        ```sh
        $ jar --create --file hello_jar.jar -C bin . 
        $ ls 
        hello_jar.jar
        ```

    2. **実行する**
       `jar`ファイルをクラスパスに追加して、`java`コマンドから実行できる

        ```sh
        $ java -cp hello_jar.jar mypkg.App 
        ```

8. メインクラスを指定するjarファイルを作成して実行

    1. **Jarをビルドする**

        ```sh
        $ jar --create --file hello_jar.jar --main-class mypkg.App  -C bin . 
        $ ls 
        hello_jar.jar
        ```

    2. **実行する**
       `-jar FILE`オプションを指定し、`java`コマンドからメインクラスを実行できる

        ```sh
        $ java -jar hello_jar.jar
        ```




# 2. Introduction to Maven



- サンプルコード [maven_hello](https://github.com/nogayama/building_cli_in_java/tree/maven_hello) は以下のコマンドで取得できる。

    ```sh
    $ git clone git@github.com:nogayama/building_cli_in_java.git -b maven_hello cli_maven_hello
    ```



## 2.1. セットアップ(システム)



1. **Mavenをインストール**

    1. 管理者権限があるユーザーとしてログイン
    1. `maven`をインストール
    ```sh
    $ brew install maven
    ```



## 2.2. セットアップ(ユーザー環境)



1. **環境変数PATHにBrewを追加する**

    1. 普段利用するユーザーとしてログイン
    2. `mvn` が実行可能か調べる

        ```sh
        $ command -v mvn
        /opt/homebrew/bin/mvn
        ```
    2. 無ければ、起動スクリプトに環境変数の宣言を追加する

        ```sh
        $ echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zshrc
        ```



1. **動作確認**

    1. 普段利用するユーザーとしてログイン
    2. バージョンを表示
        ```sh
        $ mvn --version
        Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
        ```



## 2.3. Java アプリを開発



1. **フォルダ階層を作る**

    ```sh
    $ mvn archetype:generate \
        -DarchetypeArtifactId=maven-archetype-quickstart \
        -DartifactId=cli_maven_hello \
        -DgroupId=cli_maven_hello \
        -Dpackage=mypkg \
        -DinteractiveMode=false
    
    $ cd cli_maven_hello
    ```

    ```sh
    $ tree .
    .
    ├── pom.xml
    └── src
        ├── main
        │   └── java
        │       └── mypkg
        │           └── App.java
        └── test
            └── java
                └── mypkg
                    └── AppTest.java
    ```

    

1. **Javaコードを書く**

    `src/main/java/mypkg/App.java`を編集する

    ```java
    package mypkg;
    
    public class App {
    	public static void main(String[] args) {
    		System.out.println("Hello world");
    	}
    }
    ```



1. **ビルドする**
   
    ```sh
    $ mvn package
    ```
    
    `jar`ファイルができる
    
    ```sh
    $ tree target -L 1
    target
    ...
    ├── cli_maven_hello-1.0-SNAPSHOT.jar
    ...
    ```
    
    
    
1. **Jarファイルを実行する**

    ```sh
    $ java -cp target/cli_maven_hello-1.0-SNAPSHOT.jar mypkg.App
    Hello world
    ```





## 2.4. メインクラスを指定したJarを作成



1. **フォルダ階層を作る**

    ```sh
    $ mvn archetype:generate \
        -DarchetypeArtifactId=maven-archetype-quickstart \
        -DartifactId=cli_maven_mainjar \
        -DgroupId=cli_maven_mainjar \
        -Dpackage=mypkg \
        -DinteractiveMode=false

    ```

2. **メインクラスを指定するプラグインの項目を`pom.xml`に追加する**

    - `pom.xml`に、`<build>...</build>`要素を挿入する
      
        ```xml
        <project>
          ...
          <build>
            <plugins>
              <plugin><!-- Main-Class を埋め込んで java -jar で起動可能にする 設定-->
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.4.2</version>
                <configuration>
                  <archive>
                    <manifest>
                      <mainClass>mypkg.App</mainClass>
                    </manifest>
                  </archive>
                </configuration>
              </plugin>
            </plugins> 
          </build>
          ...
        </project>
        ```

3. **ビルドする**

    ```sh
    $ mvn package
    ```

4. **Jarフィアルを実行する**

    ```sh
    $ java -jar target/cli_maven_mainjar-1.0-SNAPSHOT.jar 
    Hello world
    ```

    
