from flask import Flask, render_template, jsonify, make_response
from markupsafe import escape
import psycopg2 as pg
from contextlib import contextmanager

app = Flask(__name__)


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


def db_add_review(review_data):
    with db_connection() as con:
            con.executemany('''
                insert into user_reviews (
                    create_time,
                    rate,
                    review_title,
                    review_text,
                    platform,
                    intonation,
                    command_1_estimate,
                    command_2_estimate,
                    command_3_estimate,
                    command_4_estimate,
                    command_5_estimate,
                    command_6_estimate,
                    command_7_estimate,
                    command_8_estimate,
                    command_9_estimate,
                    command_10_estimate,
                    command_11_estimate,
                    command_12_estimate,
                    command_13_estimate,
                    command_14_estimate,
                    command_15_estimate,
                    command_16_estimate,
                    command_17_estimate,
                    command_18_estimate,
                    command_19_estimate,
                    command_20_estimate,
                    command_21_estimate,
                    command_22_estimate,
                    command_23_estimate,
                    command_24_estimate,
                    command_25_estimate,
                    command_26_estimate,
                    command_27_estimate,
                    command_28_estimate,
                    command_29_estimate,
                    command_30_estimate,
                    command_31_estimate,
                    command_32_estimate,
                    command_33_estimate,
                    command_34_estimate,
                    command_35_estimate,
                    command_36_estimate,
                    command_37_estimate,
                    command_38_estimate,
                    command_39_estimate,
                    command_40_estimate,
                    command_41_estimate,
                    command_42_estimate,
                    command_43_estimate
                    )
                    values (
                        %(communication_id)s,
                        %(virtual_phone_number)s,
                        %(communication_type)s,
                        %(cpn_region_id)s,
                        %(id)s,
                        %(total_wait_duration)s,
                        %(cpn_region_name)s,
                        %(is_lost)s,
                        %(clean_talk_duration)s,
                        %(total_duration)s,
                        %(contact_phone_number)s,
                        %(finish_time)s,
                        %(wait_duration)s,
                        %(finish_reason)s,
                        %(source)s,
                        %(start_time)s,
                        %(call_records)s,
                        %(talk_duration)s,
                        %(direction)s,
                        %(last_talked_employee_full_name)s
                    ) on conflict (communication_id) do nothing
                    ''')


def change_review_in_db(review_data):
    with db_connection() as con:
        pass


@app.route('/api/customer_reviews/change/<uuid:review>', methods=['POST'])
def change_review(review):
    pass
    return make_response('', 200)


@app.route('/api/customer_reviews/get/<uuid:review>', methods=['GET'])
def get_review(review):
    data = {}
    return make_response(jsonify(data), 200)

