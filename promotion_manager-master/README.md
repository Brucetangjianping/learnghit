promotion_manager
===============

推广管理中心
## 登陆
### 登陆
URL: `/api/login.json`
Method: `POST`
Params: 

```
userName:String;
password:String;
```
Response:

```
token:String
```

## 公众号管理
### 查看所有公众号
URL: `/api/mp/list.json`
Method: `GET`
Params: 
Response:

``` 
[{
id: int;
wechatId: String;
wechatName: String;
province: String;
city: String;
district: String;
appId: String;
appSecret: String;
},
...
]
```
### 添加公众号
URL: `/api/mp/add.json`
Method: `POST`
Params: 

```
wechatId: String;
wechatName: String;
province: String;
city: String;
district: String;
appId: String;
appSecret: String;
```
Response:

```
{
retCode: int;
errMsg: String;
}
```

### 添加模板
URL: `/api/mp/template.json`
Method: `POST`
Params: 

```
wechatId: String;
templateName: String;
templateValue: String;
```
Response:

```
{
retCode: int;
errMsg: String;
}
```

## PFeeds管理
### 获取所有区域
URL: `/api/pfeeds/locations.json`
Method: `GET`
Params: 
Response:

```
[{
id: int;
province: String;
city: String;
district: String;
};
...
]
```

### 添加区域
URL: `/api/pfeeds/locations.json`
Method: `POST`
Params: 

```
province: String;
city: String;
district: String;
```
Response:

```
{
retCode: int;
errMsg: String;
}
```

### 获取所有PFeeds
URL: `/api/pfeeds/feeds.json`
Method: `GET`
Params:  `locationId: int`
Response:

```
[
{
promotionTitle: String;
feeds: 
    [
        {
            url: String;
            title: String;
            type: int;
            // 默认为31, 如果只在微信中显示为1, 如果只在网页中显示为2
            visibility: int;
        };
        ...
    ]
},
...
]
```

### 更新PFeeds
URL: `/api/pfeeds/feeds.json`
Method: `POST`
Params:

```
locationId: int;
promotion: String;
/*
 
{
    promotionTitle: String;
    feeds: 
    [
        {
            url: String;
            type: int;
            qrCode: String;
            weChatName: String;
            brief: String;
            imageUrl: String;
            postDate: String;
            // 默认为31, 如果只在微信中显示为1, 如果只在网页中显示为2
            visibility: int;
        };
        ...
    ]
}
*/
```
Response:

```
{
retCode: int;
errMsg: String;
}
```

## 图文消息管理
### 获取所有图文消息
URL: `/api/appmsg/list.json`
Method: `GET`
Params: 
Response:

```
[{
msgId: int;
title: String;
wechatIds: String;  // 逗号分隔字符串  
publishTime: int;
status: int;   // 0表示已保存,1表示已发布
};
...
]
```

### 获取单条图文消息
URL: `/api/appmsg/msg.json`
Methon: `GET`
Params:
`msgId: int`
Response:

```
[
{
id: int,
author: String,
title: String,
content: String,
content_source_url: String,
digest: String,
}
...
]
```

### 保存图文消息
URL: `/api/appmsg/msg.json`
Methon: `POST`
Params:

```
msgId: int; // optional
articleIds: List<int>;
```
Response:
`msgId: int`

### 复制图文消息
URL: `/api/appmsg/copy.json`
Methon: `POST`
Params:

```
msgId: int; 
```
Response:
`msgId: int`

### 保存文章
URL: `/api/appmsg/article.json`
Methon: `POST`
Params:

```
id: int;   // 0 为新建
author: String
title: String
content: String,
content_source_url: String,
digest: String,
```
Response:
`articleId: int`

### 发布图文消息
URL: `/api/appmsg/publish.json`
Methon: `POST`
Params:

```
msgId: int,
wechatIds: List[String],
```
Response:
`wechatId: List[String];`
