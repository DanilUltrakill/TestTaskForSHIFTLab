# [Тестовое задание. Android](https://github.com/DanilUltrakill/TestTaskForSHIFTLab/files/15004911/SHIFTLAB_Mobile_TestTask_v3.pdf)

## Постановка
  Необходимо реализовать приложение с 2-мя экранами.
## Требования
  1. Допускаются языки: Kotlin, Java.
  2. Исходный код выполненного задания должен быть размещен в git репозитории
  3. Проект должен собираться и запускаться.
  4. Будет большим плюсом если вы будете следовать принципам SOLID, Clean Architecture и придерживаться 
  паттерна MVVM или MVI.
## Экран 1. Регистрация
1. На экране находится 6 элементов:  
    1.1. Поля для ввода имени и фамилии.  
    1.2. Поле для ввода даты рождения.  
    1.3. Поля для ввода пароля и его подтверждения.  
    1.4. Кнопка «Регистрация».  
2. «Регистрация» не может быть завершена, пока все данные не будут валидны. Правила для корректных 
данных придумайте сами. Например, фамилия не может содержать менее двух символов, пароль должен 
содержать цифры и буквы верхнего регистра, и т.д.
3. Если данные валидны, то мы переходим на «Главный экран» приложения.
## Экран 2. Главный экран
1. На экране 1 элемент — кнопка «Приветствие».
2. По нажатию на эту кнопку появляется модальное окно, в котором находится приветствие пользователя с 
указанием имени, которое было введено на самом первом экране регистрации.
## * Необязательно
1. Сделать выбор даты рождения интерактивным
2. Уведомлять/показывать сообщение о том, где именно была допущена ошибка при «Регистрации».
3. Кнопка «Регистрация» должна быть недоступна для нажатия, пока все поля не будут заполнены.
4. Реализовать кеширование данных и сохранение сессии: если пользователь единожды прошёл 
регистрацию, то следующий запуск приложения будет начинаться с главного экрана.
----------
## Реализовано
1. На *экране регистрации* имеются:  
   1.1. Поля для ввода имени и фамилии.  
   1.2. Поле для ввода даты рождения.  
   1.3. Поля для ввода пароля и его подтверждения.  
   1.4. Кнопка «Регистрация»  
2. При нажатии на кнопку «Регистрация» начинается процесс валидации:  
   2.1. Имя и Фамилия могут содержать только латиницу или кириллицу.  
   2.2. Ввод даты рождения происходит через интерактивное окно путём нажатия на соответствующую иконку, год рождения не может быть больше 2009.  
   2.3. Пароль должен содержать минимум 5 символов, также поля ввода пароля и его подтверждения должны совпадать.
   2.4. Если были допущены какие-то ошибки при регистрации, то пользователь будет уведомлён, где именно была допущена ошибка.  
4. При успешной регистрации пользователь попадает на *главный экран*:  
   3.1. После того, как пользователь успешно прошёл регистрацию, он будет сразу попадать *главный экран* при открытии приложения.  
   3.2. В центре экрана находится кнопка «Приветствие», при нажатии на которую открывается модальное окно с индвидиуальным приветствием.  
   3.3. Пользователь может вернуться на *экран регистрации*, нажав кнопку назад на устройстве, при этом произойдёт выход из аккаунта.  