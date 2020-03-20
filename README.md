Тестовое задание

Запуск
```
./gradlew bootRun -Pargs=storage-example.xml
```

Уточнение по REST API:
В задании написан пример POST вызова, но я подумал что в данном случае семантика GET больше подходит и реализовал GET.

Проверка:
```
curl -X GET 'localhost:8080/test?box=1&color=red'
```