# build
FROM maven:3.9.10-amazoncorretto-21-al2023 as build
WORKDIR /build

COPY . .

# AJUSTE 1: Se o seu projeto usa o wrapper do Maven (./mvnw), use ele.
# Se não usar, o 'mvn' puro funciona, mas o '-DskipTests' garante que não trave sem banco.
RUN mvn clean package -DskipTests

# run
FROM amazoncorretto:21.0.5

WORKDIR /app

# AJUSTE 2: Corrigido o caminho do COPY (removido o ponto antes da barra e garantido o padrão do target)
COPY --from=build /build/target/*.jar ./cardapio-escolar.jar

# AJUSTE 3: O Render exige que a aplicação escute internamente na porta 8080 no plano gratuito
EXPOSE 8080

ENV TZ='America/Sao_Paulo'

ENTRYPOINT ["java", "-jar", "cardapio-escolar.jar"]