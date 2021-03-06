Тестовое задание
================

Написать веб-приложение со следующим функционалом:

- Страница логина. поля: email, пароль (минимум 6 символов, должна быть цифра и буква в верхнем регистре)
- После того как пользователь успешно залогинится он попадает на страницу со списком пользователей системы, список состоит из:
    - email
    - имя
    - роль в системе (user, editor)
    - ссылка на страницу редактирования пользователя (ссылка показывается только если текущий пользователь имеет роль editor)
    - также должна быть возможность добавить нового пользователя (для роли editor)

- Страница редактирования пользователя системы содержит форму редактирования пользователя с полями:
    - email
    - имя
    - роль - выпадающий список (текущий пользователь не может менять свою роль)
    - timezone пользователя
    - datetime когда запись была добавлена, выровненный по timezone текущего пользователя (read only)
    - datetime когда запись была последний раз отредактирована, выровненный по timezone текущего пользователя (read only)
    - список адресов пользователя с ссылкой "Редактировать" при клике на которую на этой же странице появляется форма редактирования адреса, набор полей адреса на усмотрение разработчика
    - также должна быть возможность добавить новый адрес
    - при добавлении нового пользователя хотя бы один адрес обязательно должен быть добавлен

Обязательным условием является использование: Java, Spring Framework, Maven. Использованеие других инструментальных средств остается на усмотрение разработчика.

Готовый проект должен быть размещен на github и иметь средства для сборки и простого старта приложения и/или исчерпывающее описание того как проект можно будет поднять и проверить работоспособность.

Инструкция по сборке проекта:

- 1. Установить СУБД PostgreSQL 9.1 http://www.enterprisedb.com/products-services-training/pgdownload#windows
- 2. Создать роль test с паролем test (см. db.properties)
- 3. Создать базу данных с именем user_editor (см. db.properties)
- 4. Выполнить скрипт new_base.sql - он создаст все таблицы и последовательности
- 5. Скачать tomcat 7 https://tomcat.apache.org/download-70.cgi
- 6. Скачать maven https://maven.apache.org/download.cgi
- 7. Скачать jdk1.7 (проект тестировался на версиях 1.7.0_79, 1.7.0_45)
- 8. Собрать проект, используя команду "mvn package"
- 9. Созданный war поместить в tomcat/webapps
- 10. Запустить tomcat (startup)
- 11. Открыть страницу в браузере localhost:8080/usereditor

Скриптом new_base.sql уже созданы 2-а пользователя с параметрами:

- editor@mail.ru
- Ed1tor

- user@mail.ru
- User0k

- При РЕДАКТИРОВАНИИ пользователя (не при создании): если не заполнить пароль, то он останется неизменным
- При редактировании адреса(ов): для добавления новой строки необходимо нажать кнопку "+", для удаления строки - "-"
