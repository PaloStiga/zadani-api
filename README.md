zadani-api
=

SpringBoot aplikace vystavení REST služeb. Aplikace dokáže přijmout a vracet data o nákupu.

## Předpoklady
Pro spuštění této aplikace potřebujete mít nainstalované Java 17 a Maven.

## Použití

Po úspěšném spuštění aplikace můžete přistoupit k REST rozhraní na adrese http://localhost:8080.

## Závislosti

#### org.springframework.boot:spring-boot-starter-web:3.0.5: Starter balíček pro webové aplikace s Spring frameworkem.
#### org.projectlombok:lombok: Knihovna pro snadnou tvorbu Java projektů bez zbytečného opakování kódu.
#### org.springframework.boot:spring-boot-starter-test: Starter balíček pro testování s Spring frameworkem.
#### org.springdoc:springdoc-openapi-ui:1.7.0: Knihovna pro generování dokumentace API včetně uživatelského rozhraní.
#### org.springdoc:springdoc-openapi-data-rest:1.7.0: Knihovna pro generování dokumentace API pro aplikace s podporou Spring Data REST.
#### cz.stiga.ukol:persistence-lib:1.0.0: Knihovna pro práci s DTO

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