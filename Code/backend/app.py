from contextlib import contextmanager
from datetime import datetime
from random import randint

import psycopg2 as pg
from flask import Flask, render_template, jsonify, make_response, request, redirect
from flask_ngrok import run_with_ngrok
from psycopg2.extras import RealDictCursor, register_uuid

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False
run_with_ngrok(app=app)
register_uuid()


@contextmanager
def db_connection():
    with pg.connect(
            host='localhost',
            user='ivansizov',
            # password='',
            dbname='reviews',
            port='5432'
    ) as con:
        with con.cursor(cursor_factory=RealDictCursor) as cursor:
            yield cursor


def db_get_commands():
    with db_connection() as con:
        con.execute("select * from commands order by id")
        commands = con.fetchall()
    return commands


COLUMNS = ['create_time', 'rate', 'review_title', 'review_text', 'platform']
INTONATION = ['intonation']
COMMANDS = [f'command_{i}_estimate' for i in range(1, 44)]
RETRAIN_THRESHOLD = 500
ESTIMATE_THRESHOLD = 50


def db_add_review(review_data, with_pretrain=False):
    columns = COLUMNS + (INTONATION + COMMANDS if with_pretrain else [])
    values = [f'%({column})s' for column in columns]
    with db_connection() as con:
        con.execute(f"insert into user_reviews ({', '.join(columns)}) values ({', '.join(values)}) returning id",
                    review_data)
        new_review = con.fetchone()
    return new_review


def db_get_random_review_id():
    untrained = [f'{intonation} is Null' for intonation in INTONATION]
    untrained += [f'{command}=0' for command in COMMANDS]
    with db_connection() as con:
        con.execute(f"select * from user_reviews where rate < 5 and {' and '.join(untrained)} "
                    f"offset {randint(0, 50000)} limit 1")
        review_data = con.fetchone()
    return review_data['id']


def db_change_review(review_data):
    excluded = ('id', "create_time", "rate", "review_title", "review_text", "platform")
    update_values = ', '.join([f'{k}=%({k})s' for k in review_data if k not in excluded])
    with db_connection() as con:
        con.execute(f"update user_reviews set {update_values} where id=%(id)s", review_data)


def db_get_review(review_id):
    with db_connection() as con:
        con.execute(f"select * from user_reviews where id=%(id)s", {"id": review_id})
        review_data = con.fetchone()
    return review_data


def db_increment_error():
    with db_connection() as con:
        con.execute(f"update error set error_count = error_count + 1 returning error_count")
        count = con.fetchone()
    return count


def db_get_last_error():
    with db_connection() as con:
        con.execute(f"select last_error from error")
        count = con.fetchone()
    return count


def db_update_error():
    with db_connection() as con:
        con.execute(f"update error set last_error = error_count returning error_count")
        count = con.fetchone()
    return count


def db_get_reviews_by_command_id(command_id, offset):
    condition = f"command_{command_id}_estimate > {ESTIMATE_THRESHOLD}"
    limit = 50
    with db_connection() as con:
        con.execute("select * from commands order by id")
        commands = con.fetchall()
        con.execute(f"select * from user_reviews where {condition} "
                    f"order by create_time desc offset %(offset)s limit %(limit)s",
                    {"id": command_id, "offset": offset, "limit": limit})
        review_data = con.fetchall()
    filtered_review_data = []
    for record in review_data:
        filtered_record = {"teams": []}
        for k, v in record.items():
            if k == 'review_title':
                if not v or (isinstance(v, str) and v.strip() == ''):
                    filtered_record[k] = "Без заголовка :("
                else:
                    filtered_record[k] = v
            elif k in COMMANDS:
                command_id = int(k.split('_')[1])
                if v > ESTIMATE_THRESHOLD:
                    filtered_record["teams"].append(
                        [command for command in commands if command['id'] == command_id][0])
            else:
                filtered_record[k] = v
        filtered_review_data.append(filtered_record)
    return filtered_review_data


def mock_ml_predict(text):
    # predicted = process_text(text)
    predicted = {'intonation': bool(randint(0, 1))}
    predicted.update({f'command_{i}_estimate': randint(0, 100) for i in range(1, 44)})
    return predicted


def mock_ml_retrain():
    # send_command_to_retraining()
    pass


