#!/usr/bin/env python
# -*- encoding: utf-8 -*-
# Created on 2017-11-08 21:47:03
# Project: newv2ex

from pyspider.libs.base_handler import *
import MySQLdb
import random
import time

class Handler(BaseHandler):
    crawl_config = {
        'proxy': '127.0.0.1:1080',

    }
    def __init__(self):
        self.db=MySQLdb.connect('localhost', 'root', '52xkj1997', 'wenda',charset='utf8')

    def add_question(self,url,title,content):
        try:
            userId=int(str(100)+str(time.time()).replace(".","")[6:-1]);
            print 'userId='+str(userId)
            name=str(time.time()).replace(".","")[5:-1]
            cursor=self.db.cursor()
            sql='insert into question(title,content,user_id,created_date,comment_count) values("%s","%s","%d",now(),0)'% (title,content,userId)
            sql2='insert into user(id,name,password,salt,head_url) values ("%d","%s","123","123","%s")'%(userId,name,url)
            sql3='select max(id) from question'
            cursor.execute(sql)
            cursor.execute(sql2)
            qid=cursor.execute(sql3)
            results = cursor.fetchall()
            qid=results[0][0]
            self.db.commit()
            print 'commit'
        except Exception, e:
            print e
            self.db.rollback()
        return   qid

    def add_comment(self,qid,content,url,name):
        index=0
        while index<len(content):
            try:
                userId=int(str(time.time()).replace(".","")[6:-1]);
                name=str(time.time()).replace(".","")[5:-1]
                cursor=self.db.cursor()
                sql='insert into comment(content,user_id,created_date,entity_id,entity_type,status) values("%s","%d",now(),"%d",1,1)'% (content[index],userId,qid)
                sql2='insert into user(id,name,password,salt,head_url) values ("%d","%s","123","123","%s")'%(userId,name[index],url[index])
                cursor.execute(sql)
                cursor.execute(sql2)
                self.db.commit()
                index+=1
                print 'commit'
            except Exception, e:
                print e
                self.db.rollback()



    @every(minutes=24 * 60)
    def on_start(self):
        self.crawl('https://www.v2ex.com', callback=self.index_page,validate_cert=False)


    @config(age=10 * 24 * 60 * 60)
    def index_page(self, response):
        for each in response.doc('a[href^="https://www.v2ex.com/?tab="]').items():
            self.crawl(each.attr.href, callback=self.tab_page, validate_cert=False)


    @config(age=10 * 24 * 60 * 60)
    def tab_page(self, response):
        for each in response.doc('a[href^="https://www.v2ex.com/go/"]').items():
            self.crawl(each.attr.href, callback=self.go_page, validate_cert=False)



    @config(age=10 * 24 * 60 * 60)
    def go_page(self, response):
        for each in response.doc('a[href^="https://www.v2ex.com/t/"]').items():
            self.crawl(each.attr.href, callback=self.detail_page,validate_cert=False)
        for each in response.doc('a[href^="https://www.v2ex.com/go/"]').items():
            self.crawl(each.attr.href, callback=self.go_page,validate_cert=False)


    @config(priority=2)
    def detail_page(self, response):
        img=[]
        replay=[]
        username=[]
        k1=0;
        k2=0
        k3=0;

        title=response.doc('h1').text()
        content=response.doc('div.topic_content').html().replace('"','#')
        url=response.doc('#Main').find('img').attr.src
        qid=self.add_question(url,title,content)
        for each in response.doc('tr>td>img').items():
            if(k1>=3):
                break
            else:
                k1+=1
                img.append(each.attr.src)

        for each in response.doc('div.reply_content').items():
            if(k2>=3):
                break
            else:
                k2+=1
                replay.append(each.text())
        for each in response.doc('strong>a').items():
            if(k3>=3):
                break
            else:
                k3+=1
                username.append(each.text())
        self.add_comment(qid,replay,img,username)


        return {
            "url": response.url,
            "title": response.doc('title').text(),
        }







