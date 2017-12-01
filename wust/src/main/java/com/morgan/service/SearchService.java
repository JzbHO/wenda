package com.morgan.service;

import com.morgan.model.Question;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/30 0030.
 */
@Service
public class SearchService {
    private static final String SOLR_URL="http://127.0.0.1:8983/solr/wenda";
    private HttpSolrClient client=new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String QUESTION_TITLE_FIELD="question_title";
    private static final String QUESTION_CONTENT_FIELD="question_content";

    public List<Question> searchQuestion(String keyword,int offset,int count,String h1Pre,String h1Pos)
    throws Exception{
        List<Question> questionList=new ArrayList<>();
        SolrQuery query=new SolrQuery(keyword);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(h1Pre);
        query.setHighlightSimplePost(h1Pos);
        query.setParam("hl.fl",QUESTION_CONTENT_FIELD+","+QUESTION_TITLE_FIELD);
        QueryResponse response=client.query(query);
        for(Map.Entry<String,Map<String,List<String>>> entry:response.getHighlighting().entrySet()){
            Question q=new Question();
            q.setId(Integer.parseInt(entry.getKey() ));
            if(entry.getValue().containsKey(QUESTION_CONTENT_FIELD)){
                List<String> contentList=entry.getValue().get(QUESTION_CONTENT_FIELD);
                q.setContent(contentList.get(0));
            }
            if(entry.getValue().containsKey(QUESTION_TITLE_FIELD)){
                List<String> contentList=entry.getValue().get(QUESTION_TITLE_FIELD);
                q.setTitle(contentList.get(0));
            }
            questionList.add(q);
        }
        return questionList;
    }

    public boolean indexQuestion(int qid,String title,String content) throws Exception{
        SolrInputDocument doc=new SolrInputDocument();
        doc.setField("id",qid);
        doc.setField(QUESTION_TITLE_FIELD,title);
        doc.setField(QUESTION_CONTENT_FIELD,content);
        UpdateResponse response=client.add(doc,1000);
        return response!=null&&response.getStatus()==0;

    }




}
