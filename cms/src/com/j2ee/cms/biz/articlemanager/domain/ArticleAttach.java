package com.j2ee.cms.biz.articlemanager.domain;

import java.io.Serializable;

public class ArticleAttach implements Serializable{
    private static final long serialVersionUID = -1043439396234658409L;

    /** 唯一标识符 */
    private String id;
    private Article article;
    private String path;
    private String type;//（pic,media,attach）

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Article getArticle() {
        return article;
    }
    public void setArticle(Article article) {
        this.article = article;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    
}
