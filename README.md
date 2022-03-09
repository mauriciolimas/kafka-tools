# Kafka Tools

## Objetivo

Ferramenta para auxiliar Devs a criar aplicações, que reagem a eventos do Apache Kafka, com essa ferramenta é possivel enviar objetos para um tópico Kafka.

## Modo de usar

### Linux

Adicionar a permissão de execução de KafkaTools
```
chmod +x KafkaTools
```
Definir o topico de destino
```
export TOPIC=<topic-name>
```


Executar o programa

```
./KafkaTools
```

Acessar a URL http://localhost:9090/ui


## Outras configurações

Definir outra porta para a aplicação

Ex. Definir porta 8080
 ```
 export PORT=8080
 ```

