package exts;

public class HTML {
	/**
	 * html select默认选中判断
	 * 判断str1 str1是否相同，相同返回selected 否则为空。
	 * @param str1
	 * @param str2
	 */
	public String selected(Object str1,Object str2)
	{ 
		return str1.equals(str2) ? "selected" : ""; 
	}
	
	/**
	 * html radio默认选中判断
	 * 判断str1 str1是否相同，相同返回checked 否则为空。
	 * @param str1
	 * @param str2
	 */
	public String checked(Object str1,Object str2)
	{ 
		return str1.equals(str2) ? "checked" : ""; 
	}
	
	/**
	 * 输出n个html空格
	 * @param repeat
	 * @return string
	 */
	public String nbsp(int repeat)
	{
		String str = "";
		for(int i=0;i<repeat;i++) {
			str += "&nbsp;";
		}
		
		return str;
	}
	
	/**
	 * 输出n个html BR
	 * @param repeat
	 * @return string
	 */
	public String br(int repeat)
	{
		String str = "";
		for(int i=0;i<repeat;i++) {
			str += "<br />";
		}
		return str;
	}
	
	/**
	 * 编码HTML实体
	 * @param s
	 * @return
	 */
	public String encodeHTML(String s)
	{
	    StringBuffer out = new StringBuffer();
	    for(int i=0; i<s.length(); i++)
	    {
	        char c = s.charAt(i);
	        if(c > 127 || c=='"' || c=='<' || c=='>')
	        {
	           out.append("&#"+(int)c+";");
	        }
	        else
	        {
	            out.append(c);
	        }
	    }
	    return out.toString();
	}
}
