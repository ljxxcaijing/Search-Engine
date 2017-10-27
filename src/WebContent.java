//package com.baiyu.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class WebContent {
    public String getOneHtml(final String htmlurl) throws IOException
    {
        URL url;
        String temp;
        final StringBuffer sb = new StringBuffer();
        try
        {
            url = new URL(htmlurl);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 读取网页全部内容
            while ((temp = in.readLine()) != null)
            {
                sb.append(temp);
            }
            in.close();
        }
        catch (final MalformedURLException me)
        {
            System.out.println("你输入的URL格式有问题！请仔细输入");
            me.getMessage();
            throw me;
        }
        catch (final IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        return sb.toString();
    }


    public String getTitle(final String s)
    {
        String regex;
        String title = "";
        final List list = new ArrayList();
        regex = "< t itle>.*?< / t itle>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }

        for (int i = 0; i < list.size(); i++)
        {
            title = title + list.get(i);
        }
        return outTag(title);
    }





    public List getLink(final String s)
    {
        String regex;
        final List list = new ArrayList();
        regex = "< a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)< /  a >";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }



    public List getScript(final String s)
    {
        String regex;
        final List list = new ArrayList();
        regex = "< script.*?< / s cript >";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }


    public List getCSS(final String s)
    {
        String regex;
        final List list = new ArrayList();
        regex = "< st yle.*?< / styl e >";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }


    public String outTag(final String s)
    {
        return s.replaceAll("<.*?>", "");
    }



    public HashMap getFromYahoo(final String s)
    {
        final HashMap hm = new HashMap();
        final StringBuffer sb = new StringBuffer();
        String html = "";
        System.out.println("\n------------------开始读取网页(" + s + ")--------------------");
        try
        {
            html = getOneHtml(s);
        }
        catch (final Exception e)
        {
            e.getMessage();
        }
        // System.out.println(html);
        System.out.println("------------------读取网页(" + s + ")结束--------------------\n");
        System.out.println("------------------分析(" + s + ")结果如下--------------------\n");
        String title = outTag(getTitle(html));
        title = title.replaceAll("_雅虎知识堂", "");
        // Pattern pa=Pattern.compile("
        // class=\"original\">(.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)",Pattern.DOTALL);
        final Pattern pa = Pattern.compile("<  div class=\"original\">(.*?)< /  p>< /div>", Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find())
        {
            sb.append(ma.group());
        }
        String temp = sb.toString();
        temp = temp.replaceAll("(< b r >)+?", "\n");// 转化换行
        temp = temp.replaceAll("< p >< e m >.*?< /  em><  /  p>", "");// 去图片注释
        hm.put("title", title);
        hm.put("original", outTag(temp));
        return hm;
    }



    public static void main(final String args[])
    {
        String url = "";
        final List list = new ArrayList();
        System.out.print("输入URL，一行一个，输入结束后输入 go 程序开始运行:   \n");
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            while (!(url = br.readLine()).equals("go"))
            {
                list.add(url);
            }
        }
        catch (final Exception e)
        {
            e.getMessage();
        }
        final WebContent wc = new WebContent();
        HashMap hm = new HashMap();
        for (int i = 0; i < list.size(); i++)
        {
            //hm = wc.getFromYahoo(list.get(i));
            System.out.println("标题： " + hm.get("title"));
            System.out.println("内容： \n" + hm.get("original"));
        }




        // System.out.println(htmlurl+"网页内容结束");



    }

}