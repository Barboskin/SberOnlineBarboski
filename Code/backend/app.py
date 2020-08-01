from flask import Flask, render_template, jsonify, make_response
import psycopg2 as pg
from psycopg2.extras import RealDictCursor, register_uuid
from contextlib import contextmanager

app = Flask(__name__)
register_uuid()

@contextmanager
def db_connection():
    with pg.connect(
            host='localhost',
            # user='',
            # password='',
            dbname='reviews',
            port='5432'
    ) as con:
        with con.cursor(cursor_factory=RealDictCursor) as cursor:
            yield cursor


def db_get_commands():
    with db_connection() as con:
        con.execute("select * from commands")
        commands = con.fetchall()
    print(commands)


COLUMNS = ['create_time', 'rate', 'review_title', 'review_text', 'platform', 'intonation']
COLUMNS += [f'command_{i}_estimate' for i in range(1, 44)]
VALUES = [f'%({column})s' for column in COLUMNS]


def db_add_review(review_data):
    with db_connection() as con:
        con.execute(f"insert into user_reviews ({', '.join(COLUMNS)}) values ({', '.join(VALUES)})",
                    review_data)


def db_change_review(review_data):
    excluded = ('id', "create_time", "rate", "review_title", "review_text", "platform")
    update_values = ', '.join([f'{k}=%({k})s' for k in review_data if k not in excluded])
    with db_connection() as con:
        con.execute(f"update user_reviews set {update_values} where id=%(id)s", review_data)


@app.route('/api/customer_reviews/change', methods=['POST'])
def change_review(review):
    pass
    return make_response('', 200)


@app.route('/api/customer_reviews/category/<int:category>/get_batch', methods=['GET'])
def get_review(v):
    data = {}
    return make_response(jsonify(data), 200)

db_get_commands()
review_data = {
    'id': 'a67e40f0-c64c-443e-9918-ee5dbd8b31b7',
    'intonation': False,
}
db_change_review(review_data)