web: java -jar target/tinyurl-0.0.1-SNAPSHOT.jar
  - type: web
    name: tinyurl-backend
    env: java
    buildCommand: ./mvnw clean package
    startCommand: java -jar target/tinyurl-0.0.1-SNAPSHOT.jar
    envVars:
      - key: SPRING_PROFILES_ACTIVE
        value: production
    autoDeploy: true

