# RES_PrankBot

## Mettre en place le serveur SMTP de test

1. Build l'image avec le dockerfile situé dans *mock_server* 

```sh
 docker build -t mock_smtp mock_server/
```

2. Exécuter l'image

```sh
docker run -p 80:8282 -p 25:25 --rm mock_smtp
```

Le serveur MockMock devrait alors être accessible depuis l'hôte sur les ports 80 (HTTP) et 25 (SMTP).