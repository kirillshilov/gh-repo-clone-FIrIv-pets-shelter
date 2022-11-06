# pets-shelter
Привет!

Этим кратким письмом мы расскажем основную миссию нашего проекта, а также расскажем о том, каким проектом наша команда будет заниматься в ближайшее время.

Мы - Чернильцев, Шилов, Юков и Филонова - команда студентов sky.pro Java-Devs 5.0.1.

Мы получили задание автоматизировать по возможности работу сотрудников приюта животных из Астаны. Они же, в свою очередь, хотят помочь людям, которые задумываются о том, чтобы забрать собаку или кошку домой. Для многих их клиентов это первый опыт, а обращений к ним каждый день поступает так много, что они не успевают их обрабатывать вручную.

Руководство приюта обратилось к нам за помощью: они попросили написать телеграмм-бота, который сможет отвечать на популярные вопросы людей о том, что нужно знать и уметь, чтобы забрать животное из приюта.

Также волонтеры приюта в течение месяца просят новых хозяев присылать ежедневный отчет о том, как животное приспосабливается к новой обстановке. Эту часть работы заказчик тоже попросил передать боту. 

На разработку всего проекта у нас есть 5 недель, после чего мы передадим проект в пользование приюту. 

*********************
Описание работы бота (итоговая версия). 
*********************
Пользователь вводит первое сообщение и получает в ответ "Меню выбора приютов (кошки/собаки)" / 
*Здравствуйте! Это официальный телеграмм-бот приютов для животных PetShelter. Мы помогаем людям, которые задумались приютить питомца. Для многих из Вас это первый опыт. Не волнуйтесь. Мы поможем с этим нелегким, но важным делом!
*** Выберите интересующий пункт меню *** :
- 0 - "Приют для кошек",
- 1 - "Приют для собак". 

Пользователь выбирает приют и получает в ответ меню "Узнать информацию о приюте" / 
*Выберите интересующий вас вопрос и введите номер:
- 2 - "Информация о приюте", 
- 3 - "Как приютить питомца?", 
- 4 - "Прислать отчет", 
- 5 - "Оставить контакт для связи", 
- 6 - "Позвать волонтера", 
- 7 - для возврата в меню "Выбрать приют (собаки/кошки)". 

При выборе пункта 2 пользователь получает меню "Консультация с новым пользователем" / 
*Выберите интересующий вас вопрос и введите номер:
- 8 - "Общая информация", 
- 9 - "Расписание работы, адрес", 
- 10 - "Оформить пропуск", 
- 11 - "Техника безопасности", 
- 12 - "Оставить контакт для связи", 
- 13 - "Позвать волонтера", 
- 14 - для возврата в главное меню. 

При выборе пункта 3 пользователь получает меню "Консультация с потенциальным хозяином" / 
*А здесь мы собрали полезную информацию, которая поможет вам подготовиться ко встрече с новым членом семьи. Выберите пункт меню:
- 15 - "Знакомство с питомцем" (правила знакомства с собакой до того, как можно забрать ее из приюта). 
- 16 - "Необходимые документы" (список документов, необходимых для того, чтобы взять собаку из приюта).
- 17 - "Как перевозить" (список рекомендаций по транспортировке животного).
- 18 - "Дом для детеныша" (список рекомендаций по обустройству дома для щенка/котенка).
- 19 - "Дом для взрослого питомца" (список рекомендаций по обустройству дома для взрослой собаки/кошки).
- 20 - "Дом для питомца с огр.возможностями" (список рекомендаций по обустройству дома для собаки с ограниченными возможностями - зрение, передвижение).
- 21 - "Причины, по которым могут отказать".  
- 22 - для возврата в главное меню.  
- 23 - "Позвать волонтера".
- 24 - "Советы кинолога" (советы кинолога по первичному общению с собакой).
- 25 - "Контакты кинологов" (рекомендации по проверенным кинологам для дальнейшего обращения к ним).

При выборе пункта 4 пользователь получает сообщение: 
"Ждем от вас фото питомца и небольшой рассказ о том, как он:
Рацион животного.
Общее самочувствие и привыкание к новому месту.
Изменение в поведении: отказ от старых привычек, приобретение новых."

При выборе пункта 5 пользователь получает сообщение:
"Пожалуйста, оставьте ваш номер телефона для связи, и волонтер перезвонит вам". 

При выборе пункта 6 бот зовет в чат волонтера. 

Также будет реализован функционал...

