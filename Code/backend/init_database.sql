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

-- create table commands
-- (
--     command_1  json, --iOS Platform
--     command_2  json, --Global Navigation
--     command_3  json, --DDA Profile
--     command_4  json, --PFM
--     command_5  json, --PFMMPLACE
--     command_6  json, --PUSH iOS
--     command_7  json, --DBP.Витрины продаж
--     command_8  json, --Госуслуги
--     command_9  json, --iOS Release Engineer
--     command_10 json, --Мессенджер
--     command_11 json, --Store-n-Sales
--     command_12 json, --Integration Platform
--     command_13 json, --Data Driven App
--     command_14 json, --Краудфандинг
--     command_15 json, --DBP.Подарки
--     command_16 json, --Sberbank ID B2C
--     command_17 json, --История операций
--     command_18 json, --Редактируемый профиль клиента
--     command_19 json, --ЕФС Выписки и справки. Mobile
--     command_20 json, --ЕФС.Платежи МП
--     command_21 json, --Платежи. Штрафы
--     command_22 json, --ПДВ в Digital
--     command_23 json, --СБОЛ. Классические переводы
--     command_24 json, --ЕФС.Автоплатежи
--     command_25 json, --Автопереводы
--     command_26 json, --Digital Сбербанк Премьер
--     command_27 json, --Развитие лояльности в МП СБОЛ
--     command_28 json, --Дебетовые карты в мобильном приложении
--     command_29 json, --Карта в телефоне
--     command_30 json, --Digital PIN
--     command_31 json, --Плановый и досрочный перевыпуск дебетовых карт
--     command_32 json, --Самозанятые
--     command_33 json, --Цифровой Кредит
--     command_34 json, --ВС.МП вклады
--     command_35 json, --Комиссионные продукты
--     command_36 json, --PFM Бюджет
--     command_37 json, --Mobile Online POS (Розничный кредит)
--     command_38 json, --Текстовый чат
--     command_39 json, --[ЕФС].Кредитные карты.Космонавты (Доп. услуги и сервисы)
--     command_40 json, --ЕФС. Брокеридж. Мобайл
--     command_41 json, --[ЕФС] Б.УБ.СБОЛ.Баллонг
--     command_42 json, --ЕФС. Страхование
--     command_43 json  --Телеком
-- );