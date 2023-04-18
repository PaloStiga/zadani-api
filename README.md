zadani-api
=

SpringBoot aplikace vystavení REST služeb. Aplikace dokáže přijmout a vracet data o nákupu.

## Předpoklady
Pro spuštění této aplikace potřebujete mít nainstalované Java 17 a Maven.

## Použití

Po úspěšném spuštění aplikace můžete přistoupit k REST rozhraní na adrese http://localhost:8080.

## Příklady použití

### Vložení záznamov:
```
POST http://localhost:8080/ukol/api/zbozi
Content-Type: application/json

{"casNakupu":"24.10.2022 10:23:59","zboziDtoList":[
{"ean": "A00123", "pocet":  2, "cenaCelkem": 500.00},
{"ean": "B91201", "pocet":  1, "cenaCelkem": 120.33},
{"ean": "C70551", "pocet":  9, "cenaCelkem": 90.10}
]}
```
```
POST http://localhost:8080/ukol/api/zbozi
Content-Type: application/json

{"casNakupu":"04.11.2022 09:00:05","zboziDtoList":[
{"ean": "A00123", "pocet":  3, "cenaCelkem": 700.00},
{"ean": "C70551", "pocet":  1, "cenaCelkem": 23.50}
]}
```
### Vrátí všechny položky nákupu zboží dle EAN mezi uvedenými datumy:
```
GET http://localhost:8080/ukol/api/zbozi/A00123?datumOd=1.1.2022&datumDo=31.12.2022
```
### Vrátí všechny artikly, celkový počet prodaných ks, průměrnou cenu za 1 ks:
```
GET http://localhost:8080/ukol/api/zbozi/getAllArticlesStats
```