package exts;

/**
+------------------------------------------------------------------------------<br />
* 分页导航 通用类 暂时提供了5中通用的分页方法；<br />
* 用法：Link link = new Link('当第几页', '总数据量', '分页的url', '每页显示的数据数', '每次显示的页数');<br />
* 类初始化后：String linkstr = link->show(3);<br />
* by 木頭, 从ePHP Link.class移植过来<br />
+------------------------------------------------------------------------------<br />
* @version 2.6
* @author WangXian
* @email wo@wangxian.me
* @creation_date 2008.12.22
* @last_modified 2014-02-06
*/

public class Link
{
	/**
	 * 当前页码
	 */
	private int cpage = 1;
	
	/**
	 * ?page=xx前面的部分
	 */
	private String url;
	
	
	/**
	 * 每次显示的页码数, 有些分页样式无效。
	 */
	private int opage;
	
	/**
	 * 总页数
	 */
	private int totalpage; 
	
	/**
	 * 分页
	 * @param cpage 当前页码
	 * @param totaldata 总数据数
	 * @param url 当前页码
	 * @param pagenum 每页数据数
	 * @param opage 每次显示的页码数, 有些分页样式无效。
	 */
	public Link(int cpage, int totaldata, String url, int pagenum, int opage)
	{
		this.cpage = cpage;
		this.totalpage = (int) (totaldata > 0 ? Math.ceil( totaldata / pagenum ) : 1);
		
		if(url.contains("?"))
			this.url = url;
		else 
			this.url = url + "?";

		this.opage = opage;
	}

	/**
	 * 显示翻页样式<br />
	 * 1 : 最简单的上一页 下一页<br />
	 * 2 : 一次翻翻N页<br />
	 * 3 : 滑动滚动分页<br />
	 * 4 : wap2.0 分页样式<br />
	 * 5 : wap1.2 分页样式<br />
	 * @param style 可选：1,2,3,4,5
	 */
	public String show(int style)
	{
		switch (style) {
			case 1 : // 最简单的上一页 下一些
				return this.f1();
			case 2 : // 一次翻翻N页的 Link
					return this.f2();
			case 3 : // 滑动滚动分页
					return this.f3();
			case 4 : // wap2.0
					return this.f4();
			case 5 : // wap1.2
					return this.f5();
			default:
					return this.f1();
		}
	}

	private String url(int tpage) {
		if(tpage>0)
			return this.url + "&page=" + tpage;
		else 
			return "";
	}

	/** 普通的上一页，下一页方式 */
	private String f1()
	{
		String _linkstr = "\n<p class=\"links\">\n"; //begin
		if (this.totalpage < 2) {
			// int nextpage = this.cpage + 1;
			_linkstr += "<span class=\"disabled\">首页</span>" + " \n";
			_linkstr += "<span class=\"disabled\">上一页</span> \n";
			_linkstr += "<span class=\"disabled\">下一页</span> \n";
			_linkstr += "<span class=\"disabled\">尾页</span> \n";
		} else if (this.cpage < 2) {
			//首页
			int nextpage = this.cpage + 1;
			_linkstr += "<span class=\"disabled\">首页</span>" + " \n";
			_linkstr += "<span class=\"disabled\">上一页</span> \n";
			_linkstr += "<a href=\"" + this.url( nextpage ) + "\">下一页</a> \n";
			_linkstr += "<a href=\"" + this.url ( this.totalpage ) + "\">尾页</a> \n";
		} else if (this.cpage >= this.totalpage) {
			//＝尾页
			int prepage = this.cpage - 1;

			_linkstr += "<a href=\"" + this.url( 1 ) +"\">首页</a>\n";
			_linkstr += "<a href=\"" + this.url( prepage ) +"\">上一页</a>\n";
			_linkstr += "\n<span class=\"disabled\">下一页</span>\n";
			_linkstr += "<span class=\"disabled\">尾页</span>\n";
		} else {
			//正常
			int prepage  = this.cpage - 1;
			int nextpage = this.cpage + 1;

			_linkstr += "<a href=\""+ this.url( 1 ) +"\">首页</a>\n";
			_linkstr += "<a href=\""+ this.url( prepage ) +"\">上一页</a>\n";
			_linkstr += "\n<a href=\""+ this.url( nextpage ) +"\">下一页</a>\n";
			_linkstr += "<a href=\""+ this.url( this.totalpage ) +"\">尾页</a>\n";
		}
		_linkstr += "</p>\n"; //end div
		return _linkstr;
	}

