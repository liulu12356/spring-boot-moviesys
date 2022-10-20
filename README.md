# 工程简介
**这是对于之前基于spring MVC的电影管理系统采用spring boot脚手架进行搭建的新项目**

**并且将之前的前端项目分离，单独作为一个独立的项目部署，实现前后台分离** 

新增内容：



- 各个页面的分页操作的对应后台方法
- 有关于使用乐观锁，悲观锁抢票的接口测试与实验分析
- 整合spring security权限框架，增加认证和授权功能




#项目简介
# 影院管理系统

## 0. 参考文档

SpringCore [https://docs.spring.io/spring-framework/docs/5.2.22.RELEASE/spring-framework-reference/core.html#spring-core](https://docs.spring.io/spring-framework/docs/5.2.22.RELEASE/spring-framework-reference/core.html#spring-core)

SpringMVC [https://docs.spring.io/spring-framework/docs/5.2.22.RELEASE/spring-framework-reference/web.html#spring-web](https://docs.spring.io/spring-framework/docs/5.2.22.RELEASE/spring-framework-reference/web.html#spring-web)

MyBatis [https://mybatis.org/mybatis-3/zh/getting-started.html](https://mybatis.org/mybatis-3/zh/getting-started.html)

MyBatis-Plus [https://baomidou.com/](https://baomidou.com/)

mvnrepo [https://mvnrepository.com/](https://mvnrepository.com/)

bootstrap [https://getbootstrap.com/docs/5.2/getting-started/introduction/](https://getbootstrap.com/docs/5.2/getting-started/introduction/)

adminLTE [https://adminlte.io/](https://adminlte.io/)

MDN - Javascript [https://developer.mozilla.org/zh-CN/docs/Web/JavaScript](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript)

jQuery [https://api.jquery.com/](https://api.jquery.com/)

## 1. 影院后台管理系统

该模块需要完成电影相关基本信息的维护，目前只需要指定一个admin（角色）和一个user（角色）

### 1.1 建模

类别表（category）

| 列名          | 类型     | 说明             |
| ------------- | -------- | ---------------- |
| id            | int      | 主键             |
| name          | varchar  | 类别名称         |
| comment       | varchar  | 备注             |
| state         | int      | 0 - 无效 1- 有效 |
| created_time  | datetime | 创建日期         |
| modified_time | datetime | 修改日期         |

电影表（movie）

| 列名          | 类型     | 说明                             |
| ------------- | -------- | -------------------------------- |
| id            | int      | 主键                             |
| title         | varchar  | 电影标题                         |
| description   | varchar  | 简要描述                         |
| detail        | text     | 详情                             |
| path          | varchar  | 电影图片存储在服务器上的路径     |
| status        | int      | 0 - 未排期 1-排期中 2 - 排期完毕 |
| state         | int      | 0 - 无效 1- 有效                 |
| created_time  | datetime | 创建日期                         |
| modified_time | datetime | 修改日期                         |

电影类别关系表（movie_category）

| 列名        | 类型 | 说明             |
| ----------- | ---- | ---------------- |
| id          | int  | 主键             |
| movie_id    | int  | 关联movie主键    |
| category_id | int  | 关联category主键 |

Schedule

| 列名     | 类型     | 说明                  |
| -------- | -------- | --------------------- |
| id       | int      | PK                    |
| movie_id | int      | 关联movie的FK         |
| start    | datetime | 档期的起点            |
| end      | datetime | 档期的终点            |
| status   | int      | 0 - 未放票 1-放票完毕 |
|          |          |                       |



RBAC三件套（user、role、menu、role_menu）



电影场次表（show_time）电影播放厅无需设计

票（ticket）

![1666266416273](1666266416273.png)

### 1.2 需求

管理员可以在该模块中，进行如下的基本操作：

- 电影类别的维护
- 电影基本信息的维护（电影的上架）
- 电影档期的安排（电影放映的排期），假定一个时间段只能放一部电影
  - 展示待排期的电影列表（status = 0，1）
  - 用户点击某一个电影，进入排期页面
  - 在排期页面中可以完整对该部电影进行档期的新增、删除、修改
  - 返回待排期的电影列表

- 放票（电影一旦预先排好期以后，就可以放票了）
  - 展示可以放票的电影和档期信息（status = 2）
  - 点击放票按钮（针对特定档期，status = 3）


#### 1.2.1 电影类别

菜单项：电影类别管理

功能：

- 类别新增、更新、删除（需要清理对应类别的电影的依赖关系，如A电影属于动作、科幻2个类别，那么删除科幻类别以后，该电影变成属于动作类别）
- 可以根据类别查询该类别下的所有电影简要信息

#### 1.2.2 电影基本信息维护

菜单项：电影基本信息管理

功能：

- 电影基本信息的新增、更新、删除
- 可以根据关键字查询电影的基本信息（关键字可以根据电影标题、内容描述进行匹配）
- 点击列表上的电影连接，可以查看该电影的详情

> 要求电影类别、电影基本信息及后续所有的主表信息的删除都为逻辑删除（增加一个状态`state`字段进行维护），而不是物理删除（删除DB中的该记录）

图片上传

前端：

如果是普通`form`提交的话，那么有如下2处注意事项：

- method：post，提交数据的大小没有限制（get是有限制的）
- enctype：multipart/form-data
- input：文件域模式 `type="file"`

jQuery

```javascript
let saveMovie = () => {
    // 获取表达数据
    const title = $("#title").val();
    const uploadPic = $("#uploadPic")[0].files[0];
    // 封装一个用来提交的数据，模拟一个传统的form标签提交
    // FormData官方文档： https://developer.mozilla.org/zh-CN/docs/Web/API/FormData/Using_FormData_Objects
    const data = new FormData();
    data.append("title", title);
    data.append("uploadPic", uploadPic);

    // 提交post请求
    $.ajax({
        url: "/movie",
        type: "post",
        data,
        processData: false,  // 不处理数据
        contentType: false,   // 不设置内容类型
        success: (data) => {
            console.log(data);
        }
    });
};
```

后端：

- 开启复合文件处理器开关

  ```xml
  <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
  ```

- 引入`commons-fileupload`的依赖

  ```XML
          <dependency>
              <groupId>commons-fileupload</groupId>
              <artifactId>commons-fileupload</artifactId>
              <version>1.4</version>
          </dependency>
  ```

  

- 准备好参数接受

  ```JAVA
  public String saveMovie(@RequestParam("uploadPic") MultipartFile file, Movie movie, String title) {}
  ```

  当变量名称和前端提交数据时所用的key一样的时候，可以省略`@RequestParam`，SpringMVC会根据变量名与`key`相等的关系，使用`反射机制 + AOP`（拦截请求中的数据）把请求中的数据设置到方法的参数上

- 后续的处理

  - 把文件写入到服务器上的某一个路径

    ```java
    MultipartFile file;
    File dest = new File(path);
    file.transferTo(dest);
    ```

  - 设置到实体类的属性上，在DB中存入path字段

### 1.3 图片上传控件

将图片异步上传，单独制作一个控件进行上传、删除已上传的文件。

安装：

```html
<script src="https://unpkg.com/dropzone@5/dist/min/dropzone.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/dropzone@5/dist/min/dropzone.min.css" type="text/css" />
```

Html页面：

```html
<form action="/file-upload"
      class="dropzone"
      id="my-awesome-dropzone"></form>

<div class="form-group row">
    <label for="status" class="col-sm-2 col-form-label">电影图片:dropzone</label>
    <div class="col-sm-10 dropzone" id="uploadPic">
        <div class="previews"></div> 
    </div>
</div>
```

> previews：展示图片的预览效果

JS中的初始化逻辑

```javascript
Dropzone.options.uploadPic = {
    // Configuration options go here
    // 上传的路径
    url: "/uploadMoviePic", // 当我们选择好文件点击确定之后，dropzone会自动发送一个post请求到该地址
    // enctype = 'multipart/form-data' 后台spring是在@RequestParam部分接收数据的
    
    // 设置文件提交的key
    paramName: "uploadPic",
    maxFiles: 1,
    addRemoveLinks: true,
    // 在初始化dropzone的同时，添加2个事件监听
    init: function () {
        // 成功接收到后台响应
        this.on("success", (file, response) => {
            console.log("A file has been added");
            console.log(response);

            filePath = response.filePath;
            sessionStorage.setItem("filePath", filePath); // 防止界面刷新
        });
        // 点击删除链接
        this.on("removedfile", file => {
            console.log(file);
            // 发送一个新的异步请求删除附件表中的临时文件，（delete from attechment where path=?）
        });
    }
};

// 用来监听modal关闭
$("#modalForm").on("hide.bs.modal", () => {
    console.log("弹窗被隐藏了");
    // 提交一个请求，请求中包含图片的路径
    // 1、select * from movie where path = ? 
    // 有记录，图片有主
    // 没有记录，图片无主，可以删除
    // delete from attechment where path=?
});
// 如果不处理临时文件的话，服务器上的无主的图片（垃圾文件）会越来越多
```

> 如果用户的关闭浏览器标签非常难以严谨的捕捉到，我们还可以编写一个上下文（tomcat）的监听

```java
@WebListener
public class FileTask implements ServletContextListener {

    ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);


    // 容器启动的时候执行
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        pool.scheduleAtFixedRate(
                // 定时执行的任务
                () -> {
                    System.out.println("...");
                }
                , 0, 10, TimeUnit.SECONDS);
    }

    // 容器关闭的时候执行
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        pool.shutdown();
    }
}
```





后台接口：

```java
@PostMapping("/uploadMoviePic")
public Map<String, Object> save(MultipartFile uploadPic){}
```

> 该方法只是单独处理上传的文件

目前各种服务器根据用途主要分为：

- IO密集型
- 运算密集型（浮点运算） + GPU（主要用在AI领域，长项在于高并发的浮点运算）



在企业级项目中，针对用户上传的文件，我们会使用IO性能较好的服务器搭建一个集群，来专门处理这个场景。

### 1.4 Spring项目的异常处理

在Spring和MyBatis框架中，我们如果需要捕获程序发生的异常（框架有可能产生的异常基本都是`RuntimeExpception`的子类型），如果在Controller层不捕获的话，则错误会抛出到前端去。

因此在代码中可以通过在方法上增加`try-catch`

```java
    @GetMapping("/category")
    public List<Category> findAll() {
        List<Category> list;
        try {
            list = categoryService.findAll();
        } catch (Exception e) {
            log.error("类别查询出现异常", e);
            throw new RuntimeException(e);
        }
        return list;
    }
```

如果每一个方法都如此处理的话，代码会出现大量的冗余，不够优雅。

SpringMVC提供了@ExceptionHandler，可以帮助我们捕获该controller中各个方法发生的异常

```java
public class XxxController {
	@ExceptionHandler({SQLException.class})
    public ResponseEntity<ResultVO> handle(SQLException ex) {
        // ...
        log.error(ex);
        // new ResultVO<String>(500, ex.getMessage());
        ResultVO result = new ResultVO(500, "服务器出现内部错误");
        return ResponseEntity.status(500).body(result);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResultVO> handle(IOException ex) {
        // ...
        log.error(ex);
        // new ResultVO<String>(500, ex.getMessage());
        ResultVO result = new ResultVO(500, "服务器出现内部错误");
        return ResponseEntity.status(500).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultVO> handle(Exception ex) {
        // ...
        log.error(ex);
        // new ResultVO<String>(500, ex.getMessage());
        ResultVO result = new ResultVO(500, "服务器出现内部错误");
        return ResponseEntity.status(500).body(result);
    }
}    
```

上述异常处理只能针对特定的Controller，因此Spring还提供了一个基于AOP实现的异常处理通知

```java
@RestControllerAdvice
@Log4j2
public class CommonExceptionHandler {
    @ExceptionHandler({SQLException.class})
    public ResponseEntity<ResultVO> handle(SQLException ex, HttpServletRequest req) {
        // ...
        log.error(ex);
        // log.error("请求中的参数1{},参数2{}", req.getParameter("data"), req.getParameter("flag"));
        // new ResultVO<String>(500, ex.getMessage());
        ResultVO result = new ResultVO(500, "服务器出现内部错误");
        return ResponseEntity.status(500).body(result);
    }
    
    // ...其它异常处理逻辑
    
}
```



## 2. 用户端（C端）

客户表（customer）

订单表（order）



### 2.1 订票首页

需要展示可以订票的电影及响应的档期信息：

- 左边列表对电影进行分别展示
- 右边主窗体，已天数作为分组的依据，展示当前某一部电影的具体可订票档期信息

