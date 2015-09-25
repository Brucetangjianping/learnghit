# -*- coding: UTF-8 -*-

from TestUtils import *

class Test(TestUtil):
    def __init__(self):
        super(Test, self).__init__('http://localhost:8080/api/')

    def produce_user_detail(self, userId, shareWifis=[], updateWifis=[], connectCount = 0):
        user_detail = {
            "scoreDetails": [
            ],
            "shareCount": len(shareWifis),
            "connectCount": connectCount,
            "updateCount": len(updateWifis),
            "userId": userId,
            }
        for shareWifi in shareWifis:
            user_detail['scoreDetails'].append({
                "type": 0,
                "description": "Shared " + shareWifi,
                "points": 10
            })
        for updateWifi in updateWifis:
            user_detail['scoreDetails'].append({
                "type": 0,
                "description": "Shared " + updateWifi,
                "points": 5
            })
        return user_detail

def get_add_wechat():
    test = Test()
    #test.clear_data(['t_marketing_wechat'])

    params = {
        'wechatId': 'szyzsh',
        'wechatName': '深圳柚子生活',
        'province': '广东省',
        'city': '深圳市',
        'district': '',
        'appId': '1f23ds',
        'appSecret': 'fds456fd1s23'
    }
    test.assert_contain(test.post("mp/add", params), {})

    content = test.get("mp/list", {})
    expect_result = [
        {
            'wechatId': 'szyzsh',
            'wechatName': u'深圳柚子生活',
            'province': u'广东省',
            'city': u'深圳市',
            'district': '',
            'appId': '1f23ds',
            'appSecret': 'fds456fd1s23'
        }
    ]
    print 'test_add_wechat, result', test.assert_contain(content, expect_result)

def test_add_location():
    test = Test()
    test.clear_data(['t_marketing_promotion_config'])
    print 'start test add location'
    params = {
        'province': '广东省',
        'city': '深圳市',
        'district': '',
    }
    test.assert_contain(test.post("pfeeds/locations", params), {})

    expect_result = [
        {
            'province': u'广东省',
            'city': u'深圳市',
            'district': u'',
            }]
    content = test.get('pfeeds/locations', {})
    print 'test_add_location, result', test.assert_contain(content, expect_result)

def test_add_pfeeds():
    test = Test()
    #test.clear_data(['t_marketing_promotion_config'])
    #test_add_location()
    print 'start test add pfeeds'

    #print 'test_add_pfeeds, login', test.assert_contain(test.post("login", {'userName': 'test', "password": 'test'}), {})

    promotion = {
      'promotionTitle': "title",
      'feeds': [
          {
              'url': 'www.youzijie.com',
              'type': 0
          },
          {
              'url': 'www.qq.com',
              'type': 1
          }
      ]
    }
    params = {
        'locationId': 4,
        'promotion': json.dumps(promotion)
    }
    print 'test_add_pfeeds, add', test.assert_contain(test.post("pfeeds/feeds", params), {})

    content = test.get('pfeeds/feeds', {'locationId': 4})
    expect_result = promotion
    print 'test_add_pfeeds, get', test.assert_contain(content, expect_result)

def test_add_appmsg():
    test = Test()
    article_params = {
        'author': 'author',
        'title': 'title from {name}',
        'content': 'This is the first post',
        'content_source_url': 'www.qq.com',
        'digest': "first",
    }

    print 'test_add_appmsg, add article'
    content = test.post("appmsg/article", article_params)
    if not test.assert_contain(content, {}):
        return

    print 'test_add_appmsg, add msg'
    id = json.loads(content)['data']
    msg_params = {
        'articleIds': id
    }
    content = test.post("appmsg/msg", msg_params)
    if not test.assert_contain(content, {}):
        return

    print 'test_add_appmsg, list msg'
    msg_id = json.loads(content)['data']

    content = test.get('appmsg/list', {})
    expect_result = [
        {
            'title': 'title',
            'status': 0,
            'msgId': msg_id
        }
    ]
    if not test.assert_contain(content, expect_result):
        return

    content = test.get('appmsg/msg', {'msgId': msg_id})
    expect_result = [article_params]
    print 'test_add_appmsg, list article'
    if not test.assert_contain(content, expect_result):
        return

def test_preview_msg():
    test = Test()
    article_params = {
        'author': 'author',
        'title': 'title from {name}',
        'content': 'This is the first post',
        'content_source_url': 'www.qq.com',
        'digest': "first",
        }


if __name__ == '__main__':
    #get_add_wechat()
    #test_add_location()
    #test_add_pfeeds()
    test_add_appmsg()