	/** 一次翻N页的 Link */
	private String f2()
	{
		int p1 = (int)(Math.ceil( (this.cpage - this.opage) / this.opage ));

		//计算开始页 结束页
		int beginpage = p1 * (this.opage) + 1;
		int endpage = (p1 + 1) * (this.opage);
		if (endpage > this.totalpage)
		{
			// 最后一页 大于总页数
			endpage = this.totalpage;
		}

		// 前后滚10页
		int preopage = (beginpage - this.opage > 0) ? beginpage - this.opage : 0; // 上一个N页码
		int nextopage = (beginpage + this.opage < this.totalpage) ? beginpage + this.opage : 0; // 下一个N页码

		String _linkstr = "\n<p class=\"links\">\n"; // begin

		// 分页
		_linkstr += "<span class=\"disabled\">分页:"+ this.cpage +"/"+ this.totalpage +"</span>\n";

		// 前滚10页码
		if (preopage > 0)
			_linkstr += "<a href=\"" + this.url ( preopage ) + "\">上"+ this.opage +"页</a>\n";
		else
			_linkstr += "<span class=\"disabled\">上"+ this.opage +"页</span>\n";

		// 主要的数字分页 页码
		for(int i = beginpage; i <= endpage; i++)
		{
			if (this.cpage != i)
				_linkstr += "<a href=\""+ this.url(i) +"\">" + i + "</a>\n";
			else
				_linkstr += "<span class=\"current\">"+i+"</span>\n";
		}

		//后滚10页码
		if (nextopage > 0)
			_linkstr += "<a href=\""+ this.url(nextopage) +"\">下"+ this.opage +"页</a>\n";
		else
			_linkstr += "<span class=\"disabled\">下"+ this.opage +"页</span>\n";

		_linkstr += "</p>\n"; //end div
		return _linkstr;
	}

	/** 中间滑动滚动 */
	private String f3()
	{
		// 计算开始页 结束页
		int beginpage, endpage;
		
		if (this.cpage > Math.ceil( (this.opage) / 2 ))
		{
			beginpage = (int) (this.cpage - Math.floor( (this.opage) / 2 ));
			endpage = (int) (this.cpage + Math.floor( (this.opage) / 2 ));
		}
		else
		{
			beginpage = 1;
			endpage = this.opage;
		}

		// 限制末页
		if (endpage > this.totalpage) endpage = this.totalpage;

		String _linkstr = "\n<p class=\"links\">\n"; // begin

		// 分页
		_linkstr += "<span class=\"disabled\">分页:"+ this.cpage +"/"+ this.totalpage +"</span>\n";

		// 首页
		if (this.cpage > 1)
		{
			_linkstr += "<a href=\""+ this.url(1) +"\">首页</a> \n";
			_linkstr += "<a href=\"" + this.url( this.cpage - 1 ) +"\">上一页</a> \n";
		}
		else
		{
			_linkstr += "<span class=\"disabled\">首页</span>" +" \n";
			_linkstr += "<span class=\"disabled\">上一页</span>" +" \n";
		}

		//main num. Link
		for(int i = beginpage; i <= endpage; i++)
		{
			if (this.cpage != i)
				_linkstr += "<a href=\""+this.url(i) +"\">" + i + "</a> \n";
			else
				_linkstr += "<span class=\"current\">"+ i +"</span> \n";
		}

		//尾页
		if (this.cpage == this.totalpage || this.totalpage == 0)
		{
			_linkstr += "<span class=\"disabled\">下一页</span> \n";
			_linkstr += "<span class=\"disabled\">尾页</span> \n";
		}
		else
		{
			_linkstr += "<a href=\"" + this.url ( this.cpage + 1 ) + "\">下一页</a> \n";
			_linkstr += "<a href=\"" + this.url ( this.totalpage ) + "\">尾页</a> \n";
		}
		_linkstr += "</p>\n"; //end div
		return _linkstr;
	}

