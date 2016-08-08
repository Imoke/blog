# Blog Website
The website style is simple and clean ,look simple and easy to use.  I've put it on production for my personal blog site [ myliuwang.com](http://myliuwang.com) .Due to the background security mechanism is not perfect, so now about pages not push to Github.

Blog system is builded by many powerful frameworks and third-party components:
- [ Spring](https://spring.io/) 
- [ Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [ Spring MonogoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
- [ MongoDB](https://www.mongodb.com/)
- [ Pegdown](https://github.com/sirthias/pegdown)
- [Bootstrap](http://www.bootcss.com/)


# Development
- Install the database MongoDB.
- Ensure you have [ Maven](http://maven.apache.org/) environment,we use Maven to manage all dependent jars and build the project.
- [ Tomcat](http://tomcat.apache.org/) I guess you have installed.
- Now all preparations is done. clone the project to your IDE :`https://github.com/Imoke/blog.git`. You can import the source code into Intellij IDE to edit the code.
- At last, modify the `applicationContext.xml`: `<mongo:mongo host="localhost" port="27017"/>` , replace it with your own MongoDB server location.
- If everything is ok , run the project and view http://localhost:8080/ on your browser.


# Todo
- Blog comment module has not been implemented.
- Background security mechanism is not perfect.
- There are many ideas to achieve...

Welcome to contribute.