Пользователь в "Меню отчета о питомце" отправляет фото. 
Бот принимает фото и записывает в базу отчетов на сегодня. Если текста еще нет, бот просит отправить рассказ о питомцце. 
Бот принимает текст и записывает в базу отчетов на сегодня. Если фото еще нет, бот просит отправить фото. 
(Отчет нужно присылать каждый день, ограничений в сутках по времени сдачи отчета нет. Раз в два-три дня волонтеры отсматривают все присланные отчеты). 	
	
Бот ежедневно проверяет, прислал ли пользователь информацию (т.к. можно до полуночи, то проверку запускаем утром) / 	 
- если пользователь не присылал вчера информацию (текст и фото), напоминаем: "Добрый день, мы не получили отчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет и информациюю о питомце".
- если пользователь не присылал вчера текст, напоминаем: "Добрый день, мы не получили рассказ о питомце за вчерашний день, пожалуйста, пришлите сегодня информацию о питомце".
- если пользователь не присылал вчера фотоотчет, напоминаем: "Добрый день, мы не получили фотоотчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет о питомце".
Бот выдает сообщение: "Нажмите 3, чтобы прислать отчет о питомце".
- если пользователь не присылал 2 дня никакой информации (текст или фото), отправлять запрос волонтеру на связь с усыновителем. Текст: "Усыновитель ХХХ не отправляет информацию уже 2 дня". 

Бот ежедневно проверяет, прошло ли N (по умолчанию 30) дней испытательного срока. Можно дважды в день. 	
- если день=N и хозяин еще на испытательном сроке, бот отправляет запрос волонтеру. Текст: "Сегодня истекает N-ый день. Примите решение об успешном/неуспешном прохождении усыновителем  испытательного срока или продлите испытательный срок". 
- если день>N и усыновитель в статусе "на проверке", бот отправляет запрос волонтеру. Текст: "N-ый день уже прошел! Срочно примите решение об успешном/неуспешном прохождении усыновителем  испытательного срока или продлите испытательный срок". 

Волонтер имеет возможность изменить статус усыновителя с "на проверке" на "прошел/не прошел": 
- когда волонтер помечает, что испытательный срок пройден успешно, бот поздравляет усыновителя стандартным сообщением. 
- когда волонтер назначает дополнительное время, бот отправляет сообщение усыновителю. Текст: "Испытательный срок был продлен на XXX дней". 
- когда волонтер помечает, что усыновитель не прошел испытательный срок, бот уведомляет его об этом и дает инструкции по дальнейшим шагам.

Бот может выдать предупреждение о том, что отчет заполняется плохо (делает волонтер). Текст: «Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания собаки».

*********************
Описание работы бота (первоначальная версия). 
*********************

Пользователь вводит первое сообщение и получает в ответ "Главное меню" / 
"Выберите интересующий вас вопрос и введите номер:
- 1 - Получить информацию о приюте.
- 2 - Узнать, как взять собаку из приюта.
- 3 - Прислать отчет о питомце.
- 4 - Оставить данные для связи, волонтер перезвонит вам. 
- 5 - Позвать волонтера."

При выборе пункта 1 пользователь получает "Меню информации о приюте" / 
"Выберите интересующий вас вопрос и введите номер:
- 11 - информация о приюте.
- 12 - расписание работы приюта и адрес, схема проезда.
- 13 - общие рекомендации о технике безопасности на территории приюта (правила пропуска на территорию, правила нахождения внутри и общения с собаками).
- 0 - вернуться в главное меню."

При выборе пункта 2 пользователь получает "Меню потенциального усыновителя" / 
"А здесь мы собрали полезную информацию, которая поможет вам подготовиться ко встрече с новым членом семьи. Выберите пункт меню:
- 21 - правила знакомства с собакой до того, как можно забрать ее из приюта. 
- 22 - список документов, необходимых для того, чтобы взять собаку из приюта.
- 23 - список рекомендаций по транспортировке животного.
- 24 - список рекомендаций по обустройству дома для щенка.
- 25 - список рекомендаций по обустройству дома для взрослой собаки.
- 26 - список рекомендаций по обустройству дома для собаки с ограниченными возможностями (зрение, передвижение).
- 27 - советы кинолога по первичному общению с собакой.
- 28 - рекомендации по проверенным кинологам для дальнейшего обращения к ним.
- 29 - список причин, почему могут отказать и не дать забрать собаку из приюта.
- 0 - вернуться в главное меню."

