# Проект агрегатор вакансий. 

Описание.
Система запускается по расписанию. Период запуска указывается в настройках - app.properties.
Первый сайт будет sql.ru. В нем есть раздел job. Программа должна считывать все вакансии относящиеся к Java и записывать их в базу.
Доступ к интерфейсу будет через REST API.

Расширение.
1. В проект можно добавить новые сайты без изменения кода.
2. В проекте можно сделать параллельный парсинг сайтов.

[![codecov](https://codecov.io/gh/PavelRost/job4j_grabber/branch/master/graph/badge.svg?token=DVCVZX06S3)](https://codecov.io/gh/PavelRost/job4j_grabber)