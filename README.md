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



7. **メインクラス指定無しのjarファイルを作成して実行**

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



8. **メインクラスを指定するjarファイルを作成して実行**

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



- サンプルコード [maven_mainjar](https://github.com/nogayama/building_cli_in_java/tree/maven_mainjar) は以下のコマンドで取得できる。

    ```sh
    $ git clone git@github.com:nogayama/building_cli_in_java.git -b maven_mainjar cli_maven_mainjar
    ```



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



4. **Jarファイルを実行する**

    ```sh
    $ java -jar target/cli_maven_mainjar-1.0-SNAPSHOT.jar 
    Hello world
    ```

    

# 3. 入力引数を解析するパッケージ(PicoCli)を使う



## 3.1. 依存パッケージを取り込み、Jarファイルに同梱する



- サンプルコード [picocli_hello](https://github.com/nogayama/building_cli_in_java/tree/picocli_hello) は以下のコマンドで取得できる。

    ```sh
    $ git clone git@github.com:nogayama/building_cli_in_java.git -b picocli_hello cli_picocli_hello
    ```



1. **フォルダ階層を作る**

    ```sh
     mvn archetype:generate \
        -DarchetypeArtifactId=maven-archetype-quickstart \
        -DartifactId=cli_picocli_hello \
        -DgroupId=cli_picocli_hello \
        -Dpackage=mypkg \
        -DinteractiveMode=false
    ```



2. **依存パッケージとして、`picocli`を指定する**

    - `pom.xml`に、`<dependency>...</dependency>`要素を挿入する

        ```xml
        <project ...
          <dependencies>
            ...
            <dependency>
              <groupId>info.picocli</groupId>
              <artifactId>picocli</artifactId>
              <version>4.7.6</version>
            </dependency>
            ...
          </dependencies>
        
        </project>
        ```



4. **実行可能なJarファイルを作成し依存パッケージを同梱するように指定する**
   

    - `pom.xml`に、`<build>...</build>`要素を挿入する

        ```xml
        <project 
          ...
          <build>
            <plugins>
              <plugin><!-- 実行可能 jar を作る -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.0</version>
                <executions>
                  <execution>
                    <phase>package</phase>
                    <goals>
                      <goal>shade</goal>
                    </goals>
                    <configuration>
                      <transformers>
                        <!-- Main-Class を jar の MANIFEST に書き込む -->
                        <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                          <mainClass>mypkg.App</mainClass>
                        </transformer>
                      </transformers>
                    </configuration>
                  </execution>
                </executions>
              </plugin>
            </plugins>
          </build>
          ...
        </project>
        ```



5. **Java コードを書く**

    `src/main/java/mypkg/App.java`を編集する

    ```java
    package mypkg;
    
    import java.util.concurrent.Callable;
    
    import picocli.CommandLine;
    import picocli.CommandLine.Command;
    import picocli.CommandLine.Option;
    
    @Command(name = "hello", description = "Say hello.", //
    		mixinStandardHelpOptions = true)
    public class App implements Callable<Integer> {
    
    	@Option(names = { "-n", "--name" }, defaultValue = "world", description = "Name to greet")
    	protected String name;
    
    	@Override
    	public Integer call() {
    		System.out.println("Hello " + this.name);
    		return 0;
    	}
    
    	public static void main(String[] args) {
    		int exitCode = new CommandLine(new App()).execute(args);
    		System.exit(exitCode);
    	}
    }
    ```
    
    1. 解説(7--9行): `@Command`アノテーションで、このコマンド全体のヘルプを指定
    2. 解説(11-12行): `@Option` アノテーションで、`-n 文字列` の形の入力引数を`name`属性に格納するように指定
    3. 解説(14-17行): `Callable`インターフェースのメソッド `call()`を実装する。`execute()`を呼ぶとその中で`call()`が呼ばれるので、実質`execute()`を実装しているのと同じ意味になる。
    4. 解説(20行): 
        1. `App`インスタンスを`CommandLine`インスタンスに渡す
        2. `CommandLine.execute(string [])`メソッドに、コマンド実行時の入力引数を渡す
        3. 入力引数を解析し、`App`インスタンスの属性に解析後の値をセットする
        4. `App.call()`を実行する
    
    
    
    - シーケンス図にすると以下のようになる
    
        ```mermaid
        sequenceDiagram
        %%{init: { 'sequence': {'mirrorActors':false} } }%%
        User ->> Java: -n John
            Java ->> App: main(["-n", "John"])
                App ->> new CommandLine: execute(["-n", "John"])
                     new CommandLine ->> new App: .name = "John"
                     new CommandLine ->> new App: call()
                     new App ->> new CommandLine: exit status
                new CommandLine ->> App: exit status
            App ->> Java: exit status
        Java ->> User: exit status
        
        ```




6. **ビルドする**

    ```sh
    $ mvn package
    ```



7. **Jarファイルを実行する**

    ```sh
    $ java -jar target/cli_picocli_hello-1.0-SNAPSHOT.jar -n John 
    Hello John
    ```
    
    ```sh
    $ java -cp target/cli_picocli_hello-1.0-SNAPSHOT.jar mypkg.App -n John
    Hello John
    ```
    
    

