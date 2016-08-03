package com.lw.blog.until;


import org.pegdown.*;
import org.pegdown.ast.RootNode;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.*;
import java.util.Collections;


/**
 * Created by LWang on 2016/7/28.
 */
public class PegDownMarkdownService implements MarkdownService{

	public static void main(String args[]) throws Exception{
		String html = null;
		int hasRead = 0;
		FileInputStream inputStream = new FileInputStream("E:\\MySql.md");
		byte[] bbuf = new byte[1024];
		while( (hasRead=inputStream.read(bbuf)) >0){
			html += new String(bbuf,0,hasRead,"UTF-8");
		}
		System.out.println(new PegDownMarkdownService().renderToHtml(html));
	}
	private final PegDownProcessor pegdown;

	public PegDownMarkdownService() {
		pegdown = new PegDownProcessor(Extensions.ALL);
	}

	@Override
	public String renderToHtml(String markdownSource) {
		// synchronizing on pegdown instance since neither the processor nor the underlying parser is thread-safe.
		synchronized (pegdown) {
			RootNode astRoot = pegdown.parseMarkdown(markdownSource.toCharArray());
			ToHtmlSerializer serializer = new ToHtmlSerializer(new LinkRenderer());
			//Collections.singletonMap(VerbatimSerializer.DEFAULT, PygmentsVerbatimSerializer.INSTANCE)
			return serializer.toHtml(astRoot);
		}
	}
}
