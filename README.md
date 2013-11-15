Training-computer-database-project    
====================================  

#Installation  

##1. Database
Execute scripts **1-schema.sql** and **2-entries.sql** in your local **MySQL** server.  
Schema created: **computer-database-db**  
Tables created: **company, computer**  
User created: `admincdb`   
with password: `qwerty1234`  

##2. IDE  
###2.1. Eclipse  
- Add your project to the current workspace: **File** -> **Import** -> **Existing projects into workspace**    
- Create a new Tomcat 7.0 Server: Follow steps **[HERE](http://www.eclipse.org/webtools/jst/components/ws/M4/tutorials/InstallTomcat.html)**
- In your project properties, select **Project facets**, convert your project to faceted form, and tick **Dynamic Web Module** (3.0) and **Java** (1.7)
- Select **Runtime** tab (in the previous **project facets** menu)  and check the Tomcat 7.0 Server created above as your project runtime  

###2.2. IntelliJ IDEA   
- Add your project to the current workspace: **Import Project**, select **Create project from existing sources**
- Create a new Tomcat 7.0 Server: **Run** -> **Edit Configurations** and point it to your local Tomcat directory (button **Configure...**) 
- Set project structure: In **File** -> **Project Structure**, add an Artifact with default options (Artifact tab)  

##3. Start coding


