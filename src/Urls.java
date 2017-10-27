import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
根据指定的规则，通过构造正则表达式获取网址
*/
public class Urls
{
    private String startUrl;                                         //开始采集网址
    String  urlContent;
    String ContentArea;
    private String strAreaBegin ,strAreaEnd ;                       //采集区域开始采集字符串和结束采集字符串
    private String stringInUrl,stringNotInUrl;
    String strContent;//获得的采集内容
    String[] allUrls;                                               //采集到的所有网址
    private String regex;                                           //采集规则

    UrlAndTitle   urlAndTitle=new UrlAndTitle();    //存储网址和标题                    


    public static void main(String[] args)
    {
        Urls myurl=new Urls("<body","/body>");
        myurl.getStartUrl("http://www.baidu.com/");
        myurl.getUrlContent();
        myurl.getContentArea();
        myurl.getStartUrl("http://www.google.com/");
        myurl.getStringNotInUrl("google");
        myurl.Urls();

        //System.out.println("startUrl:"+myurl.startUrl);
        //System.out.println("urlcontent:"+myurl.urlContent);
        //System.out.println("ContentArea:"+myurl.ContentArea);

    }


    //初始化构造函数 strAreaBegin 和strAreaEnd

    public Urls (String strAreaBegin,String strAreaEnd)
    {
        this.strAreaBegin=strAreaBegin;
        this.strAreaEnd=strAreaEnd;
    }

    public void Urls()
    {
        int i=0;
        String regex ="<a.*?/a>";
        Pattern pt=Pattern.compile(regex);
        Matcher mt=pt.matcher(ContentArea);
        while(mt.find())
        {
            System.out.println(mt.group());
            i++;

            //获取标题
            Matcher title=Pattern.compile(">.*?</a>").matcher(mt.group());
            while(title.find())
            {
                System.out.println("标题:"+title.group().replaceAll(">|</a>",""));
            }

            //获取网址
            Matcher myurl=Pattern.compile("href=.*?>").matcher(mt.group());
            while(myurl.find())
            {
                System.out.println("网址:"+myurl.group().replaceAll("href=|>",""));
            }

            System.out.println();


        }

        System.out.println("共有"+i+"个符合结果");

    }

    //获得开始采集网址
    public void getStartUrl(String startUrl)
    {
        this.startUrl=startUrl;
    }

    //获得网址所在内容;
    public void getUrlContent()
    {

        StringBuffer is=new StringBuffer();
        try
        {
            URL myUrl=new URL(startUrl);
            BufferedReader br= new BufferedReader(
                    new InputStreamReader(myUrl.openStream()));

            String s;
            while((s=br.readLine())!=null)
            {
                is.append(s);
            }
            urlContent=is.toString();
        }
        catch(Exception e)

        {
            System.out.println("网址文件未能输出");
            e.printStackTrace();
        }


    }


    //获得网址所在的匹配区域部分
    public void getContentArea()
    {
        int pos1=0,pos2=0;
        pos1= urlContent.indexOf(strAreaBegin)+strAreaBegin.length();
        pos2=urlContent.indexOf(strAreaEnd,pos1);
        ContentArea=urlContent.substring(pos1,pos2);
    }

    //以下两个函数获得网址应该要包含的关键字及不能包含的关键字
    //这里只做初步的实验。后期，保护的关键字及不能包含的关键字应该是不只一个的。
    public void getStringInUrl(String stringInUrl)
    {
        this.stringInUrl=stringInUrl;

    }

    public void getStringNotInUrl(String stringNotInUrl)
    {
        this.stringNotInUrl=stringNotInUrl;
    }

    //获取采集规则

    //获取url网址
    public void getUrl()
    {

    }

    public String getRegex()
    {
        return regex;

    }

    class UrlAndTitle
    {
        String myURL;
        String title;
    }
}