## -*- base coding: utf-8 -*-
# Class to preprocess ru-lang reviews


import pandas as pd
import numpy as np
import re, string
import pymorphy2
import Stemmer

import nltk
nltk.download("stopwords")
from nltk.corpus import stopwords

from pymystem3 import Mystem
from string import punctuation

import itertools
from tqdm import tqdm
from googletrans import Translator

russian_stopwords = stopwords.words("russian")

class CustomPreProcessing(object):
    '''
    Class to preprocess specific reviews.
    '''

    def __init__(self):
        print('''Welcome this is our custom preprocessing class
        ''')

    @classmethod
    def remove_whitespace(self, text):
        """
        Function to remove extra whitespaces from text
        @param text: (str) text
        @return: (str) text clean from extra space
        """
        text = text.strip()
        return " ".join(text.split())

    @classmethod
    def remove_phone_number(self, x):
        '''
        Function to find and remove any phone numbers with regex
        @param x: (str) text
        @return: (str) text without phone numbers
        '''
        phone = []
        r = re.compile(r'(\d{3}[-\.\s]??\d{3}[-\.\s]??\d{4}|\(\d{3}\)\s*\d{3}[-\.\s]??\d{4}|\d{3}[-\.\s]??\d{4})')
        for i in r.findall(x):
            phone.append(i)
            x = x.replace(i, ' ')
        return x, phone

    @classmethod
    def remove_upper_case(self, text):
        '''
        Function to transform upper string in title words
        @param text: (str) text
        @return: (str) text without upper words
        '''
        sentences = text.split("\n")
        new_sentences = []
        for i in sentences:
            words = text.split()
            stripped = [w.title() if w.isupper() else w for w in words]
            new_sentences.append(" ".join(stripped))
        return "\n".join(new_sentences)

    @classmethod
    def find_corres(self, text, list_words):
        '''
        Function to locate a word or list of words in a string.
        @param text: (str) text
        @param list_words: (str or list) word or list of words to find in the text
        @return: (bool) indicate if the text contained the list
        '''
        if type(list_words) == str:
            list_words = [list_words]

        results = []
        for i in list_words:
            if i in text:
                results.append(True)
            else:
                results.append(False)
        return True if sum(results) > 0 else False

    @classmethod
    def remove_string(self, text, list_sentences):
        '''
        Function
        @param text: (str) text
        @param list_sentences: (list) list of sentences to be delete
        @return: (str) text without a list of sentences
        '''
        text = text.lower()
        for i in list_sentences:
            if text.find(i.lower()) != -1:
                text = text.replace(i.lower(), ' ')
        return text

    @classmethod
    def find_words(self, text, words_list):
        '''
        Function to locate words in text, estime if the words are closed and if so delete them.
        @param text: (str) text
        @param words_list: (str or list) word or list of words to locate and delete
        @return: (str) text with or without the list (words can't be seperated by more than 5 words)
        '''
        if type(words_list) == str:
            words_list = [words_list]
        if type(text) == tuple:
            print(text)
        if all(x.lower() in text.lower() for x in words_list):
            text_ = text.split()
            idx = []
            for i, j in itertools.product(words_list, enumerate(text_)):
                if i.lower() in j[1].lower():
                    idx.append(j[0])
            successive_index = [True if (t - s <= 5) else False for s, t in zip(idx, idx[1:])]
            if sum(successive_index) == len(words_list) - 1:
                text = text.split()
                for i in sorted(set(idx))[::-1]:
                    del text[i]
                if len(text) > 0:
                    return ' '.join(text)
                else:
                    return "empty"
            else:
                return text
        else:
            return text