	/** wap2.0分页 */
	private String f4()
	{
		if(this.cpage > this.totalpage) this.cpage = 1;
		String out = "<form method=\"post\" action=\""+ this.url( this.cpage ) +"\">";
		out += "<p class=\"links\">";

	    // 上一页
		if (this.cpage > 1) 
			out += "<a href=\""+ this.url(this.cpage - 1) +"\">上页</a>&nbsp;";

		// 下一页
	    if ( this.cpage < this.totalpage ) 
	    	out += "<a href=\""+ this.url(this.cpage + 1) +"\">下页</a>&nbsp;\n";

		out += "&nbsp;&nbsp;<input type=\"text\" name=\"page\" size=\"2\" value=\""+ this.cpage +"\" />";
		out += "<input type=\"submit\" name=\"pagego\" value=\"跳转\" />";
		out += "&nbsp;&nbsp;"+ this.cpage +'/'+ this.totalpage +"页\n";

		out += "</p>";
		out += "</form>";

		return out;
	}

	/** wap1.2分页 */
	private String f5()
	{
		String _linkstr = "\n<p class=\"links\">\n"; //begin

		//下一页 尾页
		if (this.cpage == this.totalpage || this.totalpage == 0)
			_linkstr += "<span class=\"disabled\">下一页</span> \n";
		else
			_linkstr += "<a href=\""+ this.url ( this.cpage + 1 ) + "\">下一页</a> \n";

		_linkstr +=" / ";

		// 首页
		if (this.cpage > 1)
			_linkstr += "<a href=\"" + this.url ( this.cpage - 1 ) + "\">上一页</a>\n";
		else
			_linkstr += "<span class=\"disabled\">上一页</span>\n";

		_linkstr +="<br />";

		// 数字分页
		if(this.totalpage<7)
		{
			for(int i=1;i<this.totalpage+1;i++)
			{
				if(this.cpage == i)
					_linkstr +=  "<span class=\"current\">"+ i+ "</span>\n";
				else
					_linkstr += "<a href=\""+ this.url(i) +"\">"+ i +"</a> \n";
			}
		}
		else if(this.cpage < 4 && this.totalpage>7)
		{
			for(int i=1;i<5;i++)
			{
				if(this.cpage == i)
					_linkstr +=  "<span class=\"current\">i</span>\n";
				else
					_linkstr += "<a href=\""+ this.url(i) +"\">{i}</a>\n";
			}
			_linkstr += " ... ";
			_linkstr += "<a href=\""+ this.url(this.totalpage) +"\">"+ this.totalpage +"</a>\n";
		}
		else if(this.cpage >= 4 && this.totalpage > this.cpage + 4)
		{
			// int beginpage = (int) (this.cpage - Math.ceil ( (this.opage) / 2 ));
			for(int i=this.cpage - 2;i<=this.cpage+1;i++)
			{
				if(this.cpage == i)
					_linkstr +=  "<span class=\"current\">"+ i +"</span>\n";
				else
					_linkstr += "<a href=\""+ this.url(i) +"\">"+i+"</a>\n";
			}
			_linkstr += " ... ";
			_linkstr += "<a href=\""+ this.url(this.totalpage) +"\">"+ this.totalpage +"</a>\n";
		}
		else if(this.totalpage <= this.cpage + 4)
		{
			// int beginpage = (int) (this.cpage - Math.ceil ( (this.opage) / 2 ));
			for(int i=this.totalpage-7;i<=this.totalpage;i++)
			{
				if(this.cpage == i)
					_linkstr +=  "<span class=\"current\">"+ i +"</span>\n";
				else
					_linkstr += "<a href=\""+ this.url(i) +"\">{i}</a> \n";
			}
		}

		_linkstr += "到<input name=\"goPageNo\" format=\"*N\" size=\"2\" value=\"\" maxlength=\"3\" emptyok=\"true\"/>页"+
			"<anchor>"+
			"<go href=\""+ this.url(0) +"\" method=\"post\" sendreferer=\"true\">"+
				"<postfield name=\"page\" value=\"goPageNo\"/>"+
			"</go>跳转"+
			"</anchor>";
		_linkstr += "</p>\n"; //end div
		return _linkstr;
	}
	
	public static void main(String[] args) {
		Link link = new Link(11, 200, "/user/list?key=wx", 15, 5);
		System.out.println(link.show(3));
	}
}