# imagem base do OpenJDK com a versão do Java que você está usando em seu projeto
FROM openjdk:11

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR do seu projeto para o diretório de trabalho no contêiner
COPY target/sumarizacao.jar /app/sumarizacao.jar

# Expõe a porta que sua aplicação está ouvindo
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "sumarizacao.jar"]