class PreProcessing(object):
    '''
    Class to preprocess common reviews
    '''

    def __init__(self):
        print("Welcome this is our basic preprocessing class")

    @classmethod
    def detect_lang_google(self, x):
        '''
        Function to detect the language of the string
        @param x: (str) sentences of text to detect language
        @return: (str or nan) language of the sentence
        '''
        translate = Translator()
        try:
            return translate.detect(x).lang
        except:
            return np.nan

    @classmethod
    def change_to_e(self, text):
        '''
        Change the letter "ё" to "e"
        @param text: (str) sentence
        @return: (str) clean text
        '''
        text = text.replace("ё", "е")
        return text

    @classmethod
    def remove_URL(self, text):
        '''
        Function to remove url from text.
        @param text: (str) sentence
        @return: (str) clean text

        '''
        url = re.compile(r'https?://\S+|www\.\S+')
        return url.sub(r'', text)

    @classmethod
    def remove_html(self, text):
        '''
        Function regex to clean text from html balises.
        @param text: (str) sentence
        @return: (str) clean text
        '''
        html = re.compile(r'<.*?>')
        return html.sub(r'', text)

    @classmethod
    def remove_appeals(self, text):
        '''

        @param text: (str) sentence
        @return: (str) clean text
        '''
        appeal = re.compile(r'@[^\s]+')
        return appeal.sub(r'', text)

    @classmethod
    def remove_emoji(self, text):
        '''
        Function to remove emojis, symbols and pictograms etc. from texts,
        not a replacement cause there is a risk of overfitting...
        @param text: (str) sentences
        @return: (str) clean text
        '''
        emoji_pattern = re.compile("["
                                   u"\U0001F600-\U0001F64F"
                                   u"\U0001F300-\U0001F5FF"
                                   u"\U0001F680-\U0001F6FF"
                                   u"\U0001F1E0-\U0001F1FF"
                                   u"\U00002702-\U000027B0"
                                   u"\U000024C2-\U0001F251"
                                   "]+", flags=re.UNICODE)
        return emoji_pattern.sub(r'', text)

    @classmethod
    def cleaning(self, text):
        '''
        Function to remove special characters in texts
        @param text: (pandas dataframe) texts
        @return: (pandas dataframe) clean texts
        '''
        text = text.replace("(<br/>)", "")
        text = text.replace('(<a).*(>).*(</a>)', '')
        text = text.replace('(&amp)', '')
        text = text.replace('(&gt)', '')
        text = text.replace('(&lt)', '')
        text = text.replace('(\xa0)', ' ')
        text = text.replace("\n", " ")
        text = text.replace("\x92", "'")
        text = re.sub('[^а-яА-Я]+',  # ^a-zA-Zа-яА-Я
                      ' ', text)
        text = text.lower()

        return text

    @classmethod
    def remove_specific_char(self, text):
        '''
        Function to remove specific characters
        @param text: (str) texts
        @return: (str) text without specific characters
        '''
        table = '!"#$%&()*+,./:;<=>?@[\]^_`{|}~•'
        table = str.maketrans(' ', ' ', table)
        words = text.split()
        stripped = [w.translate(table) for w in words]
        return ' '.join(stripped)

    @classmethod
    def remove_upper_case(self, text):
        '''
        Function to transform upper string in title words
        @param text: (str) texts
        @return: (str) texts without upper words
        '''
        words = text.split()
        stripped = [w.title() if w.isupper() else w for w in words]
        return " ".join(stripped)

    @classmethod
    def lemmatize(self, text):
        '''
        Function to normilize words in text
        @param text: (str) texts
        @return: (str) texts whith words in normal form
        '''
        ma = pymorphy2.MorphAnalyzer()
        text = " ".join(ma.parse(word)[0].normal_form for word in text.split())
        return text

    @classmethod
    def stremming(self, text):
        '''
        Function to normilize words in text
        @param text: (str) texts
        @return: (str) texts whith words in normal form
        '''
        stemmer = Stemmer.Stemmer('russian')
        text = " ".join(stemmer.stemWord(word) for word in text.split())
        return text

    @classmethod
    def lemmatize_fast(self, text):
        '''
        Function to fast normilize words in text
        @param text: (str) texts
        @return: (str) texts whith words in normal form
        '''
        mystem = Mystem()
        tokens = mystem.lemmatize(text.lower())
        tokens = [token for token in tokens if token not in russian_stopwords \
                  and token != " " \
                  and token.strip() not in punctuation]

        text = " ".join(tokens)

        return text
