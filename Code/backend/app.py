from flask import Flask, render_template, jsonify
import psycopg2 as pg


app = Flask(__name__)


def get_review_from_db():
    pass


@app.route('')
def change_review():
    pass


@app.route('')
def get_review():
    data = {}
    return jsonify(data)
