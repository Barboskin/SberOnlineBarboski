import csv
import datetime
import psycopg2 as pg
from contextlib import contextmanager


@contextmanager
def db_connection():
    with pg.connect(
            host='localhost',
            # user='',
            # password='',
            dbname='reviews',
            port='5432'
    ) as con:
        with con.cursor() as cursor:
            yield cursor


def db_create_review(con, review_data):
    con.execute(
        '''
        insert into user_reviews (
            create_time,
            rate,
            review_title,
            review_text,
            platform
        )
        values (
            %(create_time)s,
            %(rate)s,
            %(review_title)s,
            %(review_text)s,
            %(platform)s
        ) on conflict (id) do nothing
        ''',
        review_data
    )


def parse_apple(line):
    review_data = {
        "create_time": datetime.datetime.strptime(line['Date'], '%Y-%m-%d %H:%M:%S'),
        "rate": int(line['Rating']),
        "review_title": line['Title'],
        "review_text": line['Review'],
        "platform": True,
    }
    return review_data


def parse_google(line):
    review_data = {
        "create_time": datetime.datetime.strptime(line['Review Submit Date and Time'], '%Y-%m-%dT%H:%M:%S%z'),
        "rate": int(line['Star Rating']),
        "review_title": line['Review Title'],
        "review_text": line['Review Text'],
        "platform": False,
    }
    return review_data


files = [('AppStore.csv', 'utf-8')]
files += [
    (f'reviews_reviews_ru.sberbankmobile_{date}.csv', 'utf-16')
    for date in [f"20200{i}" for i in range(1, 8)]
]


for filename, encoding in files:
    print(f'processing_file: {filename}')
    with open(filename, 'r', encoding=encoding) as f, db_connection() as con:
        reader = csv.DictReader(f)
        for line in reader:
            try:
                if filename == 'AppStore.csv':
                    review_data = parse_apple(line=line)
                else:
                    review_data = parse_google(line=line)
                if review_data["review_text"]:
                    db_create_review(con=con, review_data=review_data)
            except (ValueError, KeyError):
                pass
