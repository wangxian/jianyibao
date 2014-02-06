<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<?php echo SOURCE;?>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main-wrap">
	<div class="path"><span class="path-icon"></span>当前位置：权限管理<span> &gt; </span>权限管理</div>
    <div class="set-wrap">
        <h4 class="main-title">权限管理</h4>
		<div id="set-area-int">
	 		<table width="100%" id="group_list">
			<tr>
				<td width="80" align="right" style="color:red; font-weight: bold;">选择用户组：</td>
				<td>
					<select style="font-size:15px;color:blue;padding:3px;" name="gid" onchange="location.href='<?php echo URL;?>/rights/mgr/gid/' + this.value;" >
					<?php foreach($this->allGroups as $v):?>
					<option value="<?php echo $v['id'];?>" <?php echo Html::selected(getv('gid'),$v['id']);?> ><?php echo $v['name'];?></option>
					<?php $arr_t[$v['id']]=$v['name']; endforeach; ?>
					</select>
				</td>
			</tr>
			</table>
			
			<form method="post" name="form1" action="" id="form1">
			<table width="100%" id="info_list">
				<?php
				$menus = C('','menu');
				foreach($menus as $k1=>$v1):
				if($k1 == 'index') continue;
				?>
				<tr style="text-align:center;" class="tdlist">
					<td align="right" width="120px"><b><?php echo $v1['title'];?>：</b></td>
					<td align="left">
					<?php
					foreach ($v1['actions'] as $k3=>$v3):
					$arr=explode('/',$k3);
					if( !empty($this->crights[ $arr[0] ]) && ($this->crights[ $arr[0] ] == 'all' || in_array($arr[1], $this->crights[ $arr[0] ])) )
						$checked = 'checked="checked"';
					else $checked='';
					?>
					<label><input type="checkbox" name="<?php echo $arr[0];?>[]" value="<?php echo $arr[1];?>" <?php echo $checked;?> /> <?php echo $v3;?></label>&nbsp;&nbsp;
					<?php endforeach;?>
					</td>
				</tr>
				<?php endforeach;?>
				<tr>
					<td width="80" align="right"> </td>
					<td>
						<a href="javascript:CheckAll('selectAll');">全选</a>&nbsp;&nbsp;
						<a href="javascript:CheckAll();">反选</a>&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td width="80" align="right"> </td>
					<td class="button button-position">
						<input type="submit" name="submit" value="保存修改" class="submitBtn" />
					</td>
				</tr>
			</table>
			</form>
<script type="text/javascript">
function CheckAll(value)
{
	var form=document.getElementsByTagName("form")
	for(var i=0;i<form.length;i++){
		for (var j=0;j<form[i].elements.length;j++)
		{
			if(form[i].elements[j].type=="checkbox")
			{
				var e = form[i].elements[j];
				if (value=="selectAll"){ e.checked=1; }
				else{ e.checked=!e.checked; }
			}
		}
	}
}
</script>
		
    	</div>
    </div>
</div>
</body>
</html>
