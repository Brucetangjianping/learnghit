__author__ = 'lei'
# -*- coding: UTF-8 -*-

import urllib2
import urllib
import json
import hashlib
import datetime
import MySQLdb as mdb

class TestUtil(object):
    def __init__(self, url_prefix):
        self._url_prefix = url_prefix

    def assert_fail(self, content, errCode):
        json_content = json.loads(content)
        if json_content['errCode'] == errCode:
            return True
        else:
            print 'expect errCode:', errCode, ' real errCode:', json_content['errCode']
            return False

    def assert_contain(self, content, expect_result):
        json_content = json.loads(content)
        if json_content is None:
            print 'can not load json data'
            return False
        if json_content['errCode'] != 0:
            print 'get error message', json_content['msg']
            return False
        json_data = json_content['data']
        return self.contain(json_data, expect_result)

    def assert_equal(self, content, expect_result):
        json_content = json.loads(content)
        if json_content is None:
            print 'can not load json data'
            return False
        if json_content['errCode'] != 0:
            print 'get error message', json_content['msg']
            return False
        json_data = json_content['data']
        if isinstance(expect_result, dict):
            for key in expect_result:
                if key not in json_data:
                    print key, 'not exist in result'
                    return False
                elif expect_result[key] != json_data[key]:
                    print key, 'not equals in result'
                    print '****expect****'
                    print expect_result[key]
                    print '****result****'
                    print json_data[key]
                    print '****end****'
                    return False
            for key in json_data:
                if key not in expect_result:
                    print key, 'not exist in expect'
            return True
        elif isinstance(expect_result, list):
            for key in expect_result:
                if key not in json_data:
                    print key, 'not exist in result'
                    return False
            for key in json_data:
                if key not in expect_result:
                    print key, 'not exist in expect'
            return True
        else:
            return json_data == expect_result

    def get_url(self, type):
        return self._url_prefix + type + '.json'

    def get(self, type, values):
        encoded_args = urllib.urlencode(values)
        url = self.get_url(type) + "?" + encoded_args
        req = urllib2.urlopen(url)
        return req.read().decode('utf-8')

    def post(self, type, values):
        data = urllib.urlencode(values)
        url = self.get_url(type)
        req = urllib2.urlopen(url, data)
        return req.read()

    def contain(self, result, expect, prefix = '', print_msg = True):
        if isinstance(expect, dict):
            return self._dict_contains(result, expect, prefix, print_msg)
        elif isinstance(expect, list):
            return self._list_contains(result, expect, prefix, print_msg)
        else:
            if print_msg and (result != expect):
                print 'key:', prefix, ' not match, expect:', expect, 'result:', result
            return result == expect

    def _list_contains(self, result, expect, prefix='', print_msg=True):
        for index, item in enumerate(expect):
            find = False
            for i in result:
                #print 'expect', item, 'result', i
                if self.contain(i, item, prefix + str(index) + '.', False):
                    find = True
                    break
            if not find:
                if print_msg:
                    print 'key:', prefix + str(index), ' not exist in result'
                return False
        return True

    def _dict_contains(self, result, expect, prefix = '', print_msg=True):
        for key in expect:
            if key not in result:
                if print_msg:
                    print 'key:', prefix + key, 'not exist in result'
                return False
            if not self.contain(result[key], expect[key], prefix + key + '.', print_msg):
                return False
        return True

    def clear_data(self, tables=None):
        con = mdb.connect(host = 'localhost', user = "root", passwd="", db = 'mpos')
        with con:
            cur = con.cursor()
            if tables is None:
                cur.execute("show tables")
                tables = [item[0] for item in cur.fetchall()]
            for table in tables:
                cur.execute('delete from ' + table)