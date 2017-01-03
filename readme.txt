项目笔记

使用mysql
	创建数据库:create database OA default character set utf8;
	显示创建的数据库的详细信息:show create database OA;
						status
						
声明一个bean
	注解(都可以):
		@Component("beanName")
		@Controller
		@Service
		@Repository
		
配置bean的scope
	@Scope("prototype")
	
Spring与Struts2整合
	1.在web.xml中配置Spring的监听器
	2.加一个jar包
	
Hibernate与Spring整合
	1.管理SessionFactory实例(只需要一个)
	2.声明式事务管理

Spring
	IOC 管理对象...
	AOP 事务管理...
	

设计实体:
	1.有几个实体?
		一般是一组增删改查对应一个实体
	2.实体之间有什么关系?
		一般是页面引用了其他的实体时,就表示了这个实体有关联关系
	3.每个实体中都有什么属性?
		1,主键
		2,关联关系属性.在类图中,关联关系是一条线,有两端,每一端对应一个表达此关联关系的属性,有几个端指向本类,本类中就有几个关联关系属性
		3,一般属性.分析所有有关的页面,找出表单中要填写的或是显示在页面中要显示的信息等
		4,特殊属性.为解决某问题而设计的属性,比如要显示年龄,但不会设计一个int age字段,而是一个Date brithday字段,年龄在显示时是计算出来的
		
表的映射:
	写注释:<!--users属性,本类与User的一对多-->
		格式:? 属性,本类与 ? 的 ?
		?1: 属性名
		?2:关联的类型
		?3:关系
		
	多对一:
		<many-to-one name="" class="" column=""></many-to-one>
	一对多(Set):
		<set name="">
			<key column=""></key>
			<one-to-many class=""/>
		</set>
	多对多(Set):
		<Set name="" table="">
			<key column=""></key>
			<many-to-many class="" column=""/>
		</Set>
JQuery实现全选:
	onClick="$('[name=privilegeIds]').attr('checked',this.checked)"
	
windows7杀死进程
netstat -ano | findstr 8080
tasklist | findstr 4364
taskkill -f -t -im java.exe

论坛分页：
recordList 		本页的数据列表
currentPage 	当前页
pageCount 		总页数
pageSize 		每页显示多少条
recordCount 	总记录数
beginPageIndex 	页码列表的开始索引
endPageIndex 	页码列表的结束索引


实时处理框架（实时刷新等）

文件服务器

myeclipse如何配置web modules

struts2中json的三种使用方式：
	1、将一个属性以json格式返回
		<result type="json">
			<param name="root">属性名</param>
		</result>
	2、将所有属性以json格式返回
		<result type="json"></result>
	3、将一部分属性以json格式返回
		<result type="json">
			<param name="includeProperties">属性1，属性2，...</param>
		</result>