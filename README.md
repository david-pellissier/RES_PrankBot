# RES_PrankBot

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/david-pellissier/RES_PrankBot)](releases/latest)
[![GitHub issues](https://img.shields.io/github/issues-raw/david-pellissier/RES_PrankBot)](issues)
[![GitHub repo size](https://img.shields.io/github/repo-size/david-pellissier/RES_PrankBot)](#)
[![GitHub all releases](https://img.shields.io/github/downloads/david-pellissier/RES_PrankBot/total)](releases/latest)

## Description du projet

PrankBot a pour but de générer des mails forgés à des groupes de personnes. Sa configuration se fait par fichiers afin de permettre l'automatisation de ce bot.

Le projet est issu du labo SMTP du cours HEIG-VD RES de 2021 ([lien](https://github.com/SoftEng-HEIGVD/Teaching-HEIGVD-RES-2021-Labo-SMTP)).

## Configuration

## Utilisation

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
