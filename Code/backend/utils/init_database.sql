create extension if not exists "uuid-ossp";

create table user_reviews
(
	id uuid default uuid_generate_v4() not null
		constraint user_reviews_pk
			primary key,
	create_time timestamp,
	rate int,
	review_title text,
	review_text text,
	platform bool,
	intonation bool,
	command_1_estimate int default 0,
    command_2_estimate int default 0,
    command_3_estimate int default 0,
    command_4_estimate int default 0,
    command_5_estimate int default 0,
    command_6_estimate int default 0,
    command_7_estimate int default 0,
    command_8_estimate int default 0,
    command_9_estimate int default 0,
    command_10_estimate int default 0,
    command_11_estimate int default 0,
    command_12_estimate int default 0,
    command_13_estimate int default 0,
    command_14_estimate int default 0,
    command_15_estimate int default 0,
    command_16_estimate int default 0,
    command_17_estimate int default 0,
    command_18_estimate int default 0,
    command_19_estimate int default 0,
    command_20_estimate int default 0,
    command_21_estimate int default 0,
    command_22_estimate int default 0,
    command_23_estimate int default 0,
    command_24_estimate int default 0,
    command_25_estimate int default 0,
    command_26_estimate int default 0,
    command_27_estimate int default 0,
    command_28_estimate int default 0,
    command_29_estimate int default 0,
    command_30_estimate int default 0,
    command_31_estimate int default 0,
    command_32_estimate int default 0,
    command_33_estimate int default 0,
    command_34_estimate int default 0,
    command_35_estimate int default 0,
    command_36_estimate int default 0,
    command_37_estimate int default 0,
    command_38_estimate int default 0,
    command_39_estimate int default 0,
    command_40_estimate int default 0,
    command_41_estimate int default 0,
    command_42_estimate int default 0,
    command_43_estimate int default 0
);

create table commands
(
    id          serial not null
        constraint commands_pk
            primary key,
    name        text,
    description text
);

insert into commands (name, description) values
('iOS Platform', 'Touch ID/Face ID\nВиджет Избранное\nГеокарты\nАдресная книга\nМеханизм отправки событий аналитики\nАутентификация (регистрация, авторизация)\n'),
('Global Navigation', 'Прелогин, приветствие, экраны входа, ввода пинкода, регистрации\nКурсы валют\nТуториал\nОферта, политика конфиденциальности\nДемо-режим (основная функциональность и нераспределённый функционал)\n'),
('DDA Profile', 'Профиль клиента\n'),
('PFM', 'Цели\nКопилки\n'),
('PFMMPLACE', 'Работа секции Инвестиции\n'),
('PUSH iOS', 'Пуш-уведомления\n'),
('DBP.Витрины продаж', 'Предложения Банка\n'),
('Госуслуги', 'Функционал Госуслуги\n'),
('iOS Release Engineer', 'Безопасность, размер сборки и пр.\nОферта, политика конфиденциальности\n'),
('Мессенджер', 'Диалоги\n'),
('Store-n-Sales', 'Каталог\n'),
('Integration Platform', 'Лимиты\nСправочники\n'),
('Data Driven App', 'Умный поиск\n'),
('Краудфандинг', 'Краудгифтинг\nКраудфандинг\n'),
('DBP.Подарки', 'Подарки\n'),
('Sberbank ID B2C', 'Оплата товаров из других приложений и интернет-магазинов с помощью СБОЛ\n'),
('История операций', 'Лента событий\nИстория операций\n'),
('Редактируемый профиль клиента', 'Персональные данные клиента (паспортные данные, ИНН, СНИЛС etc)\n'),
('ЕФС Выписки и справки. Mobile', 'Выписки и справки\n'),
('ЕФС.Платежи МП', 'Оплата услуг (мобильная связь, Интернет)\nОплата по ШК, QR, штрихкоду\nИзменение статуса, печать чека\n'),
('Платежи. Штрафы', 'Оплата и поиск штрафов\nБюджетные платежи\nНалоги\nПошлины\nПатенты\n'),
('ПДВ в Digital', 'Переводы до востребования\n'),
('СБОЛ. Классические переводы', 'Перевод клиенту СБ (карта, счет)\nP2P\nПеревод на карту другого банка (PCI DSS)\nПереводы между своими счетами\nПеревод на счет в другой банк: частному лицу и организациям\n'),
('ЕФС.Автоплатежи', 'Автоплатежи\nУмные счета, Счета на оплату\nШаблоны\n'),
('Автопереводы', 'Автопереводы\n'),
('Digital Сбербанк Премьер', 'Клиентский менеджер\n'),
('Развитие лояльности в МП СБОЛ', 'Баллы Спасибо (также: Партнёры)\n'),
('Дебетовые карты в мобильном приложении', 'Оформление дебетовых карт\n'),
('Карта в телефоне', 'Apple Pay, Apple Watch\n'),
('Digital PIN', 'Установка/смена ПИН-кода карты\n'),
('Плановый и досрочный перевыпуск дебетовых карт', 'Блокировка карт\nинформация по заблокированной карте\nперевыпуск карт\n'),
('Самозанятые', 'Самозанятые\n'),
('Цифровой Кредит', 'Кредиты\n'),
('ВС.МП вклады', 'Открытие вклада\nВклады (Редактирование)\nЗакрытие вклада\n'),
('Комиссионные продукты', 'ОМС\nСейфы\n'),
('PFM Бюджет', 'Анализ расходов\nБюджет\n'),
('Mobile Online POS (Розничный кредит)', 'Мобильное интернет кредитование\nОМС\n'),
('Текстовый чат', 'Чат с оператором Сбербанка\nОбратная связь (письма, написать в банк)\nЗвонок в банк\n'),
('[ЕФС].Кредитные карты.Космонавты (Доп. услуги и сервисы)', 'Автопогашение по Кредитной карте\nЗадолженность по кредитным картам в МП\n'),
('ЕФС. Брокеридж. Мобайл', 'Брокерское обслуживание\n'),
('[ЕФС] Б.УБ.СБОЛ.Баллонг', 'Продукты и сервисы Благосостояния в СБОЛ\nПродукт - Инвестиции\n'),
('ЕФС. Страхование', 'Постпродажное обслуживание страховых продуктов\nВитрина страховых продуктов\nПродажа страховых продуктов\n'),
('Телеком', 'Сбербанк-Телеком\nВиджет Сб-Телеком');

create table error
(
    error_count int,
    last_error int
)