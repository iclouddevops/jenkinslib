package org.devops

"""
# 获取job信息
#curl   -XGET -H "Content-Type: application/json"  -H "Authorization: Basic xxx" "http://xxxx/job/xxxx/job/xxxx/job/xxxx/api/json"

# copy job
#curl -I  -XPOST -H "Content-Type: application/x-www-form-urlencoded"  -H "Authorization: Basic xxx" "http://xxxx/job/filename/job/job_name/createItem?name=xxxx&mode=copy&from=xxxx"


# 禁止job
#curl -I  -XPOST -H "Content-Type: application/json"  -H "Authorization: Basic xxx" "http://xxxx/job/xxxx/job/xxxx/job/xxxx/disable"

# 开启job
#curl -I  -XPOST -H "Content-Type: application/json"  -H "Authorization: Basic xxx" "http://xxxxx/job/xxxx/job/xxxx/job/xxxx/enable"


# 创建文件夹
#curl -I  -XPOST -H "Content-Type: application/x-www-form-urlencoded"  -H "Authorization: Basic xxxx" "http://xxxxx/job/xxxx/createItem?name=xxxx&mode=com.cloudbees.hudson.plugins.folder.Folder" 

#curl -XGET -H "Content-Type: application/json"  -H "Authorization: Basic xxxx" "http://xxxx/job/xxxx/checkJobName?value=xxxx" 
"""


//封装HTTP请求
def HttpReq(reqType,reqUrl,reqBody){
    def jenkinsServer = 'http://192.168.1.200:30080'
    result = httpRequest authentication: 'jenkins-zeyang-admin',
                        httpMode: reqType,
                        consoleLogResponseBody: true,
                        ignoreSslErrors: true, 
                        requestBody: reqBody,
                        url: "${jenkinsServer}/${reqUrl}"
                        //quiet: true
                        
}


//新建项目

def CreateProject(projectName){
    
    withCredentials([usernamePassword(credentialsId: 'jenkins-zeyang-admin', passwordVariable: 'password', usernameVariable: 'username')]) {
       
        sh """
        
           curl -u ${username}:${password} -X GET 'http://192.168.1.200:30080/job/demo-project-manage/config.xml' -o config.xml
           ls -l 
       
           curl -u ${username}:${password} -X POST 'http://192.168.1.200:30080/createItem?name=${projectName}' -H 'Content-Type:text/xml' --data-binary @config.xml
    
    
        """
    }
}


//禁用项目

def Project(projectName,option){

    println(projectName)
    println(option)
    
    options = [ "DisableProject": "disable",
                "EnableProject":"enable",
                "DeleteProject":"doDelete",
                "BuildProject":"build"]
    
    result = HttpReq('POST',"job/${projectName}/${options[option]}",'')
    
}
