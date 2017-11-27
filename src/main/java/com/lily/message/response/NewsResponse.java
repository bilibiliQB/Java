package com.lily.message.response;

import java.util.List;
/*
 * 回复图文消息
 * 图文消息个数，限制为8条以内
 */
public class NewsResponse extends BaseResponse {
	// 图文条数
	private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