def maybe_retrain_model():
    count = int(db_increment_error()['error_count'])
    last_error = int(db_get_last_error()['last_error'])
    if count - last_error >= RETRAIN_THRESHOLD:
        mock_ml_retrain()
        db_update_error()


def get_form_data(with_pretrain=False):
    review_data = {k: request.form.get(k, None) for k in COLUMNS}
    review_data['platform'] = bool(review_data['platform'])
    review_data['create_time'] = datetime.utcnow()
    if with_pretrain:
        pretrain_data = {k: int(request.form.get(k, 0)) for k in COMMANDS}
        pretrain_data.update({k: request.form.get(k, None) for k in INTONATION})
        pretrain_data['intonation'] = True if pretrain_data.get('intonation', '') == 'True' else False
        review_data.update(pretrain_data)
    else:
        predicted_data = mock_ml_predict(review_data['review_text'])
        print(predicted_data)
        review_data.update(predicted_data)
    return review_data


@app.route('/', methods=['GET'])
def index():
    return render_template('index.html')


@app.route('/customer_reviews/create', methods=['GET', 'POST'])
def create_review():
    action = 'create'
    with_pretrain = request.args.get('with_pretrain', None)
    commands = db_get_commands() if with_pretrain else None
    if request.method == 'POST':
        if request.form.get('action', '') == action:
            review_data = get_form_data(with_pretrain=with_pretrain)
            new_review = db_add_review(review_data=review_data, with_pretrain=True)
            return redirect(f"/customer_reviews/{new_review['id']}/edit", code=302)
    return render_template('morda.html', action=action, with_pretrain=with_pretrain, commands=commands)


@app.route('/customer_reviews/<uuid:review_id>/edit', methods=['GET', 'POST'])
def change_review(review_id):
    action = 'edit'
    commands = db_get_commands()
    with_pretrain = True
    review_data = db_get_review(review_id=review_id)
    if request.method == 'POST':
        if request.form.get('action', '') == action:
            review_data = get_form_data(with_pretrain=with_pretrain)
            review_data['id'] = review_id
            db_change_review(review_data=review_data)
    return render_template('morda.html', action=action, with_pretrain=with_pretrain, commands=commands, review_data=review_data)


@app.route('/i_am_lucky', methods=['GET', 'POST'])
def get_random_review():
    action = 'edit'
    commands = db_get_commands()
    with_pretrain = True
    if request.method == 'GET':
        review_id = db_get_random_review_id()
    elif request.method == 'POST':
        review_id = request.form.get('id')
    review_data = db_get_review(review_id=review_id)
    if request.method == 'POST':
        if request.form.get('action', '') == action:
            review_data = get_form_data(with_pretrain=with_pretrain)
            review_data['id'] = review_id
            db_change_review(review_data=review_data)
            return redirect(f"/i_am_lucky", code=302)
    return render_template('morda.html', action=action, with_pretrain=with_pretrain, commands=commands, review_data=review_data)


@app.route('/api/commands/get_all', methods=['GET'])
def api_get_commands():
    try:
        resp = db_get_commands()
    except Exception as e:
        resp = {'error': e}
        return make_response(jsonify(resp), 500)
    return make_response(jsonify(resp), 200)


@app.route('/api/customer_reviews/change', methods=['POST'])
def api_change_review():
    try:
        review_id = request.json.get('id')
        command_ids = request.json.get('teams')
        review_data = db_get_review(review_id=review_id)
        if review_data:
            for command in COMMANDS:
                command_id = int(command.split('_')[1])
                if command_id in command_ids:
                    review_data[command] = 100
                else:
                    review_data[command] = 0
            db_change_review(review_data=review_data)
            maybe_retrain_model()
        resp = (jsonify({'error': None}), 200)
    except Exception as e:
        resp = jsonify({'error': str(e)}, 500)
    return make_response(resp)


@app.route('/api/customer_reviews/category/<int:command_id>/get_batch', methods=['GET'])
def api_get_reviews(command_id):
    offset = int(request.args.get('offset', 0))
    data = db_get_reviews_by_command_id(command_id=command_id, offset=offset)
    return make_response(jsonify(data), 200)


if __name__ == '__main__':
    app.run()