При выборе пункта 3 пользователь получает сообщение: 
"Ждем от вас фото питомца и небольшой рассказ о том, как он:
Рацион животного.
Общее самочувствие и привыкание к новому месту.
Изменение в поведении: отказ от старых привычек, приобретение новых."

При выборе пункта 5 бот зовет в чат волонтера. 

При выборе пункта 4 пользователь получает сообщение:
"Пожалуйста, оставьте ваш номер телефона для связи, и волонтер перезвонит вам. 
Для возврата в главное меню нажмите 0". 

Выбор пунктов "Меню информации о приюте" / 
- 11 - бот выдает запись "Рассказать о приюте". 
- 12 - бот выдает запись "Расписание работы приюта и адрес, схема проезда".
- 13 - бот выдает запись "Общие рекомендации о технике безопасности на территории приюта (правила пропуска на территорию, правила нахождения внутри и общения с собаками)". 
- 0 - бот выдает главное меню. 

Выбор пунктов "Меню потенциального усыновителя" /
- 21 - бот выдает запись "правила знакомства с собакой до того, как можно забрать ее из приюта". 
- 22 - бот выдает запись "список документов, необходимых для того, чтобы взять собаку из приюта".
- 23 - бот выдает запись "список рекомендаций по транспортировке животного".
- 24 - бот выдает запись "список рекомендаций по обустройству дома для щенка".
- 25 - бот выдает запись "список рекомендаций по обустройству дома для взрослой собаки".
- 26 - бот выдает запись "список рекомендаций по обустройству дома для собаки с ограниченными возможностями (зрение, передвижение)".
- 27 - бот выдает запись "советы кинолога по первичному общению с собакой".
- 28 - бот выдает запись "рекомендации по проверенным кинологам для дальнейшего обращения к ним".
- 29 - бот выдает запись "список причин, почему могут отказать и не дать забрать собаку из приюта".
- 0 - бот "выдает главное меню.

Также будет реализован функционал...

Пользователь в "Меню отчета о питомце" отправляет фото. 
Бот принимает фото и записывает в базу отчетов на сегодня. Если текста еще нет, бот просит отправить рассказ о питомцце. 
Бот принимает текст и записывает в базу отчетов на сегодня. Если фото еще нет, бот просит отправить фото. 
(Отчет нужно присылать каждый день, ограничений в сутках по времени сдачи отчета нет. Раз в два-три дня волонтеры отсматривают все присланные отчеты). 	
	
Бот ежедневно проверяет, прислал ли пользователь информацию (т.к. можно до полуночи, то проверку запускаем утром) / 	 
- если пользователь не присылал вчера информацию (текст и фото), напоминаем: "Добрый день, мы не получили отчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет и информациюю о питомце".
- если пользователь не присылал вчера текст, напоминаем: "Добрый день, мы не получили рассказ о питомце за вчерашний день, пожалуйста, пришлите сегодня информацию о питомце".
- если пользователь не присылал вчера фотоотчет, напоминаем: "Добрый день, мы не получили фотоотчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет о питомце".
Бот выдает сообщение: "Нажмите 3, чтобы прислать отчет о питомце".
- если пользователь не присылал 2 дня никакой информации (текст или фото), отправлять запрос волонтеру на связь с усыновителем. Текст: "Усыновитель ХХХ не отправляет информацию уже 2 дня". 

Бот ежедневно проверяет, прошло ли N (по умолчанию 30) дней испытательного срока. Можно дважды в день. 	
- если день=N и хозяин еще на испытательном сроке, бот отправляет запрос волонтеру. Текст: "Сегодня истекает N-ый день. Примите решение об успешном/неуспешном прохождении усыновителем  испытательного срока или продлите испытательный срок". 
- если день>N и усыновитель в статусе "на проверке", бот отправляет запрос волонтеру. Текст: "N-ый день уже прошел! Срочно примите решение об успешном/неуспешном прохождении усыновителем  испытательного срока или продлите испытательный срок". 

Волонтер имеет возможность изменить статус усыновителя с "на проверке" на "прошел/не прошел": 
- когда волонтер помечает, что испытательный срок пройден успешно, бот поздравляет усыновителя стандартным сообщением. 
- когда волонтер назначает дополнительное время, бот отправляет сообщение усыновителю. Текст: "Испытательный срок был продлен на XXX дней". 
- когда волонтер помечает, что усыновитель не прошел испытательный срок, бот уведомляет его об этом и дает инструкции по дальнейшим шагам.

Бот может выдать предупреждение о том, что отчет заполняется плохо (делает волонтер). Текст: «Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания собаки».